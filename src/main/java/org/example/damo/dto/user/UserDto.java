package org.example.damo.dto.user;


import jakarta.validation.constraints.*;
import org.example.damo.common.annotation.ValidEnum;
import org.example.damo.common.enums.Role;


public class UserDto {

    @NotBlank(message = "name must be no empty")
    @NotNull(message = "name is required")
    @Size(min = 4 , max = 40 , message = "username must be between 4 and 40 characters")
    private String name;

    @NotNull(message = "password is required")
    @NotBlank(message = "password can not be empty")
    @Size(min = 6, max = 20 , message = "password must be between  7 and 20 characters")
    private String password;


    @NotNull(message = "age is required")
    @Min(value = 18 , message = "age must be at least 18")
    private int age;

    @NotNull(message = "address is required")
    @Size(min = 5, max = 40 , message = "address must be between 7 and 40")
    private String address;


    @NotNull(message = "email is required")
    @Size(min = 7, max = 40 , message = "email must be between 7 and 40")
    @Email(message = "email must be valid")
    private String email;


    @ValidEnum(enumClass = Role.class , message = "Role must be in (USER,ADMIN)")
    private String role="USER";

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public UserDto(){}
    public UserDto( String name, int age, String address , String email , String role) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.email = email;
        this.role = role;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

}


