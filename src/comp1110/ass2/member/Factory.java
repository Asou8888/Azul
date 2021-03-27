package comp1110.ass2.member;

/**
 * @author Yixin Ge
 * @version 1.0
 * @since 2021.3.27
 */

public class Factory {
    /**
     * Factory is made of 4 tiles.
     */
    private Tile[] tiles = new Tile[4];

    /**
     * Constructor for the Factory. Given array of tiles, returns the
     * current state of where each char represented tile is location in the Factory.
     *
     * @param tiles the given array of Tile
     */
    public Factory(Tile[] tiles){
        //TODO
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
        return "";
    }

    /**
     * Determines if the factory currently has no tiles.
     * @return whether the factory currently has no tiles.
     */
    public boolean isEmpty(){
        //TODO
        return false;
    }

    /**
     * find the amount of the tiles in a Factory
     * @return the amount of tiles in a Factory
     */
    public int tileAmount(){
        //TODO
        return 0;
    }

}
