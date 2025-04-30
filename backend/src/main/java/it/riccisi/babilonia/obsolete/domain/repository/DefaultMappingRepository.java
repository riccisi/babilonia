package it.riccisi.babilonia.obsolete.domain.repository;

import it.riccisi.babilonia.domain.service.jpa.entity.DefaultMapping;
import it.riccisi.babilonia.domain.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefaultMappingRepository extends JpaRepository<DefaultMapping, ItemType> {
}