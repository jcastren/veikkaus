package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.GameGuiEntity;
import fi.joonas.veikkaus.service.GameService;
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
@RequestMapping("/api/v2/games")
@Slf4j
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping()
    public ResponseEntity<List<GameGuiEntity>> getGames() {
        return ResponseEntity.ok(gameService.findAllGames());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameGuiEntity> getGame(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.findOneGame(id));
    }

    @PostMapping
    public ResponseEntity<GameGuiEntity> createGame(@RequestBody GameGuiEntity game) {
        GameGuiEntity savedGame = gameService.insert(game);
        URI location = URI.create("/api/v2/games/" + savedGame.getId());
        return ResponseEntity.created(location).body(savedGame);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameGuiEntity> updateGame(@PathVariable Long id, @RequestBody GameGuiEntity game) {
        game.setId(id);
        GameGuiEntity updatedGame = gameService.update(game);
        return ResponseEntity.ok(updatedGame);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}