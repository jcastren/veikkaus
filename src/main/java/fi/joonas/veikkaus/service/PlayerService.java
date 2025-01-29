package fi.joonas.veikkaus.service;

import fi.joonas.veikkaus.dao.PlayerDao;
import fi.joonas.veikkaus.guientity.PlayerGuiEntity;
import fi.joonas.veikkaus.jpaentity.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic level class for DB handling of Player
 *
 * @author jcastren
 */
@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerDao playerDao;

    public Long insert(PlayerGuiEntity player) {

        return playerDao.save(player.toDbEntity()).getId();
    }

    public Long modify(PlayerGuiEntity player) {

        return playerDao.save(player.toDbEntity()).getId();
    }

    public boolean delete(String id) {

        playerDao.deleteById(Long.valueOf(id));
        return true;
    }

    public List<PlayerGuiEntity> findAllPlayers() {

        List<PlayerGuiEntity> guiEntityList = new ArrayList<>();
        playerDao.findAll().forEach(playerEntity -> guiEntityList.add(playerEntity.toGuiEntity()));
        return guiEntityList;
    }

    public PlayerGuiEntity findOnePlayer(String id) {

        return playerDao.findById(Long.valueOf(id))
                .map(Player::toGuiEntity)
                .orElse(PlayerGuiEntity.builder().build());
    }

}
