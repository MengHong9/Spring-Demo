package org.example.damo.controller;


import org.example.damo.dto.supplier.SupplierDto;
import org.example.damo.dto.supplier.UpdateSupplierDto;
import org.example.damo.exception.ResourceNotFoundException;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;


    @GetMapping
    public ResponseEntity<BaseResponseWithAdditionalDateModel> getSuppliers() {
        return supplierService.getSupplier();
    }

    @PostMapping
    public ResponseEntity<BaseResponeModel> createSupplier(@RequestBody SupplierDto supplierDto) {
        return supplierService.addSupplier(supplierDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<BaseResponeModel> updateSupplier(@PathVariable Long id, @RequestBody UpdateSupplierDto payload) {
        return supplierService.updateSupplierById(id , payload);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<BaseResponeModel> deleteSupplier(@PathVariable Long id) {
        return supplierService.deleteSupplierById(id);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponeModel> handleResourceNotFound(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponeModel("fail", e.getMessage()));
    }

}
