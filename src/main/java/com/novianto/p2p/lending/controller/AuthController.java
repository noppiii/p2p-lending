package com.novianto.p2p.lending.controller;

import com.novianto.p2p.lending.payload.request.LoginRequest;
import com.novianto.p2p.lending.payload.request.auth.RegisterRequest;
import com.novianto.p2p.lending.payload.response.auth.AuthResponse;
import com.novianto.p2p.lending.security.CustomUserDetails;
import com.novianto.p2p.lending.security.JwtUtils;
import com.novianto.p2p.lending.service.auth.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Operation(summary = "Register user baru")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Berhasil melakukan registrasi",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Input data tidak valid",
                    content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody RegisterRequest registerRequest) {
        try {
            userService.registerUser(registerRequest);
            return ResponseEntity.ok("berhasil melakukan registrasi");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Authenticate user dengan mendapatkan JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getNickname(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken((CustomUserDetails) authentication.getPrincipal());
            return ResponseEntity.ok(new AuthResponse(jwt));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid nickname or password.");
        }
    }
}
