package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.acme.entity.User;
import org.acme.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@ApplicationScoped
@Transactional
public class UserService {

    @Inject
    UserRepository userRepository;
    private Validator validator;

    public UserService() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public List<User> getAllUsers(){
        return userRepository.listAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id);
    }

    public Response addUser(User user){
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if(!violations.isEmpty()){
            return Response.status(400).entity("Username and/or email are invalid!").build();
        }

        userRepository.persist(user);
        return Response.ok("User successfully added").build();
    }

    public Response updateUser(Long id, User updatedUser){
        Set<ConstraintViolation<User>> violations = validator.validate(updatedUser);

        if(!violations.isEmpty()){
            return Response.status(400).entity("Username and/or email are invalid!").build();
        }

        User user = userRepository.findById(id);

        if(user == null){
            return Response.status(404).entity("User not found!").build();
        }

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        return Response.ok("User successfully updated").build();
    }

    public Response deleteUser(Long id){
        User user = userRepository.findById(id);

        if(user == null){
            return Response.status(404).entity("User not found!").build();
        }

        userRepository.deleteById(id);
        return Response.ok("User successfully deleted").build();
    }

}
