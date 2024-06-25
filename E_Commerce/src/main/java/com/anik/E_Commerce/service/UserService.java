package com.anik.E_Commerce.service;

import com.anik.E_Commerce.allreturns.FixedMessages;
import com.anik.E_Commerce.repositories.UserRepository;
import com.anik.E_Commerce.specification.UserSpecifications;
import com.anik.E_Commerce.table.User;
import com.anik.E_Commerce.allenum.UserType;
import com.anik.E_Commerce.response.GenericResponse;
import com.anik.E_Commerce.wrapper.UserRequestWrapper;
import com.anik.E_Commerce.wrapper.UserResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserResponseWrapper> getAllUserInfo() {
        return userRepository.findAll().stream().map(this::convertToResponseWrapper).collect(Collectors.toList());
    }

    public List<UserResponseWrapper> getSortedByName( String name ) {
        return userRepository.findAll( ( root, query, cb ) -> cb.equal( root.get( "name" ), name ), Sort.by( "name" ) )
                .stream().map( this::convertToResponseWrapper ).collect( Collectors.toList() );
    }

    public List<UserResponseWrapper> getSortedByType( String type ) {
        return userRepository.findAll( ( root, query, cb ) -> cb.equal( root.get( "type" ), UserType.valueOf( type.toUpperCase() ) ), Sort.by( "type" ) )
                .stream().map( this::convertToResponseWrapper ).collect( Collectors.toList() );
    }

    public List<UserResponseWrapper> getSortedByUserID( Integer id ) {
        return userRepository.findAll( ( root, query, cb ) -> cb.equal( root.get( "id" ), id ), Sort.by( "id" ) )
                .stream().map( this::convertToResponseWrapper ).collect( Collectors.toList() );
    }

    @Transactional
    public GenericResponse addUser( UserRequestWrapper userRequestWrapper ) {
        User user = new User();
        user.setUserID( userRequestWrapper.getUserID() );
        user.setName( userRequestWrapper.getName() );
        user.setType( userRequestWrapper.getType() );
        userRepository.save( user );
        return new GenericResponse( user.getUserID(), FixedMessages.User_added_successfully, HttpStatus.OK.value(), LocalDateTime.now() );
    }

    @Transactional
    public GenericResponse deleteUser( int id ) {
        if ( userRepository.existsById( id ) ) {
            userRepository.deleteById( id );
            return new GenericResponse( id, FixedMessages.User_deleted_successfully, HttpStatus.OK.value(), LocalDateTime.now() );
        }
        return new GenericResponse( id, "User not found", HttpStatus.NOT_FOUND.value(), LocalDateTime.now() );
    }

    @Transactional
    public GenericResponse updateUser( int id, UserRequestWrapper userRequestWrapper ) {
        if ( userRepository.existsById( id ) ) {
            User user = new User();
            user.setUserID( id );
            user.setName( userRequestWrapper.getName() );
            user.setType( userRequestWrapper.getType() );
            userRepository.save( user );
            return new GenericResponse( id, FixedMessages.User_updated_successfully, HttpStatus.OK.value(), LocalDateTime.now() );
        }
        return new GenericResponse( id, "User not found", HttpStatus.NOT_FOUND.value(), LocalDateTime.now() );
    }

    public List<UserResponseWrapper> findUser( Integer id, String name, String type ) {
        Specification<User> spec = UserSpecifications.hasId( id )
                .and( UserSpecifications.hasName( name ) )
                .and( UserSpecifications.hasType( type.toUpperCase() ) );
        return userRepository.findAll( spec ).stream().map( this::convertToResponseWrapper ).collect( Collectors.toList() );
    }

    private UserResponseWrapper convertToResponseWrapper( User user ) {
        return new UserResponseWrapper(user.getUserID(), user.getName(), user.getType().name());
    }
}
