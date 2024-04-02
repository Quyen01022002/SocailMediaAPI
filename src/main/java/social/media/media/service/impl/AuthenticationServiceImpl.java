package social.media.media.service.impl;


import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import social.media.media.model.entity.User;
import social.media.media.model.enums.StatusEnum;
import social.media.media.model.reponse.ApiResponse;
import social.media.media.model.reponse.AuthenticationResponse;
import social.media.media.model.request.AuthenticationRequest;
import social.media.media.model.request.RegisterRequest;
import social.media.media.model.request.ResetPasswordRequest;
import social.media.media.repository.UserRepository;
import social.media.media.security.JwtService;
import social.media.media.service.AuthenticationService;
import social.media.media.util.EmailService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(request.getRoleEnum())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .email(user.getEmail())
                .id(user.getId())
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user= repository.findByEmail(request.getEmail())
                .orElseThrow();
            var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .id(user.getId())
                .lastName(user.getLastName())
                .roleEnum(user.getRole())
                .firstName(user.getFirstName())
                .Avatar(user.getProfilePicture())
                .email(user.getEmail())
                .build();
    }

    @Override
    public ApiResponse resetPassword(ResetPasswordRequest request) throws MessagingException {
        User user = repository.findByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng"));
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(user);
        emailService.WarningMail(user.getEmail());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatus(StatusEnum.SUCCESS);
        apiResponse.setPayload("Thay đổi mật khẩu thành công");
        return apiResponse;
    }

}
