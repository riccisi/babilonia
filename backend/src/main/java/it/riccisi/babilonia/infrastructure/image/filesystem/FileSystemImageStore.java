package it.riccisi.babilonia.infrastructure.image.filesystem;

import it.riccisi.babilonia.infrastructure.image.ImageData;
import it.riccisi.babilonia.infrastructure.image.ImageStore;
import it.riccisi.babilonia.infrastructure.util.HashUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileSystemImageStore implements ImageStore {

    private final String baseDir = "images";

    @Override
    public String save(byte[] imageBytes, String originalPath) {
        String hash = HashUtils.sha256(imageBytes);
        String extension = getExtension(originalPath);
        Path path = getPath(hash, extension);

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.write(path, imageBytes);
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore nel salvataggio dell'immagine", e);
        }

        return hash;
    }

    @Override
    public boolean exists(String hash) {
        return knownExtensions().stream()
            .anyMatch(ext -> Files.exists(getPath(hash, ext)));
    }

    @Override
    public ImageData load(String hash) {
        for (String ext : knownExtensions()) {
            Path path = getPath(hash, ext);
            if (Files.exists(path)) {
                try {
                    byte[] content = Files.readAllBytes(path);
                    String mime = Files.probeContentType(path);
                    return new ImageData(content, mime != null ? mime : "application/octet-stream");
                } catch (IOException e) {
                    throw new RuntimeException("Errore nella lettura dell'immagine", e);
                }
            }
        }

        throw new ImageNotFoundException("Immagine non trovata per hash: " + hash);
    }

    private Path getPath(String hash, String extension) {
        return Paths.get(baseDir, hash.substring(0, 2), hash + "." + extension);
    }

    private String getExtension(String filename) {
        int dot = filename.lastIndexOf('.');
        return (dot == -1) ? "bin" : filename.substring(dot + 1).toLowerCase();
    }

    private List<String> knownExtensions() {
        return List.of("png", "jpg", "jpeg", "webp", "gif", "svg");
    }
}