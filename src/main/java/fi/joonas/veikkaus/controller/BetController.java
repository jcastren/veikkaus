package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.BetGuiEntity;
import fi.joonas.veikkaus.service.BetService;
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
@RequestMapping("/api/v2/bets")
@Slf4j
public class BetController {

    private final BetService betService;

    @Autowired
    public BetController(BetService betService) {
        this.betService = betService;
    }

    @GetMapping()
    public ResponseEntity<List<BetGuiEntity>> getBets() {
        return ResponseEntity.ok(betService.findAllBets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BetGuiEntity> getBet(@PathVariable Long id) {
        return ResponseEntity.ok(betService.findOneBet(id));
    }

    @PostMapping
    public ResponseEntity<BetGuiEntity> createBet(@RequestBody BetGuiEntity Bet) {
        BetGuiEntity savedBet = betService.insert(Bet);
        URI location = URI.create("/api/v2/bet-results/" + savedBet.getId());
        return ResponseEntity.created(location).body(savedBet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BetGuiEntity> updateBet(@PathVariable Long id, @RequestBody BetGuiEntity Bet) {
        Bet.setId(id);
        BetGuiEntity updatedBet = betService.update(Bet);
        return ResponseEntity.ok(updatedBet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBet(@PathVariable Long id) {
        betService.delete(id);
        return ResponseEntity.noContent().build();
    }
}