package uz.zako.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.zako.entity.Attachment;
import uz.zako.model.Result;
import uz.zako.repository.AttachmentRepository;
import uz.zako.service.AttachmentService;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class  AttachmentServiceImpl implements AttachmentService {

    @Value(("upload"))
    private String uploadPath;
    private final AttachmentRepository attachmentRepository;

    @Override
    public ResponseEntity<?> upload(MultipartFile multipartFile) {

        try {
            Attachment attachment = new Attachment();
            attachment.setContentType(multipartFile.getContentType());
            attachment.setName(multipartFile.getName());
            attachment.setFileSize(multipartFile.getSize() / 8 / 1024);
            attachment.setExtension(getExtension(multipartFile.getOriginalFilename()));
            attachment.setHashId(UUID.randomUUID().toString());

            String uploadFolder = String.format("%s/%d/%d/%d/%s.%s", uploadPath,
                    1900 + new Date().getYear(),
                    1 + new Date().getMonth(),
                    new Date().getDate(),
                    attachment.getHashId(),
                    attachment.getExtension()
            );
            attachment.setUploadPath(uploadFolder);
            File catalog = new File(uploadFolder);

            if (!catalog.exists()) {
                catalog.mkdirs();
            }
            Attachment attachment1 = attachmentRepository.save(attachment);
            multipartFile.transferTo(catalog.getAbsoluteFile());

            return ResponseEntity.ok(new Result(true, attachment1.getHashId(), null));

        } catch (Exception e) {
            log.error("error save attachment - {}", e.getMessage());
            return new ResponseEntity(Result.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private String getExtension(String fileName) {
        String extension = null;
        if (fileName != null && !fileName.isEmpty()) {
//            dsaghjhsdj.a.jpg
            extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return extension;
    }

    @Override
    public ResponseEntity<?> preview(String hashId) {
        try {
            Optional<Attachment> attachment = attachmentRepository.findByHashId1(hashId);
            if (!attachment.isPresent()) {
                return new ResponseEntity(Result.error(String.format("attachment not found hashId - %s", hashId)), HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok().header("contentDisposition=inline&fileName=" + attachment.get().getName()).contentType(MediaType.parseMediaType(attachment.get().getContentType())).body(
                    new FileUrlResource(String.format("%s",
                            attachment.get().getUploadPath())));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new ResponseEntity(Result.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> download(String hashId) {
        try {
            Optional<Attachment> attachment = attachmentRepository.findByHashId1(hashId);
            if (!attachment.isPresent()) {
                return new ResponseEntity(Result.error(String.format("attachment not found hashId - %s", hashId)), HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok().header("contentDisposition=attachment&fileName=" + attachment.get().getName()).contentType(MediaType.parseMediaType(attachment.get().getContentType())).body(
                    new FileUrlResource(String.format("%s",
                            attachment.get().getUploadPath())));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new ResponseEntity(Result.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> delete(String hashId) {
        try {
            Optional<Attachment> attachment = attachmentRepository.findByHashId1(hashId);
            File file = new File(attachment.get().getUploadPath());
            System.out.println(file);
            if (file.delete()) {
                if (!attachmentRepository.deleteById(hashId)) {
                    return new ResponseEntity(Result.error(String.format("attachment not found hashId - %s", hashId)), HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity(Result.ok(attachment), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(Result.error("no delete"), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.getMessage();
            return new ResponseEntity(Result.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAllAttachment() {
        try {
            return ResponseEntity.ok(attachmentRepository.findAll());
        } catch (Exception e) {
            log.error("error getAllAttachment");
            return new ResponseEntity(new Result(false, "error", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
