package com.andef.javalabs.lab5.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnerForm {
    private String fullName;
    private String city;
    private String contactMethod;
    private boolean emergencyContact;
    private String phone;
    private String email;
}
