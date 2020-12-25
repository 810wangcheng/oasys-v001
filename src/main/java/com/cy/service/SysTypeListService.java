package com.cy.service;

import com.cy.dao.TypeListDao;
import com.cy.entity.system.SystemTypeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class SysTypeListService {

    @Autowired
    private TypeListDao typeListDao;

    public SystemTypeList findTypeById(Long typeId) {
        return typeListDao.findOne(typeId);
    }

    public String findTypeNameById(Long attendsId) {
        return typeListDao.findname(attendsId);
    }

    public List<SystemTypeList> findTypeByTypeModel(String typeModel) {
        return typeListDao.findByTypeModel(typeModel);
    }

    public List<SystemTypeList> findAllType() {
        return typeListDao.findAll();
    }

    public SystemTypeList findTypeListByTypeId(long typeid) {
        return typeListDao.findOne(typeid);
    }

    public SystemTypeList saveTypeList(SystemTypeList typeList) {
        return typeListDao.save(typeList);
    }

    public void deleteTypeListByTypeId(long typeId) {
        typeListDao.delete(typeId);
    }

    public List<SystemTypeList> findTypeListByNameOrModel(String nameOrModel) {
        return typeListDao.findByTypeNameLikeOrTypeModelLike(nameOrModel,nameOrModel);
    }
}
