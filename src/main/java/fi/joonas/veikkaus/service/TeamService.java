package fi.joonas.veikkaus.service;

import fi.joonas.veikkaus.dao.TeamDao;
import fi.joonas.veikkaus.guientity.TeamGuiEntity;
import fi.joonas.veikkaus.jpaentity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic level class for DB handling of Team
 *
 * @author joonas
 */
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamDao teamDao;

    public Long insert(TeamGuiEntity team) {

        return teamDao.save(team.toDbEntity()).getId();
    }

    public Long modify(TeamGuiEntity team) {

        return teamDao.save(team.toDbEntity()).getId();
    }

    public boolean delete(String id) {

        teamDao.deleteById(Long.valueOf(id));
        return true;
    }

    public List<TeamGuiEntity> findAllTeams() {

        List<TeamGuiEntity> teamGuiEntityList = new ArrayList<>();
        teamDao.findAll().forEach(team -> teamGuiEntityList.add(team.toGuiEntity()));
        return teamGuiEntityList;
    }

    public TeamGuiEntity findOneTeam(String id) {

        return teamDao.findById(Long.valueOf(id))
                .map(Team::toGuiEntity)
                .orElse(TeamGuiEntity.builder().build());
    }

}
