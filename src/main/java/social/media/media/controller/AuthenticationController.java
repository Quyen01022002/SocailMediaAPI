package social.media.media.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import social.media.media.model.reponse.ApiResponse;
import social.media.media.model.reponse.AuthenticationResponse;
import social.media.media.model.request.AuthenticationRequest;
import social.media.media.model.request.RegisterRequest;
import social.media.media.model.request.ResetPasswordRequest;
import social.media.media.service.AuthenticationService;

@Tag(name = "Authentication", description = "Authentication APIs")
@RestController
@RequestMapping("media")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequest request) throws MessagingException {
        return ResponseEntity.ok(service.resetPassword(request));
    }

}