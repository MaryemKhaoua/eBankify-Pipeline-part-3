package com.example.ebankify_security.service;

import com.example.ebankify_security.domain.entities.Invoice;
import com.example.ebankify_security.domain.entities.User;
import com.example.ebankify_security.domain.requests.InvoiceRequest;
import com.example.ebankify_security.dto.InvoiceDto;
import com.example.ebankify_security.mapper.InvoiceMapper;
import com.example.ebankify_security.mapper.UserMapper;
import com.example.ebankify_security.repository.InvoiceRepository;
import com.example.ebankify_security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public InvoiceDto saveInvoice(InvoiceRequest invoiceRequest) {
        Invoice invoice = invoiceMapper.toEntity(invoiceRequest);

        User user = userRepository.findById(invoiceRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        invoice.setUser(user);

        Invoice savedInvoice = invoiceRepository.save(invoice);

        InvoiceDto invoiceDto = invoiceMapper.toDto(savedInvoice);

        invoiceDto.setUser(userMapper.toDto(user));

        return invoiceDto;
    }

    public InvoiceDto getInvoiceById(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        InvoiceDto invoiceDto = invoiceMapper.toDto(invoice);

        invoiceDto.setUser(userMapper.toDto(invoice.getUser()));

        return invoiceDto;
    }

    public void deleteInvoiceById(Long invoiceId) {
        invoiceRepository.deleteById(invoiceId);
    }

    public List<InvoiceDto> getInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoiceMapper.toDtoList(invoices);
    }
}
