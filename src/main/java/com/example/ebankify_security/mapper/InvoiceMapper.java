package com.example.ebankify_security.mapper;

import com.example.ebankify_security.dto.InvoiceDto;
import com.example.ebankify_security.domain.entities.Invoice;
import com.example.ebankify_security.domain.requests.InvoiceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    InvoiceMapper Instance = Mappers.getMapper(InvoiceMapper.class);
    InvoiceDto toDto(Invoice invoice);
    Invoice toEntity(InvoiceDto invoiceDto);
    Invoice toEntity(InvoiceRequest invoiceRequest);

    List<InvoiceDto> toDtoList(List<Invoice> invoices);
}
