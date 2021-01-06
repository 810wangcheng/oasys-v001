package com.cy.dao;

import com.cy.entity.file.FilePath;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FilePathDao extends PagingAndSortingRepository<FilePath,Long> {

    /**
     * 根据路径名称和父路径id获取当前路径
     * @param fileName
     * @param id
     * @return
     */
    FilePath findByPathNameAndParentId(String fileName, Long id);
}
