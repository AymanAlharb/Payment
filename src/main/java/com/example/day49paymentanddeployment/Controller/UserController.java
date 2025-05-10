package com.example.day49paymentanddeployment.Controller;

import com.example.day49paymentanddeployment.Api.ApiResponse;
import com.example.day49paymentanddeployment.Model.User;
import com.example.day49paymentanddeployment.Service.InvoiceService;
import com.example.day49paymentanddeployment.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;
    private final InvoiceService invoiceService;

    @GetMapping("/get-all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }

    @PostMapping("/add-user")
    public ResponseEntity<ApiResponse> addUser(@RequestBody @Valid User user) {
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("User added successfully."));
    }

    @PutMapping("/update-user/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Integer userId, @Valid @RequestBody User user) {
        userService.updateUser(userId, user);
        return ResponseEntity.status(200).body(new ApiResponse("User updated successfully."));
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully."));
    }
    @GetMapping("/get-invoice-as-pdf/{invoiceId}")
    public ResponseEntity<byte[]> getInvoiceAsPdf(@PathVariable Integer invoiceId) {
        byte[] pdfBytes = invoiceService.getContractAsPdf(invoiceId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=contract-" + invoiceId + ".pdf") // Forces download
                .contentType(MediaType.APPLICATION_PDF) // Sets MIME type
                .body(pdfBytes);
    }
}
