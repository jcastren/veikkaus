package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.TeamGuiEntity;
import fi.joonas.veikkaus.service.TeamService;
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
@RequestMapping("/api/v2/teams")
@Slf4j
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService TeamService) {
        this.teamService = TeamService;
    }

    @GetMapping()
    public ResponseEntity<List<TeamGuiEntity>> getTeams() {
        return ResponseEntity.ok(teamService.findAllTeams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamGuiEntity> getTeam(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.findOneTeam(id));
    }

    @PostMapping
    public ResponseEntity<TeamGuiEntity> createTeam(@RequestBody TeamGuiEntity team) {
        TeamGuiEntity savedTeam = teamService.insert(team);
        URI location = URI.create("/api/v2/teams/" + savedTeam.getId());
        return ResponseEntity.created(location).body(savedTeam);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TeamGuiEntity> updateTeam(@PathVariable Long id, @RequestBody TeamGuiEntity team) {
        team.setId(id);
        TeamGuiEntity updatedTeam = teamService.update(team);
        return ResponseEntity.ok(updatedTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
