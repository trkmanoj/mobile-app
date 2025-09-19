package com.mnj.mobile.controller;

import com.mnj.mobile.dto.PaymentRequest;
import com.mnj.mobile.service.impl.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/api/v1/payment")
public class PaymentController {


    private PaymentService paymentService;

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public HashMap<String, Object> createPayment(@RequestBody PaymentRequest request) throws Exception {
        return paymentService.createPaymentIntent(request);
    }


    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload,
                                                @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            // Invalid signature
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Webhook signature verification failed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid payload");
        }

        // Handle the event
        switch (event.getType()) {
            case "payment_intent.succeeded":
                PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer()
                        .getObject()
                        .orElseThrow(() -> new RuntimeException("Unable to deserialize PaymentIntent"));
                handlePaymentSucceeded(paymentIntent);
                break;

            case "payment_intent.payment_failed":
                PaymentIntent failedIntent = (PaymentIntent) event.getDataObjectDeserializer()
                        .getObject()
                        .orElseThrow(() -> new RuntimeException("Unable to deserialize PaymentIntent"));
                handlePaymentFailed(failedIntent);
                break;

            default:
                // Unhandled event type
                System.out.println("Received unknown event type: " + event.getType());
        }

        return ResponseEntity.ok("Received");
    }

    private void handlePaymentSucceeded(PaymentIntent paymentIntent) {
        String paymentId = paymentIntent.getId();
        long amountReceived = paymentIntent.getAmountReceived();

        // TODO: Update your order/payment status in the database
        System.out.println("Payment succeeded: " + paymentId + " amount: " + amountReceived);
    }

    private void handlePaymentFailed(PaymentIntent paymentIntent) {
        String paymentId = paymentIntent.getId();
        System.out.println("Payment failed: " + paymentId);
        // TODO: Update order/payment status as failed
    }
}
