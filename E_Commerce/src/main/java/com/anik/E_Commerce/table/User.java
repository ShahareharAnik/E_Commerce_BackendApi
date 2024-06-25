package com.anik.E_Commerce.table;

import com.anik.E_Commerce.allenum.UserType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table( name = "users" )
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column( name = "user_id" )
    private Integer userID;

    @Column( name = "name" )
    private String name;

    @Enumerated( EnumType.STRING )
    @Column( name = "type" )
    private UserType type;
}
