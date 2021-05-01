package comp1110.ass2.member;

/**
 * @author XIAO XU
 * @version 1.0
 * @since 2021.3.27
 */

import gittest.A;
import gittest.C;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Modified by Ruizheng Shen, 2021.4.19
 * Add a constructor
 */

public class Center {
    private ArrayList<Tile> tiles;

    /* Member for javafx */
    private final Group tilesView = new Group();
    public static final int CENTER_HEIGHT = 5;
    public static final int CENTER_WIDTH = 6;
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

    // Constructor for javafx
    public Center(ArrayList<Tile> tiles, int xIndex, int yIndex) {
        setLocation(xIndex, yIndex);
        int cnt = 0;
        for (int i = 0; i < CENTER_HEIGHT; i++) {
            for (int j = 0; j < CENTER_WIDTH; j++) {
                if (cnt < tiles.size()) {
                    Tile t = tiles.get(cnt);
                    t.setLocation(j * Tile.TILE_WIDTH, i * Tile.TILE_WIDTH); // relative location to center
                    // TODO: set the location of this tile
                    this.tilesView.getChildren().add(t);
                    cnt++;
                } else {
                    Tile t = new Tile(' ', j * Tile.TILE_WIDTH, i * Tile.TILE_WIDTH); // relative location to center
                    // TODO: add empty tile to fill up the remaining space.
                    this.tilesView.getChildren().add(t);
                }
            }
        }
    }

    /**
     * The empty constructor for javafx
     *
     * @param xIndex layoutX
     * @param yIndex layoutY
     */
    public Center(int xIndex, int yIndex) {
        setLocation(xIndex, yIndex);
        this.tiles = new ArrayList<>();
    }


    public String getCode() {
        return encode();
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
        String center = "C";
        for (int i = 0; i < tiles.size(); i++) {
            center += tiles.get(i).getCode();
        }
        return center;
    }

    public void decode(String center) {
        int num = center.length();
        for (int n = 1; n < num; n++) {
            Tile a = new Tile(center.charAt(n));
            this.tiles.add(a);
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
        if (c.contains("f")) {
            return true;
        } else {
            return false;
        }

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
     * The visitor for tilesView
     *
     * @return tilesView
     */
    public Group getTilesView() {
        updateView(); // First update the view, then return.
        return this.tilesView;
    }

    /**
     * Set the location of Center
     *
     * @param xIndex the layoutX inboard
     * @param yIndex the layoutY inboard
     */
    public void setLocation(int xIndex, int yIndex) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.tilesView.setLayoutX(xIndex);
        this.tilesView.setLayoutY(yIndex);
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

    /**
     * clear the tiles in center.
     */
    public void clear() {
        this.tiles.clear();
        updateView();
    }

    /**
     * Update the view of Center(After removing or adding tiles.)
     * This function should be called everytime the center has action.
     */
    public void updateView() {
        this.tilesView.getChildren().clear(); // refresh the center
        int cnt = 0;
        for (int i = 0; i < CENTER_HEIGHT; i++) {
            for (int j = 0; j < CENTER_WIDTH; j++) {
                if (cnt < tiles.size()) {
                    Tile t = tiles.get(cnt);
                    t.setLocation(j * Tile.TILE_WIDTH, i * Tile.TILE_WIDTH);
                    // TODO: set the location of this tile
                    this.tilesView.getChildren().add(t);
                    cnt++;
                } else {
                    Tile t = new Tile(' ', j * Tile.TILE_WIDTH, i * Tile.TILE_WIDTH);
                    // TODO: add empty tile to fill up the remaining space.
                    this.tilesView.getChildren().add(t);
                }
            }
        }
    }

    public static void main(String[] args) {
        String c = "Cbbef";
        Center center = new Center();
        center.decode(c);
        center.addTile("ab");
        System.out.println(center.getCode());
    }

}
