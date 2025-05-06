package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.UserRoleDao;
import fi.joonas.veikkaus.guientity.UserRoleGuiEntity;
import fi.joonas.veikkaus.jpaentity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    UserRoleDao userRoleDao;

    protected static UserRoleGuiEntity convertDbToGui(UserRole db) {
        UserRoleGuiEntity ge = new UserRoleGuiEntity();

        ge.setId(db.getId().toString());
        ge.setName(db.getName());

        return ge;
    }

    protected static UserRole convertGuiToDb(UserRoleGuiEntity ge) {
        UserRole db = new UserRole();

        if (ge.getId() != null && !ge.getId().isEmpty()) {
            db.setId(Long.valueOf(ge.getId()));
        } else {
            db.setId(null);
        }
        db.setName(ge.getName());

        return db;
    }

    public Long insert(UserRoleGuiEntity userRole) {
        return userRoleDao.save(convertGuiToDb(userRole)).getId();
    }

    public Long modify(UserRoleGuiEntity userRole) {
        return userRoleDao.save(convertGuiToDb(userRole)).getId();
    }

    public boolean delete(String id) {
        userRoleDao.deleteById(Long.valueOf(id));
        return true;
    }

    public List<UserRoleGuiEntity> findAllUserRoles() {
        List<UserRoleGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(userRoleDao.findAll()).forEach(userRole -> geList.add(convertDbToGui(userRole)));
        return geList;
    }

    public UserRoleGuiEntity findOneUserRole(String id) {
        return convertDbToGui(userRoleDao.findById(Long.valueOf(id)).get());
    }

}
