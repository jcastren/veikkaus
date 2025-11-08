package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.UserRoleGuiEntity;
import fi.joonas.veikkaus.service.UserRoleService;
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
@RequestMapping("/api/v2/user-roles")
@Slf4j
public class UserRoleController {

    private final UserRoleService userRoleService;

    @Autowired
    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GetMapping()
    public ResponseEntity<List<UserRoleGuiEntity>> getUserRoles() {
        return ResponseEntity.ok(userRoleService.findAllUserRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRoleGuiEntity> getUserRole(@PathVariable Long id) {
        return ResponseEntity.ok(userRoleService.findOneUserRole(id));
    }

    @PostMapping
    public ResponseEntity<UserRoleGuiEntity> createUserRole(@RequestBody UserRoleGuiEntity tournamentTeam) {
        UserRoleGuiEntity savedUserRole = userRoleService.insert(tournamentTeam);
        URI location = URI.create("/api/v2/user-roles/" + savedUserRole.getId());
        return ResponseEntity.created(location).body(savedUserRole);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRoleGuiEntity> updateUserRole(@PathVariable Long id, @RequestBody UserRoleGuiEntity tournamentTeam) {
        tournamentTeam.setId(id);
        UserRoleGuiEntity updatedUserRole = userRoleService.update(tournamentTeam);
        return ResponseEntity.ok(updatedUserRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable Long id) {
        userRoleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
