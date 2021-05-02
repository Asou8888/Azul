package comp1110.ass2.member;

import comp1110.ass2.Azul;
import gittest.A;
import javafx.scene.Group;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The player class implements the player in the game. Including the implements of player's name and player's moves.
 *
 * @author Ruizheng Shen
 * @version 1.0
 * @since 2021.3.27
 */
public class Player {

    private final String playerName;
    private final String playerCode;
    public final Floor floor;
    public final NewMosaic mosaic;
    public final Storage storage;
    public int score;

    /* state */
    String[] playerState; // The split player state, decode from game state
    String[] sharedState; // The split shared state, decode from game state.

    /* Members of javafx */
    private final Group views = new Group();
    int xIndex;
    int yIndex;

    public Player(String name, String playerCode) {
        this.playerName = name;
        this.playerCode = playerCode; // the code could be 'A' - 'D', assigned by Azul
        this.floor = new Floor();
        this.mosaic = new NewMosaic();
        this.storage = new Storage();
        this.score = 0;
    }

    /**
     * create the view of Player
     */
    private void createView() {
        // add the views into Player's group.
        storage.setLocation(20,0);
        mosaic.setLocation(300,0);
        floor.setLocation(100,300);
        this.views.getChildren().add(this.floor.getFloorView());
        this.views.getChildren().add(this.mosaic.getMosaicView());
        this.views.getChildren().add(this.storage.getStorageView());
        // TODO: apply location for each views.
    }

    public void updateView() {
        createView();
        this.views.getChildren().clear();
        this.views.getChildren().add(this.floor.getFloorView());
        this.views.getChildren().add(this.mosaic.getMosaicView());
        this.views.getChildren().add(this.storage.getStorageView());
        // No need to rearrange location.
    }

    public Group getViews() {
        updateView();
        return this.views;
    }

    /**
     * The visitor of xIndex and yIndex.
     * @return xIndex, yIndex
     */
    public int getxIndex() {
        return xIndex;
    }
    public int getyIndex() {
        return yIndex;
    }

    public void setLocation(int xIndex, int yIndex) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.views.setLayoutX(this.xIndex);
        this.views.setLayoutY(this.yIndex);
    }

    /**
     * The visitor of the playerName. Return a String of this player's name.
     * @return plaerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Move tiles from factories or centre to this player's storage or floor.(Also determine whether this move is valid.)
     *
     * From README.md(About drafting):
     * The starting player places the first player token in the *centre*, then takes the first turn. Play continues clockwise.
     * On your turn, you must either:
     *  1. Pick all tiles of the same colour from one factory and move the remaining tiles on this factory to the centre.
     *  2. Pick all tiles of the same colour from the centre. If you are the first person to do so, you also take the starting player
     *    token and place it on your floor. You will be the first player next round.
     *
     * After picking up tiles, you must add them to **one** of the five *storage rows* on your board, adhering to the following conditions:
     *  1. You must place the tiles **from right to left** in your chosen row.
     *  2. If a row already contains tiles, you may only add tiles of the same colour to it.
     *  3. If you have more tiles than can fit in your chosen row, then you must place the excess tiles on the *floor*.
     *  4. You are not allowed to place tiles of a certain colour in a row if the corresponding mosaic row already contains a tile
     *      of that colour.
     *  5. If you cannot or do not want to place tiles on a row, you may place them directly onto the *floor*.
     *
     * @param tiles: the tiles which the player chooses from the factories or centre.
     * @param row: the row which the player choose to place the tiles.
     * @return whether this draft move is available according to the game rules.
     */
    public boolean draftMove(Tile[] tiles, int row) {
        // TODO: implements the draftMove method.
        // check whether there's a 'first player' tile.
        tiles = placeFirstPlayerTile(tiles);
        // move tiles to storage
        if (storage.placeTiles(tiles, row)) {
            String tile = tiles[0].getCode();
            storage.move(tile.charAt(0),row,tiles.length);
            if(storage.emptySpace(row) < tile.length()){
                int moreTiles = tiles.length - storage.emptySpace(row);
                floor.placeTile(tile.charAt(0),moreTiles);
            }
            return true;
            // the move success;
        } else {
            // the move failed;
            return false;
        }

    }

    /**
     * Move tiles of the fullfilled row from the storage to this player's mosaic, and place the remaining tiles to the floor.
     *
     * @param row: the row index in mosaic which player decides to place the tiles.
     * @param col: the col index in mosaic which player decides to place the tiles.
     * @return whether this tile move is available according to the game rules.
     */
    public boolean tileMove(int row, int col) {
        // TODO: implements the tileMove method.
        if(storage.isRowFull(row)){
            storage.emptyRow(row);
            mosaic.move(storage.rowColour(row),row,col);
            return true;
        }
        return false;
    }

    /**
     * If there's a 'first player' tile in the tiles, put it on the floor.
     * @param tiles
     */
    public Tile[] placeFirstPlayerTile(Tile[] tiles) {
        // TODO: test
        ArrayList<Tile> newTiles = new ArrayList<>();
        for (Tile t: tiles) {
            if (t.getTileType() != TileType.FirstPlayer) {
                newTiles.add(t);
            }
        }
        Tile[] afterRemoving = new Tile[newTiles.size()];
        for (int i = 0; i < afterRemoving.length; i++) {
            afterRemoving[i] = newTiles.get(i);
        }
        return afterRemoving;
    }
    public String getPlayerCode() {
        return this.playerCode;
    }
    public int getScore() {
        return this.score;
    }

    /**
     * encode player state
     * @author Ruizheng Shen, @date: 2021.4.28
     * @return the player state of this player
     */
    private String encode() {
        return getPlayerCode() + getScore() + mosaic.getCode() + storage.getCode() + floor.getCode();
    }
    public String getCode() {
        return encode();
    }

    /**
     * calculate the score of this player
     */
    public void calculateScore() {
        // TODO: calculate the score of this player
    }

    public NewMosaic getMosaic(){
        return this.mosaic;
    }

    /**
     * Decode the state
     */
    public void decode(String[] gameState) {
        // TODO: get the shared state and the player state according to game state.
        this.sharedState = Azul.splitSharedState(gameState);
        this.playerState = Azul.splitPlayerState(gameState).get(this.playerCode);
    }

    public static void main(String[] args) {

    }

}
