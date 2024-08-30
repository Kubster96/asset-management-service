CREATE TABLE asset_group (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE group_asset_ids (
    group_id UUID NOT NULL,
    asset_ids UUID NOT NULL,
    PRIMARY KEY (group_id, asset_ids),
    FOREIGN KEY (group_id) REFERENCES asset_group(id)
);