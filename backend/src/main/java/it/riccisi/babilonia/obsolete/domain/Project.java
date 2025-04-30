package it.riccisi.babilonia.obsolete.domain;

import it.riccisi.babilonia.domain.*;
import it.riccisi.babilonia.domain.Member;
import it.riccisi.babilonia.domain.User;

public interface Project {

    String id();
    String name();
    String description();
    String instanceId();
    String system();
    String world();
    ProjectStatus status();
    double progress();

    Iterable<Member> members();
    Iterable<Compendium> compendiums();

    void addMember(User user, ProjectRole role);
    void addCompendium(String key, String name, String module, String lang, ItemType type, String json);

    void save();

    void setStatus(ProjectStatus projectStatus);

    void updateProgress(double progress);
}