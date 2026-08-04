package com.v1zefull.pft.service;

import com.v1zefull.pft.dto.user.UserRequest;
import com.v1zefull.pft.dto.user.UserResponse;
import com.v1zefull.pft.entity.User;
import com.v1zefull.pft.exception.ResourceNotFoundException;
import com.v1zefull.pft.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Entity -> Response
    public UserResponse toResponse(User user){
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    //Request -> Entity
    private User toEntity(UserRequest request){
        return  new User(request.getName(), request.getEmail());
    }

    public UserResponse createUser(UserRequest request){
        User user = toEntity(request);
        User saved = userRepository.save(user);
        return toResponse(saved);
    }

    public UserResponse getUserById(Long id) {
        User user = getUserEntityById(id);
        return toResponse(user);
    }

    public User getUserEntityById(Long id){
        return userRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User not found with id: " + id)
        );
    }

    public List<UserResponse> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponse updateUser(Long id, UserRequest request){
        User user = getUserEntityById(id);
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        User updated = userRepository.save(user);
        return toResponse(updated);
    }

    public void deleteUser(Long id){
        User user = getUserEntityById(id);
        userRepository.deleteById(user.getId());
    }
}
