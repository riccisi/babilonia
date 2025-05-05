package it.riccisi.babilonia.domain.jpa;

import it.riccisi.babilonia.domain.ConnectionStatus;
import it.riccisi.babilonia.domain.FoundryConnection;
import it.riccisi.babilonia.domain.exception.AlreadyPairedException;
import it.riccisi.babilonia.domain.exception.InvalidPairingException;
import it.riccisi.babilonia.domain.jpa.entity.FoundryConnectionEntity;

import it.riccisi.babilonia.domain.jpa.repository.Repositories;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class JpaFoundryConnection implements FoundryConnection {

    @NonNull private final FoundryConnectionEntity entity;
    @NonNull private final Repositories repo;

    @Override
    public String id() {
        return this.entity.getId();
    }

    @Override
    public String name() {
        return this.entity.getName();
    }

    @Override
    public String secret() {
        return this.entity.getSecret();
    }

    @Override
    public Optional<String> instanceId() {
        return Optional.of(this.entity.getInstanceId());
    }

    @Override
    public ConnectionStatus status() {
        return this.entity.getStatus();
    }

    @Override
    public void confirmPairing(String provided, String instanceId) throws InvalidPairingException, AlreadyPairedException {
        if (!this.secret().equals(provided)) {
            throw new InvalidPairingException();
        }
        if (this.instanceId().isPresent() && !this.instanceId().get().equals(instanceId)) {
            throw new AlreadyPairedException();
        }
        this.entity.setInstanceId(instanceId);
        this.entity.setStatus(ConnectionStatus.INACTIVE);
    }

    @Override
    public void updateStatus(ConnectionStatus newStatus) {
        this.entity.setStatus(newStatus);
    }

    @Override
    public void save() {
        this.repo.connections().save(this.entity);
    }

    @Override
    public void delete() {
        this.repo.connections().delete(this.entity);
    }

    @Override
    public boolean checkToken(String token) {
        return this.secret().equals(token);
    }
}