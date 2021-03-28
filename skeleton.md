# skeleton

## Brief

After discussing and analysing the rules of the Azul, we concluded 8 classes which need to be added,
during the implement of the game. These 8 classes include: Bag, Center, Discard, Factory, Floor, Mosaic, Player and Storage.

## Bag

The bag represents the real bag that store the tiles in the game. This class will first act at the start of the
game with 100 tiles in it. Then, some tiles will be moved from the bag to the factories, the actual number
of moved tiles will depend on the number of factories(which depends on the number of players). At the start of
each round, tiles should be taken from the bag to the factories, until the bag is empty.

Members: an array of tiles: Tile[100]
Methods: getCode(), enCode(), curNum()

## Factory

The factory represents the factories in the game, whose number depends on the number of players. The instance factory will act at
the start of each round, and everytime player decides to grasp tiles from this factory. Each factory contains at most 4 tiles.

Members: an array of tiles: Tile[4]
Methods: getCode(), enCode(), isEmpty(), tileAmount()

## Center

The center represents the actual center place in the game(which is in the middle of the factories). This class will first
act at the start of the game, when the 'first player' tile will be placed in. Each time player grasps tiles from a factory,
the remaining tiles will be moved to the center.

Members: an array of tiles: Tile[100](maybe using a collection is better)
Methods: getCode(), enCode() getCurrentNum(), hasFirstPlayer(), isEmpty()

## Discard

The discard represents the box in the game, which will store the tiles from the storages at the end of each round.
When the bag does not have enough tiles to fulfill factories at the start of a round, the tiles in the discard will be used.

Members: an array of tiles: Tile[100](maybe using a collection is better)
Methods: getCode(), enCode(), isEmpty(), curNum()

## Storage

The storage represents the storage in the game, which will temporarily store tiles, which hose be moved to the mosaic or the
floor. Everytime a player grasps tiles from center or factories, those tiles should be placed in the storage, according to a
series of rules. Firstly, each row must store tiles with same color. Secondly, before placing tiles in this row, the corresponding
row in the mosaic needs to be checked(whether this color has already been placed in this row in mosaic). A the end of each round,
when tiles are moved to mosaic, the remaining tiles of this row have to be placed in the discard.

Members: An array of tiles: Tile[15]
Methods: getCode(), enCode(), isRowFull(), isRowColorSame(), isColorDifInMosaicRow(), isRightEmpty()

## Mosaic

The mosaic represents the mosaic in the game, which will store tiles for scoring. The mosaic should ensure each tile's color should be
different in each row and column. At the end of each round and the end of the game, player is scored according to their mosaic.

Members: An array of tiles: Tile[25]
Methods: getCode(), enCode(), score(), getBonusScore(), isRowFull()

## Floor

The floor represents the floor with penalty in the game. The first player who grasps the 'first player' tile, has to
put the tile in the floor. Each time the storage is full, the remaining tiles also need to be placed in the floor. The total score of
a player depends on the score in mosaic and the penalty in floor.

Members: An array of tiles: Tile[7], An array of penalty score: int[7] = {-1, -1, -2, -2, -2, -3, -3}
Methods: getCode(), enCode(), score()

## Player

The player represents the player attending the game, which will be up to 4 and no less than 2. The implement of moves should be in
this class. Each player should be assigned by a mark('A' - 'D'), which will become the code of this player.

Members: playerName, code, floor, storage, mosaic
Methods: draftMove(), tileMove(), getCode(), enCode()

## Tile

The Tile class should be abstract and be extended as each color tile and 'first player' tile.

Members: colorChar, colorName
Methods: getCode()