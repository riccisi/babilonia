package it.riccisi.babilonia.obsolete.api;

import it.riccisi.babilonia.infrastructure.image.ImageData;
import it.riccisi.babilonia.infrastructure.image.ImageStore;
import it.riccisi.babilonia.infrastructure.image.filesystem.ImageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageStore imageStore;

    @GetMapping("/{hash}")
    public ResponseEntity<byte[]> getImage(@PathVariable String hash) {
        try {
            ImageData image = imageStore.load(hash);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(image.mimeType()));
            return new ResponseEntity<>(image.content(), headers, HttpStatus.OK);
        } catch (ImageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}