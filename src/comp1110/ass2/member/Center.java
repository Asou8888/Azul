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

    /* Member for javafx */
    public static final int CENTER_HEIGHT = 8;
    public static final int CENTER_WIDTH = 2;
    int xIndex; // the layoutX in board
    int yIndex; // the layoutY in board

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

    public String encode() {
        //TODO
        /**
         * Written by Xiao Xu 4/29
         */
        /*
        String center = "C";
        for (int i = 0; i < tiles.size(); i++) {
            center += tiles.get(i).getCode();
        }

         */
        /**
         * Modified bby Ruizheng Shen, 5.1
         */
        StringBuilder center = new StringBuilder("C");
        boolean hasFirstPlayer = false;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getTileType() != TileType.FirstPlayer) {
                center.append(tiles.get(i).getCode());
            } else {
                hasFirstPlayer = true;
            }
        }
        if (hasFirstPlayer) center.append("f");
        return sortChar(center.toString());
    }

    public void decode(String center) {
        int num = center.length();
        for (int n = 1; n < num; n++) {
            Tile t = new Tile(center.charAt(n));
            t.setBelong(TileBelonging.Center);
            t.setOnMouseClicked(e -> {
                // TODO
                t.setOpacity(0.6);
                if (!Game.isClick) {
                    Game.isClick = true;
                    Game.from = t;
                }
            });
            this.tiles.add(t);
        }
    }


    /**
     * get the number of tiles in current stage
     *
     * @return the number of tiles in center
     */
    public int getCurrentNum() {
        //TODO:return the number of tiles in current stage
        int num = this.tiles.size();
        return num;

    }

    /**
     * Determine whether there is a firstPlayer tile in center or not
     *
     * @return true if there is and false if there is not
     */
    public boolean hasFirstPlayer() {
        //TODO:return if there is a firstplayer in center
        String c = encode();
        return c.contains("f");
    }

    public int tileNum(char color){
        int num = 0;
        String center = getCode();
        for(int i = 0;i<center.length();i++){
            if(center.charAt(i) == color){
                num += 1;
            }
        }
        return num;
    }

    public String deleteTile(char color){
        String c = encode();
        String newcenter = "";
        for(int i = 0; i<c.length();i++){
            if(c.charAt(i) != color){
                newcenter += c.charAt(i);
            }
        }
        return newcenter;
    }

    public TileBelonging belongsTo() {
        return TileBelonging.Center;
    }


    /**
     * Determine if the center is empty or not
     *
     * @return true if center is empty and false if not
     */
    public boolean isEmpty() {
        //TODO:return if the center is empty or not
        return tiles.size() == 0;
    }

    public void addTile(String tile){
        for(int i =0;i<tile.length();i++){
            Tile a = new Tile(tile.charAt(i));
            tiles.add(a);
        }
    }

    /**
     * List out the colors in the center(using HashSet to avoid color duplicated).
     * @return colors
     */
    public HashSet<TileType> getColors() {
        HashSet<TileType> colors = new HashSet<>();
        for (Tile t: tiles) {
            colors.add(t.getTileType());
        }
        return colors;
    }

    /**
     * Place the tiles in Center.
     *
     * @param tiles
     */
    public void placeTiles(ArrayList<Tile> tiles) {
        // TODO: check whether the input tiles valid.
        this.tiles.addAll(tiles);
    }

    /**
     * Remove the tiles from center according to the tile's color.
     *
     * @param color the color of tiles need to be removed(move to storage/floor)
     * @return removeList the tiles removed.(If empty, this move is invalid)
     */
    public ArrayList<Tile> removeTiles(TileType color) {
        // TODO: test
        ArrayList<Tile> removeList = new ArrayList<>();
        tiles.forEach(t -> {
            if (t.getTileType() == color) {
                removeList.add(t);
            }
        });
        tiles.removeAll(removeList);
        return removeList;
    }


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

    public static void main(String[] args) {
        String c = "Cbbef";
        Center center = new Center();
        center.decode(c);
        center.addTile("ab");
        System.out.println(center.getCode());

        // test case for task 11
        Center c1 = new Center();
        c1.decode("Cf");
        c1.addTile("bbe");
        System.out.println(c1.getCode());
    }

}
