package comp1110.ass2.member;

import java.awt.*;

/**
 * @author Yixin Ge
 * @version 1.0
 * @since 2021.3.27
 */

public class Storage {
    public static final int STORAGE_ROW_NUM = 5;
    public static final int[] STORAGE_ROW_LENGTH = new int[]{1, 2, 3, 4, 5};
    /**Storage has 5 rows and it is a triangular array of tiles */
    private Tile[][] tiles = new Tile[][]{
            new Tile[STORAGE_ROW_LENGTH[0]],
            new Tile[STORAGE_ROW_LENGTH[1]],
            new Tile[STORAGE_ROW_LENGTH[2]],
            new Tile[STORAGE_ROW_LENGTH[3]],
            new Tile[STORAGE_ROW_LENGTH[4]]
    };
    private static final int STORAGE_WIDTH = 15;
    /**
     * Constructor for the Storage. Given an array of tiles, returns the
     * current state of where each char represented tile is location in the storage.
     *
     * @param tiles the given array of Tile
     */
    public Storage(Tile[] tiles) {
        int pointer = 0;
        for (int i = 0; i < STORAGE_ROW_NUM; i++) {
            for (int j = 0; j < STORAGE_ROW_LENGTH[i]; j++) {
                if (tiles[pointer] == null) {
                    this.tiles[i][j] = null;
                    pointer++;
                } else {
                    this.tiles[i][j] = tiles[pointer];
                    pointer++;
                }
            }
        }
    }

    /**
     * Return the code of the current state in Storage.
     * @return the String code of Storage.
     */
    public String getCode() {
        return encode();
    }

    /**
     * The Storage String is composed of 3-character substrings representing the tiles in the storage area. These are ordered
     * numerically by row number.
     *
     * The first character is "S"
     *
     * The remaining characters are in groups of three in the pattern: {row number}{tile}{number of tiles}
     *
     * row number 0 - 4.
     * tile a - e.
     * number of tiles stored in that row from 1 - 5.
     * The maximum number of tiles in a given row is equal to (row number + 1).
     * @return a String of code
     */
    private String encode() {
        // TODO: test
        StringBuilder code = new StringBuilder("S");
        for (int i = 0; i < STORAGE_ROW_NUM; i++) {
            // count the number of tiles in this row
            int cnt = 0;
            String colorChar = "";
            for (int j = 0; j < STORAGE_ROW_LENGTH[i]; j++) {
                if (this.tiles[i][j] != null) {
                    cnt++; // if there is a tile in position(i, j), add the count by 1.
                    colorChar = this.tiles[i][j].getCode(); // record the tile's color of this row.
                }
            }
            if (cnt != 0) {
                // if this row is not empty, encode this row.
                // 'i': the row number
                // 'colorChar': a - e
                // 'cnt': number of tiles stored in row[i]
                code.append(i).append(colorChar).append(cnt);
            }
        }
        return code.toString();
    }

    /**
     * Determines if the tiles are filled fully in one row.
     * @return whether a row is filled by tiles fully.
     */
    public boolean isRowFull(int row) {
        //TODO: test
        /* tiles structure:
         *      *
         *      **
         *      ***
         *      ****
         *      *****
         */

        if (row < 0 || row >= STORAGE_ROW_NUM) return false; // might be raise as an error.
        for (int i = 0; i < STORAGE_ROW_LENGTH[row]; i++) {
            if (this.tiles[row][i] == null) return false;
        }
        return true;
    }

    /**
     * Determines if the color of tiles got from factory is same
     * as the tiles in a row in Storage.
     * @param tile, row
     * @return whether the color of tiles from factory and currently
     * in a row in Storage are same.
     */
    public boolean isRowColorSame(Tile tile, int row){
        //TODO
        // If this row is empty and this tile is not a first player tile, then any color could be place here.
        if (isRowEmpty(row) && tile.getTileType() != TileType.FirstPlayer) return true;
        for (int i = 0; i < STORAGE_ROW_LENGTH[row]; i++) {
            // check the tiles' color in this row.
        }
        return false;
    }

    /**
     * Check whether this row is empty.
     * @param row
     * @return whether this row is empty.
     */
    public boolean isRowEmpty(int row) {
        // TODO: test
        for (int i = 0; i < STORAGE_ROW_LENGTH[row]; i++) {
            if (this.tiles[row][i] != null) return false;
        }
        return true;
    }

    /**
     * Determines the color of tiles from factory is not same as any
     * one of colors in the same row in Mosaic.
     * @param row
     * @return whether the color is not same as any colors having in the same row in Mosaic.
     */
    public boolean isColorDifInMosaicRow(int row) {
        // TODO: should be written in mosaic
        return false;
    }

    /**
     * Determines if the rightmost space for tile in a row is empty.
     * @param row
     * @return whether the rightmost space for tile in a row is empty or not.
     */
    public boolean isRightEmpty(int row){
        // TODO: test
        // the rightmost position in a row, should be 'STORAGE_ROW_LENGTH[row] - 1'.
        return this.tiles[row][STORAGE_ROW_LENGTH[row] - 1] == null;
    }

    public static void main(String[] args) {
        Tile[] tiles = new Tile[]{
                new Tile(TileType.Red),
                null,
                new Tile(TileType.Green),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue),
                new Tile(TileType.Blue),
                null,
                null,
                new Tile(TileType.Purple),
                new Tile(TileType.Purple),
                new Tile(TileType.Orange),
                new Tile(TileType.Orange),
                new Tile(TileType.Orange),
                new Tile(TileType.Orange),
                new Tile(TileType.Orange),
        };
        Storage s = new Storage(tiles);

        // testing isRowFull(int row)
        System.out.println(s.isRowFull(0));
        System.out.println(s.isRowFull(1));
        System.out.println(s.isRowFull(2));
        System.out.println(s.isRowFull(3));
        System.out.println(s.isRowFull(4));

        // testing encode()
        System.out.println(s.encode());
    }

}
