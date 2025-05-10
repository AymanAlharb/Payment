package com.example.day49paymentanddeployment.Repository;

import com.example.day49paymentanddeployment.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Invoice findInvoiceById(Integer invoiceId);
}
