# Railventure
Railventure is an open-world game that seamlessly blends RPG, adventure, and sandbox genres. Players take control of a customizable wagon that moves by laying down tracks, as they explore a procedurally generated world filled with resources to gather and enemies to battle. Drawing inspiration from popular titles such as Minecraft, Satisfactory, and Raft, the game delivers both a relaxing and fast-paced experience tailored to suit different playstyles.

In Railventure, players can switch between a top-down 2D view and a side-scrolling 3D perspective, offering a unique and immersive gaming experience. The game will be available on multiple platforms including Android, Web, Desktop, and iOS. Dive into the world of Railventure, and embark on an unforgettable adventure that offers endless opportunities for creativity, exploration, and fun!
# Summary
1. [Current look](#current-look-of-railventure)
2. [Debug keys](#debug-keys)
3. [Documentation](#documentation)
4. [Adding features](#adding-features)
    1. [Adding a Tile](#adding-a-tile)
    2. [Adding an Item](#adding-an-item)
    3. [Adding a TileEntity](#adding-a-tileentity)
    4. [Adding an Inventory](#adding-an-inventory)
    5. [Adding a Biome](#adding-a-biome)

# Current look of Railventure
![Rendu 2D](./documentation/assets/img/2d_render.png)
![Rendu 3D](./documentation/assets/img/3d_render.png)

# Debug keys
- `←` `→` `↓` `↑` : Move camera
- `Scroll` : Zoom / Dezoom
- `F1` : Toggle debug mode
- `F2` : Toggle tiles borders
- `F3` : Toggle chunks borders
- `F4` : Toggle gpu profiler

# Documentation

- [Game Design Document](https://docs.google.com/document/d/11n7iS0IGyN1e3w6MINMN4v4J-sQadCq4GiFhUPvfKh4/)
- [Project Management](https://trello.com/b/kmGSew56/railventure)

## Structure of res.json

```json
{
  "textures" : [
    {"name": "", "path": ""}
  ],
  "sounds" : [
    {"name": "", "path": ""}
  ]
}
```

# Adding features

## Adding a Tile
To add a new tile to the game, you need to create a JSON file in the `assets/model/tile/` folder. The JSON file must follow the structure below:

```json
{
    "key": "tile_uid",
    "name": "Tile Name",
    "texture": "texture_name.png",
    "isWalkable": true,
    "isSpawnable": true
}
```
### Property Descriptions
- `key` : **(string, required)**
    - Unique identifier for the tile.
- `name` : **(string, required)**
    - Name of the tile.
- `texture` : **(string, required)**
    - Path of the texture used for the tile.
- `isWalkable` : **(boolean, optional, default : false)**
    - Indicates if the tile is walkable by entities.
- `isSpawnable` : **(boolean, optional, default : false)**
    - Indicates if the tile is an area where entities can spawn.

## Adding an Item
To add a new item to the game, you need to create a JSON file in the `assets/model/item/` folder. The JSON file must follow the structure below:

```json
{
    "key": "item_uid",
    "name": "Tile Name",
    "texture": "texture_name.png",
    "maxStackSize": 12,
    "isStackable": true,
    "isUsable": true,
    "isPlaceable": true,
    "isDroppable": true,
    "isPickable": true,
    "isEquippable": true,
    "isConsumable": true,
    "isCraftable": true,
    "isRepairable": true,
    "isEnchantable": true,
    "isEnchanted": true,
    "isDamaged": true,
    "isDamagable": true
}
```
### Property Descriptions
- `key` : **(string, required)**
    - Unique identifier for the item.
- `name` : **(string, required)**
    - Name of the item.
- `texture` : **(string, required)**
    - Name of the texture used for the item.
- `maxStackSize` : **(int, optional, default : 64)**
    - Maximum number of items that can be stacked together (default is 64).
- `isStackable` : **(string, optional, default : false)**
    - Indicates if the item can be stacked.
- `isUsable` : **(boolean, optional, default : false)**
    - Indicates if the item can be used.
- `isPlaceable` : **(boolean, optional, default : false)**
    - Indicates if the item can be placed in the game world.
- `isDroppable` : **(boolean, optional, default : false)**
    - Indicates if the item can be dropped by the player.
- `isPickable` : **(boolean, optional, default : false)**
    - Indicates if the item can be picked up by the player.
- `isEquippable` : **(boolean, optional, default : false)**
    - Indicates if the item can be equipped by the player.
- `isConsumable` : **(boolean, optional, default : false)**
    - Indicates if the item can be consumed by the player.
- `isCraftable` : **(boolean, optional, default : false)**
    - Indicates if the item can be crafted by the player.
- `isRepairable` : **(boolean, optional, default : false)**
    - Indicates if the item can be repaired by the player.
- `isEnchantable` : **(boolean, optional, default : false)**
    - Indicates if the item can be enchanted by the player.
- `isEnchanted` : **(boolean, optional, default : false)**
    - Indicates if the item is already enchanted.
- `isDamaged` : **(boolean, optional, default : false)**
    - Indicates if the item is damaged.
- `isDamagable` : **(boolean, optional, default : false)**
    - Indicates if the item can be damaged.

## Adding a TileEntity
To add a new tileEntity to the game, you need to create a JSON file in the `assets/model/tileentity/` folder. The JSON file must follow the structure below:

```json
{
    "key": "tileentity_uid",
    "name": "TileEntity Name",
    "texture": {},
    "renderType": "TILE",
    "width": 1.0,
    "height": 1.0,
    "textureScale": 0.2,
    "isOrientable": true,
    "isSolid": true,
    "isHarvestable": true
}
```
### Property Descriptions
- `key` : **(string, required)**
    - Unique identifier for the tileEntity.
- `name` : **(string, required)**
    - Name of the tileEntity.
- `texture` : **(seasonWeatherTexture, required)**
    - Path of the texture used for the tile.
- `renderType` : **(renderType, optional, default : TILE)**
    - Type of rendering used for the tileEntity.
- `width` : **(float, optional, default : 1.0)**
    - Width in world size of the tileEntity. Only used for hitbox calculation.
- `height` : **(float, optional, default : 1.0)**
    - Height in world size of the tileEntity. Only used for hitbox calculation.
- `textureScale` : **(float, optional, default : 1.0)**
    - Scale of the texture used for the tileEntity.
- `isOrientable` : **(boolean, optional, default : false)**
    - Indicates if the tileEntity can be oriented.
- `isSolid` : **(boolean, optional, default : false)**
    - Indicates if the tileEntity is solid, if solid, the player can't walk through it.
- `isHarvestable` : **(boolean, optional, default : false)**
    - Indicates if the tileEntity can be harvested by the player.

#### RenderType

Describe how the tileEntity should be rendered.

- `TILE` : The tileEntity is rendered as a tile.
- `SLAB` : The tileEntity is rendered as a tile in 2D mode and as a slab in 3D mode (used for rails).
- `FOLIAGE` : The tileEntity is rendered as a foliage (used for grass, flowers, etc...). In 3D mode, it is rendered on z-axis. In 2D mode, it is rendered on y-axis from the bottom.
- `RENDER_3D` : Same as `FOLIAGE` but in 3D mode, it use a 3D model instead of a texture. (WIP)
- `NO_RENDER` : The tileEntity is not rendered.

#### SeasonWeatherTexture

It holds the texture path for each season and weather. The JSON file must follow the structure below:

```json
{
    "base" : {
        "base": "texture_name.png"
    },
    "spring": {
        "base": "texture_name.png"
    },
    "summer": {
        "base": "texture_name.png"
    },
    "autumn": {
        "base": "texture_name.png"
    },
    "winter": {
        "base": "texture_name.png"
    }
}
```
##### Property Descriptions
- `base` : **(orientedTexture, required)**
    - Texture path for any seasons.
- `spring` : **(orientedTexture, optional, default : base)**
    - Used during spring.
- `summer` : **(orientedTexture, optional, default : base)**
    - Used during summer.
- `autumn` : **(orientedTexture, optional, default : base)**
    - Used during autumn.
- `winter` : **(orientedTexture, optional, default : base)**
    - Used during winter.

#### OrientedTexture

It holds the texture path for each orientation. The JSON file must follow the structure below:

```json
{
    "base": "texture_name.png",
    "north": "texture_name.png",
    "east": "texture_name.png",
    "south": "texture_name.png",
    "west": "texture_name.png"
}
```
##### Property Descriptions
- `base` : **(string, required)**
    - Path of the texture used for any orientation.
- `north` : **(string, optional, default : base)**
    - Path of the texture used for the tileEntity when oriented to the north.
- `east` : **(string, optional, default : base)**
    - Path of the texture used for the tileEntity when oriented to the east.
- `south` : **(string, optional, default : base)**
    - Path of the texture used for the tileEntity when oriented to the south.
- `west` : **(string, optional, default : base)**
    - Path of the texture used for the tileEntity when oriented to the west.

## Adding a Biome

To add a new biome to the game, you need to create a JSON file in the `assets/model/biome/` folder. The JSON file must follow the structure below:

```json
{
    "key": "biome_uid",
    "name": "Biome Name",
    "altitude": 150,
    "temperature": 20,
    "humidity": 50,
    "color": 165245,
    "type": "TERRESTRIAL",
    "tiles": [],
    "gradient": "RANDOM",
    "tileEntities": []
}
```
### Property Descriptions
- `key` : **(string, required)**
    - Unique identifier for the biome.
- `name` : **(string, required)**
    - Name of the biome.
- `altitude` : **(int, required)**
    - Altitude of the biome. Must be between 0 and Integer.MAX_VALUE.
- `temperature` : **(int, required)**
    - Temperature of the biome. Must be between 0 and Integer.MAX_VALUE.
- `humidity` : **(int, required)**
    - Humidity of the biome. Must be between 0 and 100.
- `color` : **(int, optional, default : purple)**
    - Color of the biome. Must be a hexadecimal value (0x000000 to 0xFFFFFF).
    - Used for debug purpose only.
- `type` : **(biomeType, optional, default : TERRESTRIAL)**
    - Type of the biome.
- `tiles` : **(array of TileWithProbability, required)**
    - List of tiles that can be generated in the biome. 
    - The probability is used to generate the biome, the sum of probability must be equal to 1.0.
- `gradient` : **(biomeGradient, optional, default : RANDOM)** 
    - Type of gradient used to generate the biome.
- `tileEntities` : **(array of biomeTileEntitiesConfig, optional, default : [])**
    - List of tileEntities that can be generated in the biome under certain conditions.

#### BiomeType
- `TERRESTRIAL` : The biome is a terrestrial biome.
- `AQUATIC` : The biome is an aquatic biome.
- `EXTRATERRESTRIAL` : The biome is an extraterrestrial biome.
- `UNDERGROUND` : The biome is an underground biome.

#### BiomeGradient
- `UNSET` : The biome is generated randomly.
- `RANDOM` : The biome is generated randomly.
- `SMOOTH` : The biome is generated based on the altitude. Tile with the lowest probability will be generated first.
- `SMOOTH_NOISY` : Like `SMOOTH` but with a noise applied to the intersection of the tiles.

#### TileWithProbability

It holds the tile and its probability to be generated in the biome. The JSON file must follow the structure below:

```json
{
    "key": "tile_key",
    "probability": 1.0
}
```
##### Property Descriptions
- `key` : **(string, required)**
    - Unique identifier for the tile.
    - The tile must be defined in the `assets/model/tile/` folder.
- `probability` : **(float, required)** 
    - Probability of the tile to be generated in the biome.
    - Must be between 0.0 and 1.0.

#### BiomeTileEntitiesConfig

It holds the tileEntity and its conditions to be generated in the biome. The JSON file must follow the structure below:

```json
{
    "spawnOn": ["tile_key1", "tile_key2", "tile_key..."],
    "keys": ["tileEntity_key1", "tileEntity_key2", "tileEntity_key..."],
    "odds": 0.03
}
```
##### Property Descriptions
- `spawnOn` : **(array of string, optional, default : [])**
    - List of tiles on which the tileEntity can be generated.
    - If empty, the tileEntity can't be generated.
    - The tile must be defined in the `assets/model/tile/` folder.
- `keys` : **(array of string, required)**
    - List of tileEntities that can be generated in the biome.
    - The tileEntity must be defined in the `assets/model/tileEntity/` folder.
- `odds` : **(float, required)** 
    - Probability of the tileEntity to be generated on the tile.
    - Must be between 0.0 and 1.0.

## Adding an Inventory

To add a new inventory to the game, you need to create a JSON file in the `assets/model/inventory/` folder. The JSON file must follow the structure below:

```json
{
    "key": "inventory_uid",
    "texture": "texture_name.png",
    "width": 400,
    "height": 400,
    "slots": []
}
```
### Property Descriptions
- `key` : **(string, required)**
    - Unique identifier for the inventory.
- `texture` : **(string, required)**
    - Path of the texture used for the inventory.
- `width` : **(int, required)**
    - Width in pixels of the inventory.
- `height` : **(int, required)**
    - Height in pixels of the inventory.
- `slots` : **(array of slot, required)**
    - Array of slots in the inventory.

#### Slot

It holds the position and size of a slot in the inventory. The JSON file must follow the structure below:

```json
{
    "x": 10,
    "y": 10,
    "width": 20,
    "height": 20
}
```

##### Property Descriptions
- `x` : **(int, required)** X
    - position in pixels in the texture of the slot.
- `y` : **(int, required)** Y
    - position in pixels in the texture of the slot.
- `width` : **(int, required)**
    - Width in pixels of the slot.
- `height` : **(int, required)**
    - Height in pixels of the slot.