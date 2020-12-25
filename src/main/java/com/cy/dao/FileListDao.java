package com.cy.dao;

import com.cy.entity.file.FileList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 */
public interface FileListDao extends JpaRepository<FileList,Long> {
}
