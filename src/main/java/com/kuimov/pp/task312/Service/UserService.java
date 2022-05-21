package com.kuimov.pp.task312.Service;

import com.kuimov.pp.task312.models.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    void save(User user);

    void delete(User user);

    void edit(User user);

    User getUserById(long id);

    User getUserByEmail (String email);

}
