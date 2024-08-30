package io.kontakt.assetmanagmentservice.assetgroup;

import io.kontakt.assetmanagmentservice.asset.AssetFacade;
import io.kontakt.assetmanagmentservice.asset.dto.AssetDto;
import io.kontakt.assetmanagmentservice.asset.dto.AssetType;
import io.kontakt.assetmanagmentservice.assetgroup.dto.AssetGroupDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AssetGroupFacadeTest {
    private AssetGroupRepository assetGroupRepository;
    private final AssetGroupMapper assetGroupMapper = AssetGroupMapper.INSTANCE;
    private final AssetFacade assetFacade = mock(AssetFacade.class);

    private AssetGroupFacade assetGroupFacade;

    private static final String GROUP_NAME = "Group";
    private static final String ASSET_NAME = "Asset";

    private static final AssetType TYPE_DTO = AssetType.SOFTWARE;

    private static final String GROUP_DESCRIPTION = "GroupDescription";
    private static final String ASSET_DESCRIPTION = "AssetDescription";

    private static final int PAGE = 0;
    private static final int SIZE = 10;

    @BeforeEach
    void setUp() {
        assetGroupRepository = new AssetGroupInMemoryRepository();
        assetGroupFacade = new AssetGroupService(assetGroupRepository, assetGroupMapper, assetFacade);
    }

    @Test
    void shouldGetAssetGroups() {
        //given
        var assetGroup1 = new AssetGroup(null, GROUP_NAME, GROUP_DESCRIPTION, new HashSet<>());
        var assetGroup2 = new AssetGroup(null, GROUP_NAME, GROUP_DESCRIPTION, new HashSet<>());
        assetGroupRepository.save(assetGroup1);
        assetGroupRepository.save(assetGroup2);

        //when
        var resultAssets = assetGroupFacade.getAssetGroups(PageRequest.of(PAGE, SIZE));

        //then
        assertThat(resultAssets)
                .hasSize(2);

    }

    @Test
    void shouldGetAssetGroup() {
        //given
        var assetGroup = new AssetGroup(null, GROUP_NAME, GROUP_DESCRIPTION, new HashSet<>());
        var savedAssetGroup = assetGroupRepository.save(assetGroup);


        //when
        var resultAsset = assetGroupFacade.getAssetGroup(savedAssetGroup.getId());

        //then
        assertThat(resultAsset)
                .returns(savedAssetGroup.getId(), AssetGroupDto::getId)
                .returns(GROUP_NAME, AssetGroupDto::getName)
                .returns(GROUP_DESCRIPTION, AssetGroupDto::getDescription);
    }

    @Test
    void shouldCreateAssetGroup() {
        //given
        var assetGroupDtoToCreate = new AssetGroupDto(null, GROUP_NAME, GROUP_DESCRIPTION);

        //when
        var resultAssetGroup = assetGroupFacade.createAssetGroup(assetGroupDtoToCreate);

        //then
        assertThat(resultAssetGroup)
                .returns(true, assetGroupDto -> assetGroupDto.getId() != null)
                .returns(GROUP_NAME, AssetGroupDto::getName)
                .returns(GROUP_DESCRIPTION, AssetGroupDto::getDescription);
    }

    @Test
    void shouldGetGroupAssets() {
        //given
        var assetId1 = UUID.randomUUID();
        var assetId2 = UUID.randomUUID();

        var assetIds = Set.of(assetId1, assetId2);

        var asset1 = new AssetDto(assetId1, ASSET_NAME, TYPE_DTO, ASSET_DESCRIPTION);
        var asset2 = new AssetDto(assetId2, ASSET_NAME, TYPE_DTO, ASSET_DESCRIPTION);

        var assets = List.of(asset1, asset2);

        var assetGroup = new AssetGroup(null, GROUP_NAME, GROUP_DESCRIPTION, assetIds);
        var savedAssetGroup = assetGroupRepository.save(assetGroup);

        var pageable = PageRequest.of(PAGE, SIZE);
        when(assetFacade.getAssets(assetIds, pageable)).thenReturn(new PageImpl<>(assets, pageable, assets.size()));

        //when
        var groupAssets = assetGroupFacade.getGroupAssets(savedAssetGroup.getId(), pageable);

        //then
        assertThat(groupAssets)
                .hasSize(2)
                .anyMatch(assetDto -> assetDto.getId() == assetId1)
                .anyMatch(assetDto -> assetDto.getId() == assetId2);
    }

    @Test
    void shouldAddAssetToGroup() {
        //given
        var assetId = UUID.randomUUID();
        var asset = new AssetDto(assetId, ASSET_NAME, TYPE_DTO, ASSET_DESCRIPTION);

        var assetGroup = new AssetGroup(null, GROUP_NAME, GROUP_DESCRIPTION, new HashSet<>());
        var savedAssetGroup = assetGroupRepository.save(assetGroup);

        when(assetFacade.getAsset(assetId)).thenReturn(asset);

        //when
        assetGroupFacade.addAssetToGroup(savedAssetGroup.getId(), assetId);

        //then
        var updatedAssetGroup = assetGroupRepository.findById(savedAssetGroup.getId());
        assertThat(updatedAssetGroup).isPresent();

        var groupAssetsIds = updatedAssetGroup.get().getAssetIds();

        assertThat(groupAssetsIds)
                .hasSize(1)
                .anyMatch(groupAssetId -> groupAssetId == assetId);
    }

    @Test
    void shouldRemoveAssetFromGroup() {
        //given
        var assetId = UUID.randomUUID();
        var asset = new AssetDto(assetId, ASSET_NAME, TYPE_DTO, ASSET_DESCRIPTION);

        var assetGroup = new AssetGroup(null, GROUP_NAME, GROUP_DESCRIPTION, new HashSet<>(List.of(assetId)));
        var savedAssetGroup = assetGroupRepository.save(assetGroup);

        when(assetFacade.getAsset(assetId)).thenReturn(asset);

        //when
        assetGroupFacade.removeAssetFromGroup(savedAssetGroup.getId(), assetId);

        //then
        var updatedAssetGroup = assetGroupRepository.findById(savedAssetGroup.getId());
        assertThat(updatedAssetGroup).isPresent();

        var groupAssetsIds = updatedAssetGroup.get().getAssetIds();

        assertThat(groupAssetsIds)
                .isEmpty();
    }

    @Test
    void shouldHandleAssetDeleted() {
        //given
        var assetId1 = UUID.randomUUID();
        var assetId2 = UUID.randomUUID();

        var assetGroup1 = new AssetGroup(null, GROUP_NAME, GROUP_DESCRIPTION, new HashSet<>(List.of(assetId1, assetId2)));
        var assetGroup2 = new AssetGroup(null, GROUP_NAME, GROUP_DESCRIPTION, new HashSet<>(List.of(assetId1)));
        var savedAssetGroup1 = assetGroupRepository.save(assetGroup1);
        var savedAssetGroup2 = assetGroupRepository.save(assetGroup2);

        //when
        assetGroupFacade.handleAssetDeleted(assetId1);

        //then
        var updatedAssetGroup1 = assetGroupRepository.findById(savedAssetGroup1.getId());
        assertThat(updatedAssetGroup1).isPresent();
        assertThat(updatedAssetGroup1.get().getAssetIds())
                .noneMatch(assetId -> assetId == assetId1)
                .anyMatch(assetId -> assetId == assetId2);

        var updatedAssetGroup2 = assetGroupRepository.findById(savedAssetGroup2.getId());
        assertThat(updatedAssetGroup2).isPresent();
        assertThat(updatedAssetGroup2.get().getAssetIds())
                .isEmpty();
    }
}