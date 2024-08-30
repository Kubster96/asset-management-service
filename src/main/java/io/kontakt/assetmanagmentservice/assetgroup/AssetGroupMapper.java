package io.kontakt.assetmanagmentservice.assetgroup;

import io.kontakt.assetmanagmentservice.assetgroup.dto.AssetGroupDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
interface AssetGroupMapper {
    AssetGroupMapper INSTANCE = Mappers.getMapper(AssetGroupMapper.class);

    AssetGroupDto toDto(AssetGroup assetGroup);

    AssetGroup toEntity(AssetGroupDto assetGroupDto);
}
