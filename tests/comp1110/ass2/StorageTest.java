package comp1110.ass2;

import comp1110.ass2.member.Storage;
import comp1110.ass2.member.Tile;
import comp1110.ass2.member.TileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**  Following Methods need to be tested.
 *      1. getCode(), encode()
 *      2. decode()
 *      3. placeTiles(), isPlaceValid()
 *      4. isRowFull()
 *      5. rowColor()
 *      6. isRowColorSame()
 *      7. isRowEmpty()
 *      8. emptyRow()
 *      9. emptySpace()
 *      10. move()
 */

public class StorageTest {
    @Test
    public void testStorage() {
        // test case 1(normal consequence): the case given by readme.md:
        Tile[] tiles = new Tile[]{
                new Tile(TileType.Red),
                null,
                new Tile(TileType.Green),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue),
                null,
                null,
                null,
                null,
                new Tile(TileType.Orange),
                new Tile(TileType.Orange),
                new Tile(TileType.Orange),
                new Tile(TileType.Orange),
                new Tile(TileType.Orange),
        };
        Storage storage1 = new Storage(tiles);
        // test encode()
        assertEquals("S0e11b12a34c5", storage1.getCode());
        // test isRowFull()
        boolean[] rowFull = new boolean[]{true, false, true, false, true};
        for (int i = 0; i < 4; i++) {
            assertEquals(rowFull[i], storage1.isRowFull(i));
        }

        // test isRowEmpty(int row)
        boolean[] rowEmpty = new boolean[]{false, false, false, true, false};
        for (int i = 0; i < 4; i++) {
            assertEquals(rowEmpty[i], storage1.isRowEmpty(i));
        }

        // test isRowColorSame(Tile tile, int row)
        Tile tileR = new Tile(TileType.Red);
        Tile tileG = new Tile(TileType.Green);
        Tile tileB = new Tile(TileType.Blue);
        Tile tileP = new Tile(TileType.Purple);
        Tile tileO = new Tile(TileType.Orange);
        Tile[] difColorTiles = new Tile[]{tileR, tileG, tileB, tileP, tileO};

        // test the first row(Full)
        assertFalse(storage1.isRowColorSame(tileG, 0));
        assertTrue(storage1.isRowColorSame(tileR, 0));
        // test the second row(not full)
        assertFalse(storage1.isRowColorSame(tileB, 1));
        assertTrue(storage1.isRowColorSame(tileG, 1));
        // test the fifth row(full but bigger than 1)
        assertFalse(storage1.isRowColorSame(tileP, 4));
        assertTrue(storage1.isRowColorSame(tileO, 4));
        // test the empty row
        assertTrue(storage1.isRowColorSame(tileP, 3));
        assertTrue(storage1.isRowColorSame(tileG, 3));
    }
}
