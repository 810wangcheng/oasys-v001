package com.cy.service;

import com.cy.dao.AttachmentDao;
import com.cy.dao.FileListDao;
import com.cy.dao.FilePathDao;
import com.cy.entity.file.FileList;
import com.cy.entity.file.FilePath;
import com.cy.entity.note.Attachment;
import com.cy.entity.user.User;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Administrator
 */
@Service
public class FileListService {
    Logger logger = LoggerFactory.getLogger(FileListService.class);

    @Autowired
    private FileListDao fileListDao;

    @Autowired
    private FilePathDao filePathDao;

    @Autowired
    private AttachmentDao attachmentDao;

    private String rootPath;

    @PostConstruct
    public void createFilePath(){
        try {
            String temp = ResourceUtils.getURL("classpath:").getPath().replace("target/classes/","static/file");
            rootPath = URLDecoder.decode(temp, "utf-8");
            logger.info("文件rootPath:{}",rootPath);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("获取项目路径异常");
        }
    }

    public Long getCount() {
        return fileListDao.count();
    }

    /**
     * 保存文件以及附件
     * @param file
     * @param user
     * @param nowPath
     * @param isFile
     * @return
     * @throws IOException
     */
    public Object saveFile(MultipartFile file, User user, FilePath nowPath, boolean isFile) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        String tmp = simpleDateFormat.format(new Date())+"/" + user.getUserName();
        //文件路径
        File savePath = new File(this.rootPath, tmp);
        //逐层创建文件路径
        if (!savePath.exists()){
            savePath.mkdirs();
        }

        //获取文件扩展名
        String shuffix = FilenameUtils.getExtension(file.getOriginalFilename());
        logger.info("shuffix:{}",shuffix);
        //拼接文件名称
        String newFileName = UUID.randomUUID().toString().toLowerCase() + "." + shuffix;

        tmp = "/" +tmp+ "/" +newFileName;
        File targetFile = new File(savePath, newFileName);
        file.transferTo(targetFile);

        if (isFile){
            FileList fileList = new FileList();
            String fileName = file.getOriginalFilename();
            fileName = onlyName(fileName,nowPath,shuffix,1,true);
            fileList.setFileName(fileName);
            fileList.setFilePath(tmp);
            fileList.setFileShuffix(shuffix);
            fileList.setSize(file.getSize());
            fileList.setUploadTime(new Date());
            fileList.setFpath(nowPath);
            fileList.setContentType(file.getContentType());
            fileList.setUser(user);
            fileListDao.save(fileList);
            return fileList;
        }else{
            Attachment attachment = new Attachment();
            attachment.setAttachmentName(file.getOriginalFilename());
            attachment.setAttachmentPath(tmp);
            attachment.setAttachmentShuffix(shuffix);
            attachment.setAttachmentSize(file.getSize());
            attachment.setAttachmentType(file.getContentType());
            attachment.setUploadTime(new Date());
            attachment.setUserId(user.getUserId()+"");
            attachment.setModel("note");
            attachmentDao.save(attachment);
            return attachment;
        }
    }

    /**
     * 遍历文件夹获取文件名
     * 文件以及路径同名处理
     * @param fileName
     * @param filePath
     * @param shuffix
     * @param num
     * @param isFile
     * @return
     */
    private String onlyName(String fileName, FilePath filePath, String shuffix, int num, boolean isFile) {
        Object f = null;
        if (isFile){
            f = fileListDao.findByFileNameAndFpath(fileName,filePath);
        }else{
            f = filePathDao.findByPathNameAndParentId(fileName,filePath.getId());
        }

        if (f != null){
            int num2 = num -1;
            if (shuffix == null){
                fileName = fileName.replace("("+num2+")","") + "("+num+")";
            }else {
                fileName = fileName.replace("."+shuffix,"").replace("("+num2+")","") + "("+num+")"+"."+shuffix;
            }
            num += 1;
            return onlyName(fileName,filePath,shuffix,num,isFile);
        }
        return fileName;
    }

    /**
     * 修改附件
     * @param file
     * @param user
     * @param nowPath
     * @param attachId
     * @return
     */
    public Integer updateAtt(MultipartFile file, User user, FilePath nowPath, Long attachId) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        //创建路径 rootPath/tmp/fileName
        String tmp = simpleDateFormat.format(new Date()) + "/" +user.getUserName();
        File savePath = new File(this.rootPath, tmp);
        logger.info("文件的保存路径为:{}",savePath);

        //路径不存在逐层创建
        if (!savePath.exists()){
            savePath.mkdirs();
        }

        if (!file.isEmpty()){
            //获取扩展名
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            logger.info("extension:{}",extension);
            //拼接文件名称
            String newFileName = UUID.randomUUID().toString().toLowerCase()+"."+extension;
            //创建目标文件
            File targetFile = new File(savePath, newFileName);
            file.transferTo(targetFile);
            tmp = "/" + tmp +"/" + newFileName;
            return attachmentDao.updateAtt(file.getOriginalFilename(),tmp,extension,file.getSize(),file.getContentType(),new Date(),attachId);
        }
        return 0;
    }
}
