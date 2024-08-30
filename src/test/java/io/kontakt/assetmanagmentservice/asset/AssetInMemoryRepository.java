package io.kontakt.assetmanagmentservice.asset;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

class AssetInMemoryRepository implements AssetRepository {
    private final HashMap<UUID, Asset> assetHashMap = new HashMap<>();

    @Override
    public Page<Asset> findByIds(Set<UUID> assetIds, Pageable pageable) {
        List<Asset> filteredAssets = assetIds.stream()
                .map(assetHashMap::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return new PageImpl<>(filteredAssets, pageable, filteredAssets.size());
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Asset> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Asset> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Asset> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<UUID> uuids) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Asset getOne(UUID uuid) {
        return null;
    }

    @Override
    public Asset getById(UUID uuid) {
        return null;
    }

    @Override
    public Asset getReferenceById(UUID uuid) {
        return null;
    }

    @Override
    public <S extends Asset> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Asset> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Asset> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Asset> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Asset> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Asset> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Asset, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Asset> S save(S entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        assetHashMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends Asset> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Asset> findById(UUID uuid) {
        if (assetHashMap.containsKey(uuid)) {
            return Optional.of(assetHashMap.get(uuid));
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return assetHashMap.containsKey(uuid);
    }

    @Override
    public List<Asset> findAll() {
        return List.of();
    }

    @Override
    public List<Asset> findAllById(Iterable<UUID> uuids) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {
        assetHashMap.remove(uuid);
    }

    @Override
    public void delete(Asset entity) {
        assetHashMap.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends Asset> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Asset> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Asset> findAll(Pageable pageable) {
        List<Asset> allAssets = assetHashMap.values()
                .stream()
                .toList();

        return new PageImpl<>(allAssets, pageable, allAssets.size());
    }
}
