package edu.iu.c322.test3.controllers;

import edu.iu.c322.test3.model.Customer;
import edu.iu.c322.test3.service.IAuthenticationService;
import edu.iu.c322.test3.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
@RestController
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final IAuthenticationService authenticationService;
    private final TokenService tokenService;
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    IAuthenticationService authenticationService,
                                    TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
    }

    // Endpoint to handle user registration
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody Customer customer) {
        try {
            // Attempt to register the customer using the authentication service
            authenticationService.register(customer);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            //catch error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed due to an internal error.");
        }
    }
    //handle user login endpoint
    @PostMapping("/signin")
    public ResponseEntity<String> login(@RequestBody Customer customer) {
        try {
            //Authenticate the customer using username and password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword()));
            //Generate token
            String token = tokenService.generateToken(authentication);
            // Return the token in the response body
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            // Return an unauthorized status


            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: Invalid username or password.");
        }
    }
}
