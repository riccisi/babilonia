package it.riccisi.babilonia.obsolete.domain.jpa;

import it.riccisi.babilonia.domain.FoundryConnection;
import it.riccisi.babilonia.domain.FoundryConnections;
import it.riccisi.babilonia.domain.jpa.JpaFoundryConnection;
import it.riccisi.babilonia.domain.jpa.entity.FoundryConnectionEntity;
import it.riccisi.babilonia.domain.jpa.repository.FoundryConnectionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterator.Mapped;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;

@Service("foundryConnections")
@RequiredArgsConstructor
public final class JpaFoundryConnections implements FoundryConnections {

    @NonNull private final FoundryConnectionRepository connectionRepo;

    @Override
    public FoundryConnection create(String name) {
        return new it.riccisi.babilonia.domain.jpa.JpaFoundryConnection(
            new FoundryConnectionEntity(name),
            this.connectionRepo
        );
    }

    @Override
    public Optional<FoundryConnection> getById(String id) {
        return this.connectionRepo
            .findById(id)
            .map(entity -> new it.riccisi.babilonia.domain.jpa.JpaFoundryConnection(entity, this.connectionRepo));
    }

    @Override
    public Optional<FoundryConnection> getBySecret(String secret) {
        return this.connectionRepo
            .findBySecret(secret)
            .map(entity -> new it.riccisi.babilonia.domain.jpa.JpaFoundryConnection(entity, this.connectionRepo));
    }

    @Override
    public Optional<FoundryConnection> getByInstanceId(String instanceId) {
        return this.connectionRepo
            .findByInstanceId(instanceId)
            .map(entity -> new it.riccisi.babilonia.domain.jpa.JpaFoundryConnection(entity, this.connectionRepo));
    }

    @Override
    public Iterator<FoundryConnection> iterator() {
        return new Mapped<>(
            entity -> new JpaFoundryConnection(entity, this.connectionRepo),
            this.connectionRepo.findAll().iterator()
        );
    }
}
