package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.BetResultGuiEntity;
import fi.joonas.veikkaus.service.BetResultService;
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
@RequestMapping("/api/v2/bet-results")
@Slf4j
public class BetResultController {

    private final BetResultService betResultService;

    @Autowired
    public BetResultController(BetResultService betResultService) {
        this.betResultService = betResultService;
    }

    @GetMapping()
    public ResponseEntity<List<BetResultGuiEntity>> getBetResults() {
        return ResponseEntity.ok(betResultService.findAllBetResults());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BetResultGuiEntity> getBetResult(@PathVariable Long id) {
        return ResponseEntity.ok(betResultService.findOneBetResult(id));
    }

    @PostMapping
    public ResponseEntity<BetResultGuiEntity> createBetResult(@RequestBody BetResultGuiEntity BetResult) {
        BetResultGuiEntity savedBetResult = betResultService.insert(BetResult);
        URI location = URI.create("/api/v2/bet-results/" + savedBetResult.getId());
        return ResponseEntity.created(location).body(savedBetResult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BetResultGuiEntity> updateBetResult(@PathVariable Long id, @RequestBody BetResultGuiEntity BetResult) {
        BetResult.setId(id);
        BetResultGuiEntity updatedBetResult = betResultService.update(BetResult);
        return ResponseEntity.ok(updatedBetResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBetResult(@PathVariable Long id) {
        betResultService.delete(id);
        return ResponseEntity.noContent().build();
    }
}