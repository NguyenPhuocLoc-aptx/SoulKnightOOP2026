# Bao cao chinh sua load 3 map

## 1. Cau truc game dang dung

- Launcher Gradle dang chay module LibGDX: `lwjgl3` -> `core`.
- Logic game chinh nam trong `core/src/main/java/io/mygdx/soulknight`.
- Asset map, tileset, anh, am thanh nam trong `assets`.
- Thu muc `src/` la code Java/Swing cu, khong phai luong chinh cua Gradle hien tai.

## 2. Luong map sau khi sua

File chinh: `core/src/main/java/io/mygdx/soulknight/screens/PlayScreen.java`

Code khai bao 3 map nam o bien:

```java
private static final LevelConfig[] LEVELS = { ... };
```

Moi `LevelConfig` gom:

## 2a. Fix loi camera theo player

- Nguyen nhan: `Player.currentPos` chi duoc khoi tao trong `defineCharacter()` va khong duoc cap nhat moi frame.
- Hieu ung: sprite render tren toa do cu, nen camera duoc cap nhat theo `player.b2body` van khong duoc dong bo dung o truc y.
- Da sua `core/src/main/java/io/mygdx/soulknight/Sprites/Player.java`: `Player.update(float dt)` cap nhat `currentPos = b2body.getWorldCenter()` moi frame.
- Ket qua: sprite va camera di chuyen dung theo ca 2 truc x va y.

## 2b. Muc luc LevelConfig

File chinh: `core/src/main/java/io/mygdx/soulknight/screens/PlayScreen.java`

Code khai bao 3 map nam o bien:

```java
new LevelConfig(
    "map2.tmx",                 // file map trong assets
    "Dungeon 2",                // ten hien tren HUD
    new Vector2(100, 100),      // vi tri spawn player
    new Vector2(896, 136),      // tam vung win
    new Vector2(16, 8),         // nua kich thuoc vung win
    new String[]{"Obstacle", "Walls"}, // object groups tao collision
    new EnemySpawn[]{ ... }     // quai cua map
)
```

Khi player cham `WinArea`:

- Neu dang o map 1 hoac map 2: `PlayScreen.win()` goi `new PlayScreen(game, levelIndex + 1)` de chuyen sang map ke tiep.
- Neu dang o map 3: game chuyen sang `WinScreen`.

## 3. File map da tao

Da tao 3 file placeholder:

- `assets/map1.tmx`
- `assets/map2.tmx`
- `assets/map3.tmx`

Hien tai ca 3 file dang copy tu `assets/map.tmx` de game chay duoc ngay. Ban co the mo `map2.tmx` va `map3.tmx` bang Tiled de sua rieng tung map.

## 4. Sua wall, obstacle, pickup cho map 2 va map 3

### 4.1 Wall va obstacle co collision

File xu ly collision: `core/src/main/java/io/mygdx/soulknight/Tools/B2WorldCreator.java`

Sau khi sua, collision khong con phu thuoc vao index layer `8`, `9`. Code doc object group theo ten:

```java
new B2WorldCreator(world, map, levelConfig.collisionLayerNames);
```

Mac dinh trong `PlayScreen.LEVELS`:

```java
new String[]{"Obstacle", "Walls"}
```

De them/sua collision trong map 2/3:

1. Mo `assets/map2.tmx` hoac `assets/map3.tmx` bang Tiled.
2. Tao hoac giu object group ten dung chinh xac la `Obstacle`.
3. Tao hoac giu object group ten dung chinh xac la `Walls`.
4. Them Rectangle Object vao cac group nay.
5. Save file `.tmx`.

Moi rectangle trong `Obstacle` hoac `Walls` se duoc tao thanh StaticBody Box2D va chan player/quai/dan theo collision hien tai.

Neu ban muon doi ten group, sua dong nay trong `PlayScreen.LEVELS` cua map tuong ung:

```java
new String[]{"Obstacle", "Walls"}
```

Vi du:

```java
new String[]{"Map2Obstacle", "Map2Walls"}
```

### 4.2 Tile visual

