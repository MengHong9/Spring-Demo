package org.example.damo.mapper;


import org.example.damo.dto.supplier.SupplierDto;
import org.example.damo.dto.supplier.SupplierResponseDto;
import org.example.damo.dto.supplier.UpdateSupplierDto;
import org.example.damo.entity.Supplier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SupplierMapper {

    public SupplierResponseDto toDto(Supplier entity) {
        SupplierResponseDto dto = new SupplierResponseDto();


        dto.setId(entity.getId());
        dto.setSupplierName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setRating(entity.getRating());
        dto.setPhone(entity.getPhone());
        dto.setEmail(entity.getEmail());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public Supplier toEntity(SupplierDto dto) {
        Supplier entity = new Supplier();


        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setRating(dto.getRating());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());

        return entity;
    }

    public List<SupplierResponseDto> toDto(List<Supplier> entities) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }
        return entities.stream().map(supplier -> this.toDto(supplier)).collect(Collectors.toList());
    }

    public void updateSupplierFromDto(Supplier entity , UpdateSupplierDto dto) {
        if (entity == null || dto == null) {
            return;
        }
        entity.setName(dto.getName());
        entity.setAddress(dto.getAddress());
        entity.setRating(dto.getRating());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
    }

}
