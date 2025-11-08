package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.TeamDao;
import fi.joonas.veikkaus.exception.VeikkausNotFoundException;
import fi.joonas.veikkaus.guientity.TeamGuiEntity;
import fi.joonas.veikkaus.jpaentity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic level class for DB handling of teams
 *
 * @author jcastren
 */
@Service
public class TeamService {

    private final TeamDao teamDao;

    @Autowired
    public TeamService(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    public List<TeamGuiEntity> findAllTeams() {
        List<TeamGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(teamDao.findAll()).forEach(team -> geList.add(convertDbToGui(team)));
        return geList;
    }

    public TeamGuiEntity findOneTeam(Long id) {
        return convertDbToGui(getFromDb(id));
    }

    public TeamGuiEntity insert(TeamGuiEntity team) {
        return save(team);
    }

    public TeamGuiEntity update(TeamGuiEntity team) {
        getFromDb(team.getId());
        return save(team);
    }

    public void delete(Long id) {
        teamDao.delete(getFromDb(id));
    }

    public Team getFromDb(Long id) {
        return teamDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(Team.class, id));
    }

    private TeamGuiEntity save(TeamGuiEntity team) {
        return convertDbToGui(teamDao.save(convertGuiToDb(team)));
    }

    protected static TeamGuiEntity convertDbToGui(Team db) {
        TeamGuiEntity ge = new TeamGuiEntity();

        ge.setId(db.getId());
        ge.setName(db.getName());

        return ge;
    }

    protected static Team convertGuiToDb(TeamGuiEntity ge) {
        Team db = new Team();

        db.setId(ge.getId());
        db.setName(ge.getName());

        return db;
    }
}
