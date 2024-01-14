package users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import users.model.UserModel;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public List<UserModel> users() {
        return List.of(
                new UserModel(1L, "Artyom L"),
                new UserModel(2L, "Alex313"),
                new UserModel(3L, "Vladimir"));
    }
}
