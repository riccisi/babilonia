package it.riccisi.babilonia.obsolete.domain;

import it.riccisi.babilonia.domain.Member;
import it.riccisi.babilonia.domain.Project;

public interface User {

    String id();
    String username();

    Member asMemberOf(Project project);

    Iterable<Project> projects();
}