package com.pinkladydev.DartsRestAPI.api;

import com.pinkladydev.DartsRestAPI.model.User;
import com.pinkladydev.DartsRestAPI.api.models.UserRequest;
import com.pinkladydev.DartsRestAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
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
        userService.insertUser(userRequest);
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
