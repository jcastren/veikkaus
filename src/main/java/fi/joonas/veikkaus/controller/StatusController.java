package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.StatusGuiEntity;
import fi.joonas.veikkaus.service.StatusService;
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
@RequestMapping("/api/v2/statuses")
@Slf4j
public class StatusController {

    private final StatusService statusService;

    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping()
    public ResponseEntity<List<StatusGuiEntity>> getStatuss() {
        return ResponseEntity.ok(statusService.findAllStatuses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusGuiEntity> getStatus(@PathVariable Long id) {
        return ResponseEntity.ok(statusService.findOneStatus(id));
    }

    @PostMapping
    public ResponseEntity<StatusGuiEntity> createStatus(@RequestBody StatusGuiEntity status) {
        StatusGuiEntity savedStatus = statusService.insert(status);
        URI location = URI.create("/api/v2/bet-results/" + savedStatus.getId());
        return ResponseEntity.created(location).body(savedStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StatusGuiEntity> updateStatus(@PathVariable Long id, @RequestBody StatusGuiEntity status) {
        status.setId(id);
        StatusGuiEntity updatedStatus = statusService.update(status);
        return ResponseEntity.ok(updatedStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatus(@PathVariable Long id) {
        statusService.delete(id);
        return ResponseEntity.noContent().build();
    }
}