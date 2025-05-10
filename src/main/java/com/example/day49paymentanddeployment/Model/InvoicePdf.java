package com.example.day49paymentanddeployment.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoicePdf {
    private String status;
    private Double totalPrice;
    private LocalDateTime dateTime;
    private String userFullName;
    private String userPhoneNumber;
    private String courseName;
}
