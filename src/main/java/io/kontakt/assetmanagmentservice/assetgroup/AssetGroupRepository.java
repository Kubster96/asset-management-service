package io.kontakt.assetmanagmentservice.assetgroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface AssetGroupRepository extends JpaRepository<AssetGroup, UUID> {
    @Modifying
    @Query(value = "DELETE FROM group_asset_ids WHERE asset_ids = :assetId", nativeQuery = true)
    void deleteAllByAssetId(@Param("assetId") UUID assetId);
}
