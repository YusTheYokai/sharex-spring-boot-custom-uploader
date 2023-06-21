package xyz.yustheyokai.sharexspringbootcustomuploader.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/view")
public class ViewController {

    @Value("${sharex.folder}")
    private String folder;

    // /////////////////////////////////////////////////////////////////////////
    // Methods
    // /////////////////////////////////////////////////////////////////////////

    @GetMapping("/{name}")
    public ResponseEntity<Resource> single(@PathVariable String name) {
        try {
            var bytes = Files.readAllBytes(Paths.get(folder, name));
            var resource = new ByteArrayResource(bytes);
            return ResponseEntity.ok().contentLength(resource.contentLength()).contentType(MediaType.IMAGE_PNG).body(resource);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
