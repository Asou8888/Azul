package comp1110.ass2.member;

/**
 * The player class implements the player in the game. Including the implements of player's name and player's moves.
 *
 * @author Ruizheng Shen
 * @version 1.0
 * @since 2021.3.27
 */
public class Player {

    private final String playerName;
    private final String code;
    public final Floor floor;
    public final Mosaic mosaic;

    public Player(String name, String code) {
        this.playerName = name;
        this.code = code; // the code could be 'A' - 'D', assigned by Azul
        this.floor = new Floor();
        this.mosaic = new Mosaic();
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
        return false;
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
        return false;
    }

    public String getCode() {
        return this.code;
    }

}
