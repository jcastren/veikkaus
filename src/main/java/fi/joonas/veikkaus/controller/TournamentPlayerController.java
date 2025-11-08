package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.TournamentPlayerGuiEntity;
import fi.joonas.veikkaus.service.TournamentPlayerService;
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
@RequestMapping("/api/v2/tournament-players")
@Slf4j
public class TournamentPlayerController {

    private final TournamentPlayerService tournamentPlayerService;

    @Autowired
    public TournamentPlayerController(TournamentPlayerService tournamentPlayerService) {
        this.tournamentPlayerService = tournamentPlayerService;
    }

    @GetMapping()
    public ResponseEntity<List<TournamentPlayerGuiEntity>> getTournamentPlayers() {
        return ResponseEntity.ok(tournamentPlayerService.findAllTournamentPlayers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentPlayerGuiEntity> getTournamentPlayer(@PathVariable Long id) {
        return ResponseEntity.ok(tournamentPlayerService.findOneTournamentPlayer(id));
    }

    @PostMapping
    public ResponseEntity<TournamentPlayerGuiEntity> createTournamentPlayer(@RequestBody TournamentPlayerGuiEntity tournamentPlayer) {
        TournamentPlayerGuiEntity savedTournamentPlayer = tournamentPlayerService.insert(tournamentPlayer);
        URI location = URI.create("/api/v2/tournament-players/" + savedTournamentPlayer.getId());
        return ResponseEntity.created(location).body(savedTournamentPlayer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TournamentPlayerGuiEntity> updateTournamentPlayer(@PathVariable Long id, @RequestBody TournamentPlayerGuiEntity tournamentPlayer) {
        tournamentPlayer.setId(id);
        TournamentPlayerGuiEntity updatedTournamentPlayer = tournamentPlayerService.update(tournamentPlayer);
        return ResponseEntity.ok(updatedTournamentPlayer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournamentPlayer(@PathVariable Long id) {
        tournamentPlayerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}