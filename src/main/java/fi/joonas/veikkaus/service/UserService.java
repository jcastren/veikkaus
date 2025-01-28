package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.UserDao;
import fi.joonas.veikkaus.dao.UserRoleDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.UserGuiEntity;
import fi.joonas.veikkaus.jpaentity.User;
import fi.joonas.veikkaus.jpaentity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    UserRoleDao userRoleDao;

    public Long insert(UserGuiEntity userGuiEntity) throws VeikkausServiceException {

        String userRoleId = userGuiEntity.getUserRole().getId();
        Optional<UserRole> userRoleDb = userRoleDao.findById(Long.valueOf(userRoleId));
        if (!userRoleDb.isPresent()) {
            throw new VeikkausServiceException("User role with id: " + userRoleId + " wasn't found, insert failed");
        }

        /** TODO Why is userRole set again? Did it miss originally some fields????? */
        userGuiEntity.setUserRole(userRoleDb.get().toGuiEntity());

        return userDao.save(userGuiEntity.toDbEntity()).getId();
    }

    public Long modify(UserGuiEntity userGuiEntity) throws VeikkausServiceException {

        String id = userGuiEntity.getId();
        Optional<User> userDb = userDao.findById(Long.valueOf(id));
        if (!userDb.isPresent()) {
            throw new VeikkausServiceException("User with id: " + id + " wasn't found, modify failed");
        }

        String userRoleId = userGuiEntity.getUserRole().getId();
        Optional<UserRole> userRoleDb = userRoleDao.findById(Long.valueOf(userRoleId));
        if (!userRoleDb.isPresent()) {
            throw new VeikkausServiceException("User role with id: " + id + " wasn't found, modify failed");
        }

        userGuiEntity.setUserRole(userRoleDb.get().toGuiEntity());

        return userDao.save(userGuiEntity.toDbEntity()).getId();
    }

    public boolean delete(String id) throws VeikkausServiceException {

        boolean succeed;
        userDao.deleteById(Long.valueOf(id));
        succeed = true;
        return succeed;
    }

    public List<UserGuiEntity> findAllUsers() {

        List<UserGuiEntity> guiEntityList = new ArrayList<>();
        List<User> dbUsers = ImmutableList.copyOf(userDao.findAll());

        for (User dbUser : dbUsers) {
            guiEntityList.add(dbUser.toGuiEntity());
        }

        return guiEntityList;
    }

    public UserGuiEntity findOneUser(String id) {

        return userDao.findById(Long.valueOf(id)).get().toGuiEntity();
    }

}
