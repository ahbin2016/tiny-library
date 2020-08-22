package com.example.tinylibrary.service;

import com.example.tinylibrary.domain.User;
import com.example.tinylibrary.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceTest {

  UserService userService;

  @MockBean UserRepository userRepository;

  @Before
  public void setUp() {
    userService = new UserService();
    MockitoAnnotations.initMocks(this);
    ReflectionTestUtils.setField(userService, "userRepository", userRepository);
  }

  @Test
  public void testCreateUser() {
    when(userRepository.save(any(User.class))).thenReturn(mockUser());
    User user = userService.createUser(mockUser());
    assertNotNull(user);
  }

  @Test
  public void testUpdateUser() {
    when(userRepository.save(any(User.class))).thenReturn(mockUser());
    User user = userService.updateUser(mockUser());
    assertNotNull(user);
  }

  private User mockUser() {
    User user = new User();
    user.setId(1L);
    user.setCountBook(0);
    user.setName("TestName");
    return user;
  }
}
