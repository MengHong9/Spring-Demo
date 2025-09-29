package org.example.damo.dto.user;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ChangePasswordUserDto {

    @NotNull(message = "old password is required")
    @Size(min = 6, max = 20 , message = "password must be between  7 and 20 characters")
    private String oldPassword;


    @NotNull(message = "new password is required")
    @Size(min = 6, max = 20 , message = "password must be between  7 and 20 characters")
    private String newPassword;


    @NotNull(message = "confirm password is required")
    @Size(min = 6, max = 20 , message = "password must be between  7 and 20 characters")
    private String confirmPassword;


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
