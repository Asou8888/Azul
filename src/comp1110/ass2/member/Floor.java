package comp1110.ass2.member;

import comp1110.ass2.gui.Game;
import javafx.scene.Group;

/**
 * The Floor class implements the floor of a player, which has a lostPoint array to record the lost points and a tiles array,
 * which are the tiles in the floor.
 *
 * @author Ruizheng Shen
 * @version 1.0
 * @since 2021.3.27
 */
public class Floor {
    private Tile[] tiles;
    public static final int FLOOR_WIDTH = 7;
    public static final int[] lostPoint = {-1, -1, -2, -2, -2, -3, -3};


    public Floor() {
        this.tiles = new Tile[FLOOR_WIDTH];
    }

    // for testing(Author: Ruizheng Shen)
    public Floor(Tile[] tiles) {
        this.tiles = tiles;
    }

    /**
     * return the code of the current state of floor.
     *
     * @return the String code of the current floor.
     */
    public String getCode() {
        return encode();
    }

    /**
     * encode the current state of floor.
     * <p>
     * [Floor encoding]
     * The Floor String is composed of 0 or more tiles sorted alphabetically. Note that you may choose to display the tiles in your
     * GUI unsorted.
     * * first character is `"F"`
     * * the following 0 or more characters are `a` to `f` where `f` represents the first player token. Note that there is only
     * one first player token.
     * <p>
     * For example:
     * `"Faabbe"` means two `a` tiles, two `b` tiles and one `e` tile have been dropped on the floor.
     * An empty Floor string would look like this: `"F"`
     * <p>
     * [Score encoding]
     * The Score is a string of up to three digits representing the player's score.
     * <p>
     * For example "120" means the player has a score of 120. A score of 0 is represented by "0".
     *
     * @return the code
     */
    private String encode() {
        StringBuilder code = new StringBuilder("F"); //The string starts at "F".
        for (int i = 0; i < FLOOR_WIDTH; i++) {
            String floor = "";
            int nu = 0; //later use to find whether it is null or not
            if (this.tiles[i] != null) {
                nu++; // if there is a tile in position i, add by 1.
                floor = this.tiles[i].getCode(); // get the char of tile at position i
            }
            if (nu != 0) { // if the position i is not a null
                code.append(floor); // add the char in string code
            }
        }
        return code.toString(); //return the String start with "F".
    }


    /**
     * Calculate the current score in the floor.(This will be calculate at the end of each round.)
     *
     * @return the current score in the floor
     */
    public int score() {
        int index = 0;  // set an index
        for (Tile x : tiles) {
            if (x != null) {    // find out amount of tiles in Floor
                index += 1;
            }   //each valid tile count 1
        }
        int scr = 0; //score starts at 0 point.
        if (index > 0) {
            for (int i = 0; i < index; i++) {
                // if one more tiles, add one more point corresponding to lostPoint[].
                scr += lostPoint[i];
            }
        }
        return scr;
    }

    /**
     * Determine the floor contains full amount of tiles, which should exactly have 7 tiles.
     *
     * @return the state of floor if it is fulfilled.
     */
    public boolean isFloorFull() {
        for (int i = 0; i < FLOOR_WIDTH; i++) { // the maximum length of floor is 7
            if (tiles[i] == null) { // in the range, if there is a null
                return false; // then the floor is not full
            }
        }
        return true;

    }

    /**
     * given a string which is represented floor state and decode it into the list of tiles.
     *
     * @param floor the floor state
     */
    public void decode(String floor) {
        if (floor.charAt(0) == 'F' && floor.length() <= FLOOR_WIDTH + 1) {
            Tile[] newTiles = new Tile[floor.length() - 1];
            for (int i = 1; i < floor.length(); i++) {
                char tile = floor.charAt(i);
                Tile t = new Tile(tile);
                t.setBelong(TileBelonging.Floor);
                newTiles[i - 1] = t;
            }
            placeTile(newTiles);
        }
    }

    /**
     * Determine whether the tiles has enough space to place into floor
     *
     * @param tiles the tiles to be placed in the floor.
     */
    public boolean placeTile(Tile[] tiles) {
        int cnt = lengthTile();
        if (cnt + tiles.length <= 7) {
            System.arraycopy(tiles, 0, this.tiles, cnt, tiles.length);
            return true;
        }
        return false;
    }

    /**
     * @return the length of tiles in floor without null.
     */
    public int lengthTile() {
        for (int i = 0; i < FLOOR_WIDTH; i++) {
            if (this.tiles[i] == null) {
                return i;
            }
        }
        return FLOOR_WIDTH;
    }

    /**
     * @return the amount of empty space in floor.
     */
    public int emptyNum() {
        int num = 0;
        for (int i = 0; i < FLOOR_WIDTH; i++) {
            if (tiles[i] == null) {
                num += 1;
            }
        }
        return num;
    }

    /**
     * change the tile of the last position in floor via changing its color.
     *
     * @param color the color of tile to be placed.
     */
    public void replaceTile(char color) {
        tiles[6] = new Tile(color);
    }

    /**
     * place tiles into floor within the limit of maximum position of floor.
     * if there is fulfilled, then the last position will be replaced by the color given.
     *
     * @param color the color of tiles to be placed
     * @param num   the number of tiles.
     */
    public void placeTile(char color, int num) {
        if (emptyNum() == 0) replaceTile(color);
        if (emptyNum() >= num) {
            num = num;
        } else {
            num = emptyNum();
        }
        int numOfTiles = 0;
        for (int i = 0; i < FLOOR_WIDTH; i++) {
            if (tiles[i] == null) {
                tiles[i] = new Tile(color);
                numOfTiles += 1;
                if (numOfTiles == num) {
                    break;
                }
            }
        }
    }
}
