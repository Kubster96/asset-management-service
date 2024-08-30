package io.kontakt.assetmanagmentservice.assetgroup;

import io.kontakt.assetmanagmentservice.asset.AssetFacade;
import io.kontakt.assetmanagmentservice.asset.dto.AssetDto;
import io.kontakt.assetmanagmentservice.error.ResourceNotFoundException;
import io.kontakt.assetmanagmentservice.assetgroup.dto.AssetGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@RequiredArgsConstructor
class AssetGroupService implements AssetGroupFacade {
    private final AssetGroupRepository assetGroupRepository;
    private final AssetGroupMapper assetGroupMapper;
    private final AssetFacade assetFacade;

    @Override
    public Page<AssetGroupDto> getAssetGroups(Pageable pageable) {
        return assetGroupRepository.findAll(pageable)
                .map(assetGroupMapper::toDto);
    }

    @Override
    public AssetGroupDto getAssetGroup(UUID id) {
        var group = getGroupById(id);
        return assetGroupMapper.toDto(group);
    }

    @Override
    public AssetGroupDto createAssetGroup(AssetGroupDto assetGroupDto) {
        var group = assetGroupMapper.toEntity(assetGroupDto);
        var savedGroup = assetGroupRepository.save(group);
        return assetGroupMapper.toDto(savedGroup);
    }

    @Override
    public Page<AssetDto> getGroupAssets(UUID id, Pageable pageable) {
        var group = getGroupById(id);
        var assetIds = group.getAssetIds();
        return assetFacade.getAssets(assetIds, pageable);
    }

    @Override
    public void addAssetToGroup(UUID groupId, UUID assetId) {
        var group = getGroupById(groupId);
        var assetDto = assetFacade.getAsset(assetId);

        group.addAssetToGroup(assetDto.getId());
        assetGroupRepository.save(group);
    }

    @Override
    public void removeAssetFromGroup(UUID groupId, UUID assetId) {
        var group = getGroupById(groupId);
        var assetDto = assetFacade.getAsset(assetId);

        group.removeAssetFromGroup(assetDto.getId());
        assetGroupRepository.save(group);
    }

    @Override
    public void handleAssetDeleted(UUID assetId) {
        assetGroupRepository.deleteAllByAssetId(assetId);
    }

    private AssetGroup getGroupById(UUID id) {
        return assetGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group", id.toString()));
    }
}
