package fi.joonas.veikkaus.controller;

import fi.joonas.veikkaus.guientity.TeamGuiEntity;
import fi.joonas.veikkaus.service.TeamService;
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
@RequestMapping("/api/v2/teams")
@Slf4j
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService TeamService) {
        this.teamService = TeamService;
    }

    @GetMapping()
    public List<TeamGuiEntity> getTeams() {
        return teamService.findAllTeams();
    }

    @GetMapping("/{id}")
    public TeamGuiEntity getTeam(@PathVariable Long id) {
        return teamService.findOneTeam(id);
    }

    @PostMapping
    public String createTeam(@RequestBody TeamGuiEntity Team) {
        Long id = teamService.insert(Team);
        return "Team created: " + id;
    }

    @PutMapping()
    public String updateTeam(@RequestBody TeamGuiEntity Team) {
        Long id = teamService.update(Team);
        return "Team updated: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteTeam(@PathVariable Long id) {
        teamService.delete(id);
        return "Team deleted: " + id;
    }
}
