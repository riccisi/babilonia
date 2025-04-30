package it.riccisi.babilonia.obsolete.infrastructure.image;

import it.riccisi.babilonia.infrastructure.image.ImageData;

public interface ImageStore {
    String save(byte[] imageBytes, String originalPath);
    boolean exists(String hash);
    ImageData load(String hash); // restituisce contenuto + MIME type
}