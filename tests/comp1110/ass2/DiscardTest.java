package comp1110.ass2;

import comp1110.ass2.member.Discard;
import comp1110.ass2.member.Tile;
import org.junit.jupiter.api.Test;


import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.*;

/** written by Yixin Ge*/

@org.junit.jupiter.api.Timeout(value = 5000, unit = MILLISECONDS)
public class DiscardTest {

    @Test
    public void testDiscardEmp() {
        int num = 100;
        Tile[] tiles = new Tile[100];
        for(int i = 0; i < num; i ++){
            tiles[i] = null;
        }
        Discard discardEmp = new Discard(tiles);
        assertEquals("D0000000000",discardEmp.getCode());
        assertTrue(discardEmp.isEmpty());
        assertEquals(0,discardEmp.lengthTile());
        Tile[] tiles1 = new Tile[10];
        for (int j = 0; j < 10; j++){
            tiles1[j] = new Tile('a');
        }
        assertTrue(discardEmp.placeTiles(tiles1));
        discardEmp.replaceTile('a');
        assertEquals("D1000000000",discardEmp.getCode());

    }

    @Test
    public void testDiscardPart() {
        int num = 100;
        Tile[] tiles = new Tile[100];
        for (int i = 0; i < 10; i++) {
            tiles[i] = new Tile('a');
        }
        for(int j = 0; j < 20;j++){
            tiles[j+10] = new Tile('b');
        }
        for(int k = 0; k < num-10-20; k++) {
            tiles[k+10+20] = null;
        }
        Discard discardPart = new Discard(tiles);
        assertEquals("D1020000000",discardPart.getCode());
        assertFalse(discardPart.isEmpty());
        assertEquals(30,discardPart.lengthTile());
        Tile[] tiles1 = new Tile[10];
        for (int n = 0; n < 10; n++){
            tiles1[n] = new Tile('a');
        }
        assertTrue(discardPart.placeTiles(tiles1));
        discardPart.replaceTile('c');
        assertEquals("D1920010000",discardPart.getCode());
    }

    @Test
    public void testDiscardFull() {
        int num = 100;
        Tile[] tiles = new Tile[100];
        for(int i = 0; i < 20;i++){
            tiles[i] = new Tile('a');
        }
        for(int j = 0; j < 20;j++){
            tiles[j+20] = new Tile('b');
        }
        for(int k = 0; k < 20;k++){
            tiles[k+40] = new Tile('c');
        }
        for(int n = 0; n < 20;n++){
            tiles[n+60] = new Tile('d');
        }
        for(int m = 0; m < 20;m++){
            tiles[m + 80] = new Tile('e');
        }
        Discard discardFull = new Discard(tiles);
        assertEquals("D2020202020",discardFull.getCode());
        assertFalse(discardFull.isEmpty());
        assertEquals(100,discardFull.lengthTile());
        Tile[] tiles1 = new Tile[10];
        for (int j = 0; j < 10; j++){
            tiles1[j] = new Tile('a');
        }
        assertFalse(discardFull.placeTiles(tiles1));
        discardFull.replaceTile('e');
        assertEquals("D2020202020",discardFull.getCode());
    }
    @Test
    public void testDiscardEmptyString() {
        String discardEmp = "D0000000000";
        Discard discard = new Discard();
        discard.decode(discardEmp);
        assertEquals("D0000000000",discard.getCode());
    }

    @Test
    public void testDiscardPartString() {
        String discardPart = "D1020000000";
        Discard discard = new Discard();
        discard.decode(discardPart);
        assertEquals("D1020000000",discard.getCode());
    }

    @Test
    public void testDiscardFullString() {
        String discardFull = "D2020202020";
        Discard discard = new Discard();
        discard.decode(discardFull);
        assertEquals("D2020202020",discard.getCode());
    }
}
