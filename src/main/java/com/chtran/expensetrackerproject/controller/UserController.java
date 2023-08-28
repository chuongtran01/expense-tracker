package com.chtran.expensetrackerproject.controller;

import com.chtran.expensetrackerproject.entity.BudgetDTO;
import com.chtran.expensetrackerproject.entity.OverallBudgetDTO;
import com.chtran.expensetrackerproject.entity.User;
import com.chtran.expensetrackerproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = service.saveUser(user);

        if (newUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }


    @PostMapping("/addUsers")
    public ResponseEntity<List<User>> addUsers(@RequestBody List<User> users) {
        List<User>newUsers = service.saveUsers(users);

        if (newUsers == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(newUsers, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User>users = service.getUsers() ;

        if (users == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK) ;
    }

    @GetMapping("/usersById")
    public ResponseEntity<User> getUserById(@RequestParam int id) {
        User user = service.getUserById(id) ;

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK) ;
    }

    @GetMapping("/usersByName")
    public ResponseEntity<User> getUserByName(@RequestParam String name) {
        User user = service.getUserByName(name) ;

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK) ;
    }

    @PutMapping("/users/overallBudget")
    public ResponseEntity<String>modifyOverallBudget(@RequestBody OverallBudgetDTO body) {
        String res = service.setOverallBudget(body.getUserId(), body.getAmount());

        if (res.equals("Cannot find user.")) {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/users/budget")
    public ResponseEntity<String>modifyBudget(@RequestBody BudgetDTO body) {
        String res = service.setBudgetByCategory(body.getUserId(), body.getCategory(), body.getAmount());

        if (res.equals("Please try again")) {
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/users/getBudget")
    public ResponseEntity<Double>getBudgetByCategory(@RequestParam int userId, @RequestParam String category) {
        Double res = service.getBudgetByCategoryName(userId, category);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/users")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        String response = service.updateUser(user);

        if (response.equals("Cannot find user") ) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK) ;
    }



    @DeleteMapping("users/{id}")
    public ResponseEntity<String> deleteUser(@RequestParam int id) {
        String response = service.deleteUser(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
