package omg.controller;

import omg.model.User;
import omg.repository.UserRepository;
import omg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String login, @RequestParam String password,
                                            @RequestParam String name, @RequestParam String data,
                                            @RequestParam String emailNew, @RequestParam String phoneNew,
                                            @RequestParam Double sum) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String ENpassword = passwordEncoder.encode(password);
        System.out.println(ENpassword);
        return service.addUser(login, ENpassword, name, data, emailNew, phoneNew, sum);
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Iterable<User> getAllUsers() {
        return service.getAll();
    }

    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> getUsersPage(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "3") int size) {
        return service.getAllUsers(page,size);
    }

    @PostMapping(path="/pay")
    public @ResponseBody String payTo (@RequestParam String login, @RequestParam String loginTo,
                                            @RequestParam Double sum) {
        return service.pay(login, loginTo, sum);
    }

}
