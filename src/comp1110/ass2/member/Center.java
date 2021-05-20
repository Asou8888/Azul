package comp1110.ass2.member;

/**
 * @author XIAO XU
 * @version 1.0
 * @since 2021.3.27
 */

import comp1110.ass2.gui.Game;
import gittest.A;
import gittest.C;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Modified by Ruizheng Shen, 2021.4.19
 * Add a constructor
 */

public class Center {
    private ArrayList<Tile> tiles;

    public static final int CENTER_HEIGHT = 8;
    public static final int CENTER_WIDTH= 2;
    // Constructors for Azul
    public Center(Tile[] tiles) {
        this.tiles = new ArrayList<Tile>(Arrays.asList(tiles));
    }

    public Center() {
        this.tiles = new ArrayList<Tile>();
    }

    public Center(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }


    public Center(String center) {
        this.tiles = new ArrayList<>();
        decode(center);
    }


    public String getCode() {
        return encode();
    }

    /**
     * The visitor of tiles.
     *
     * @return tiles.
     */
    public ArrayList<Tile> getTiles() {
        return this.tiles;
    }


    /**
     * The encoding for the Centre placement string is very similar for factories, but is not limited to 4 tiles.
     * <p>
     * The first character is a "C".
     * The following characters are any number of a to e tiles and up to one f (first player) tile, in alphabetical order.
     * for example:
     * <p>
     * "C" means the Centre is empty.
     * <p>
     * "Caaaabbcf" means the Centre contains four a tiles, two b tiles, one c tile and one f tile.
     *
     * @return String of the code
     */

    /**
     * @return the code
     * @author Xiao Xu
     */
    public String encode() {
        StringBuilder center = new StringBuilder("C");
        boolean hasFirstPlayer = false;
        for (Tile tile : tiles) {
            if (tile.getTileType() != TileType.FirstPlayer) {
                center.append(tile.getCode());
            } else {
                hasFirstPlayer = true;
            }
        }
        if (hasFirstPlayer) center.append("f");
        return sortChar(center.toString());
    }

    /**
     * @param center the center state
     * @author Ruizheng Shen
     */
    public void decode(String center) {
        int num = center.length();
        for (int n = 1; n < num; n++) {
            Tile t = new Tile(center.charAt(n));
            t.setBelong(TileBelonging.Center);
            this.tiles.add(t);
        }
    }


    /**
     * @return the number of tiles in center
     * @author Xiao Xu
     * get the number of tiles in current stage.
     */
    public int getCurrentNum() {
        return this.tiles.size();
    }

    /**
     * @return true if there is and false if there is not
     * @author Xiao Xu
     * Determine whether there is a firstPlayer tile in center or not
     */
    public boolean hasFirstPlayer() {
        String c = encode();
        return c.contains("f");
    }

    /**
     * @param color the colorChar
     * @return the number of tiles of that color.
     * @author Xiao Xu
     */
    public int tileNum(char color) {
        int num = 0;
        String center = getCode();
        for (int i = 0; i < center.length(); i++) {
            if (center.charAt(i) == color) {
                num += 1;
            }
        }
        return num;
    }

    /**
     * @param color the colorChar
     * @return the updated state of center
     * @author Xiao Xu
     */
    public String deleteTile(char color) {
        String c = encode();
        String newcenter = "";
        for (int i = 0; i < c.length(); i++) {
            if (c.charAt(i) != color) {
                newcenter += c.charAt(i);
            }
        }
        return newcenter;
    }

    public TileBelonging belongsTo() {
        return TileBelonging.Center;
    }


    /**
     * @return true if center is empty and false if not
     * @author Xiao Xu
     * Determine if the center is empty or not.
     */
    public boolean isEmpty() {
        return tiles.size() == 0;
    }

    /**
     * @param tile the string of tiles to be placed in the center.
     * @author Xiao Xu
     */
    public void addTile(String tile) {
        for (int i = 0; i < tile.length(); i++) {
            Tile a = new Tile(tile.charAt(i));
            tiles.add(a);
        }
    }

    /**
     * @return colors
     * @author Ruizheng Shen
     * List out the colors in the center(using HashSet to avoid color duplicated).
     */
    public HashSet<TileType> getColors() {
        HashSet<TileType> colors = new HashSet<>();
        for (Tile t : tiles) {
            colors.add(t.getTileType());
        }
        return colors;
    }

    /**
     * @param tiles the tiles to be placed in the center.
     * @author Ruizheng Shen
     * Place the tiles in Center.
     */
    public void placeTiles(ArrayList<Tile> tiles) {
        this.tiles.addAll(tiles);
    }

    /**
     * @param color the color of tiles need to be removed(move to storage/floor)
     * @return removeList the tiles removed.(If empty, this move is invalid)
     * @author Ruizheng Shen
     * Remove the tiles from center according to the tile's color.
     */
    public ArrayList<Tile> removeTiles(TileType color) {
        ArrayList<Tile> removeList = new ArrayList<>();
        tiles.forEach(t -> {
            if (t.getTileType() == color) {
                removeList.add(t);
            }
        });
        tiles.removeAll(removeList);
        return removeList;
    }

    /**
     * @param str sort the string
     * @return the String in order.
     * @author Xiao Xu
     */
    private static String sortChar(String str) {
        char[] chs = stringToArray(str);
        sort(chs);
        return toString(chs);
    }

    private static String toString(char[] chs) {
        return new String(chs);
    }

    private static void sort(char[] chs) {
        Arrays.sort(chs);
    }

    private static char[] stringToArray(String string) {
        return string.toCharArray();
    }

}
