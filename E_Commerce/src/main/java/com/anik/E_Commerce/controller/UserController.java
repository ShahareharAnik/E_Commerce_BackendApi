package com.anik.E_Commerce.controller;

import com.anik.E_Commerce.service.UserService;
import com.anik.E_Commerce.response.GenericResponse;
import com.anik.E_Commerce.wrapper.UserRequestWrapper;
import com.anik.E_Commerce.wrapper.UserResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/users" )
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping( "/info" )
    public List<UserResponseWrapper> getAllUserInfo() {
        return userService.getAllUserInfo();
    }

    @GetMapping( "/name/{name}" )
    public List<UserResponseWrapper> getSortedByName( @PathVariable String name ) {
        return userService.getSortedByName( name );
    }

    @GetMapping( "/type/{type}" )
    public List<UserResponseWrapper> getSortedByType( @PathVariable String type ) {
        return userService.getSortedByType( type );
    }

    @GetMapping( "/id/{id}" )
    public List<UserResponseWrapper> getSortedByUserID( @PathVariable Integer id ) {
        return userService.getSortedByUserID( id );
    }

    @GetMapping( "/search" )
    public List<UserResponseWrapper> searchUsers(
            @RequestParam( required = false ) Integer id,
            @RequestParam( required = false ) String name,
            @RequestParam( required = false ) String type ) {
        return userService.findUser( id, name, type );
    }

    @PostMapping( "/add" )
    public GenericResponse addUser( @RequestBody UserRequestWrapper userRequestWrapper ) {
        return userService.addUser( userRequestWrapper );
    }

    @DeleteMapping( "/delete/{id}" )
    public GenericResponse deleteUser( @PathVariable int id ) {
        return userService.deleteUser( id );
    }

    @PutMapping( "/update/{id}" )
    public GenericResponse updateUser( @PathVariable int id, @RequestBody UserRequestWrapper userRequestWrapper ) {
        return userService.updateUser( id, userRequestWrapper );
    }
}
