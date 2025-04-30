package it.riccisi.babilonia.obsolete.infrastructure.image.filesystem;

public final class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(String message) {
        super(message);
    }
}