package comp1110.ass2;
import comp1110.ass2.member.Factories;
import comp1110.ass2.member.Factory;
import comp1110.ass2.member.Tile;
import comp1110.ass2.member.TileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Written by Xiao Xu
 */

public class FactoriesTest {
    String f1 = "F0bbcc1abcd2aabe3abbc4acde";
    String f2 = "F0bbcc2aabe3abbc4acde";
    String f3 = "F3abbc4acde";
    String f4 = "F";

    Factories factories1 = new Factories(f1);
    Factories factories2 = new Factories(f2);
    Factories factories3 = new Factories(f3);
    Factories factories4 = new Factories(f4);

    @Test
    public void testEncode(){
        assertEquals("F0bbcc1abcd2aabe3abbc4acde",factories1.getCode());
        assertEquals("F0bbcc2aabe3abbc4acde",factories2.getCode());
        assertEquals("F3abbc4acde",factories3.getCode());
        assertEquals("F",factories4.getCode());
    }

    @Test
    public void testGetColor(){
        ArrayList<TileType> tileTypes = new ArrayList<>();
        assertEquals(tileTypes,factories2.getColors(1));
        assertEquals(tileTypes,factories3.getColors(0));
        assertEquals(tileTypes,factories4.getColors(3));
        tileTypes.add(TileType.Blue);
        tileTypes.add(TileType.Green);
        tileTypes.add(TileType.Orange);
        assertEquals(tileTypes,factories1.getColors(3));
        assertEquals(tileTypes,factories2.getColors(3));
        assertEquals(tileTypes,factories3.getColors(3));

    }

    @Test
    public void testClear(){
        factories1.clear(0);
        factories2.clear(4);
        factories3.clear(3);

        assertEquals("F1abcd2aabe3abbc4acde",factories1.getCode());
        assertEquals("F0bbcc2aabe3abbc",factories2.getCode());
        assertEquals("F4acde",factories3.getCode());
    }

    @Test
    public void testGetFactory(){
        assertEquals("abcd",factories1.getFactory(1).getCode());
        assertEquals("",factories2.getFactory(1).getCode());
        assertEquals("acde",factories3.getFactory(4).getCode());
        assertEquals("",factories4.getFactory(1).getCode());
    }

    @Test
    public void testTileNum(){
        assertEquals(0,factories1.tileNum('a',0));
        assertEquals(2,factories2.tileNum('a',2));
        assertEquals(1,factories3.tileNum('c',3));
        assertEquals(0,factories4.tileNum('a',2));
    }


}
