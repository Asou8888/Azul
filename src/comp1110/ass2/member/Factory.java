package comp1110.ass2.member;

import gittest.A;
import javafx.scene.Group;

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
    private static final int MAX_FACTORY_TILES_NUM = 4;
    private ArrayList<Tile> tiles;

    /* Members for javafx */
    int xIndex;
    int yIndex;
    Group factoryView = new Group();




    /**
     * Constructor for the Factory. Given array of tiles, returns the
     * current state of where each char represented tile is location in the Factory.
     *
     * @param tiles the given array of Tile
     */
    public Factory(Tile[] tiles){
        this.tiles = new ArrayList<>(Arrays.asList(tiles));
    }

    /**
     * Another constructor with ArrayList as input.
     * @param tiles
     */
    public Factory(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    /**
     * Empty constructor.
     */
    public Factory() {
        this.tiles = new ArrayList<>();
        createView();
    }

    /**
     * Create view for a factory, only called by the constructor
     */
    private void createView() {
        factoryView = new Group();
        int cnt = 0;
        for (int i = 0; i < MAX_FACTORY_TILES_NUM / 2; i++) {
            for (int j = 0; j < MAX_FACTORY_TILES_NUM / 2; j++) {
                if (tiles.get(cnt) == null) {
                    cnt++;
                    factoryView.getChildren().add(new Tile(' ', i * Tile.TILE_WIDTH, j * Tile.TILE_WIDTH));
                } else {
                    cnt++;
                    Tile t = tiles.get(cnt);
                    t.setLocation(i * Tile.TILE_WIDTH, j * Tile.TILE_WIDTH);
                    factoryView.getChildren().add(t);
                }
            }
        }
    }
    public void updateFactoryView() {
        this.factoryView.getChildren().clear(); // clear the previous view.
        int cnt = 0;
        for (int i = 0; i < MAX_FACTORY_TILES_NUM / 2; i++) {
            for (int j = 0; j < MAX_FACTORY_TILES_NUM; j++) {
                if (tiles.get(cnt) == null) {
                    cnt++;
                    factoryView.getChildren().add(new Tile(' ', i * Tile.TILE_WIDTH, j * Tile.TILE_WIDTH));
                } else {
                    cnt++;
                    Tile t = tiles.get(cnt);
                    t.setLocation(i * Tile.TILE_WIDTH, j * Tile.TILE_WIDTH);
                    factoryView.getChildren().add(t);
                }
            }
        }
    }
    public Group getFactoryView() {
        updateFactoryView();
        return this.factoryView;
    }
    public void setLocation(int x, int y) {
        this.xIndex = x;
        this.yIndex = y;
        this.factoryView.setLayoutX(this.xIndex);
        this.factoryView.setLayoutY(this.yIndex);
    }

    /**
     *
     * @return the number of tiles in this factory
     */
    public int tileNum() {
        return this.tiles.size();
    }

    public int tileNum(char color){
        int num = 0;
        for(int i = 0;i<tiles.size();i++){
            if(tiles.get(i).getCode() == String.valueOf(color)){
                num += 1;
            }
        }
        return num;
    }

    public String deleteTile(char color){
        String factory = encode();
        String newFactory = "";
        for(int i = 0; i< factory.length();i++){
            if(factory.charAt(i) != color){
                newFactory += String.valueOf(factory.charAt(i));
            }
        }
        return newFactory;
    }

    /**
     * Return the code of the current state in Factory.
     * @return the String code of Factory.
     */
    public String getCode() {
        return encode();
    }

    /**
     * The Factories placement string begins with an 'F' and is followed by a number of individual factory placement string.
     * Each encoding of a singular Factory placement string is as follows:
     *
     * {Factory number}{tiles}
     *
     * The first character is a number 0-8 representing the Factory number. Factories are numbered sequentially, so in a 2-player
     * game, we will have factories 0 to 4.
     * The following 0 - 4 characters are letters a to e representing the tiles stored there in alphabetical order.
     * For example: "F0abbe2ccdd" reads "Factory 0 has one a tile, two b tiles, one c tile, one e tile, Factory 2
     * contains two c tiles and two d tiles, and Factories 1, 3, and 4 are empty.
     * If a factory is empty, it does not appear in the factories string.
     *
     * The number of factories depends on the number of players.
     *
     * 2 players: 5 factories
     * 3 players: 7 factories
     * 4 players: 9 factories
     */
    private String encode() {
        //TODO
        StringBuilder code = new StringBuilder();
        this.tiles.forEach(t -> {
            code.append(t.getCode());
        });
        return code.toString();
    }

    /**
     * Determines if the factory currently has no tiles.
     * @return whether the factory currently has no tiles.
     */
    public boolean isEmpty(){
        return this.tiles.isEmpty();
    }

    public void decode(String thisFactory) {
        // TODO: test
        // If this factory is null, this factory will be empty.
        if (thisFactory != null) {
            for (int i = 0; i < thisFactory.length(); i++) {
                //TODO: check whether the code valid
                this.tiles.add(new Tile(thisFactory.charAt(i))); // add tiles to this factory according to code
            }
        }
    }

    /**
     * Find the colors of tiles in this factory
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
     * @return the amount of tiles in a Factory
     */
    public int tileAmount(){
        return this.tiles.size();
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

    public static void main(String[] args) {
        // test toString()
        Factory f1 = new Factory();
        f1.decode("abbe");
        Factory f2 = new Factory();
        f2.decode("ccdd");
        System.out.println("Factory 1: " + f1 + "\nFactory 2: " + f2);

    }

}


