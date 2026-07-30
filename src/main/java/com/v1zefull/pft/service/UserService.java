package com.v1zefull.pft.service;

import com.v1zefull.pft.entity.User;
import com.v1zefull.pft.exception.ResourceNotFoundException;
import com.v1zefull.pft.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User not found with id: " + id)
        );
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(Long id){
        User user = getUserById(id);
        userRepository.deleteById(user.getId());
    }
}
