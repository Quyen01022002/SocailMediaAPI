package social.media.media.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social.media.media.model.reponse.ApiResponse;
import social.media.media.model.reponse.AuthenticationResponse;
import social.media.media.model.request.AuthenticationRequest;
import social.media.media.model.request.RegisterRequest;
import social.media.media.model.request.ResetPasswordRequest;
import social.media.media.service.AuthenticationService;
import social.media.media.util.EmailService;

import java.text.DecimalFormat;
import java.util.Random;

@Tag(name = "Authentication", description = "Authentication APIs")
@RestController
@RequestMapping("media")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;
    private final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }
    @GetMapping("/sendEmail")
    public String sendEmail(@RequestParam("email") String Email) throws MessagingException {

        Random random = new Random();

        int randomNumber = random.nextInt(900000) + 100000;
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        String formattedNumber = decimalFormat.format(randomNumber);
        emailService.sendConfirmationEmail(Email, formattedNumber);
        return formattedNumber;


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