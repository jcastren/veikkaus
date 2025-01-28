package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.PlayerDao;
import fi.joonas.veikkaus.guientity.PlayerGuiEntity;
import fi.joonas.veikkaus.jpaentity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic level class for DB handling of Player
 *
 * @author joona
 */
@Service
public class PlayerService {

    @Autowired
    PlayerDao playerDao;

    protected static Player convertGuiToDb(PlayerGuiEntity ge) {

        Player db = new Player();

        if (ge.getId() != null && !ge.getId().isEmpty()) {
            db.setId(Long.valueOf(ge.getId()));
        } else {
            db.setId(null);
        }
        db.setFirstName(ge.getFirstName());
        db.setLastName(ge.getLastName());

        return db;
    }

    public Long insert(PlayerGuiEntity player) {

        return playerDao.save(convertGuiToDb(player)).getId();
    }

    /**
     * @param player
     * @return
     */
    public Long modify(PlayerGuiEntity player) {

        return playerDao.save(convertGuiToDb(player)).getId();
    }

    /**
     * @param id
     * @return
     */
    public boolean delete(String id) {

        boolean succeed;
        playerDao.deleteById(Long.valueOf(id));
        succeed = true;
        return succeed;
    }

    /**
     * @return
     */
    public List<PlayerGuiEntity> findAllPlayers() {

        List<PlayerGuiEntity> guiEntityList = new ArrayList<>();
        List<Player> dbPlayers = ImmutableList.copyOf(playerDao.findAll());

        for (Player dbPlayer : dbPlayers) {
            guiEntityList.add(dbPlayer.toGuiEntity());
        }

        return guiEntityList;
    }

    /**
     * @param id
     * @return
     */
    public PlayerGuiEntity findOnePlayer(String id) {

        return playerDao.findById(Long.valueOf(id)).get().toGuiEntity();
    }

}
