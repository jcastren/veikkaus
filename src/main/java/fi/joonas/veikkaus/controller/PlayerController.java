package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.PlayerGuiEntity;
import fi.joonas.veikkaus.service.PlayerService;
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
@RequestMapping("/api/v2/players")
@Slf4j
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping()
    public ResponseEntity<List<PlayerGuiEntity>> getPlayers() {
        return ResponseEntity.ok(playerService.findAllPlayers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerGuiEntity> getPlayer(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.findOnePlayer(id));
    }

    @PostMapping
    public ResponseEntity<PlayerGuiEntity> createPlayer(@RequestBody PlayerGuiEntity tournament) {
        PlayerGuiEntity savedPlayer = playerService.insert(tournament);
        URI location = URI.create("/api/v2/tournaments/" + savedPlayer.getId());
        return ResponseEntity.created(location).body(savedPlayer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerGuiEntity> updatePlayer(@PathVariable Long id, @RequestBody PlayerGuiEntity tournament) {
        tournament.setId(id);
        PlayerGuiEntity updatedPlayer = playerService.update(tournament);
        return ResponseEntity.ok(updatedPlayer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
