package it.riccisi.babilonia.domain;

import java.util.Map;

/**
 * Represents a project within the system, allowing for the management of members,
 * compendiums, and progress tracking. Provides details such as ID, name, description,
 * instance, system information, and project mappings.
 */
public interface Project {

    String id();
    String name();
    String description();
    String instanceId();
    String system();
    String world();
    Map<ItemType, Mappings> mappings();
    ProjectStatus status();
    double progress();
    Iterable<Member> members();
    Iterable<Compendium> compendiums();

    void setStatus(ProjectStatus projectStatus);
    void updateProgress(double progress);

    void addMember(User user, ProjectRole projectRole);
    void removeMember(Member member);
    void addCompendium(CompendiumData compendium);
    void removeCompendium(Compendium compendium);

    void save();
    void delete();
}