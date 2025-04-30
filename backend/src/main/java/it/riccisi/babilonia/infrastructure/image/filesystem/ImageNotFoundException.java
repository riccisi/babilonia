package it.riccisi.babilonia.infrastructure.image.filesystem;

public final class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(String message) {
        super(message);
    }
}