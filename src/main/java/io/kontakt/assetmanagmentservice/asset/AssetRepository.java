package io.kontakt.assetmanagmentservice.asset;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
interface AssetRepository extends JpaRepository<Asset, UUID> {
    @Query("SELECT a FROM Asset a WHERE a.id IN :assetIds")
    Page<Asset> findByIds(@Param("assetIds") Set<UUID> assetIds, Pageable pageable);
}
