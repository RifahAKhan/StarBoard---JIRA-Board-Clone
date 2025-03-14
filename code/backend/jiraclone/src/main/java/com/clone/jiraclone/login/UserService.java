package com.clone.jiraclone.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public LoginResponseDTO validateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        LoginResponseDTO response = new LoginResponseDTO();
        if (user != null && user.getPassword().equals(password)) {
            response.setSuccess(true);
            response.setMessage("Login successful");
        } else {
            response.setSuccess(false);
            response.setMessage("Invalid username or password");
        }
        return response;
    }
}
