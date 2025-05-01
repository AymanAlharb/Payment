package com.example.day49paymentanddeployment.Service;

import com.example.day49paymentanddeployment.Api.ApiException;
import com.example.day49paymentanddeployment.Model.Product;
import com.example.day49paymentanddeployment.Model.User;
import com.example.day49paymentanddeployment.Repository.ProductRepository;
import com.example.day49paymentanddeployment.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void updateProduct(Integer productId, Product product) {
        Product tempProductObject = productRepository.findProductById(productId);
        if (tempProductObject == null) throw new ApiException("Product not found.");
        tempProductObject.setName(product.getName());
        tempProductObject.setPrice(product.getPrice());
        productRepository.save(tempProductObject);
    }

    public void deleteProduct(Integer productId) {
        Product product = productRepository.findProductById(productId);
        if (product == null) throw new ApiException("Product not found.");
        productRepository.delete(product);
    }

    public void buyProduct(Integer userId, Integer productId) {
        User user = userRepository.findUserById(userId);
        if (user == null) throw new ApiException("User not found.");
        Product product = productRepository.findProductById(productId);
        if (product == null) throw new ApiException("Product not found.");
        if(product.getPrice() > user.getBalance()) throw new ApiException("Insufficient balance.");
        product.setUser(user);
        productRepository.save(product);
    }
}
