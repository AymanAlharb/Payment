package com.example.day49paymentanddeployment.Repository;

import com.example.day49paymentanddeployment.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findProductById(Integer productId);
}
