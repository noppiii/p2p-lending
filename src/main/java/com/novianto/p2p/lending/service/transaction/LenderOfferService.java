package com.novianto.p2p.lending.service.transaction;

import com.novianto.p2p.lending.model.LendingOffer;
import com.novianto.p2p.lending.model.User;
import com.novianto.p2p.lending.payload.request.transaction.LenderOfferRequest;

import java.util.List;

public interface LenderOfferService {

    LendingOffer createLenderOffer(User lender, LenderOfferRequest offerRequest);

    List<LendingOffer> getActiveLenderOffers();
}
