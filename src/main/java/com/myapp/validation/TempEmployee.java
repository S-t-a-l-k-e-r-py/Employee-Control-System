package com.myapp.validation;

import com.myapp.validation.age.ValidAge;
import com.myapp.validation.name.ValidName;
import com.myapp.validation.password.ValidPwd;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ValidPwd.List({
        @ValidPwd(first = "password", second = "tempPassword", message = "Passwords fields must be same")})
public class TempEmployee {

    @ValidName
    @Size(min = 2, message = "required")
    @NotNull(message = "required")
    private String firstName;

    @ValidName
    @Size(min = 2, message = "required")
    @NotNull(message = "required")
    private String lastName;

    @Size(min = 2, message = "required any username with length higher then 2")
    @NotNull(message = "required any username")
    private String userName;

    @NotNull(message = "required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "required minimum length: 4")
    @Size(min = 4, message = "required minimum length: 4")
    private String password;

    @NotNull(message = "required minimum length: 4")
    @Size(min = 4, message = "required minimum length: 4")
    private String tempPassword;

    @ValidAge
    private String age;

    public TempEmployee() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTempPassword() {
        return tempPassword;
    }

    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}
