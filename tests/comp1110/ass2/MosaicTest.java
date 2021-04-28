package comp1110.ass2;

import comp1110.ass2.member.Mosaic;
import comp1110.ass2.member.Storage;
import comp1110.ass2.member.Tile;
import comp1110.ass2.member.TileType;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class MosaicTest {
    @Test //test getCode of mosaic
    public void testMosaicGetCode(){
        //test with general case
        Tile[] tiles = new Tile[]{
                new Tile(TileType.Red), null, null, null, null,
                null, null, new Tile(TileType.Green), null, new Tile(TileType.Blue),
                new Tile(TileType.Blue),null, null, null, null,
                null,new Tile(TileType.Blue),null,null,new Tile(TileType.Purple),
                new Tile(TileType.Purple), null,new Tile(TileType.Orange),new Tile(TileType.Orange),null
        };
        Mosaic m = new Mosaic(tiles);

        assertEquals("Me00b12a14a20a31d34d40c42c43", m.getCode());
    }
    @Test //test isRowFull   if returns true, the current round is over
    public void testMosaicIsRowFull(){
        //test1: there is no full row and returns false
        Tile[] tiles1 = new Tile[]{
                new Tile(TileType.Red), null, null, null, null,
                null, null, new Tile(TileType.Green), null, new Tile(TileType.Blue),
                new Tile(TileType.Blue),null, null, null, null,
                null,new Tile(TileType.Blue),null,null,new Tile(TileType.Purple),
                new Tile(TileType.Purple), null,new Tile(TileType.Orange),new Tile(TileType.Orange),null
        };
        Mosaic m1 = new Mosaic(tiles1);
        //test2: there is a full row and returns true
        Tile[] tiles2 = new Tile[]{
                new Tile(TileType.Red), new Tile(TileType.Green), new Tile(TileType.Blue), new Tile(TileType.Purple), new Tile(TileType.Orange),
                null, null, new Tile(TileType.Green), null, new Tile(TileType.Blue),
                new Tile(TileType.Blue),null, null, null, null,
                null,new Tile(TileType.Blue),null,null,new Tile(TileType.Purple),
                new Tile(TileType.Purple), null,new Tile(TileType.Orange),new Tile(TileType.Orange),null
        };
        Mosaic m2 = new Mosaic(tiles2);
        assertEquals(false, m1.isRowFull());
        assertEquals(true, m2.isRowFull());

    }
    @Test //test ColorList method
    public void testColorList(){
        Tile[] tiles = new Tile[]{
                new Tile(TileType.Red), null, null, null, null,
                null, null, new Tile(TileType.Green), null, new Tile(TileType.Blue),
                new Tile(TileType.Blue),null, null, null, null,
                null,new Tile(TileType.Blue),null,null,new Tile(TileType.Purple),
                new Tile(TileType.Purple), null,new Tile(TileType.Orange),new Tile(TileType.Orange),null
        };
        Mosaic m = new Mosaic(tiles);
        assertEquals(TileType.Red, m.RowcolorList(1)[0]); //test the first element
        assertEquals(TileType.Blue, m.RowcolorList(2)[1]); //test teh case that the first element in tile[i] is null
        assertEquals(TileType.Purple, m.RowcolorList(5)[0]); // test the last row
        assertEquals(TileType.Orange, m.RowcolorList(5)[2]); //test the last element in colorList

        assertEquals(TileType.Red, m.ColumncolorList(1)[0]);
        assertEquals(TileType.Blue, m.ColumncolorList(2)[0]);
        assertEquals(TileType.Blue, m.ColumncolorList(5)[0]);
        assertEquals(TileType.Purple, m.ColumncolorList(5)[1]);

    }

}
