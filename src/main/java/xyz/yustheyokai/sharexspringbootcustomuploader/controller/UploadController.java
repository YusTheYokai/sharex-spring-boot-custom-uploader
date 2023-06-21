package xyz.yustheyokai.sharexspringbootcustomuploader.controller;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static xyz.yustheyokai.sharexspringbootcustomuploader.controller.ViewController.VIEW;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;

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

    @Value("${sharex.url}")
    private String url;

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
        @RequestParam(value = "password", required = true) String password,
        @RequestParam(value = "file", required = true) MultipartFile file
    ) {
        if (!this.password.equals(password)) {
            return ResponseEntity.status(UNAUTHORIZED).build();
        }

        try {
            var filename = file.getOriginalFilename();
            file.transferTo(Path.of(folder, filename));
            // url needs to be prepended manually because
            // Path.of removes one of the slashes from the URL
            // (e.g. https://example.com/view/abc.png -> https:/example.com/view/abc.png)
            var fileUrl = url + Path.of(VIEW, filename).toString();
            return ResponseEntity.created(URI.create(fileUrl)).body(fileUrl);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
