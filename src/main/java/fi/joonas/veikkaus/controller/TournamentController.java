package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.TournamentGuiEntity;
import fi.joonas.veikkaus.service.TournamentService;
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
@RequestMapping("/api/v2/tournaments")
@Slf4j
public class TournamentController {

    private final TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping()
    public ResponseEntity<List<TournamentGuiEntity>> getTournaments() {
        return ResponseEntity.ok(tournamentService.findAllTournaments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentGuiEntity> getTournament(@PathVariable Long id) {
        return ResponseEntity.ok(tournamentService.findOneTournament(id));
    }

    @PostMapping
    public ResponseEntity<TournamentGuiEntity> createTournament(@RequestBody TournamentGuiEntity tournament) {
        TournamentGuiEntity savedTournament = tournamentService.insert(tournament);
        URI location = URI.create("/api/v2/tournaments/" + savedTournament.getId());
        return ResponseEntity.created(location).body(savedTournament);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TournamentGuiEntity> updateTournament(@PathVariable Long id, @RequestBody TournamentGuiEntity tournament) {
        tournament.setId(id.toString());
        TournamentGuiEntity updatedTournament = tournamentService.update(tournament);
        return ResponseEntity.ok(updatedTournament);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        tournamentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
