package it.riccisi.babilonia.obsolete.domain;

import it.riccisi.babilonia.domain.Project;
import it.riccisi.babilonia.domain.User;

public interface Member {

    User user();

    Project project();

    String role();

    boolean canRead();

    boolean canTranslate();

    boolean canManageCompendium();

    boolean canManageProject();
}