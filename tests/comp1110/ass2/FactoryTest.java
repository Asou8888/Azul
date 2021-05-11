package comp1110.ass2;

import comp1110.ass2.member.Factory;
import comp1110.ass2.member.Tile;
import comp1110.ass2.member.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Written by Xiao Xu
 */

public class FactoryTest {
    Tile[] tiles1 = new Tile[]{
            new Tile(TileType.Red),new Tile(TileType.Green),new Tile(TileType.Blue),
            new Tile(TileType.Blue)
    };
    Tile[] tiles2 = new Tile[]{
            new Tile(TileType.Red),new Tile(TileType.Green)
    };
    Tile[] tiles3 = new Tile[]{

    };
    Tile[] tiles4 = new Tile[]{
            new Tile(TileType.Red),new Tile(TileType.Green),new Tile(TileType.Blue)
    };

    Factory factory1 = new Factory(tiles1);
    Factory factory2 = new Factory(tiles2);
    Factory factory3 = new Factory(tiles3);
    Factory factory4 = new Factory(tiles4);

    @Test
    public void testTileNum(){
        assertEquals(4,factory1.tileNum());
        assertEquals(2,factory2.tileNum());
        assertEquals(0,factory3.tileNum());
        assertEquals(3,factory4.tileNum());
    }
    @Test
    public void testEncode(){
        assertEquals("aabe",factory1.getCode());
        assertEquals("be",factory2.getCode());
        assertEquals("",factory3.getCode());
        assertEquals("abe",factory4.getCode());
    }

    @Test
    public void testDeleteTile(){
        assertEquals("be",factory1.deleteTile('a'));
        assertEquals("e",factory2.deleteTile('b'));
        assertEquals("",factory3.deleteTile('a'));
        assertEquals("ab",factory4.deleteTile('e'));
    }

    @Test
    public void testIsEmpty(){
        assertFalse(factory1.isEmpty());
        assertFalse(factory2.isEmpty());
        assertTrue(factory3.isEmpty());
        assertFalse(factory4.isEmpty());
    }

    @Test
    public void testGetColor(){
        ArrayList<TileType> tileTypes = new ArrayList<>();
        assertEquals(tileTypes,factory3.getColors());
        tileTypes.add(TileType.Red);
        tileTypes.add(TileType.Green);
        assertEquals(tileTypes,factory2.getColors());
        tileTypes.add(TileType.Blue);
        assertEquals(tileTypes,factory1.getColors());
        assertEquals(tileTypes,factory4.getColors());
    }

    @Test
    public void testTileAmount(){
        assertEquals(4,factory1.tileAmount());
        assertEquals(2,factory2.tileAmount());
        assertEquals(0,factory3.tileAmount());
        assertEquals(3,factory4.tileAmount());
    }

    @Test
    public void testClear(){
        factory1.clear();
        factory2.clear();
        factory3.clear();
        factory4.clear();
        assertEquals("",factory1.getCode());
        assertEquals("",factory2.getCode());
        assertEquals("",factory3.getCode());
        assertEquals("",factory4.getCode());
        assertEquals(0,factory1.tileAmount());
        assertEquals(0,factory2.tileAmount());
        assertEquals(0,factory3.tileAmount());
        assertEquals(0,factory4.tileAmount());
    }


}