Tat ca layer tile trong file `.tmx` van duoc `OrthogonalTiledMapRenderer` render tu dong:

```java
renderer = new OrthogonalTiledMapRenderer(map);
```

Vi vay ban co the sua tile layer nhu `Background`, `Foreground`, `Torches`, `Box`, `Treasure`, `Bot` trong Tiled. Khong can sua Java neu chi doi hinh anh/tile.

### 4.3 Pickup

Trong map hien co object group `Pick up`, nhung code gameplay hien tai chua co class xu ly pickup. Neu chi them object vao `Pick up` trong Tiled thi no chua tao item trong game.

De lam pickup hoat dong, can them code moi theo huong:

1. Tao class pickup moi trong `core/src/main/java/io/mygdx/soulknight/Sprites` va implement `Contactable`.
2. Cho `B2WorldCreator` doc object group `Pick up`.
3. Khi tao body pickup, set fixture la sensor.
4. Trong `onContact`, neu object la player thi apply effect va destroy pickup.

Hien tai yeu cau chinh la load 3 map va chuyen map khi win, nen pickup chi duoc note lai de ban modify sau.

## 5. Sua vi tri player spawn cho map 2/3

File: `core/src/main/java/io/mygdx/soulknight/screens/PlayScreen.java`

Trong `LEVELS`, sua tham so thu 3:

```java
new Vector2(100, 100)
```

Vi du map 2 spawn o x=80, y=120:

```java
new Vector2(80, 120)
```

Class `Player` da duoc sua de nhan start position:

```java
new Player(world, mousePos, camera, levelConfig.playerStart);
```

## 6. Sua vung win cho map 2/3

File: `core/src/main/java/io/mygdx/soulknight/screens/PlayScreen.java`

Trong `LEVELS`, sua 2 tham so:

```java
new Vector2(896, 136), // tam vung win
new Vector2(16, 8),    // nua kich thuoc vung win
```

Class `WinArea` da duoc sua de nhan vi tri/kich thuoc:

```java
new WinArea(world, this, levelConfig.winAreaCenter, levelConfig.winAreaHalfSize);
```

Vi du vung win tam x=1200, y=100, rong 40, cao 20:

```java
new Vector2(1200, 100),
new Vector2(20, 10),
```

## 7. Sua quai cho map 2/3

File: `core/src/main/java/io/mygdx/soulknight/screens/PlayScreen.java`

Trong `LEVELS`, sua mang:

```java
new EnemySpawn[]{
    EnemySpawn.shooter(600, 115),
    EnemySpawn.chaser(380, 150)
}
```

Loai quai hien co:

```java
EnemySpawn.shooter(x, y)
EnemySpawn.chaser(x, y)
```

Neu map nao chua muon co quai:

```java
new EnemySpawn[]{}
```

## 8. Bang toa do trong game

Muc dich: chi hien toa do de ghi chu ra giay, khong xu ly hay thay doi gameplay.

Phim bat/tat:

```text
P
```

File xu ly phim: `core/src/main/java/io/mygdx/soulknight/screens/PlayScreen.java`

Code toggle:

```java
private void handleCoordinatePanelInput() {
    if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
        hud.setCoordinatePanelVisible(!hud.isCoordinatePanelVisible());
    }
}
```

Trong `update(float dt)`, game cap nhat noi dung bang toa do bang:

```java
hud.updateCoordinatePanel(
    levelConfig.displayName,
    levelConfig.mapPath,
    player.b2body.getPosition(),
    levelConfig.playerStart,
    levelConfig.winAreaCenter
);
```

File hien thi bang: `core/src/main/java/io/mygdx/soulknight/Scenes/Hud.java`

Cac method moi:

```java
public void setCoordinatePanelVisible(boolean visible)
public boolean isCoordinatePanelVisible()
public void updateCoordinatePanel(...)
```

Bang hien cac dong:

- Map hien tai va file `.tmx`.
- `Player: X ... | Y ...`
- `Spawn: X ... | Y ...`
- `Win: X ... | Y ...`

Bang nam o goc duoi ben trai man hinh game va co nen den mo de de doc tren nen map.

