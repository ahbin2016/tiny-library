package com.example.tinylibrary.service;

import com.example.tinylibrary.domain.User;
import com.example.tinylibrary.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
public class UserService {

  @Autowired private UserRepository userRepository;

  public User createUser(User user) {
    log.info("Calling service to add new user");
    return userRepository.save(user);
  }

  public User searchUser(Long userId) {
    log.info("Calling service to search user");
    Optional<User> user = userRepository.findById(userId);
    return user.orElse(null);
  }

  public User updateUser(User user) {
    log.info("Calling service to update user");
    return userRepository.save(user);
  }
}
