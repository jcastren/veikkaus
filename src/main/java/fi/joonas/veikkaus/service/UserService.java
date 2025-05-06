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

    protected static UserGuiEntity convertDbToGui(User db) {
        UserGuiEntity ge = new UserGuiEntity();

        ge.setId(db.getId().toString());
        ge.setEmail(db.getEmail());
        ge.setName(db.getName());
        ge.setPassword(db.getPassword());
        ge.setUserRole(UserRoleService.convertDbToGui(db.getUserRole()));
        return ge;
    }

    protected static User convertGuiToDb(UserGuiEntity ge) {
        User db = new User();

        if (ge.getId() != null && !ge.getId().isEmpty()) {
            db.setId(Long.valueOf(ge.getId()));
        } else {
            db.setId(null);
        }
        db.setEmail(ge.getEmail());
        db.setName(ge.getName());
        db.setPassword(ge.getPassword());
        db.setUserRole(UserRoleService.convertGuiToDb(ge.getUserRole()));

        return db;
    }

    public Long insert(UserGuiEntity userGe) throws VeikkausServiceException {
        String userRoleId = userGe.getUserRole().getId();
        Optional<UserRole> userRoleDb = userRoleDao.findById(Long.valueOf(userRoleId));
        if (userRoleDb.isEmpty()) {
            throw new VeikkausServiceException("User role with id: %s wasn't found, insert failed".formatted(userRoleId));
        }

        /** TODO Why is userRole set again? Did it miss originally some fields????? */
        userGe.setUserRole(UserRoleService.convertDbToGui(userRoleDb.get()));

        return userDao.save(convertGuiToDb(userGe)).getId();
    }

    public Long modify(UserGuiEntity userGe) throws VeikkausServiceException {
        String id = userGe.getId();
        Optional<User> userDb = userDao.findById(Long.valueOf(id));
        if (userDb.isEmpty()) {
            throw new VeikkausServiceException("User with id: %s wasn't found, modify failed".formatted(id));
        }

        String userRoleId = userGe.getUserRole().getId();
        Optional<UserRole> userRoleDb = userRoleDao.findById(Long.valueOf(userRoleId));
        if (userRoleDb.isEmpty()) {
            throw new VeikkausServiceException("User role with id: %s wasn't found, modify failed".formatted(userRoleId));
        }

        userGe.setUserRole(UserRoleService.convertDbToGui(userRoleDb.get()));

        return userDao.save(convertGuiToDb(userGe)).getId();
    }

    public boolean delete(String id) throws VeikkausServiceException {
        userDao.deleteById(Long.valueOf(id));
        return true;
    }

    public List<UserGuiEntity> findAllUsers() {
        List<UserGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(userDao.findAll()).forEach(user -> geList.add(convertDbToGui(user)));
        return geList;
    }

    public UserGuiEntity findOneUser(String id) {
        return convertDbToGui(userDao.findById(Long.valueOf(id)).get());
    }

}
