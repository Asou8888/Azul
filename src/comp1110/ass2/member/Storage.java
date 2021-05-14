package comp1110.ass2.member;

import comp1110.ass2.gui.Game;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;

import java.awt.*;

/**
 * @author Yixin Ge
 * @version 1.0
 * @since 2021.3.27
 */

/**
 * Modified by Ruizheng Shen.
 * @author Ruizheng Shen
 * @version 2.0
 * @since 2021.4.26
 */

public class Storage {
    public static final int STORAGE_ROW_NUM = 5;
    public static final int[] STORAGE_ROW_LENGTH = new int[]{1, 2, 3, 4, 5};
    /**
     * Storage has 5 rows and it is a triangular array of tiles
     */
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

    /* Member of javafx */
    private Group storageView = new Group();
    int xIndex;
    int yIndex;

    /**
     * Constructor for the Storage. Given an array of tiles, returns the
     * current state of where each char represented tile is location in the storage.
     *
     * @param tiles the given array of Tile
     */
    public Storage(Tile[] tiles) {
        createView();
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
        createView();
        this.tiles = tiles;
    }

    public Storage(String storage) {
        // TODO test
        decode(storage);
    }

    /**
     * Constructor with no parameters.
     */
    public Storage() {
        createView();
    }

    private void createView() {
        for (int i = 0; i < STORAGE_ROW_NUM; i++) {
            for (int j = 0; j < STORAGE_ROW_LENGTH[i]; j++) {
                this.storageView.getChildren().add(new Tile(' ', (j - STORAGE_ROW_LENGTH[i] + 1) * Tile.TILE_WIDTH, i * Tile.TILE_WIDTH));
            }
        }
    }

    public void updateStorageView() {
        this.storageView.getChildren().clear();
        for (int i = 0; i < STORAGE_ROW_NUM; i++) {
            for (int j = 0; j < STORAGE_ROW_LENGTH[i]; j++) {
                if (this.tiles[i][j] == (Tile) null) {
                    this.storageView.getChildren().add(new Tile(' ', (j - STORAGE_ROW_LENGTH[i] + 1) * Tile.TILE_WIDTH, i * Tile.TILE_WIDTH));
                } else {
                    this.storageView.getChildren().add(new Tile(tiles[i][j].getTileType(), (j - STORAGE_ROW_LENGTH[i] + 1) * Tile.TILE_WIDTH, i * Tile.TILE_WIDTH));
                }
            }
        }
    }

    public Group getStorageView() {
        updateStorageView();
        return this.storageView;
    }

