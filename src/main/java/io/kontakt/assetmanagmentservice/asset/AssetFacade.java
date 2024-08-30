package io.kontakt.assetmanagmentservice.asset;

import io.kontakt.assetmanagmentservice.asset.dto.AssetDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;
import java.util.UUID;

public interface AssetFacade {
    Page<AssetDto> getAssets(Pageable pageable);

    AssetDto getAsset(UUID id);

    AssetDto createAsset(AssetDto assetDto);

    AssetDto updateAsset(UUID id, AssetDto assetDto);

    void deleteAsset(UUID id);

    Page<AssetDto> getAssets(Set<UUID> assetIds, Pageable pageable);
}
