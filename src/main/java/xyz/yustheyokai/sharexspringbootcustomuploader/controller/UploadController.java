package xyz.yustheyokai.sharexspringbootcustomuploader.controller;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Value("${sharex.password}")
    private String password;

    @Value("${sharex.folder}")
    private String folder;

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    @PostMapping("/upload")
    public ResponseEntity<Void> upload(
        @RequestParam(value = "password", required = true) String password,
        @RequestParam(value = "file", required = true) MultipartFile file
    ) {
        if (!this.password.equals(password)) {
            return ResponseEntity.status(UNAUTHORIZED).build();
        }

        try {
            file.transferTo(Paths.get(folder, file.getOriginalFilename()));
            return ResponseEntity.ok().build();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
