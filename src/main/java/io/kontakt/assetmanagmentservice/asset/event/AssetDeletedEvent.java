package io.kontakt.assetmanagmentservice.asset.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AssetDeletedEvent {
    private UUID assetId;
}
