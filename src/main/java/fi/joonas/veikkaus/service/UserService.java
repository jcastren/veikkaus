package fi.joonas.veikkaus.service;

import com.google.common.collect.ImmutableList;
import fi.joonas.veikkaus.dao.UserDao;
import fi.joonas.veikkaus.exception.VeikkausNotFoundException;
import fi.joonas.veikkaus.exception.VeikkausServiceException;
import fi.joonas.veikkaus.guientity.UserGuiEntity;
import fi.joonas.veikkaus.jpaentity.User;
import fi.joonas.veikkaus.jpaentity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;
    private final UserRoleService userRoleService;

    @Autowired
    public UserService(UserDao userDao, UserRoleService userRoleService) {
        this.userDao = userDao;
        this.userRoleService = userRoleService;
    }

    public List<UserGuiEntity> findAllUsers() {
        List<UserGuiEntity> geList = new ArrayList<>();
        ImmutableList.copyOf(userDao.findAll()).forEach(user -> geList.add(convertDbToGui(user)));
        return geList;
    }

    public UserGuiEntity findOneUser(Long id) {
        return convertDbToGui(getFromDb(id));
    }

    public UserGuiEntity insert(UserGuiEntity user) throws VeikkausServiceException {
        return save(user);
    }

    public UserGuiEntity update(UserGuiEntity user) throws VeikkausServiceException {
        getFromDb(user.getId());
        return save(user);
    }

    public void delete(Long id) {
        userDao.delete(getFromDb(id));
    }

    public User getFromDb(Long id) {
        return userDao.findById(id).orElseThrow(() -> new VeikkausNotFoundException(User.class, id));
    }

    private UserGuiEntity save(UserGuiEntity user) {
        UserRole userRoleDb = userRoleService.getFromDb(user.getUserRole().getId());
        return convertDbToGui(userDao.save(convertGuiToDb(user, userRoleDb)));
    }

    protected static UserGuiEntity convertDbToGui(User db) {
        UserGuiEntity ge = new UserGuiEntity();

        ge.setId(db.getId());
        ge.setEmail(db.getEmail());
        ge.setName(db.getName());
        ge.setPassword(db.getPassword());
        ge.setUserRole(UserRoleService.convertDbToGui(db.getUserRole()));
        return ge;
    }

    protected static User convertGuiToDb(UserGuiEntity ge, UserRole userRoleDb) {
        User db = new User();

        db.setId(ge.getId());
        db.setEmail(ge.getEmail());
        db.setName(ge.getName());
        db.setPassword(ge.getPassword());
        db.setUserRole(userRoleDb);
        return db;
    }
}