package it.riccisi.babilonia.domain;

import java.util.concurrent.Future;

public interface FoundrySession {

    /** L’ID dell’istanza di Foundry su cui siamo connessi. */
    String instanceId();

    Future<CompendiumData> fetchCompendiumData(String key);

}