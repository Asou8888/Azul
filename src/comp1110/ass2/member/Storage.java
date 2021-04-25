package comp1110.ass2.member;

import java.awt.*;

/**
 * @author Yixin Ge
 * @version 1.0
 * @since 2021.3.27
 */

public class Storage {
    /**Storage has 5 rows and it is a triangular array of tiles */
    private Tile[] tiles;
    private static final int STORAGE_WIDTH = 15;
    /**
     * Constructor for the Storage. Given an array of tiles, returns the
     * current state of where each char represented tile is location in the storage.
     *
     * @param tiles the given array of Tile
     */
    public Storage(Tile[] tiles) {
        //TODO
        this.tiles = tiles;
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
        //TODO
        StringBuilder code = new StringBuilder();
        for (Tile t: tiles) {

        }
        return "";
    }

    /**
     * Determines if the tiles are filled fully in one row.
     * @return whether a row is filled by tiles fully.
     */
    public boolean isRowFull(int row) {
        //TODO
        /** Tiles:
         *      *
         *      **
         *      ***
         *      ****
         *      *****
         */
        // The first line
        switch (row) {
            case 0:
                return tiles[0] != null;
            case 1:
                return tiles[1] != null && tiles[2] != null;
            case 2:
                return tiles[3] != null && tiles[4] != null && tiles[5] != null;
            case 3:
                return tiles[6] != null && tiles[7] != null && tiles[8] != null && tiles[9] != null;
            default:
                return tiles[10] != null && tiles[11] != null && tiles[12] != null && tiles[13] != null && tiles[14] != null;
        }
    }

    /**
     * Determines if the color of tiles got from factory is same
     * as the tiles in a row in Storage.
     * @return whether the color of tiles from factory and currently
     * in a row in Storage are same.
     */
    public boolean isRowColorSame(){
        //TODO
        return false;}

    /**
     * Determines the color of tiles from factory is not same as any
     * one of colors in the same row in Mosaic.
     * @return whether the color is not same as any colors having in the same row in Mosaic.
     */
    public boolean isColorDifInMosaicRow(){
        //TODO
        return false;}

    /**
     * Determines if the rightmost space for tile in a row is empty.
     * @return whether the rightmost space for tile in a row is empty or not.
     */
    public  boolean isRightEmpty(){
        //TODO
        return false;
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
    }

}
