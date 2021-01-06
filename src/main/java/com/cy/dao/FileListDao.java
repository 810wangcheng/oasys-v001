package com.cy.dao;

import com.cy.entity.file.FileList;
import com.cy.entity.file.FilePath;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 */
public interface FileListDao extends JpaRepository<FileList,Long> {

    /**
     * 根据文件名称和路径查询唯一文件
     * @param fileName
     * @param filePath
     * @return
     */
    FileList findByFileNameAndFpath(String fileName, FilePath filePath);

}
