package org.example.damo.controller;


import jakarta.validation.Valid;
import org.example.damo.dto.base.Response;
import org.example.damo.dto.supplier.SupplierDto;
import org.example.damo.dto.supplier.UpdateSupplierDto;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;


    @GetMapping
    public ResponseEntity<Response> getSuppliers() {
        return supplierService.getSupplier();
    }

    @PostMapping
    public ResponseEntity<Response> createSupplier(@Valid @RequestBody SupplierDto supplierDto) {
        return supplierService.addSupplier(supplierDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response> updateSupplier( @PathVariable Long id, @Valid @RequestBody UpdateSupplierDto payload) {
        return supplierService.updateSupplierById(id , payload);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteSupplier(@PathVariable Long id) {
        return supplierService.deleteSupplierById(id);
    }

}
