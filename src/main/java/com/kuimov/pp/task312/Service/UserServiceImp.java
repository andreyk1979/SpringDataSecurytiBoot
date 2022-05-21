package com.kuimov.pp.task312.Service;

import com.kuimov.pp.task312.models.User;
import com.kuimov.pp.task312.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImp(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void edit(User user) {
        userRepository.save(user);
    }

    public User getUserById(long id) {
        return userRepository.getById(id);
    }

    @Override
    public User getUserByEmail(String email) throws NotFoundException {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new NotFoundException(email);
        }
        return user;
    }
}