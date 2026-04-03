package com.bohemian.intellect.controller;

import com.bohemian.intellect.dto.LoginRequest;
import com.bohemian.intellect.dto.RegistrationRequest;
//import com.bohemian.intellect.service.AIService;
import com.bohemian.intellect.service.EmailService;
import com.bohemian.intellect.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class AuthController {
    private final UserService userService;
    private final EmailService emailService;
//    private final AIService aiService;

//    public AuthController(UserService userService, EmailService emailService, AIService aiService) {
//        this.userService = userService;
//        this.emailService = emailService;
//        this.aiService = aiService;
//    }


    public AuthController(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping({"/sign-up"})
    public ResponseEntity<?> saveUser(@Valid @RequestBody RegistrationRequest request) {
        return userService.saveUser(request);
    }

    @PostMapping({"/login"})
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return userService.generateToken(request);
    }

    @GetMapping({"/generate-otp"})
    public ResponseEntity<?> generateOpt() {
        return emailService.generateOtp();
    }

    @PostMapping({"/validate-otp/{otp}"})
    public ResponseEntity<?> validateOtp(@PathVariable @NotBlank(message = "OTP is required") String otp) {
        return emailService.validateOtp(otp);
    }

//    @GetMapping({"generate"})
//    public ResponseEntity<?> test(@RequestParam String topic, @RequestParam String size, @RequestParam String level) throws JsonProcessingException {
//        return new ResponseEntity<>(aiService.generateAnswers(Map.of("topic", topic, "size", size, "level", level)), HttpStatus.OK);
//    }
}
