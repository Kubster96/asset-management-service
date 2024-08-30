package io.kontakt.assetmanagmentservice.asset;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AssetConfiguration {

    @Bean
    AssetFacade assetService(AssetRepository assetRepository, ApplicationEventPublisher applicationEventPublisher) {
        return new AssetService(assetRepository, AssetMapper.INSTANCE, applicationEventPublisher);
    }
}
