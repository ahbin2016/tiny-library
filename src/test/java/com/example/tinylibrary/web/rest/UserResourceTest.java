package com.example.tinylibrary.web.rest;

import com.example.tinylibrary.TinyLibraryApplication;
import com.example.tinylibrary.domain.User;
import com.example.tinylibrary.service.UserService;
import com.example.tinylibrary.util.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TinyLibraryApplication.class)
public class UserResourceTest {

  private MockMvc mvc;

  @Mock private UserService userService;

  @Autowired private WebApplicationContext webApplicationContext;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    UserResource userResource = new UserResource();
    ReflectionTestUtils.setField(userResource, "userService", userService);
    this.mvc = MockMvcBuilders.standaloneSetup(userResource).build();
  }

  @Test
  public void testCreateUser() throws Exception {
    when(userService.createUser(mockUser())).thenReturn(mockUser());
    mvc.perform(
            post("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(mockUser())))
        .andExpect(status().isCreated());
  }

  private User mockUser() {
    User user = new User();
    user.setId(1L);
    user.setCountBook(0);
    user.setName("TestName");
    return user;
  }
}
