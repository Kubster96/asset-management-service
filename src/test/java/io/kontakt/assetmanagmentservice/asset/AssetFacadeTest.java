package io.kontakt.assetmanagmentservice.asset;


import io.kontakt.assetmanagmentservice.asset.dto.AssetDto;
import io.kontakt.assetmanagmentservice.asset.event.AssetDeletedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


class AssetFacadeTest {
    private AssetRepository assetRepository;
    private final AssetMapper assetMapper = AssetMapper.INSTANCE;
    private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);

    private AssetFacade assetFacade;

    private static final String NAME = "Asset";
    private static final String UPDATED_NAME = "UpdatedAsset";

    private static final AssetType TYPE = AssetType.SOFTWARE;
    private static final io.kontakt.assetmanagmentservice.asset.dto.AssetType TYPE_DTO
            = io.kontakt.assetmanagmentservice.asset.dto.AssetType.SOFTWARE;

    private static final String DESCRIPTION = "AssetDescription";
    private static final String UPDATED_DESCRIPTION = "UpdatedAssetDescription";

    private static final int PAGE = 0;
    private static final int SIZE = 10;

    @BeforeEach
    void setUp() {
        assetRepository = new AssetInMemoryRepository();
        assetFacade = new AssetService(assetRepository, assetMapper, applicationEventPublisher);
    }

    @Test
    void shouldGetAssets() {
        //given
        var asset1 = new Asset(null, NAME, TYPE, DESCRIPTION);
        var asset2 = new Asset(null, NAME, TYPE, DESCRIPTION);
        assetRepository.save(asset1);
        assetRepository.save(asset2);

        //when
        var resultAssets = assetFacade.getAssets(PageRequest.of(PAGE, SIZE));

        //then
        assertThat(resultAssets)
                .hasSize(2);
    }

    @Test
    void shouldGetAsset() {
        //given
        var asset = new Asset(null, NAME, TYPE, DESCRIPTION);
        var savedAsset = assetRepository.save(asset);


        //when
        var resultAsset = assetFacade.getAsset(savedAsset.getId());

        //then
        assertThat(resultAsset)
                .returns(savedAsset.getId(), AssetDto::getId)
                .returns(NAME, AssetDto::getName)
                .returns(TYPE_DTO, AssetDto::getType)
                .returns(DESCRIPTION, AssetDto::getDescription);
    }

    @Test
    void shouldCreateAsset() {
        //given
        var assetDtoToCreate = new AssetDto(null, NAME, TYPE_DTO, DESCRIPTION);

        //when
        var resultAsset = assetFacade.createAsset(assetDtoToCreate);

        //then
        assertThat(resultAsset)
                .returns(true, assetDto -> assetDto.getId() != null)
                .returns(NAME, AssetDto::getName)
                .returns(TYPE_DTO, AssetDto::getType)
                .returns(DESCRIPTION, AssetDto::getDescription);
    }

    @Test
    void shouldUpdateAsset() {
        //given
        var asset = new Asset(null, NAME, TYPE, DESCRIPTION);
        var savedAsset = assetRepository.save(asset);
        var assetDto = new AssetDto(savedAsset.getId(), UPDATED_NAME, TYPE_DTO, UPDATED_DESCRIPTION);

        //when
        var resultAsset = assetFacade.updateAsset(savedAsset.getId(), assetDto);

        //then
        assertThat(resultAsset)
                .returns(UPDATED_NAME, AssetDto::getName)
                .returns(TYPE_DTO, AssetDto::getType)
                .returns(UPDATED_DESCRIPTION, AssetDto::getDescription);
    }

    @Test
    void shouldDeleteAsset() {
        //given
        var asset = new Asset(null, NAME, TYPE, DESCRIPTION);
        var savedAsset = assetRepository.save(asset);
        var assetId = savedAsset.getId();

        //when
        assetFacade.deleteAsset(assetId);

        //then
        var assetExists = assetRepository.existsById(assetId);
        assertThat(assetExists)
                .isFalse();
        verify(applicationEventPublisher).publishEvent(new AssetDeletedEvent(assetId));
    }

    @Test
    void shouldGetAssetsByAssetIds() {
        //given
        var asset1 = new Asset(null, NAME, TYPE, DESCRIPTION);
        var asset2 = new Asset(null, NAME, TYPE, DESCRIPTION);
        var asset3 = new Asset(null, NAME, TYPE, DESCRIPTION);
        var savedAsset1 = assetRepository.save(asset1);
        var savedAsset2 = assetRepository.save(asset2);
        assetRepository.save(asset3);

        //when
        var resultAssets = assetFacade.getAssets(Set.of(savedAsset1.getId(), savedAsset2.getId()), PageRequest.of(PAGE, SIZE));

        //then
        assertThat(resultAssets)
                .hasSize(2);
    }

}