package io.kontakt.assetmanagmentservice.asset;

import io.kontakt.assetmanagmentservice.asset.dto.AssetDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
interface AssetMapper {
    AssetMapper INSTANCE = Mappers.getMapper(AssetMapper.class);

    AssetDto toDto(Asset asset);

    Asset toEntity(AssetDto assetDto);
}
