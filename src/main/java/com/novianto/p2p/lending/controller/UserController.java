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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Top up saldo user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance topped up successfully",
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
}
