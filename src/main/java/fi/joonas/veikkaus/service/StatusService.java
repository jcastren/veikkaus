package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.StatusDao;
import fi.joonas.veikkaus.exception.VeikkausNotFoundException;
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

    StatusDao statusDao;

    @Autowired
    public StatusService(StatusDao statusDao) {
        this.statusDao = statusDao;
    }

    public List<StatusGuiEntity> findAllStatuses() {
        List<StatusGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(statusDao.findAll()).forEach(status -> geList.add(convertDbToGui(status)));
        return geList;
    }

    public StatusGuiEntity findOneStatus(Long id) {
        return convertDbToGui(getFromDb(id));
    }

    public StatusGuiEntity insert(StatusGuiEntity status) {
        return save(status);
    }

    public StatusGuiEntity update(StatusGuiEntity status) {
        getFromDb(status.getId());
        return save(status);
    }

    public void delete(Long id) {
        statusDao.delete(getFromDb(id));
    }

    public Status getFromDb(Long id) {
        return statusDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(Status.class, id));
    }

    private StatusGuiEntity save(StatusGuiEntity status) {
        return convertDbToGui(statusDao.save(convertGuiToDb(status)));
    }

    protected static StatusGuiEntity convertDbToGui(Status db) {
        StatusGuiEntity ge = new StatusGuiEntity();

        ge.setId(db.getId());
        ge.setStatusNumber(db.getStatusNumber());
        ge.setDescription(db.getDescription());
        return ge;
    }

    protected static Status convertGuiToDb(StatusGuiEntity ge) {
        Status db = new Status();

        db.setId(ge.getId());
        db.setStatusNumber(ge.getStatusNumber());
        db.setDescription(ge.getDescription());
        return db;
    }
}