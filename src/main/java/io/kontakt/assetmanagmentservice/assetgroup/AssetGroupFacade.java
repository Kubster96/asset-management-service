package io.kontakt.assetmanagmentservice.assetgroup;

import io.kontakt.assetmanagmentservice.asset.dto.AssetDto;
import io.kontakt.assetmanagmentservice.assetgroup.dto.AssetGroupDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AssetGroupFacade {
    Page<AssetGroupDto> getAssetGroups(Pageable pageable);

    AssetGroupDto getAssetGroup(UUID id);

    AssetGroupDto createAssetGroup(AssetGroupDto assetGroupDto);

    Page<AssetDto> getGroupAssets(UUID id, Pageable pageable);

    void addAssetToGroup(UUID groupId, UUID assetId);

    void removeAssetFromGroup(UUID groupId, UUID assetId);

    void handleAssetDeleted(UUID assetId);
}
