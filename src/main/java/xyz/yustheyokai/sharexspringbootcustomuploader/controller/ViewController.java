package xyz.yustheyokai.sharexspringbootcustomuploader.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ViewController.VIEW)
public class ViewController {

    protected static final String VIEW = "/view";

    @Value("${sharex.folder}")
    private String folder;

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    @GetMapping("/{name}")
    public ResponseEntity<Resource> single(@PathVariable String name) {
        try {
            var bytes = Files.readAllBytes(Path.of(folder, name));
            var resource = new ByteArrayResource(bytes);

            BodyBuilder builder = ResponseEntity.ok().contentLength(resource.contentLength());

            if (name.contains(".png")) {
                builder.contentType(MediaType.IMAGE_PNG);
            } else if (name.contains(".pdf")) {
                builder.contentType(MediaType.APPLICATION_PDF);
            } else if (name.contains(".wav")) {
                builder.header("Content-Type", "audio/wav");
            }

            return builder.body(resource);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
