package com.example.day49paymentanddeployment.Service;

import com.example.day49paymentanddeployment.Model.Invoice;
import com.example.day49paymentanddeployment.Model.PaymentRequest;
import com.example.day49paymentanddeployment.Model.Product;
import com.example.day49paymentanddeployment.Model.User;
import com.example.day49paymentanddeployment.Repository.InvoiceRepository;
import com.example.day49paymentanddeployment.Repository.ProductRepository;
import com.example.day49paymentanddeployment.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
    @Value("${moyasar.api.key}")
    private String apiKey;
    private static final String MOYASAR_API_URL = "https://api.moyasar.com/v1/payments/";
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    public ResponseEntity<String> processPayment(PaymentRequest paymentRequest, Integer userId, Integer productId) {
        String url = "https://api.moyasar.com/v1/payments";
        String callbackUrl = "https://your-server.com/api/payments/callback";
        String requestBody = String.format(
                "source[type]=creditcard&" +
                        "source[name]=%s&" +
                        "source[number]=%s&" +
                        "source[month]=%d&" +
                        "source[year]=%d&" +
                        "source[cvc]=%d&" +
                        "amount=%d&" +
                        "currency=%s&" +
                        "description=%s&" +
                        "callback_url=%s",
                paymentRequest.getName(),
                paymentRequest.getNumber(),
                paymentRequest.getMonth(),
                paymentRequest.getYear(),
                paymentRequest.getCvc(),
                (int) (paymentRequest.getAmount() * 100),
                paymentRequest.getCurrency(),
                paymentRequest.getDescription(),
                paymentRequest.getCallbackUrl()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey, "");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // Create the invoice.
        createInvoice(userRepository.findUserById(userId) ,productRepository.findProductById(productId), paymentRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    public void createInvoice(User user, Product product, PaymentRequest paymentRequest) {
        Invoice invoice = new Invoice(null, "paid", paymentRequest.getAmount(), LocalDateTime.now(), product, user);
        invoiceRepository.save(invoice);
    }



    public String getPaymentStatus(String paymentId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey, "");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(MOYASAR_API_URL + paymentId, HttpMethod.GET, entity, String.class);

        return response.getBody();


    }
}
