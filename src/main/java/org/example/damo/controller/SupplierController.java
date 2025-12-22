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
