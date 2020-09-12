package com.pinkladydev.darts.web;

import com.pinkladydev.darts.authentication.JwtUtil;
import com.pinkladydev.darts.user.UserService;
import com.pinkladydev.darts.web.models.AuthenticationRequest;
import com.pinkladydev.darts.web.models.AuthenticationResponce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static com.pinkladydev.darts.authentication.exceptions.UserException.InvalidUserCredentials;

@RestController
@RequestMapping("")
@EnableWebMvc
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtUtil jwtTokenUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, UserService userDetailsService, JwtUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw InvalidUserCredentials(authenticationRequest.getUsername());
        }


        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponce(jwt, userService.getUser(authenticationRequest.getUsername())));
    }

}
