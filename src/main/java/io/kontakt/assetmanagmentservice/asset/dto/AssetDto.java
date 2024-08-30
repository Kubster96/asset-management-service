package io.kontakt.assetmanagmentservice.asset.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetDto {
    private UUID id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Type is required")
    private AssetType type;
    private String description;
}