    public void setLocation(int xIndex, int yIndex) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.storageView.setLayoutX(this.xIndex);
        this.storageView.setLayoutY(this.yIndex);
    }


    /**
     * Return the code of the current state in Storage.
     *
     * @return the String code of Storage.
     */
    public String getCode() {
        return encode();
    }

    /**
     * The Storage String is composed of 3-character substrings representing the tiles in the storage area. These are ordered
     * numerically by row number.
     * <p>
     * The first character is "S"
     * <p>
     * The remaining characters are in groups of three in the pattern: {row number}{tile}{number of tiles}
     * <p>
     * row number 0 - 4.
     * tile a - e.
     * number of tiles stored in that row from 1 - 5.
     * The maximum number of tiles in a given row is equal to (row number + 1).
     *
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
     *
     * @param storage
     */
    public void decode(String storage) {
        // TODO: test
        // String storage: S{[row][colorChar][number]}*
        // storage.charAt(0) == 'S'
        for (int i = 1; i < storage.length(); i = i + 3) {
            int row = storage.charAt(i) - '0'; // (which row)translate character to int.(EX: (ascii)'4' - (ascii)'0' = 4)
            char colorChar = storage.charAt(i + 1); // the tile colorChar.
            int num = storage.charAt(i + 2) - '0'; // (how many)translate character to int.
            Tile[] newTiles = new Tile[num];
            for (int j = 0; j < num; j++) {
                Tile t = new Tile(colorChar);
                t.setBelong(TileBelonging.Storage);
                t.setOnMouseClicked(mouseEvent -> t.setOpacity(0)
                );
                // newTiles[j] = new Tile(colorChar); // initialize the tile with colorChar.
                newTiles[j] = t;
            }
            placeTiles(newTiles, row);
        }
    }

    /**
     * place the tiles in the storage.
     * Author: Ruizheng Shen, Date: 2021.4.27
     *
     * @param tiles the tiles which will be placed in the storage
     * @param row   the row to place
     * @return whether this move is valid.
     */
    public boolean placeTiles(Tile[] tiles, int row) {
        // TODO: test, first check the validity(not finished yet)
        if (!isPlaceValid(tiles, row)) return false;
        for (int i = 0; i < tiles.length; i++) {
            this.tiles[row][STORAGE_ROW_LENGTH[row] - 1 - i] = tiles[i];
        }
        return true;
    }

    /**
     * determine whether the placing in this row is valid.
     * @param tiles the tiles to be placed
     * @param row the row to place in
     * @return whether the placing in this row is valid.
     */
    public boolean isPlaceValid(Tile[] tiles, int row) {
        // TODO get check with NewMosaic
        // check the tiles color
        for (Tile t : tiles) {
            if (!isRowColorSame(t, row)) return false;
        }
        // check the empty space in this row
        int spaceCnt = 0;
        for (int i = 0; i < STORAGE_ROW_LENGTH[row]; i++) {
            if (this.tiles[row][i] == null) spaceCnt++;
        }
        if (tiles.length > spaceCnt) return false;
        return true;
    }

    /**
     * Determines if the tiles are filled fully in one row.
     *
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
     *
     * @param tile, row
     * @return whether the color of tiles from factory and currently
     * in a row in Storage are same.
     */
    public boolean isRowColorSame(Tile tile, int row) {
        // If this row is empty and this tile is not a first player tile, then any color could be place here.
        if (isRowEmpty(row) && tile.getTileType() != TileType.FirstPlayer)
            return true;
        for (int i = 0; i < STORAGE_ROW_LENGTH[row]; i++) {
            // check the tiles' color in this row.
            if (this.tiles[row][i] != null) {
                return this.tiles[row][i].getTileType() == tile.getTileType();
            }
        }
        return false;
    }

    public char rowColour(int row) {
        char a = tiles[row][0].getCode().charAt(0);
        return a;
    }


    /**
     * Check whether this row is empty.
     *
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
     *
     * @param tilesFromFactory, colorList
     * @return whether the color is not same as any colors having in the same row in Mosaic.
     */
    public boolean isColorDifInMosaicRow(Tile[] tilesFromFactory, TileType[] colorList) {
        // TODO: test
        // row: the index of the row
        // colorList: the colors contained in this row in mosaic
        TileType tileColor = tilesFromFactory[0].getTileType();
        for (TileType color : colorList) {
            if (tileColor == color) return false;
        }
        return true;
    }

    /**
     * Determines if the rightmost space for tile in a row is empty.
     *
     * @param row
     * @return whether the rightmost space for tile in a row is empty or not.
     */
    public boolean isRightEmpty(int row) {
        // TODO: test
        // the rightmost position in a row, should be 'STORAGE_ROW_LENGTH[row] - 1'.
        return this.tiles[row][STORAGE_ROW_LENGTH[row] - 1] == null;
    }

    public Tile[][] emptyRow(int row) {
        for (int i = 0; i <= row; i++) {
            tiles[row][i] = null;
        }
        return tiles;
    }

    public int emptySpace(int row) {
        int num = 0;
        for (int i = 0; i <= row; i++) {
            if (tiles[row][i] == null) {
                num += 1;
            }
        }
        return num;
    }

    public void move(char color, int row, int num) {
        num = Math.min(emptySpace(row), num);
        int numOfTile = 0;
        for (int i = 0; i <= row; i++) {
            if (tiles[row][i] == null) {
                tiles[row][i] = new Tile(color);
                numOfTile += 1;
                if (numOfTile == num) {
                    break;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Storage: \n");
        for (int i = 0; i < STORAGE_ROW_NUM; i++) {
            for (int j = 0; j < STORAGE_ROW_LENGTH[i]; j++) {
                s.append(tiles[i][j] + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }


    public static void main(String[] args) {
        Storage s = new Storage();
        s.decode("S0a11c22a33c44b5"); // decode the String and put them into the storage
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
        // test case, test placeTile() validity
        Storage s1 = new Storage();
        Tile[] tiles = new Tile[]{
                new Tile('a'),
                new Tile('a'),
        };
        System.out.println(s1.placeTiles(tiles, 0));
    }

}
