package com.pinkladydev.DartsRestAPI.api;

import com.pinkladydev.DartsRestAPI.model.User;
import com.pinkladydev.DartsRestAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void insertUser (@RequestBody User user)
    {
        userService.insertUser(user);
    }

    /**   PATH: /user/all   **/
    /**   POST get list of users **/
    /**   TODO : May not be worth keeping **/
    // This should probs be deleted or heavily restricted
    @GetMapping("/user/all")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/user/{userId}/password")
    public void changePassword(){

    }

    @PostMapping("/user/{userId}/email")
    public void changeEmail(){

    }
}
