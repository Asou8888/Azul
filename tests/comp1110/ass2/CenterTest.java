package comp1110.ass2;

import comp1110.ass2.member.Center;
import comp1110.ass2.member.Tile;
import comp1110.ass2.member.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Written by Xiao Xu
 */

public class CenterTest {
    Tile[] tiles1 = new Tile[]{
            new Tile(TileType.Red),new Tile(TileType.Green),new Tile(TileType.Blue),
            new Tile(TileType.Blue)
    };

    Tile[] tiles2 = new Tile[]{
            new Tile(TileType.FirstPlayer), new Tile(TileType.Green), new Tile(TileType.Blue),
    };

    Tile[] tiles3 = new Tile[]{

    };

    Tile[] tiles4 = new Tile[]{
            new Tile(TileType.Red),  new Tile(TileType.Green),  new Tile(TileType.Blue),
            new Tile(TileType.FirstPlayer)
    };
    Center center1 = new Center(tiles1);
    Center center2 = new Center(tiles2);
    Center center3 = new Center(tiles3);
    Center center4 = new Center(tiles4);


    @Test
    public void testCenterEncode(){
        assertEquals("Caabe",center1.encode());
        assertEquals("Cabf",center2.encode());
        assertEquals("C",center3.encode());
        assertEquals("Cabef",center4.encode());

    }

    @Test
    public void testCenterGetCurrentNum(){
        assertEquals(4,center1.getCurrentNum());
        assertEquals(3,center2.getCurrentNum());
        assertEquals(0,center3.getCurrentNum());
        assertEquals(4,center4.getCurrentNum());

    }

    @Test
    public void testCenterHasFirstPlayer(){
        assertFalse(center1.hasFirstPlayer());
        assertTrue(center2.hasFirstPlayer());
        assertFalse(center3.hasFirstPlayer());
        assertTrue(center4.hasFirstPlayer());
    }

    @Test
    public void testTileNum(){
        assertEquals(2,center1.tileNum('a'));
        assertEquals(1,center2.tileNum('b'));
        assertEquals(0,center3.tileNum('a'));
        assertEquals(1,center4.tileNum('e'));
    }

    @Test
    public void testDeleteTile(){
        assertEquals("Cbe",center1.deleteTile('a'));
        assertEquals("Cab",center2.deleteTile('f'));
        assertEquals("C",center3.deleteTile('a'));
        assertEquals("Cabf",center4.deleteTile('e'));
    }

    @Test
    public void testIsEmpty(){
        assertFalse(center1.isEmpty());
        assertFalse(center2.isEmpty());
        assertTrue(center3.isEmpty());
        assertFalse(center4.isEmpty());
    }

    @Test
    public void testAddTile(){
        center1.addTile("aa");
        center2.addTile("bce");
        center3.addTile("");
        center4.addTile("bb");
        assertEquals("Caaaabe",center1.encode());
        assertEquals("Cabbcef",center2.encode());
        assertEquals("C",center3.encode());
        assertEquals("Cabbbef",center4.encode());
    }

    @Test
    public void testPlaceTiles(){
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(TileType.Green));
        tiles.add(new Tile(TileType.Red));
        center1.placeTiles(tiles);
        center2.placeTiles(tiles);
        center3.placeTiles(tiles);
        center4.placeTiles(tiles);
        assertEquals("Caabbee",center1.encode());
        assertEquals("Cabbef",center2.encode());
        assertEquals("Cbe",center3.encode());
        assertEquals("Cabbeef",center4.encode());

    }

}
