package org.example.damo.service;


import org.example.damo.common.config.ApplicationConfiguration;
import org.example.damo.dto.base.PaginatedResponse;
import org.example.damo.dto.base.Response;
import org.example.damo.dto.supplier.SupplierDto;
import org.example.damo.dto.supplier.SupplierResponseDto;
import org.example.damo.dto.supplier.UpdateSupplierDto;
import org.example.damo.entity.Supplier;
import org.example.damo.exception.model.DuplicateResourceException;
import org.example.damo.exception.model.ResourceNotFoundException;
import org.example.damo.mapper.SupplierMapper;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private ApplicationConfiguration appConfig;


    public PaginatedResponse listSuppliersPaginated(Pageable pageable) {
        Page<Supplier> suppliers = supplierRepository.findAll(pageable);
        Page<SupplierResponseDto> supplierPageDto = suppliers.map(supplier -> supplierMapper.toDto(supplier));

        return PaginatedResponse.from(supplierPageDto , appConfig.getPagination().getUrlByResource("supplier"));
    }


    public ResponseEntity<Response> getSupplier(){
        List<Supplier> suppliers = supplierRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200","success" , "successfully retrieve supplier" , supplierMapper.toDto(suppliers)));
    }

    public ResponseEntity<Response> addSupplier(SupplierDto supplierDto) {
        if (supplierRepository.existsByName(supplierDto.getName())) {
            throw new DuplicateResourceException("supplier already exists with name : " + supplierDto.getName());
        }

        Supplier supplier = supplierMapper.toEntity(supplierDto);

        supplierRepository.save(supplier);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200","success", "successfully added supplier"));
    }

    public ResponseEntity<Response> updateSupplierById(Long id , UpdateSupplierDto dto) {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("supplier not found with id: " + id));


        supplierMapper.updateSupplierFromDto(existingSupplier , dto);
        supplierRepository.save(existingSupplier);

        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200","success", "successfully updated supplier"));
    }

    public ResponseEntity<Response> deleteSupplierById(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new ResourceNotFoundException("supplier not found with id: " + id);
        }

        supplierRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200","success", "successfully deleted supplier"));
    }
}
