package com.pinkladydev.DartsRestAPI.api;

import com.pinkladydev.DartsRestAPI.model.User;
import com.pinkladydev.DartsRestAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("api/v1/user")
@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public void insertUser (@RequestBody User user) {
        userService.insertUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/all")
    public List<String> getAllUsers() {
        List<String> tempList = new ArrayList<>();
        for (User u :userService.getAllUsers()) {
            tempList.add(u.getName());
        }
        return tempList;
    }
}
