package comp1110.ass2;

import comp1110.ass2.member.Floor;
import comp1110.ass2.member.Tile;
import comp1110.ass2.member.TileType;
import org.junit.jupiter.api.Test;


import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.*;


@org.junit.jupiter.api.Timeout(value = 5000, unit = MILLISECONDS)
public class FloorTest {

    @Test
    public void testFloorEmpty() {
        Tile[] tiles = new Tile[]{null,null,null,null,null,null,null};
        Floor floorEmp = new Floor(tiles);
        assertEquals("F",floorEmp.getCode());
        assertEquals(0,floorEmp.score());
        assertFalse(floorEmp.isFloorFull());
    }

    @Test
    public  void testFloorFull() {
        Tile[] tiles = new Tile[]{
                new Tile(TileType.Red),
                new Tile(TileType.Green),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue),
                null,
                null};
        Floor floorFul = new Floor(tiles);
        assertEquals("Febaaa",floorFul.getCode());
        assertEquals(-8,floorFul.score());
        assertFalse(floorFul.isFloorFull());
        }

    }


