package comp1110.ass2;

import comp1110.ass2.member.Bag;
import comp1110.ass2.member.Tile;
import org.junit.jupiter.api.Test;


import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.*;

/** written by Yixin Ge*/

@org.junit.jupiter.api.Timeout(value = 5000, unit = MILLISECONDS)
public class BagTest {
    @Test
    public void testBagEmpty() {
        Tile[] tiles = new Tile[100];
        for (int i = 0; i < 100; i++) {
            tiles[i] = null;
        }
        Bag bagEmp = new Bag(tiles);
        assertEquals("B0000000000",bagEmp.getCode());
        assertEquals(0,bagEmp.lengthTile());

    }

    @Test
    public void testBagPart() {
        Tile[] tiles = new Tile[100];
        for (int i = 0; i < 10; i++){
            tiles[i] = new Tile('a');
        }
        for(int j = 0; j < 20; j++){
            tiles[j+10] = new Tile('b');
        }
        for(int n = 0; n < 100-10-20; n++){
            tiles[n+10+20] = null;
        }
        Bag bagPart = new Bag(tiles);
        assertEquals("B1020000000",bagPart.getCode());
        assertEquals(30,bagPart.lengthTile());
    }

    @Test
    public void testBagFull() {
        Tile[] tiles = new Tile[100];
        for (int i = 0; i < 20; i++) tiles[i] = new Tile('a');
        for (int j = 0; j < 20; j++) tiles[j+20] = new Tile('b');
        for (int k = 0; k < 20; k++) tiles[k+40] = new Tile('c');
        for (int n = 0; n < 20; n++) tiles[n+60] = new Tile('d');
        for (int m = 0; m < 20; m++) tiles[m+80] = new Tile('e');
        Bag bagFull = new Bag(tiles);
        assertEquals("B2020202020", bagFull.getCode());
        assertEquals(100,bagFull.lengthTile());
    }

    @Test
    public void testBagEmptyString() {
        String bagEmp = "B0000000000";
        Bag bag = new Bag();
        bag.decode(bagEmp);
        assertEquals("B0000000000",bag.getCode());
    }

    @Test
    public void testBagPartString() {
        String bagPart = "B1020000000";
        Bag bag = new Bag();
        bag.decode(bagPart);
        assertEquals("B1020000000",bag.getCode());
    }

    @Test
    public void testBagFullString() {
        String bagFull = "B2020202020";
        Bag bag = new Bag();
        bag.decode(bagFull);
        assertEquals("B2020202020",bag.getCode());
    }
}
