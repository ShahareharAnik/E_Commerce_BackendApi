package com.anik.E_Commerce.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseWrapper {
    private Integer userID;
    private String name;
    private String type;
}
