package it.riccisi.babilonia.domain.jpa;

import it.riccisi.babilonia.domain.FoundryConnection;
import it.riccisi.babilonia.domain.FoundryConnections;
import it.riccisi.babilonia.domain.jpa.entity.FoundryConnectionEntity;
import it.riccisi.babilonia.domain.jpa.repository.FoundryConnectionRepository;
import it.riccisi.babilonia.domain.jpa.repository.Repositories;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.cactoos.iterator.Mapped;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;

@Service("foundryConnections")
@RequiredArgsConstructor
public final class JpaFoundryConnections implements FoundryConnections {

    @NonNull private final Repositories repo;

    @Override
    public FoundryConnection create(String name) {
        return new JpaFoundryConnection(
            new FoundryConnectionEntity(name),
            this.repo
        );
    }

    @Override
    public Optional<FoundryConnection> getById(String id) {
        return this.repo.foundryConnections()
            .findById(id)
            .map(entity -> new JpaFoundryConnection(entity, this.repo));
    }

    @Override
    public Optional<FoundryConnection> getBySecret(String secret) {
        return this.repo.foundryConnections()
            .findBySecret(secret)
            .map(entity -> new JpaFoundryConnection(entity, this.repo));
    }

    @Override
    public Optional<FoundryConnection> getByInstanceId(String instanceId) {
        return this.repo.foundryConnections()
            .findByInstanceId(instanceId)
            .map(entity -> new JpaFoundryConnection(entity, this.repo));
    }

    @Override
    public Iterator<FoundryConnection> iterator() {
        return new Mapped<>(
            entity -> new JpaFoundryConnection(entity, this.repo),
            this.repo.foundryConnections().findAll().iterator()
        );
    }
}
