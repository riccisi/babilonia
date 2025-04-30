package it.riccisi.babilonia.infrastructure.image;

public interface ImageStore {
    String save(byte[] imageBytes, String originalPath);
    boolean exists(String hash);
    ImageData load(String hash); // restituisce contenuto + MIME type
}