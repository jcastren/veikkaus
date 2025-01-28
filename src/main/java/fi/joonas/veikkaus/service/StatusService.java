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

    public Long insert(StatusGuiEntity status) {

        return statusDao.save(status.toDbEntity()).getId();
    }

    public Long modify(StatusGuiEntity status) {

        return statusDao.save(status.toDbEntity()).getId();
    }

    public boolean delete(String id) {

        boolean succeed;
        statusDao.deleteById(Long.valueOf(id));
        succeed = true;
        return succeed;
    }

    public List<StatusGuiEntity> findAllStatuses() {

        List<StatusGuiEntity> statusGuiEntityList = new ArrayList<>();
        List<Status> dbStatuses = ImmutableList.copyOf(statusDao.findAll());

        for (Status dbStatus : dbStatuses) {
            statusGuiEntityList.add(dbStatus.toGuiEntity());
        }

        return statusGuiEntityList;
    }

    public StatusGuiEntity findOneStatus(String id) {

        return statusDao.findById(Long.valueOf(id)).get().toGuiEntity();
    }
}
