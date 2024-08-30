package io.kontakt.assetmanagmentservice.asset;

import io.kontakt.assetmanagmentservice.asset.dto.AssetDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
class AssetController {

    private final AssetFacade assetFacade;

    @GetMapping
    Page<AssetDto> getAssets(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return assetFacade.getAssets(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    AssetDto getAsset(@PathVariable UUID id) {
        return assetFacade.getAsset(id);
    }

    @PostMapping
    AssetDto createAsset(@Valid @RequestBody AssetDto assetDTO) {
        return assetFacade.createAsset(assetDTO);
    }

    @PutMapping("/{id}")
    AssetDto updateAsset(@PathVariable UUID id, @Valid @RequestBody AssetDto assetDto) {
        return assetFacade.updateAsset(id, assetDto);
    }

    @DeleteMapping("/{id}")
    void deleteAsset(@PathVariable UUID id) {
        assetFacade.deleteAsset(id);
    }
}
