package io.kontakt.assetmanagmentservice.assetgroup.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetGroupDto {
    private UUID id;
    @NotBlank(message = "Name is required")
    private String name;
    private String description;
}
