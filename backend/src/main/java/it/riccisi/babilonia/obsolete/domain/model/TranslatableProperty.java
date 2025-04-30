package it.riccisi.babilonia.obsolete.domain.model;

import it.riccisi.babilonia.domain.TranslationStatus;

public record TranslatableProperty(
    String jsonPath,
    String original,
    String translated,
    TranslationStatus status
) {}
