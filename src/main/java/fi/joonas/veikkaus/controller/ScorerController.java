package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.ScorerGuiEntity;
import fi.joonas.veikkaus.service.ScorerService;
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
@RequestMapping("/api/v2/scorers")
@Slf4j
public class ScorerController {

    private final ScorerService scorerService;

    @Autowired
    public ScorerController(ScorerService scorerService) {
        this.scorerService = scorerService;
    }

    @GetMapping()
    public ResponseEntity<List<ScorerGuiEntity>> getScorers() {
        return ResponseEntity.ok(scorerService.findAllScorers());
    }

    @PostMapping
    public ResponseEntity<ScorerGuiEntity> createScorer(@RequestBody ScorerGuiEntity scorer) {
        ScorerGuiEntity savedScorer = scorerService.insert(scorer);
        URI location = URI.create("/api/v2/scorers/" + savedScorer.getId());
        return ResponseEntity.created(location).body(savedScorer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScorerGuiEntity> updateScorer(@PathVariable Long id, @RequestBody ScorerGuiEntity scorer) {
        scorer.setId(id);
        ScorerGuiEntity updatedScorer = scorerService.update(scorer);
        return ResponseEntity.ok(updatedScorer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScorer(@PathVariable Long id) {
        scorerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}