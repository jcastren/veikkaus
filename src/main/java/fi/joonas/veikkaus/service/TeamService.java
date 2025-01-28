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

    /**
     * @param team
     * @return
     */
    public Long insert(TeamGuiEntity team) {

        return teamDao.save(team.toDbEntity()).getId();
    }

    /**
     * @param team
     * @return
     */
    public Long modify(TeamGuiEntity team) {

        return teamDao.save(team.toDbEntity()).getId();
    }

    /**
     * @param id
     * @return
     */
    public boolean delete(String id) {

        boolean succeed;
        teamDao.deleteById(Long.valueOf(id));
        succeed = true;
        return succeed;
    }

    /**
     * @return
     */
    public List<TeamGuiEntity> findAllTeams() {

        List<TeamGuiEntity> teamGuiEntityList = new ArrayList<>();
        List<Team> dbTeams = ImmutableList.copyOf(teamDao.findAll());

        for (Team dbTeam : dbTeams) {
            teamGuiEntityList.add(dbTeam.toGuiEntity());
        }

        return teamGuiEntityList;
    }

    /**
     * @param id
     * @return
     */
    public TeamGuiEntity findOneTeam(String id) {

        return teamDao.findById(Long.valueOf(id)).get().toGuiEntity();
    }

}