Neu chi can ghi vi tri player trong map 2/3:

1. Vao game va di chuyen den `Dungeon 2` hoac `Dungeon 3`.
2. Nhan `P` de hien bang.
3. Doc dong `Player: X ... | Y ...`.
4. Nhan `P` lan nua de tat bang.

## 9. Cac file code da sua

- `core/src/main/java/io/mygdx/soulknight/screens/PlayScreen.java`: them `LEVELS`, `LevelConfig`, `EnemySpawn`, load map theo level, chuyen map khi win.
- `core/src/main/java/io/mygdx/soulknight/Sprites/Player.java`: them constructor nhan vi tri spawn.
- `core/src/main/java/io/mygdx/soulknight/Sprites/WinArea.java`: them constructor nhan vi tri va kich thuoc vung win.
- `core/src/main/java/io/mygdx/soulknight/Tools/B2WorldCreator.java`: doc collision object group theo ten thay vi layer index.
- `core/src/main/java/io/mygdx/soulknight/Scenes/Hud.java`: HUD hien ten dungeon theo level va bang toa do bat/tat bang phim `P`.
- `core/src/main/java/io/mygdx/soulknight/Sprites/Monster/Monster.java`: them `clearAll()` de don static list khi chuyen map.
- `core/src/main/java/io/mygdx/soulknight/Sprites/Bullets/Bullet.java`: them `clearAll()` de don static list khi chuyen map.
- `core/src/main/java/io/mygdx/soulknight/screens/PlayScreen.java`: bo `TextureAtlas("Weapons.pack")` khong duoc dung de tranh tao asset thua moi lan vao level.

## 10. Cap nhat them ngay 2026-05-17

### 10.1 Cap nhat toa do win va spawn cho map 2/3

File: `core/src/main/java/io/mygdx/soulknight/screens/PlayScreen.java`

- `map2.tmx`
    - spawn: `new Vector2(900, 10)`
    - win center: `new Vector2(48, 12)`
- `map3.tmx`
    - spawn: `new Vector2(138, 460)`
    - win center: `new Vector2(893, 445)`

### 10.2 Sua path tileset map3

File: `assets/map3.tmx`

- Da doi path tuyet doi sang path tuong doi:

```xml
<image source="0x72_16x16DungeonTileset.v4.png" .../>
```

- Muc tieu: map3 load dung tren moi may, khong phu thuoc duong dan local cu.

### 10.3 Sua collision loader de lay dung Walls object group

File: `core/src/main/java/io/mygdx/soulknight/Tools/B2WorldCreator.java`

Van de:

- Trong TMX co the co trung ten `Walls` giua tile layer va object group.
- Cach cu `map.getLayers().get("Walls")` chi lay layer dau tien => de nham tile layer.
- Ket qua: chi tao duoc collision `Obstacle`, bi thieu collision `Walls`.

Da sua:

- Quet tat ca layer co ten trung `layerName`.
- Chi tao body tu `RectangleMapObject` cua tung layer tim duoc.

### 10.4 Bo sung ho tro rotation cho collision rectangle

File: `core/src/main/java/io/mygdx/soulknight/Tools/B2WorldCreator.java`

- Doc `rotation` tu properties cua object.
- Gan vao Box2D body angle:

```java
bdef.angle = rotationDegrees * MathUtils.degreesToRadians;
```

- Muc tieu: rectangle xoay `90/-90` trong Tiled van chan dung.

### 10.5 Hien collider de debug bang phim P

File: `core/src/main/java/io/mygdx/soulknight/screens/PlayScreen.java`

- Them `collisionDebugVisible`.
- Bam `P` se:
    - Bat/tat coordinate panel (nhu cu).
    - Bat/tat Box2D debug render (moi).

Code render:

```java
if (collisionDebugVisible) {
        b2dr.render(world, camera.combined);
}
```

Loi ich:

- Kiem tra truc tiep fixture cua `Walls` va `Obstacle` dang duoc tao hay khong.
- Khoanh vung nhanh loi xuyen tuong la do map data hay do physics setup.
