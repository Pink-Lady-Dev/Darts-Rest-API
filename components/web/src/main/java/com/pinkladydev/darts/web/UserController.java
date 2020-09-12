package com.pinkladydev.darts.web;

import com.pinkladydev.darts.player.PlayerService;
import com.pinkladydev.darts.user.User;
import com.pinkladydev.darts.user.UserService;
import com.pinkladydev.darts.web.models.UserRequest;
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

    private final PlayerService playerService;

    @Autowired
    public UserController(final UserService userService, final PlayerService playerService){
        this.userService = userService;
        this.playerService = playerService;
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
        playerService.newPlayer(userRequest.getUsername());
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
