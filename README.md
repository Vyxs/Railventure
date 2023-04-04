## Current 2D Version of Railventure
![Rendu 2D](./documentation/assets/img/2d_render.png)

## Debug keys
- `←` `→` `↓` `↑` : Move camera
- `Scroll` : Zoom / Dezoom
- `F1` : Toggle debug mode
- `F2` : Toggle tiles borders
- `F3` : Toggle chunks borders
- `F4` : Toggle gpu profiler

## Documentation

- [Game Design Document](https://docs.google.com/document/d/11n7iS0IGyN1e3w6MINMN4v4J-sQadCq4GiFhUPvfKh4/)
- [Project Management](https://trello.com/b/kmGSew56/railventure)

## Data generation

An R class is generated from the res.json file. It contains all the paths to the assets and a method to load them.

To generate the R.class file, you need to run the task generateRessourcesFromAssets from datagen group.
It will look at `core/datagen/res.json` to generate it under `core/src/fr/manigames/railventure/generated/`.

### Structure of res.json

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