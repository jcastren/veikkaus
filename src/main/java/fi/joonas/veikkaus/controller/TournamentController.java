package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.TournamentGuiEntity;
import fi.joonas.veikkaus.service.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/tournaments")
@Slf4j
public class TournamentController {

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping()
    public List<TournamentGuiEntity> getTournaments() {
        return tournamentService.findAllTournaments();
    }

    @GetMapping("/{id}")
    public TournamentGuiEntity getTournament(@PathVariable Long id) {
        return tournamentService.findOneTournament(id);
    }

    @PostMapping
    public String createTournament(@RequestBody TournamentGuiEntity tournament) {
        Long id = tournamentService.insert(tournament);
        return "Tournament created: " + id;
    }

    @PutMapping()
    public String updateTournament(@RequestBody TournamentGuiEntity tournament) {
        Long id = tournamentService.update(tournament);
        return "Tournament updated: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteTournament(@PathVariable Long id) {
        tournamentService.delete(id);
        return "Tournament deleted: " + id;
    }
}
