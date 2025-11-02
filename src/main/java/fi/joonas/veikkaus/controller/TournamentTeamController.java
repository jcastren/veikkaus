package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.TournamentTeamGuiEntity;
import fi.joonas.veikkaus.service.TournamentTeamService;
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
@RequestMapping("/api/v2/tournament-teams")
@Slf4j
public class TournamentTeamController {

    private final TournamentTeamService tournamentTeamService;

    @Autowired
    public TournamentTeamController(TournamentTeamService tournamentTeamService) {
        this.tournamentTeamService = tournamentTeamService;
    }

    @GetMapping()
    public ResponseEntity<List<TournamentTeamGuiEntity>> getTournamentTeams() {
        return ResponseEntity.ok(tournamentTeamService.findAllTournamentTeams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentTeamGuiEntity> getTournamentTeam(@PathVariable Long id) {
        return ResponseEntity.ok(tournamentTeamService.findOneTournamentTeam(id));
    }

    @PostMapping
    public ResponseEntity<TournamentTeamGuiEntity> createTournamentTeam(@RequestBody TournamentTeamGuiEntity tournamentTeam) {
        TournamentTeamGuiEntity savedTournamentTeam = tournamentTeamService.insert(tournamentTeam);
        URI location = URI.create("/api/v2/tournament-teams/" + savedTournamentTeam.getId());
        return ResponseEntity.created(location).body(savedTournamentTeam);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TournamentTeamGuiEntity> updateTournamentTeam(@PathVariable Long id, @RequestBody TournamentTeamGuiEntity tournamentTeam) {
        tournamentTeam.setId(id.toString());
        TournamentTeamGuiEntity updatedTournamentTeam = tournamentTeamService.update(tournamentTeam);
        return ResponseEntity.ok(updatedTournamentTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournamentTeam(@PathVariable Long id) {
        tournamentTeamService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
