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

    public Long insert(UserRoleGuiEntity userRole) {

        return userRoleDao.save(userRole.toDbEntity()).getId();
    }

    public Long modify(UserRoleGuiEntity userRole) {

        return userRoleDao.save(userRole.toDbEntity()).getId();
    }

    public boolean delete(String id) {

        boolean succeed;
        userRoleDao.deleteById(Long.valueOf(id));
        succeed = true;
        return succeed;
    }

    public List<UserRoleGuiEntity> findAllUserRoles() {

        List<UserRoleGuiEntity> userRoleGuiEntityList = new ArrayList<>();
        List<UserRole> dbTournaments = ImmutableList.copyOf(userRoleDao.findAll());

        for (UserRole dbTournament : dbTournaments) {
            userRoleGuiEntityList.add(dbTournament.toGuiEntity());
        }

        return userRoleGuiEntityList;
    }

    public UserRoleGuiEntity findOneUserRole(String id) {

        return userRoleDao.findById(Long.valueOf(id)).get().toGuiEntity();
    }

}
