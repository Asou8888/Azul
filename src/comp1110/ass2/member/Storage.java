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
            /* Storage:
             *          *
             *          **
             *          ***
             *          ****
             *          *****
             */
            new Tile[STORAGE_ROW_LENGTH[0]],
            new Tile[STORAGE_ROW_LENGTH[1]],
            new Tile[STORAGE_ROW_LENGTH[2]],
            new Tile[STORAGE_ROW_LENGTH[3]],
            new Tile[STORAGE_ROW_LENGTH[4]]
    };

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
    public Storage(Tile[][] tiles) {
        this.tiles = tiles;
    }

    /**
     * Constructor with no parameters.
     */
    public Storage() {}


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
     * decode the state String(storage) to the storage class
     * Author: Ruizheng Shen, Date: 2021.4.27
     * @param storage
     */
    public void decode(String storage) {
        // TODO: test
        // String storage: S{[row][colorChar][number]}*
        // storage.charAt(0) == 'S'
        for (int i = 1; i < storage.length(); i = i + 3) {
            int row = storage.charAt(i) - '0'; // (which row)translate character to int.(EX: (ascii)'4' - (ascii)'0' = 4)
            char tile = storage.charAt(i + 1); // the tile colorChar.
            int num = storage.charAt(i + 2) - '0'; // (how many)translate character to int.
            Tile[] newTiles = new Tile[num];
            for (int j = 0; j < num; j++) {
                newTiles[j] = new Tile(tile); // initialize the tile with colorChar.
            }
            placeTiles(newTiles, row);
        }
    }

    /**
     * place the tiles in the storage.
     * Author: Ruizheng Shen, Date: 2021.4.27
     * @param tiles the tiles which will be placed in the storage
     * @param row the row to place
     * @return whether this move is valid.
     */
    public boolean placeTiles(Tile[] tiles, int row) {
        // TODO: test, first check the validity(not finished yet)
        for (int i = 0; i < tiles.length; i++) {
            this.tiles[row][STORAGE_ROW_LENGTH[row] - 1 - i] = tiles[i];
        }
        return false;
    }

    /**
     * Determines if the tiles are filled fully in one row.
     * @return whether a row is filled by tiles fully.
     */
    public boolean isRowFull(int row) {
        if (row < 0 || row >= STORAGE_ROW_NUM) return false; // might be raise as an error.
        for (int i = 0; i < STORAGE_ROW_LENGTH[row]; i++) {
            if (this.tiles[row][i] == null) return false;
        }
        return true;
    }

    public TileType rowColor(int row) {
        if (isRowEmpty(row)) {
            return (TileType) null;
        }
        for (int i = 0; i < STORAGE_ROW_LENGTH[row]; i++) {
            if (tiles[row][i] != null) {
                return tiles[row][i].getTileType();
            }
        }
        return null;
    }

    public char charRowColor(int row) {
        return tiles[row][0].getCode().toCharArray()[0];
    }

    /**
     * Determines if the color of tiles got from factory is same
     * as the tiles in a row in Storage.
     * @param tile, row
     * @return whether the color of tiles from factory and currently
     * in a row in Storage are same.
     */
    public boolean isRowColorSame(Tile tile, int row){
        // If this row is empty and this tile is not a first player tile, then any color could be place here.
        if (isRowEmpty(row) && tile.getTileType() != TileType.FirstPlayer) return true;
        for (int i = 0; i < STORAGE_ROW_LENGTH[row]; i++) {
            // check the tiles' color in this row.
            if (this.tiles[row][i] != null) {
                if (this.tiles[row][i].getTileType() == tile.getTileType()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Check whether this row is empty.
     * @param row
     * @return whether this row is empty.
     */
    public boolean isRowEmpty(int row) {
        for (int i = 0; i < STORAGE_ROW_LENGTH[row]; i++) {
            if (this.tiles[row][i] != null) return false;
        }
        return true;
    }

    /**
     * Determines the color of tiles from factory is not same as any
     * one of colors in the same row in Mosaic.(use to check the validity of player move)
     * @param tilesFromFactory, colorList
     * @return whether the color is not same as any colors having in the same row in Mosaic.
     */
    public boolean isColorDifInMosaicRow(Tile[] tilesFromFactory, TileType[] colorList) {
        // TODO: test
        // row: the index of the row
        // colorList: the colors contained in this row in mosaic
        TileType tileColor = tilesFromFactory[0].getTileType();
        for (TileType color: colorList) {
            if (tileColor == color) return false;
        }
        return true;
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

    public Tile[][] emptyRow(int row){
        for(int i =0;i<=row;i++){
            tiles[row][i] = null;
        }
        return tiles;
    }

    public int emptySpace(int row){
        int num = 0;
        for(int i = 0; i<=row;i++){
            if(tiles[row][i] == null){
                num += 1;
            }
        }
        return num;
    }

    public Tile[][] move(char color,int row,int num){
        if(emptySpace(row) >= num) {
            num = num;
        }else {
            num = emptySpace(row);
        }
            int numOfTile = 0;
            for(int i = 0;i<=row;i++){
                if(tiles[row][i] == null){
                    tiles[row][i] = new Tile(color);
                    numOfTile += 1;
                    if(numOfTile == num){
                        break;
                    }
                }
            }

        return tiles;
    }

    public static void main(String[] args) {
        Storage s = new Storage();
        s.decode("S2a13e44a1"); // decode the String and put them into the storage
        for (int i = 0; i < STORAGE_ROW_NUM; i++) {
            System.out.print("[");
            for (int j = 0; j < STORAGE_ROW_LENGTH[i]; j++) {
                if (s.tiles[i][j] == null) {
                    System.out.print(" , ");
                } else {
                    System.out.print(s.tiles[i][j].getCode() + ", ");
                }
            }
            System.out.println("]");
        }



    }

}
