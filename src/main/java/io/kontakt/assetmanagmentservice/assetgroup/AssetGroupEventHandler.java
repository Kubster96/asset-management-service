package io.kontakt.assetmanagmentservice.assetgroup;

import io.kontakt.assetmanagmentservice.asset.event.AssetDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
class AssetGroupEventHandler {

    private final AssetGroupFacade assetGroupFacade;

    @EventListener
    void handleContextStart(AssetDeletedEvent assetDeletedEvent) {
        assetGroupFacade.handleAssetDeleted(assetDeletedEvent.getAssetId());
    }
}
