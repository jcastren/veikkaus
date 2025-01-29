package fi.joonas.veikkaus.service;

import fi.joonas.veikkaus.dao.UserDao;
import fi.joonas.veikkaus.dao.UserRoleDao;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.UserGuiEntity;
import fi.joonas.veikkaus.jpaentity.User;
import fi.joonas.veikkaus.jpaentity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    private final UserRoleDao userRoleDao;

    public Long insert(UserGuiEntity userGuiEntity) throws VeikkausServiceException {

        String userRoleId = userGuiEntity.getUserRole().getId();
        UserRole userRoleDb = userRoleDao.findById(Long.valueOf(userRoleId))
                .orElseThrow(() -> new VeikkausServiceException("User role with id: %s wasn't found, insert failed".formatted(userRoleId)));

        /** TODO Why is userRole set again? Did it miss originally some fields????? */
        userGuiEntity.setUserRole(userRoleDb.toGuiEntity());

        return userDao.save(userGuiEntity.toDbEntity()).getId();
    }

    public Long modify(UserGuiEntity userGuiEntity) throws VeikkausServiceException {

        String id = userGuiEntity.getId();
        userDao.findById(Long.valueOf(id))
                .orElseThrow(() -> new VeikkausServiceException("User with id: %s wasn't found, modify failed".formatted(id)));

        String userRoleId = userGuiEntity.getUserRole().getId();
        UserRole userRoleDb = userRoleDao.findById(Long.valueOf(userRoleId))
                .orElseThrow(() -> new VeikkausServiceException("User role with id: %s wasn't found, modify failed".formatted(userRoleId)));

        userGuiEntity.setUserRole(userRoleDb.toGuiEntity());

        return userDao.save(userGuiEntity.toDbEntity()).getId();
    }

    public boolean delete(String id) throws VeikkausServiceException {

        userDao.deleteById(Long.valueOf(id));
        return true;
    }

    public List<UserGuiEntity> findAllUsers() {

        List<UserGuiEntity> guiEntityList = new ArrayList<>();
        userDao.findAll().forEach(user -> guiEntityList.add(user.toGuiEntity()));
        return guiEntityList;
    }

    public UserGuiEntity findOneUser(String id) {

        return userDao.findById(Long.valueOf(id))
                .map(User::toGuiEntity)
                .orElse(UserGuiEntity.builder().build());
    }

}
