package com.anik.E_Commerce.wrapper;

import com.anik.E_Commerce.allenum.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserRequestWrapper {
    private Integer userID;
    private String name;
    private UserType type;
}
