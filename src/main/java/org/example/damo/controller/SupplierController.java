package org.example.damo.controller;


import jakarta.validation.Valid;
import org.example.damo.dto.base.PaginatedResponse;
import org.example.damo.dto.base.Response;
import org.example.damo.dto.supplier.SupplierDto;
import org.example.damo.dto.supplier.SupplierResponseDto;
import org.example.damo.dto.supplier.UpdateSupplierDto;
import org.example.damo.model.BaseResponeModel;
import org.example.damo.model.BaseResponseWithAdditionalDateModel;
import org.example.damo.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;


    @GetMapping("/paginated")
    public ResponseEntity<Response> listSuppliersPaginated(@PageableDefault(size = 10 , page = 0) Pageable pageable){
        PaginatedResponse<SupplierResponseDto> supplier = supplierService.listSuppliersPaginated(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200","success" , "successfully retrieve supplier with pagination" , supplier));
    }

    @GetMapping
    public ResponseEntity<Response> getSuppliers() {
        List<SupplierResponseDto> dtos = supplierService.getSupplier();
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200" , "success" , "successfully retrieved data" , dtos));
    }

    @PostMapping
    public ResponseEntity<Response> createSupplier(@Valid @RequestBody SupplierDto supplierDto) {
        supplierService.addSupplier(supplierDto);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200","success", "successfully added supplier"));
    }

    @PutMapping("{id}")
    public ResponseEntity<Response> updateSupplier( @PathVariable Long id, @Valid @RequestBody UpdateSupplierDto payload) {
         supplierService.updateSupplierById(id , payload);
        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200","success", "successfully updated supplier"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteSupplier(@PathVariable Long id) {
         supplierService.deleteSupplierById(id);

        return ResponseEntity.status(HttpStatus.OK).body(Response.success("200","success", "successfully deleted supplier"));

    }

}
