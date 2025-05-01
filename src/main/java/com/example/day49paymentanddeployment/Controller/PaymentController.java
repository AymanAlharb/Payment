package com.example.day49paymentanddeployment.Controller;

import com.example.day49paymentanddeployment.Model.PaymentRequest;
import com.example.day49paymentanddeployment.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/card")
    public ResponseEntity<ResponseEntity<String>> processPayment(@RequestBody PaymentRequest paymentRequest){
        return ResponseEntity.status(200).body(paymentService.processPayment(paymentRequest));
    }

    @GetMapping("/get-status/{id}")
    public ResponseEntity<String> getPaymentStatus(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPaymentStatus(id));
    }
}


