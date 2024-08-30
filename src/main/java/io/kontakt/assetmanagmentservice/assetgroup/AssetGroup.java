package io.kontakt.assetmanagmentservice.assetgroup;

import io.kontakt.assetmanagmentservice.error.BadRequestException;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static java.lang.String.format;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class AssetGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    @ElementCollection(targetClass = UUID.class)
    @CollectionTable(name = "group_asset_ids", joinColumns = {@JoinColumn(name = "group_id")})
    private Set<UUID> assetIds = new HashSet<>();

    void addAssetToGroup(UUID assetId) {
        if (assetIds.contains(assetId)) {
            throw new BadRequestException(format("Asset [%s] is already in Group [%s]", assetId, this.id));
        }
        assetIds.add(assetId);
    }

    void removeAssetFromGroup(UUID assetId) {
        if (!assetIds.contains(assetId)) {
            throw new BadRequestException(format("Asset [%s] is not in Group [%s]", assetId, this.id));
        }
        assetIds.remove(assetId);
    }
}
