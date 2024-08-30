package io.kontakt.assetmanagmentservice.asset;

import io.kontakt.assetmanagmentservice.asset.dto.AssetDto;
import io.kontakt.assetmanagmentservice.asset.event.AssetDeletedEvent;
import io.kontakt.assetmanagmentservice.error.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
class AssetService implements AssetFacade {
    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Page<AssetDto> getAssets(Pageable pageable) {
        return assetRepository.findAll(pageable)
                .map(assetMapper::toDto);
    }

    @Override
    public Page<AssetDto> getAssets(Set<UUID> assetIds, Pageable pageable) {
        return assetRepository.findByIds(assetIds, pageable)
                .map(assetMapper::toDto);
    }

    @Override
    public AssetDto getAsset(UUID id) {
        var asset = getAssetById(id);
        return assetMapper.toDto(asset);
    }

    @Override
    public AssetDto createAsset(AssetDto assetDto) {
        var asset = assetMapper.toEntity(assetDto);
        var savedAsset = assetRepository.save(asset);
        return assetMapper.toDto(savedAsset);
    }

    @Override
    public AssetDto updateAsset(UUID id, AssetDto assetDto) {
        var existingAsset = getAssetById(id);
        var updatedAsset = assetMapper.toEntity(assetDto);

        existingAsset.setName(updatedAsset.getName());
        existingAsset.setType(updatedAsset.getType());
        existingAsset.setDescription(updatedAsset.getDescription());

        var asset = assetRepository.save(existingAsset);
        return assetMapper.toDto(asset);
    }

    @Override
    @Transactional
    public void deleteAsset(UUID id) {
        var asset = getAssetById(id);
        assetRepository.delete(asset);
        applicationEventPublisher.publishEvent(new AssetDeletedEvent(id));
    }

    private Asset getAssetById(UUID id) {
        return assetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asset", id.toString()));
    }
}
