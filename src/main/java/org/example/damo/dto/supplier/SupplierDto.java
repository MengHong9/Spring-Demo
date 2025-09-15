package org.example.damo.dto.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SupplierDto {

    @NotBlank(message = "name can not be empty!")
    @NotNull(message = "name is required")
    @Size(min = 4 , max = 40 , message = "supplier name must be between 5 to 40 characters")
    private String name;

    @Size(max = 255 , message = "address must not exceed 255 characters")
    private String address;

    @Size(max = 50 , message = "rating must not exceed 50 characters")
    private String rating;


    @Size(max = 14 , message = "phone must not exceed 14 characters")
    private String phone;


    @Email(message = "email should be valid")
    @Size(max = 50 , message = "email must not exceed 50 characters")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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


}
