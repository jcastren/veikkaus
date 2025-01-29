package fi.joonas.veikkaus.service;

import fi.joonas.veikkaus.dao.StatusDao;
import fi.joonas.veikkaus.guientity.StatusGuiEntity;
import fi.joonas.veikkaus.jpaentity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic level class for DB handling of Status
 *
 * @author joonas
 */
@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusDao statusDao;

    public Long insert(StatusGuiEntity status) {

        return statusDao.save(status.toDbEntity()).getId();
    }

    public Long modify(StatusGuiEntity status) {

        return statusDao.save(status.toDbEntity()).getId();
    }

    public boolean delete(String id) {

        statusDao.deleteById(Long.valueOf(id));
        return true;
    }

    public List<StatusGuiEntity> findAllStatuses() {

        List<StatusGuiEntity> statusGuiEntityList = new ArrayList<>();
        statusDao.findAll().forEach(status -> statusGuiEntityList.add(status.toGuiEntity()));
        return statusGuiEntityList;
    }

    public StatusGuiEntity findOneStatus(String id) {

        return statusDao.findById(Long.valueOf(id))
                .map(Status::toGuiEntity)
                .orElse(StatusGuiEntity.builder().build());
    }
}
