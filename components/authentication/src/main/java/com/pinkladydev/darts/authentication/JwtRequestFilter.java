package com.pinkladydev.darts.authentication;

import com.pinkladydev.darts.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = httpServletRequest.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorization != null && authorization.startsWith("Bearer ")) {
            jwt = authorization.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        // This avoids the jwt for new users and authentication while still ensuring that there is some form of security...
        // TODO - set actual private key
        if ( httpServletRequest.getRequestURI().equals("/user")
                && httpServletRequest.getMethod().equals("POST")
                && !httpServletRequest.getHeader("Secret").equals(System.getenv("SECRET_KEY"))) {

            throw InvalidKeyException.invalidKeyWhenCreatingUser();
        }
        // TODO - set actual private key
        if ( httpServletRequest.getRequestURI().equals("/authenticate")
                && httpServletRequest.getMethod().equals("POST")
                && !httpServletRequest.getHeader("Secret").equals(System.getenv("SECRET_KEY"))) {

            throw InvalidKeyException.invalidKeyWhenAuthenticatingUser();
        }

        // TODO when editing users -- we need to use something like this to make sure that users are only
        //      able to edit their own profiles
        //      if (  httpServletRequest.getRequestURI() contains some userId)
        //          allow access
        //          System.out.println("Method: " + httpServletRequest.getMethod());=

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
