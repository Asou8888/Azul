package comp1110.ass2.member;

import comp1110.ass2.gui.Game;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Yixin Ge
 * @version 1.0
 * @since 2021.3.27
 */

/**
 * Modified By Ruizheng Shen, Date: 2021.4.29
 */

public class Factory {
    /**
     * Factory is made of 4 tiles.
     */
    public static final int MAX_FACTORY_TILES_NUM = 4;
    private ArrayList<Tile> tiles;


    /**
     * Constructor for the Factory. Given array of tiles, returns the
     * current state of where each char represented tile is location in the Factory.
     *
     * @param tiles the given array of Tile
     */
    public Factory(Tile[] tiles) {
        this.tiles = new ArrayList<>(Arrays.asList(tiles));
    }

    /**
     * Another constructor with ArrayList as input.
     *
     * @param tiles the input tile list
     */
    public Factory(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    /**
     * Empty constructor.
     */
    public Factory() {
        this.tiles = new ArrayList<>();
    }

    /**
     * @return the number of tiles in this factory
     */
    public int tileNum() {
        return this.tiles.size();
    }

    public int tileNum(char color) {
        int num = 0;
        for (Tile tile : tiles) {
            if (tile.getCode().equals(String.valueOf(color))) {
                num += 1;
            }
        }
        return num;
    }

    /**
     * Updated by Ruizheng Shen
     *
     * @param color the color of tiles.
     * @return the number of this color of tiles.
     * @author Ruizheng Shen
     */
    public int tileNum(TileType color) {
        int cnt = 0;
        for (Tile t : this.tiles) {
            if (t.getTileType() == color) {
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * Draft tiles from factory according to color.
     *
     * @param color the color which player chooses.
     * @return the tiles of this color
     */
    public Tile[] draftTile(TileType color) {
        int num = tileNum(color); // find the number of tiles of this color.
        if (num == 0) return null;
        Tile[] tiles = new Tile[num];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tile(color);
        }
        return tiles;
    }


    public String deleteTile(char color) {
        String factory = encode();
        StringBuilder newFactory = new StringBuilder();
        for (int i = 0; i < factory.length(); i++) {
            if (factory.charAt(i) != color) {
                newFactory.append(factory.charAt(i));
            }
        }
        return newFactory.toString();
    }

    /**
     * Return the code of the current state in Factory.
     *
     * @return the String code of Factory.
     */
    public String getCode() {
        return encode();
    }

    /**
     * The Factories placement string begins with an 'F' and is followed by a number of individual factory placement string.
     * Each encoding of a singular Factory placement string is as follows:
     * <p>
     * {Factory number}{tiles}
     * <p>
     * The first character is a number 0-8 representing the Factory number. Factories are numbered sequentially, so in a 2-player
     * game, we will have factories 0 to 4.
     * The following 0 - 4 characters are letters a to e representing the tiles stored there in alphabetical order.
     * For example: "F0abbe2ccdd" reads "Factory 0 has one a tile, two b tiles, one c tile, one e tile, Factory 2
     * contains two c tiles and two d tiles, and Factories 1, 3, and 4 are empty.
     * If a factory is empty, it does not appear in the factories string.
     * <p>
     * The number of factories depends on the number of players.
     * <p>
     * 2 players: 5 factories
     * 3 players: 7 factories
     * 4 players: 9 factories
     */
    private String encode() {
        StringBuilder code = new StringBuilder();
        this.tiles.forEach(t -> {
            code.append(t.getCode());
        });
        return sortChar(code.toString());
    }

    /**
     * add by Xiao Xu
     *
     * @param str the stringto be sorted
     * @return the string in order.
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

    /**
     * Determines if the factory currently has no tiles.
     *
     * @return whether the factory currently has no tiles.
     */
    public boolean isEmpty() {
        return this.tiles.isEmpty();
    }

    public void decode(String thisFactory) {
        // If this factory is null, this factory will be empty.
        if (thisFactory != null) {
            for (int i = 0; i < thisFactory.length(); i++) {
                Tile t = new Tile(thisFactory.charAt(i));
                t.setBelong(TileBelonging.Factory);
                this.tiles.add(t); // add tiles to this factory according to code
            }
        }
    }

    /**
     * Find the colors of tiles in this factory
     *
     * @return List of colors(TileType)
     */
    public ArrayList<TileType> getColors() {
        ArrayList<TileType> colors = new ArrayList<>();
        this.tiles.forEach(t -> {
            if (!colors.contains(t.getTileType())) {
                colors.add(t.getTileType());
            }
        });
        return colors;
    }

    /**
     * find the amount of the tiles in a Factory
     *
     * @return the amount of tiles in a Factory
     */
    public int tileAmount() {
        return this.tiles.size();
    }

    /**
     * clear this factory
     */
    public void clear() {
        this.tiles.clear();
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.tiles.size(); i++) {
            if (i == this.tiles.size() - 1) {
                s.append(this.tiles.get(i).toString());
            } else {
                s.append(this.tiles.get(i).toString()).append(", ");
            }
        }
        return s.toString();
    }

}


