package com.cy.dao;

import com.cy.entity.note.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * @author Administrator
 */
public interface AttachmentDao extends JpaRepository<Attachment,Long> {
    @Query("UPDATE Attachment a set a.attachmentName=?1,a.attachmentPath=?2,a.attachmentShuffix=?3,a.attachmentSize=?4,a.attachmentType=?5,a.uploadTime=?6 WHERE a.attachmentId=?7")
    @Modifying
    Integer updateAtt(String originalFilename, String tmp, String extension, long size, String contentType, Date date, Long attachId);
}
