package com.pinkladydev.gameWeb.api;

import com.pinkladydev.gameWeb.api.models.UserRequest;
import com.pinkladydev.user.User;
import com.pinkladydev.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@RestController
@RequestMapping("")
@EnableWebMvc
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    /********** User ************/

    /**   PATH: /user   **/
    /**   POST new user **/
    /**   This is the only exposed end point **/

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public void insertUser (@RequestBody UserRequest userRequest)
    {
        userService.insertUser(userRequest.getUsername(), userRequest.getPassword());
    }

    /**   PATH: /user/all   **/
    /**   POST get list of users **/
    /**   TODO : May not be worth keeping **/
    // This should probs be deleted or heavily restricted
    @GetMapping("/user/all")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

}
