package com.fitness.app.controllers;

import com.fitness.app.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class ServiceController {

    private int userCounter = 3;

    private List<Map<String, String>> users = new ArrayList<Map<String, String>>(){{
        add(new HashMap<String, String>() {{ put("name", "user1"); put("id", "1");}});
        add(new HashMap<String, String>() {{ put("name", "user2"); put("id", "2");}});
    }};

    @GetMapping
    public List<Map<String, String>> getUsers(){
        return users;
    }

    @GetMapping("{id}")
    public Map<String, String> getUser(@PathVariable String id){
        return getUserById(id);
    }

    @PostMapping
    public Map<String, String> createUser(@RequestBody Map<String, String> user){
        user.put("id", String.valueOf(userCounter++));
        users.add(user);
        return user;
    }

    @PutMapping("{id}")
    public Map<String, String> updateUser(@PathVariable String id, @RequestBody Map<String, String> user){
        Map<String, String> userUpdate = getUserById(id);

        userUpdate.putAll(user);
        userUpdate.put("id", id);

        return userUpdate;
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable String id){
        Map<String, String> userDelete = getUserById(id);
        users.remove(userDelete);
    }

    private Map<String, String> getUserById(@PathVariable String id) {
        return users.stream().filter(user -> user.get("id").equals(id)).findFirst().orElseThrow(NotFoundException::new);
    }
}
