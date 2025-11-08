package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.UserRoleDao;
import fi.joonas.veikkaus.exception.VeikkausNotFoundException;
import fi.joonas.veikkaus.guientity.UserRoleGuiEntity;
import fi.joonas.veikkaus.jpaentity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleService {

    private final UserRoleDao userRoleDao;

    @Autowired
    public UserRoleService(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    public List<UserRoleGuiEntity> findAllUserRoles() {
        List<UserRoleGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(userRoleDao.findAll()).forEach(userRole -> geList.add(convertDbToGui(userRole)));
        return geList;
    }

    public UserRoleGuiEntity findOneUserRole(Long id) {
        return convertDbToGui(getFromDb(id));
    }

    public UserRoleGuiEntity insert(UserRoleGuiEntity userRole) {
        return save(userRole);
    }

    public UserRoleGuiEntity update(UserRoleGuiEntity userRole) {
        getFromDb(userRole.getId());
        return save(userRole);
    }

    public void delete(Long id) {
        userRoleDao.delete(getFromDb(id));
    }

    public UserRole getFromDb(Long id) {
        return userRoleDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(UserRole.class, id));
    }

    private UserRoleGuiEntity save(UserRoleGuiEntity userRole) {
        return convertDbToGui(userRoleDao.save(convertGuiToDb(userRole)));
    }

    protected static UserRoleGuiEntity convertDbToGui(UserRole db) {
        UserRoleGuiEntity ge = new UserRoleGuiEntity();

        ge.setId(db.getId());
        ge.setName(db.getName());
        return ge;
    }

    protected static UserRole convertGuiToDb(UserRoleGuiEntity ge) {
        UserRole db = new UserRole();
        db.setId(ge.getId());
        db.setName(ge.getName());
        return db;
    }
}
