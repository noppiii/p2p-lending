package com.novianto.p2p.lending.controller;

import com.novianto.p2p.lending.model.User;
import com.novianto.p2p.lending.payload.request.transaction.BalanceRequest;
import com.novianto.p2p.lending.security.CustomUserDetails;
import com.novianto.p2p.lending.service.auth.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Top up saldo user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Top up saldo berjasil dilakukan",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content)
    })
    @PostMapping("/balance/top-up")
    public ResponseEntity<?> topUpBalance(@RequestBody BalanceRequest balanceRequest, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));
        userService.updateUserBalance(user, balanceRequest.getAmount());
        return ResponseEntity.ok("Top up berhasil sebesar:" + balanceRequest.getAmount());
    }

    @Operation(summary = "Get saldo user saat ini")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Current balance retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content)
    })
    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userService.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        return ResponseEntity.ok("Saldo Anda saat ini: " + user.getBalance());
    }
}
