package com.example.demo.user;

import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers(){
      return   userRepository.findAll();

    }
    public User getById(Long Id){
    return userRepository.findById(Id).orElse(null);
    }

    public User updateUser(String id,UserDTO userDTO){
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setUser_id(7L);
       return userRepository.save(user);
    }

    public void deleteUser(Long id){
         userRepository.deleteById(id);
    }

    private boolean register(UserDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setUser_id(userDTO.getId());
        user.setName(userDTO.getName());
        return true;
    }
}
