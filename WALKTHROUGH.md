# Walkthrough lich su chinh sua code

## 2026-05-16

### Buoc 1: Doc cau truc project

- Kiem tra repo va thay Gradle chi include 2 module: `core`, `lwjgl3`.
- Xac dinh luong game chinh nam trong `core/src/main/java/io/mygdx/soulknight`.
- Xac dinh `src/` la code Java/Swing rieng, khong nam trong Gradle module hien tai.

### Buoc 2: Xac dinh luong win/map cu

- `PlayScreen` load truc tiep `map.tmx`.
- `WinArea` hard-code vi tri win tai `(896, 136)`.
- Khi player cham `WinArea`, bien `PlayScreen.win` duoc set `true`.
- `PlayScreen.win()` truoc do chuyen thang sang `WinScreen`.

### Buoc 3: Them cau hinh 3 level

- Them `FIRST_LEVEL_INDEX`.
- Them mang `LEVELS` gom 3 `LevelConfig`.
- Them `LevelConfig` de gom:
  - `mapPath`
  - `displayName`
  - `playerStart`
  - `winAreaCenter`
  - `winAreaHalfSize`
  - `collisionLayerNames`
  - `enemySpawns`
- Them `EnemySpawn` de khai bao quai bang code gon:
  - `EnemySpawn.shooter(x, y)`
  - `EnemySpawn.chaser(x, y)`

### Buoc 4: Sua PlayScreen load theo level

- Constructor `PlayScreen(SoulKnightGame game)` tiep tuc bat dau tu map dau tien.
- Them constructor `PlayScreen(SoulKnightGame game, int levelIndex)`.
- Map duoc load bang:

```java
map = mapLoader.load(levelConfig.mapPath);
```

- HUD nhan ten map bang:

```java
hud = new Hud(game.batch, levelConfig.displayName);
```

- Player spawn bang:

```java
player = new Player(world, mousePos, camera, levelConfig.playerStart);
```

- Win area tao bang:

```java
new WinArea(world, this, levelConfig.winAreaCenter, levelConfig.winAreaHalfSize);
```

### Buoc 5: Sua chuyen map khi win

- Neu `levelIndex + 1 < LEVELS.length`, `PlayScreen.win()` tao `new PlayScreen(game, levelIndex + 1)`.
- Neu da o map cuoi, `PlayScreen.win()` moi chuyen sang `WinScreen`.
- Them `clearLevelObjects()` de clear danh sach static cua monster va bullet truoc khi dispose world cu.

### Buoc 6: Sua collision loader

- `B2WorldCreator` khong dung index layer `8`, `9` nua.
- Collision duoc doc theo ten object group:

```java
new String[]{"Obstacle", "Walls"}
```

- Ham moi:

```java
void createRectangle(String layerName)
```

- Neu map thieu object group, code se skip group do thay vi crash.

### Buoc 7: Tao map placeholder

- Tao `assets/map1.tmx` tu `assets/map.tmx`.
- Tao `assets/map2.tmx` tu `assets/map.tmx`.
- Tao `assets/map3.tmx` tu `assets/map.tmx`.
- Muc dich: game load du 3 file map ngay, con noi dung map 2/3 de sua sau trong Tiled.

### Buoc 8: Tao tai lieu huong dan

- Tao `REPORT.md`.
- Ghi lai file can sua, code khai bao, vi tri config, cach sua map 2/3, collision, win area, player spawn, quai, va pickup.

### Buoc 9: Don tai nguyen phu

- Bo `TextureAtlas("Weapons.pack")` trong `PlayScreen` vi bien nay khong duoc dung va khong duoc dispose.
- Dung `clearLevelObjects()` cho ca win va game over de tranh giu lai monster/bullet cu trong static list khi vao man moi.

### Buoc 10: Them bang toa do bat/tat bang phim P

- Them logic toggle trong `PlayScreen`:

