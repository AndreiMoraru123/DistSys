package edu.utcn.gpstrack.server.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/login")
    public ResponseEntity<User> getUser(@RequestParam("username") String username , @RequestParam("password") String password) {

        User user=new User();
        User newUser;

        user.setUsername(username);
        user.setPassword(password);
        newUser=userService.findByEmailAndPassword(user.getUsername(), user.getPassword());

        if (newUser!=null) {
            return new ResponseEntity<User>(newUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping(value = "/save")
    public User putUser(@RequestBody User user) {
        return userService.save(user);

    }

}