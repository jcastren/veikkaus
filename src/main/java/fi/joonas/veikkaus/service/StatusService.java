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
 * @author joonas
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
        boolean succeed = false;
        statusDao.deleteById(Long.valueOf(id));
        succeed = true;
        return succeed;
    }

    public List<StatusGuiEntity> findAllStatuses() {
        List<StatusGuiEntity> geList = new ArrayList<>();
        List<Status> dbStatuses = ImmutableList.copyOf(statusDao.findAll());

        for (Status dbStatus : dbStatuses) {
            geList.add(convertDbToGui(dbStatus));
        }

        return geList;
    }

    public StatusGuiEntity findOneStatus(String id) {
        StatusGuiEntity statusGe = convertDbToGui(statusDao.findById(Long.valueOf(id)).get());
        return statusGe;
    }

}
