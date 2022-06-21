package uz.zako.controller;//package uz.baxtiyor.swagger_token.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.zako.service.AttachmentService;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @GetMapping("/")
    @ApiOperation("Hello chiqarish")
    public String get() {
        return "Hello:)";
    }

    @PostMapping("/")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile multipartFile) {
        System.out.println(multipartFile.toString());
        return attachmentService.upload(multipartFile);
    }

    @GetMapping("/preview/{hashId}")
    public ResponseEntity<?> getPreview(@PathVariable("hashId") String hashId) {
        return attachmentService.preview(hashId);
    }

    @GetMapping("/download/{hashId}")
    public ResponseEntity<?> getDownload(@PathVariable("hashId") String hashId) {
        return attachmentService.download(hashId);
    }

    @GetMapping("/delete/{hashId}")
    public ResponseEntity<?> getDelete(@PathVariable("hashId") String hashId) {
        return attachmentService.delete(hashId);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAttachment() {
        return attachmentService.getAllAttachment();
    }

}
