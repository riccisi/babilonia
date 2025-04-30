package it.riccisi.babilonia.obsolete.domain;

public enum ConnectionStatus {
    UNPAIRED,    // appena creato, nessun tentativo di pairing
    ACTIVE,      // pairing completato, sessione web-socket attiva
    INACTIVE     // pairing completato ma Babele non è connesso ora
}
