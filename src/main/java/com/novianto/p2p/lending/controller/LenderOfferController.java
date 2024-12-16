package com.novianto.p2p.lending.controller;

import com.novianto.p2p.lending.model.LendingOffer;
import com.novianto.p2p.lending.model.User;
import com.novianto.p2p.lending.payload.request.transaction.LenderOfferRequest;
import com.novianto.p2p.lending.payload.response.transaction.LenderOfferResponse;
import com.novianto.p2p.lending.security.CustomUserDetails;
import com.novianto.p2p.lending.service.auth.UserService;
import com.novianto.p2p.lending.service.transaction.LenderOfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lender/offer")
public class LenderOfferController {

    @Autowired
    private UserService userService;

    @Autowired
    private LenderOfferService lenderOfferService;

    @Operation(summary = "Buat penawaran pemberi pinjaman baru")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Penawaran pemberi pinjaman berhasil dibuat",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LenderOfferResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized access",
                    content = @Content)
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_LENDER')")
    public ResponseEntity<?> createLenderOffer(@RequestBody LenderOfferRequest offerRequest,
                                               Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User lender = userService.findByNickname(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        LendingOffer offer = lenderOfferService.createLenderOffer(lender, offerRequest);
        return ResponseEntity.ok(convertToResponse(offer));
    }

    private LenderOfferResponse convertToResponse(LendingOffer offer) {

        LenderOfferResponse response = LenderOfferResponse.builder()
                .id(offer.getId())
                .amount(offer.getAmount())
                .interestRate(offer.getInterestRate())
                .termInMonths(offer.getTermInMonths())
                .status(offer.getStatus())
                .createdAt(offer.getCreatedAt())
                .lenderId(offer.getLender().getId())
                .lenderNickname(offer.getLender().getNickname())
                .build();

        return response;
    }
}
