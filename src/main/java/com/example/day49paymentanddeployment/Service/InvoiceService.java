package com.example.day49paymentanddeployment.Service;

import java.io.ByteArrayOutputStream;

import com.example.day49paymentanddeployment.Api.ApiException;
import com.example.day49paymentanddeployment.Model.Invoice;
import com.example.day49paymentanddeployment.Repository.InvoiceRepository;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    public byte[] getContractAsPdf(Integer invoiceId) {
        Invoice invoice = invoiceRepository.findInvoiceById(invoiceId);
        if (invoice == null) throw new ApiException("Contract not found.");
        //***Check contract status.
        return createPlainTextPdf(invoice);

    }
    // This method was taking from Bealdung and customized to the contract.
    private byte[] createPlainTextPdf(Invoice invoice) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();

            // Title (Plain, Centered)
            Paragraph title = new Paragraph("Contract");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE); // Blank line
            // Format the renters
            StringBuilder renters = new StringBuilder("");
//            for (Renter renter : invoice.getRenters()) {
//                renters.append("Name: " + renter.getName() + "\nEmail: " + renter.getEmail() + "\n---------\n");
//            }
            // Format the pdf
            document.add(new Paragraph("---------------------------------------------------------------------"));
            document.add(new Paragraph("Contract ID: " + invoice.getStatus()));
            // document.add(new Paragraph("Renters:\n" + renters));
            document.add(new Paragraph("---------------------------------------------------------------------"));


            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}
