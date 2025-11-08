package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.PlayerDao;
import fi.joonas.veikkaus.exception.VeikkausNotFoundException;
import fi.joonas.veikkaus.guientity.PlayerGuiEntity;
import fi.joonas.veikkaus.jpaentity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic level class for DB handling of Player
 *
 * @author jcastren
 */
@Service
public class PlayerService {

    private final PlayerDao playerDao;

    @Autowired
    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public List<PlayerGuiEntity> findAllPlayers() {
        List<PlayerGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(playerDao.findAll()).forEach(player -> geList.add(convertDbToGui(player)));
        return geList;
    }

    public PlayerGuiEntity findOnePlayer(Long id) {
        Player db = playerDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(Player.class, id));
        return convertDbToGui(db);
    }

    public PlayerGuiEntity insert(PlayerGuiEntity player) {
        return convertDbToGui(playerDao.save(convertGuiToDb(player)));
    }

    public PlayerGuiEntity update(PlayerGuiEntity player) {
        playerDao.findById(player.getId()).orElseThrow(() -> new VeikkausNotFoundException(Player.class, player.getId()));
        return convertDbToGui(playerDao.save(convertGuiToDb(player)));
    }

    public void delete(Long id) {
        Player player = playerDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(Player.class, id));
        playerDao.delete(player);
    }

    protected static PlayerGuiEntity convertDbToGui(Player db) {
        PlayerGuiEntity ge = new PlayerGuiEntity();

        ge.setId(db.getId());
        ge.setFirstName(db.getFirstName());
        ge.setLastName(db.getLastName());

        return ge;
    }

    protected static Player convertGuiToDb(PlayerGuiEntity ge) {
        Player db = new Player();

        db.setId(ge.getId());
        db.setFirstName(ge.getFirstName());
        db.setLastName(ge.getLastName());

        return db;
    }

}
