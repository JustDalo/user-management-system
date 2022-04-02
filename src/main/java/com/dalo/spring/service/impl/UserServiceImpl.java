package com.dalo.spring.service.impl;

import com.dalo.spring.dao.UserRepository;
import com.dalo.spring.exception.ResourceNotFoundException;
import com.dalo.spring.model.Country;
import com.dalo.spring.model.User;
import com.dalo.spring.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user, Country country) {
        user.setCountry(country);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, Long id) {
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "Id", id));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setMiddleName(user.getMiddleName());
        existingUser.setSex(user.getSex());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setEmail(user.email);

        userRepository.save(existingUser);

        return existingUser;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new ResourceNotFoundException("User", "Id", id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
