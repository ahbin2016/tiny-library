package com.example.tinylibrary.web.rest;

import com.example.tinylibrary.domain.User;
import com.example.tinylibrary.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserResource {

  @Autowired private UserService userService;

  /**
   * POST Add a new user
   *
   * @param user to create
   * @return the ResponseEntity with status 201 (Created) and with body the new user
   */
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(notes = "POST Add a new book", value = "createUser")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "Success", response = User.class),
        @ApiResponse(code = 204, message = "No Data Available - empty List"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 404, message = "Not Found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
      })
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) throws URISyntaxException {
    log.info("REST request to add User : {}", user.toString());
    User result = userService.createUser(user);
    return ResponseEntity.created(new URI("/api/users/" + result.getId())).body(result);
  }
}
