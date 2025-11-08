package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.UserGuiEntity;
import fi.joonas.veikkaus.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v2/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserGuiEntity>> getUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGuiEntity> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findOneUser(id));
    }

    @PostMapping
    public ResponseEntity<UserGuiEntity> createUser(@RequestBody UserGuiEntity User) {
        UserGuiEntity savedUser = userService.insert(User);
        URI location = URI.create("/api/v2/users/" + savedUser.getId());
        return ResponseEntity.created(location).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserGuiEntity> updateUser(@PathVariable Long id, @RequestBody UserGuiEntity User) {
        User.setId(id);
        UserGuiEntity updatedUser = userService.update(User);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}