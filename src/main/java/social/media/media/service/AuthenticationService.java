package social.media.media.service;


import jakarta.mail.MessagingException;
import social.media.media.model.reponse.ApiResponse;
import social.media.media.model.reponse.AuthenticationResponse;
import social.media.media.model.request.AuthenticationRequest;
import social.media.media.model.request.RegisterRequest;
import social.media.media.model.request.ResetPasswordRequest;

public interface AuthenticationService {
    public AuthenticationResponse register(RegisterRequest request);
    public AuthenticationResponse authenticate(AuthenticationRequest request);
    ApiResponse resetPassword(ResetPasswordRequest request) throws MessagingException;
}
