package com.example.ebankify_security.controller;

import com.example.ebankify_security.dto.InvoiceDto;
import com.example.ebankify_security.domain.requests.InvoiceRequest;
import com.example.ebankify_security.vm.InvoiceVM;
import com.example.ebankify_security.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "http://localhost:8080")
@AllArgsConstructor
public class InvoiceController {
    private InvoiceService invoiceService;
    @PostMapping("/save")
    public ResponseEntity<InvoiceVM> saveInvoice(@Valid @RequestBody InvoiceRequest invoiceRequest) {
        InvoiceDto invoiceDto = invoiceService.saveInvoice(invoiceRequest);
        InvoiceVM invoiceVM = InvoiceVM.builder()
                .invoiceDto(invoiceDto)
                .message("Invoice saved successfuly")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(invoiceVM);
    }
    @GetMapping("/{id}")
    public ResponseEntity<InvoiceVM> getInvoiceById(@PathVariable Long id) {
        InvoiceDto invoiceDto = invoiceService.getInvoiceById(id);
        InvoiceVM invoiceVM = InvoiceVM.builder()
                .invoiceDto(invoiceDto)
                .message("Invoice retrieved successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(invoiceVM);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InvoiceVM> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoiceById(id);
        InvoiceVM invoiceVM = InvoiceVM.builder()
                .message("Invoice deleted successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(invoiceVM);
    }
    @GetMapping("/all")
    public ResponseEntity<InvoiceVM> getAllInvoices() {
        List<InvoiceDto> invoiceDtos = invoiceService.getInvoices();
        InvoiceVM invoiceVM = InvoiceVM.builder()
                .invoices(invoiceDtos)
                .message("All invoices retrieved successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(invoiceVM);
    }

}