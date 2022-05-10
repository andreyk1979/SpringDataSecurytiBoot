package com.example.demo.Service;

import com.example.demo.models.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    void save(User user);

    void delete(User user);

    void edit(User user);

    User getById(long id);

    User getUserByEmail (String email);

}
