package io.kontakt.assetmanagmentservice.assetgroup;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

class AssetGroupInMemoryRepository implements AssetGroupRepository {
    private final HashMap<UUID, AssetGroup> assetGroupHashMap = new HashMap<>();

    @Override
    public void deleteAllByAssetId(UUID assetId) {
        assetGroupHashMap.forEach((assetGroupId, assetGroup) -> {
            assetGroup.getAssetIds().remove(assetId);
        });
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends AssetGroup> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends AssetGroup> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<AssetGroup> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<UUID> uuids) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public AssetGroup getOne(UUID uuid) {
        return null;
    }

    @Override
    public AssetGroup getById(UUID uuid) {
        return null;
    }

    @Override
    public AssetGroup getReferenceById(UUID uuid) {
        return null;
    }

    @Override
    public <S extends AssetGroup> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends AssetGroup> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends AssetGroup> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends AssetGroup> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends AssetGroup> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends AssetGroup> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends AssetGroup, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends AssetGroup> S save(S entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        assetGroupHashMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends AssetGroup> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<AssetGroup> findById(UUID uuid) {
        if (assetGroupHashMap.containsKey(uuid)) {
            return Optional.of(assetGroupHashMap.get(uuid));
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public List<AssetGroup> findAll() {
        return List.of();
    }

    @Override
    public List<AssetGroup> findAllById(Iterable<UUID> uuids) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void delete(AssetGroup entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends AssetGroup> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<AssetGroup> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<AssetGroup> findAll(Pageable pageable) {
        List<AssetGroup> allAssetGroups = assetGroupHashMap.values()
                .stream()
                .toList();

        return new PageImpl<>(allAssetGroups, pageable, allAssetGroups.size());
    }
}
