package comp1110.ass2.member;

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
    public Storage(Tile[] tiles){
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
        return "";
    }

    /**
     * Determines if the tiles are filled fully in one row.
     * @return whether a row is filled by tiles fully.
     */
    public boolean isRowFull(){
        //TODO
        return false;}

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

}
