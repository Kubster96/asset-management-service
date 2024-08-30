package io.kontakt.assetmanagmentservice.assetgroup;

import io.kontakt.assetmanagmentservice.asset.dto.AssetDto;
import io.kontakt.assetmanagmentservice.assetgroup.dto.AssetGroupDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
class AssetGroupController {

    private final AssetGroupFacade assetGroupFacade;

    @GetMapping
    Page<AssetGroupDto> getGroups(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return assetGroupFacade.getAssetGroups(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    AssetGroupDto getGroup(@PathVariable UUID id) {
        return assetGroupFacade.getAssetGroup(id);
    }

    @PostMapping
    AssetGroupDto createGroup(@Valid @RequestBody AssetGroupDto assetGroupDto) {
        return assetGroupFacade.createAssetGroup(assetGroupDto);
    }

    @GetMapping("/{id}/assets")
    Page<AssetDto> getGroupAssets(
            @PathVariable UUID id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return assetGroupFacade.getGroupAssets(id, PageRequest.of(page, size));
    }

    @PostMapping("/{groupId}/assets/{assetId}")
    void addAssetToGroup(@PathVariable UUID groupId, @PathVariable UUID assetId) {
        assetGroupFacade.addAssetToGroup(groupId, assetId);
    }

    @DeleteMapping("/{groupId}/assets/{assetId}")
    void removeAssetFromGroup(@PathVariable UUID groupId, @PathVariable UUID assetId) {
        assetGroupFacade.removeAssetFromGroup(groupId, assetId);
    }
}
