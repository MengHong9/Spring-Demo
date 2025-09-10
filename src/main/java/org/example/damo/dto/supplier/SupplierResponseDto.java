package org.example.damo.dto.supplier;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


import java.time.LocalDateTime;

@JsonPropertyOrder({"id" , "supplier_name" , "address" , "rating" , "phone" , "email" , "created_at" , "updated_at"})
public class SupplierResponseDto {
    @JsonProperty("supplier_id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("supplier_name")
    private String supplierName;


    private String address;

    @JsonProperty("contact_number")
    private String phone;

    private String email;

    private String rating;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;


    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
}
