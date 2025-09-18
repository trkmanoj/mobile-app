package com.mnj.mobile.service.impl;

import com.mnj.mobile.dto.PaymentRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PaymentService {
    public PaymentService( @Value("${stripe.secret-key}") String secretKey) {
        com.stripe.Stripe.apiKey = secretKey; // Initialize Stripe API key
    }

    public HashMap<String, Object> createPaymentIntent(PaymentRequest request) throws StripeException {
        try {
            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(request.getAmount()) // long, in cents
                            .setCurrency(request.getCurrency()) // e.g. "usd"
                            .build();

            PaymentIntent intent = PaymentIntent.create(params);

            HashMap<String, Object> map = new HashMap<>();

            map.put("clientSecret", intent.getClientSecret());
            return map;

        } catch (StripeException e) {
            e.printStackTrace();
            throw e;
        }

    }
}
