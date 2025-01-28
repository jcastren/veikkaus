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

    protected static UserRole convertGuiToDb(UserRoleGuiEntity userRoleGuiEntity) {

        UserRole db = new UserRole();

        if (userRoleGuiEntity.getId() != null && !userRoleGuiEntity.getId().isEmpty()) {
            db.setId(Long.valueOf(userRoleGuiEntity.getId()));
        } else {
            db.setId(null);
        }
        db.setName(userRoleGuiEntity.getName());

        return db;
    }

    /**
     * @param userRole
     * @return
     */
    public Long insert(UserRoleGuiEntity userRole) {

        return userRoleDao.save(convertGuiToDb(userRole)).getId();
    }

    /**
     * @param userRole
     * @return
     */
    public Long modify(UserRoleGuiEntity userRole) {

        return userRoleDao.save(convertGuiToDb(userRole)).getId();
    }

    /**
     * @param id
     * @return
     */
    public boolean delete(String id) {

        boolean succeed;
        userRoleDao.deleteById(Long.valueOf(id));
        succeed = true;
        return succeed;
    }

    /**
     * @return
     */
    public List<UserRoleGuiEntity> findAllUserRoles() {

        List<UserRoleGuiEntity> userRoleGuiEntityList = new ArrayList<>();
        List<UserRole> dbTournaments = ImmutableList.copyOf(userRoleDao.findAll());

        for (UserRole dbTournament : dbTournaments) {
            userRoleGuiEntityList.add(dbTournament.toGuiEntity());
        }

        return userRoleGuiEntityList;
    }

    /**
     * @param id
     * @return
     */
    public UserRoleGuiEntity findOneUserRole(String id) {

        return userRoleDao.findById(Long.valueOf(id)).get().toGuiEntity();
    }

}
