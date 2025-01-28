package fi.joonas.veikkaus.controller.nextjs;

import fi.joonas.veikkaus.guientity.TournamentGuiEntity;
import fi.joonas.veikkaus.service.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tournaments")
@Slf4j
public class NextjsTournamentController {

    @Autowired
    private TournamentService tournamentService;

    @GetMapping("/get_all")
    public List<TournamentGuiEntity> getAll() {

        return tournamentService.findAllTournaments();
    }

}
