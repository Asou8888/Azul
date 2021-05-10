package comp1110.ass2;

import comp1110.ass2.member.Floor;
import comp1110.ass2.member.Tile;
import comp1110.ass2.member.TileType;
import org.junit.jupiter.api.Test;


import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.*;

/** written by Yixin Ge*/

@org.junit.jupiter.api.Timeout(value = 5000, unit = MILLISECONDS)
public class FloorTest {

    @Test
    public void testFloorEmpty() {
        Tile[] tiles = new Tile[]{null,null,null,null,null,null,null};
        Floor floorEmp = new Floor(tiles);
        assertEquals("F",floorEmp.getCode());
        assertEquals(0,floorEmp.score());
        assertFalse(floorEmp.isFloorFull());
        floorEmp.placeTile('a',3);
        assertEquals("Faaa",floorEmp.getCode());
    }

    @Test
    public  void testFloorPart() {
        Tile[] tiles = new Tile[]{
                new Tile(TileType.Red),
                new Tile(TileType.Green),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue),
                null,
                null};
        Floor floorPart = new Floor(tiles);
        assertEquals("Febaaa",floorPart.getCode());
        assertEquals(-8,floorPart.score());
        assertFalse(floorPart.isFloorFull());
        floorPart.placeTile('b',4);
        assertEquals("Febaaabb",floorPart.getCode());
        }

    @Test
    public  void testFloorFull() {
        Tile[] tiles = new Tile[]{
                new Tile(TileType.Red),
                new Tile(TileType.Green),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue)};
        Floor floorFul = new Floor(tiles);
        assertEquals("Febaaaaa",floorFul.getCode());
        assertEquals(-14,floorFul.score());
        assertTrue(floorFul.isFloorFull());
        floorFul.placeTile('b',3);
        assertEquals("Febaaaab",floorFul.getCode());
    }

    @Test
    public void testFloorEmptyString() {
        String floorEmp =  "F";
        Floor floor = new Floor();
        floor.decode(floorEmp);
        assertEquals("F",floor.getCode());
    }

    @Test
    public void testFloorPartString() {
        String floorPart = "Faabb";
        Floor floor = new Floor();
        floor.decode(floorPart);
        assertEquals("Faabb",floor.getCode());
    }

    @Test
    public void testFloorFullString() {
        String floorFull = "Faabbccc";
        Floor floor = new Floor();
        floor.decode(floorFull);
        assertEquals("Faabbccc",floor.getCode());
    }
    }


