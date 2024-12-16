package com.novianto.p2p.lending.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.novianto.p2p.lending.model.Loan;
import com.novianto.p2p.lending.model.User;
import com.novianto.p2p.lending.security.CustomUserDetails;
import com.novianto.p2p.lending.service.auth.UserService;
import com.novianto.p2p.lending.service.transaction.LenderOfferService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LenderOfferService lenderOfferService;
    private final UserService userService;

    @Autowired
    public LoanController(LenderOfferService lenderOfferService, UserService userService) {
        this.lenderOfferService = lenderOfferService;
        this.userService = userService;
    }

    @Operation(summary = "Terima tawaran pemberi pinjaman untuk membuat pinjaman")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pinjaman berhasil dibuat",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "ID penawaran tidak valid atau penawaran tidak aktif",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content)
    })
    @PostMapping("/accept-offer/{offerId}")
    @PreAuthorize("hasRole('ROLE_BORROWER')")
    public ResponseEntity<?> acceptLenderOffer(@PathVariable Long offerId, Authentication auhthentication) {
        CustomUserDetails userDetails = (CustomUserDetails) auhthentication.getPrincipal();
          User borrower = userService.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Pengguna tidak ditemukan"));

        try {
            Loan loan = lenderOfferService.acceptLenderOffer(borrower, offerId);
            return ResponseEntity.ok("Pinjaman berhasil dibuat dengan ID: " + loan.getId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