```java
private void handleCoordinatePanelInput() {
    if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
        hud.setCoordinatePanelVisible(!hud.isCoordinatePanelVisible());
    }
}
```

- Goi `handleCoordinatePanelInput()` trong `update(float dt)`.
- Moi frame, `PlayScreen` gui thong tin level va toa do vao HUD bang `hud.updateCoordinatePanel(...)`.
- Them `coordinateTable` trong `Hud`, mac dinh `setVisible(false)`.
- Them nen den mo cho `coordinateTable` de doc toa do ro hon tren map.
- Them cac label:
  - `coordinateTitleLabel`
  - `coordinateMapLabel`
  - `coordinatePlayerLabel`
  - `coordinateSpawnLabel`
  - `coordinateWinLabel`
- Bang toa do chi hien thong tin de doc, khong thay doi vi tri player, map, enemy, collision hay game state.

### Buoc 11: Sua loi camera khong di chuyen dung theo player

- Nguyen nhan: `Player.currentPos` chi duoc gan mot lan trong `defineCharacter()` va khong duoc cap nhat moi frame.
- Ket qua: `Player.render()` su dung toa do cu cua sprite, nen camera duoc cap nhat theo `player.b2body` van khong hien thi dung theo y.
- Da sua `Player.update(float dt)` de cap nhat `currentPos = b2body.getWorldCenter()` moi frame truoc khi draw.
- Ket qua: sprite va camera di chuyen dung theo ca 2 truc x va y.

## 2026-05-17

### Buoc 12: Sua loi path tileset tuyet doi trong map3

- Phat hien `assets/map3.tmx` con tham chieu anh tileset theo duong dan may cu:
  - `C:/Users/Admin/Downloads/SoulKnightOOP2026/assets/0x72_16x16DungeonTileset.v4.png`
- Da doi ve duong dan tuong doi:
  - `0x72_16x16DungeonTileset.v4.png`
- Muc tieu: tranh loi khong tim thay file khi chay tren may khac.

### Buoc 13: Cap nhat toa do win theo map thuc te

- Da cap nhat `PlayScreen.LEVELS`:
  - `map2.tmx`: `winAreaCenter = new Vector2(48, 12)`
  - `map3.tmx`: `winAreaCenter = new Vector2(893, 445)`
- Toa do spawn hien tai:
  - `map2.tmx`: `new Vector2(900, 10)`
  - `map3.tmx`: `new Vector2(138, 460)`

### Buoc 14: Sua loi chi hien Obstacle, khong hien Walls

- Nguyen nhan:
  - TMX co the co 2 layer trung ten `Walls` (tile layer va object group).
  - `map.getLayers().get("Walls")` chi lay layer dau tien, de nham vao tile layer.
  - Ket qua: collision rectangle cua object group `Walls` khong duoc tao.
- Da sua `B2WorldCreator.createRectangle(String layerName)`:
  - Quet tat ca `MapLayer` co cung ten.
  - Tao body tu moi `RectangleMapObject` tim duoc.
  - Xu ly duoc truong hop layer trung ten.

### Buoc 15: Ho tro rotation cho rectangle collision

- Da bo sung doc property `rotation` cua `RectangleMapObject`.
- Gan goc vao body bang:

```java
bdef.angle = rotationDegrees * MathUtils.degreesToRadians;
```

- Muc tieu: cac wall rectangle xoay `90/-90` trong Tiled van tao collision dung huong.

### Buoc 16: Bat/tat hien collider bang phim P

- Da them cờ `collisionDebugVisible` trong `PlayScreen`.
- Khi bam `P`:
  - Van toggle bang toa do HUD nhu cu.
  - Dong thoi toggle hien thi Box2D debug.
- Trong `render(float delta)`:

```java
if (collisionDebugVisible) {
    b2dr.render(world, camera.combined);
}
```

- Muc dich: quan sat truc tiep fixture cua `Walls` va `Obstacle` de debug loi xuyen tuong.
