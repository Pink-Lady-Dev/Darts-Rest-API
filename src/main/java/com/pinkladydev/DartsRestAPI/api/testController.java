package com.pinkladydev.DartsRestAPI.api;

import com.pinkladydev.DartsRestAPI.config.JwtUtil;
import com.pinkladydev.DartsRestAPI.model.AuthenticationRequest;
import com.pinkladydev.DartsRestAPI.model.AuthenticationResponce;
import com.pinkladydev.DartsRestAPI.service.UsersDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class testController {

    private final AuthenticationManager authenticationManager;

    private final UsersDetailsService usersDetailsService;

    private final JwtUtil jwtTokenUtil;

    public testController(AuthenticationManager authenticationManager, UsersDetailsService userDetailsService, JwtUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.usersDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect Username or Password");
        }

        final UserDetails userDetails = usersDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponce(jwt));
    }

}
