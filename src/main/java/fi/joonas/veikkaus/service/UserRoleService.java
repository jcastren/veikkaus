package fi.joonas.veikkaus.service;

import fi.joonas.veikkaus.dao.UserRoleDao;
import fi.joonas.veikkaus.guientity.UserRoleGuiEntity;
import fi.joonas.veikkaus.jpaentity.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleDao userRoleDao;

    public Long insert(UserRoleGuiEntity userRole) {

        return userRoleDao.save(userRole.toDbEntity()).getId();
    }

    public Long modify(UserRoleGuiEntity userRole) {

        return userRoleDao.save(userRole.toDbEntity()).getId();
    }

    public boolean delete(String id) {

        userRoleDao.deleteById(Long.valueOf(id));
        return true;
    }

    public List<UserRoleGuiEntity> findAllUserRoles() {

        List<UserRoleGuiEntity> userRoleGuiEntityList = new ArrayList<>();
        userRoleDao.findAll().forEach(userRole -> userRoleGuiEntityList.add(userRole.toGuiEntity()));
        return userRoleGuiEntityList;
    }

    public UserRoleGuiEntity findOneUserRole(String id) {

        return userRoleDao.findById(Long.valueOf(id))
                .map(UserRole::toGuiEntity)
                .orElse(UserRoleGuiEntity.builder().build());
    }

}
