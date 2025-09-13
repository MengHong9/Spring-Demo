package org.example.damo.service;


import org.example.damo.dto.supplier.SupplierDto;
import org.example.damo.dto.supplier.UpdateSupplierDto;
import org.example.damo.entity.Supplier;
import org.example.damo.exception.model.ResourceNotFoundException;
import org.example.damo.mapper.SupplierMapper;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper supplierMapper;


    public ResponseEntity<BaseResponseWithAdditionalDateModel> getSupplier(){
        List<Supplier> suppliers = supplierRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponseWithAdditionalDateModel("success" , "successfully retrieve supplier" , supplierMapper.toDto(suppliers)));
    }

    public ResponseEntity<BaseResponeModel> addSupplier(SupplierDto supplierDto) {
        if (supplierRepository.existsByName(supplierDto.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponeModel("fail" , "supplierName already exists with name : "+supplierDto.getName()));
        }

        Supplier supplier = supplierMapper.toEntity(supplierDto);

        supplierRepository.save(supplier);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponeModel("success", "successfully added supplier"));
    }

    public ResponseEntity<BaseResponeModel> updateSupplierById(Long id , UpdateSupplierDto dto) {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("supplier not found with id : "+id));


        supplierMapper.updateSupplierFromDto(existingSupplier , dto);
        supplierRepository.save(existingSupplier);

        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponeModel("success", "successfully updated supplier"));
    }

    public ResponseEntity<BaseResponeModel> deleteSupplierById(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new ResourceNotFoundException("supplier not found with id : "+id);
        }

        supplierRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponeModel("success", "successfully deleted supplier"));
    }
}
