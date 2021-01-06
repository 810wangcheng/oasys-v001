package com.cy.dao;

import com.cy.entity.system.SystemTypeList;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Administrator
 */
public interface TypeListDao extends JpaRepository<SystemTypeList,Long> {

    @Query("select type.typeName from SystemTypeList type where type.typeId=:id")
    String findname(@Param("id") Long attendsId);

    /**
     * 通过类型查询系统类型列表
     * @param typeModel
     * @return
     */
    List<SystemTypeList> findByTypeModel(String typeModel);

    /**
     * 根据类型名称和类型模型进行模糊查询
     * @param name
     * @param model
     * @return
     */
    List<SystemTypeList> findByTypeNameLikeOrTypeModelLike(String name,String model);

    /**
     * 根据类型模型和名称查询唯一类型
     * @param aoa_plan_list
     * @param type
     * @return
     */
    SystemTypeList findByTypeModelAndTypeName(String aoa_plan_list, String type);
}
