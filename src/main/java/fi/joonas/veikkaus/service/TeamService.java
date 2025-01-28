package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.TeamDao;
import fi.joonas.veikkaus.guientity.TeamGuiEntity;
import fi.joonas.veikkaus.jpaentity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic level class for DB handling of Team
 *
 * @author joonas
 */
@Service
public class TeamService {

    @Autowired
    TeamDao teamDao;

    protected static TeamGuiEntity convertDbToGui(Team db) {
        TeamGuiEntity ge = new TeamGuiEntity();

        ge.setId(db.getId().toString());
        ge.setName(db.getName());

        return ge;
    }

    protected static Team convertGuiToDb(TeamGuiEntity ge) {
        Team db = new Team();

        if (ge.getId() != null && !ge.getId().isEmpty()) {
            db.setId(Long.valueOf(ge.getId()));
        } else {
            db.setId(null);
        }
        db.setName(ge.getName());

        return db;
    }

    /**
     * @param team
     * @return
     */
    public Long insert(TeamGuiEntity team) {
        return teamDao.save(convertGuiToDb(team)).getId();
    }

    /**
     * @param team
     * @return
     */
    public Long modify(TeamGuiEntity team) {
        return teamDao.save(convertGuiToDb(team)).getId();
    }

    /**
     * @param id
     * @return
     */
    public boolean delete(String id) {
        boolean succeed = false;
        teamDao.deleteById(Long.valueOf(id));
        succeed = true;
        return succeed;
    }

    /**
     * @return
     */
    public List<TeamGuiEntity> findAllTeams() {
        List<TeamGuiEntity> geList = new ArrayList<>();
        List<Team> dbTeams = ImmutableList.copyOf(teamDao.findAll());

        for (Team dbTeam : dbTeams) {
            geList.add(convertDbToGui(dbTeam));
        }

        return geList;
    }

    /**
     * @param id
     * @return
     */
    public TeamGuiEntity findOneTeam(String id) {
        TeamGuiEntity teamGe = convertDbToGui(teamDao.findById(Long.valueOf(id)).get());
        return teamGe;
    }

}
