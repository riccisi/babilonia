package it.riccisi.babilonia.domain.jpa.repository;

public interface Repositories {
    CompendiumRepository compendiums();
    TranslationRepository translations();
    CompendiumMappingsRepository compendiumMappings();
    DefaultMappingsRepository defaultMappings();
    ProjectRepository projects();
    UserRepository users();
    ProjectMembershipRepository projectMemberships();
    ProjectMappingsRepository projectMappings();
    FoundryConnectionRepository connections();
}