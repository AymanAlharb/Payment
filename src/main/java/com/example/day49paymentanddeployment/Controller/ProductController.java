package com.example.day49paymentanddeployment.Controller;

import com.example.day49paymentanddeployment.Api.ApiResponse;
import com.example.day49paymentanddeployment.Model.Product;
import com.example.day49paymentanddeployment.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/get-all-products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.status(200).body(productService.getAllProducts());
    }

    @PostMapping("/add-product")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody @Valid Product product) {
        productService.addProduct(product);
        return ResponseEntity.status(200).body(new ApiResponse("Product added successfully."));
    }

    @PutMapping("/update-product/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Integer productId, @Valid @RequestBody Product product) {
        productService.updateProduct(productId, product);
        return ResponseEntity.status(200).body(new ApiResponse("Product updated successfully."));
    }

    @DeleteMapping("/delete-product/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(200).body(new ApiResponse("Product deleted successfully."));
    }
}
