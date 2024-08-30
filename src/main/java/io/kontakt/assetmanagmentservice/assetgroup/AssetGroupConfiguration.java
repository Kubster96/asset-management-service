package io.kontakt.assetmanagmentservice.assetgroup;

import io.kontakt.assetmanagmentservice.asset.AssetFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AssetGroupConfiguration {

    @Bean
    AssetGroupFacade assetGroupService(AssetGroupRepository assetGroupRepository, AssetFacade assetFacade) {
        return new AssetGroupService(assetGroupRepository, AssetGroupMapper.INSTANCE, assetFacade);
    }

    @Bean
    AssetGroupEventHandler groupEventHandler(AssetGroupFacade assetGroupFacade) {
        return new AssetGroupEventHandler(assetGroupFacade);
    }
}
