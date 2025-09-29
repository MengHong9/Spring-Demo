package org.example.damo.dto.user;

import jakarta.validation.constraints.*;
import org.example.damo.common.annotation.ValidEnum;
import org.example.damo.common.enums.Role;

public class UserUpdateDto {
    @NotBlank(message = "name must be no empty")
    @NotNull(message = "name is required")
    @Size(min = 4 , max = 40 , message = "username must be between 4 and 40 characters")
    private String name;


    @NotNull(message = "address is required")
    @Size(min = 5, max = 40 , message = "address must be between 7 and 40")
    private String address;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @NotNull(message = "role is required")
    @ValidEnum(enumClass = Role.class , message = "Role must be in (USER,ADMIN)")
    private String role;
}
