package uz.zako.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {

    public ResponseEntity<?> upload(MultipartFile multipartFile);

    public ResponseEntity<?> preview(String hashId);

    public ResponseEntity<?> download(String hashId);

    public ResponseEntity<?> delete(String hashId);

    public ResponseEntity<?> getAllAttachment();

}
