package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.StatusDao;
import fi.joonas.veikkaus.guientity.StatusGuiEntity;
import fi.joonas.veikkaus.jpaentity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Business logic level class for DB handling of Status
 *
 * @author jcastren
 */
@Service
public class StatusService {

    @Autowired
    StatusDao statusDao;

    protected static StatusGuiEntity convertDbToGui(Status db) {
        StatusGuiEntity ge = new StatusGuiEntity();

        ge.setId(db.getId().toString());
        ge.setStatusNumber(db.getStatusNumber());
        ge.setDescription(db.getDescription());

        return ge;
    }

    protected static Status convertGuiToDb(StatusGuiEntity ge) {
        Status db = new Status();

        if (ge.getId() != null && !ge.getId().isEmpty()) {
            db.setId(Long.valueOf(ge.getId()));
        } else {
            db.setId(null);
        }
        db.setStatusNumber(ge.getStatusNumber());
        db.setDescription(ge.getDescription());

        return db;
    }

    public Long insert(StatusGuiEntity status) {
        return statusDao.save(convertGuiToDb(status)).getId();
    }

    public Long modify(StatusGuiEntity status) {
        return statusDao.save(convertGuiToDb(status)).getId();
    }

    public boolean delete(String id) {
        statusDao.deleteById(Long.valueOf(id));
        return true;
    }

    public List<StatusGuiEntity> findAllStatuses() {
        List<StatusGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(statusDao.findAll()).forEach(status -> geList.add(convertDbToGui(status)));
        return geList;
    }

    public StatusGuiEntity findOneStatus(String id) {
        return convertDbToGui(statusDao.findById(Long.valueOf(id)).get());
    }

}
