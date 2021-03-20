package comp1110.ass2;

import java.util.Locale;

public class Azul {
    /**
     * Given a shared state string, determine if it is well-formed.
     * Note: you don't need to consider validity for this task.
     * A sharedState is well-formed if it satisfies the following conditions.
     * <p>
     * [factories][centre][bag][discard]
     * where [factories], [centre], [bag] and [discard] are replaced by the
     * corresponding small string as described below.
     * <p>
     * 1. [factories] The factories substring begins with an 'F'
     * and is followed by a collection of *up to* 5 5-character factory strings
     * representing each factory.
     * Each factory string is defined in the following way:
     * 1st character is a sequential digit '0' to '4' - representing the
     * factory number.
     * 2nd - 5th characters are 'a' to 'e', alphabetically - representing
     * the tiles.
     * A factory may have between 0 and 4 tiles. If a factory has 0 tiles,
     * it does not appear in the factories string.
     * Factory strings are ordered by factory number.
     * For example: given the string "F1aabc2abbb4ddee": Factory 1 has tiles
     * 'aabc', Factory 2 has tiles 'abbb', Factory 4 has tiles 'ddee', and
     * Factories 0 and 4 are empty.
     * <p>
     * 2. [centre] The centre substring starts with a 'C'
     * This is followed by *up to* 15 characters.
     * Each character is 'a' to 'e', alphabetically - representing a tile
     * in the centre.
     * The centre string is sorted alphabetically.
     * For example: "Caaabcdde" The Centre contains three 'a' tiles, one 'b'
     * tile, one 'c' tile, two 'd' tile and one 'e' tile.
     * <p>
     * 3. [bag] The bag substring starts with a 'B'
     * and is followed by 5 2-character substrings
     * 1st substring represents the number of 'a' tiles, from 0 - 20.
     * 2nd substring represents the number of 'b' tiles, from 0 - 20.
     * 3rd substring represents the number of 'c' tiles, from 0 - 20.
     * 4th substring represents the number of 'd' tiles, from 0 - 20.
     * 5th substring represents the number of 'e' tiles, from 0 - 20.
     * <p>
     * For example: "B0005201020" The bag contains zero 'a' tiles, five 'b'
     * tiles, twenty 'c' tiles, ten 'd' tiles and twenty 'e' tiles.
     * 4. [discard] The discard substring starts with a 'D'
     * and is followed by 5 2-character substrings defined the same as the
     * bag substring.
     * For example: "D0005201020" The bag contains zero 'a' tiles, five 'b'
     * tiles, twenty 'c' tiles, ten 'd' tiles, and twenty 'e' tiles.
     *
     * @param sharedState the shared state - factories, bag and discard.
     * @return true if sharedState is well-formed, otherwise return false
     * TASK 2
     */
    public static boolean isSharedStateWellFormed(String sharedState) {
        // find the index of F,B,D in the string
        int F = 0;
        int B = 0;
        int C = 0;
        int D = 0;
        for(int i = 0; i < sharedState.length();i++){
            if(sharedState.toCharArray()[i] == 'F'& i < 5){
                F = i;
            }
            else if(sharedState.toCharArray()[i] == 'B' & i<sharedState.length()-20){
                B = i;
            }
            else if(sharedState.toCharArray()[i] == 'C'){
                C = i;
            }
            else if(sharedState.toCharArray()[i] == 'D'){
                D = i;
            }
        }

        int numberOfWellFormed = 0;


        //test if factories if well-formed
        String factories = sharedState.substring(F,C);
        int zero = 0;
        int one = 0;
        int two = 0;
        int three = 0;
        int four = 0;
        for(int n = 0 ; n < factories.length();n++){
            if(factories.toCharArray()[n] == '0'){
                zero = n;
            }
            else if(factories.toCharArray()[n] == '1'){
                one = n;
            }
            else if(factories.toCharArray()[n] == '2'){
                two = n;
            }
            else if(factories.toCharArray()[n] == '3'){
                three = n;
            }
            else if(factories.toCharArray()[n] == '4'){
                four = n;
            }
        }

        String zeroFactories = factories.substring(zero,one);
        String oneFactories = factories.substring(one,two);
        String twoFactories = factories.substring(two,three);
        String threeFactories  = factories.substring(three,four);
        String fourFactories = factories.substring(four,factories.length());

        int numOfInvalidFactories = 0;

        for(int z = 1; z+1 < zeroFactories.length();z++){
            if((byte)zeroFactories.toCharArray()[z] > (byte)zeroFactories.toCharArray()[z+1]){
                numOfInvalidFactories +=1;
            }
        }
        for(int o = 1; o+1 < oneFactories.length();o++){
            if((byte)oneFactories.toCharArray()[o] > (byte)oneFactories.toCharArray()[o+1]){
                numOfInvalidFactories +=1;
            }
        }
        for(int t = 1; t+1 < twoFactories.length();t++){
            if((byte)twoFactories.toCharArray()[t] > (byte)twoFactories.toCharArray()[t+1]){
                numOfInvalidFactories +=1;
            }
        }
        for(int h = 1; h+1 < threeFactories.length();h++){
            if((byte)threeFactories.toCharArray()[h] > (byte)threeFactories.toCharArray()[h+1]){
                numOfInvalidFactories +=1;
            }
        }
        for(int f = 1; f+1 < fourFactories.length();f++){
            if((byte)fourFactories.toCharArray()[f] > (byte)fourFactories.toCharArray()[f+1]){
                numOfInvalidFactories +=1;
            }
        }


        if(numOfInvalidFactories == 0 & zeroFactories.length() == 5 & oneFactories.length() == 5 & twoFactories.length() ==5
        & threeFactories.length() ==5 & fourFactories.length() ==5 & factories.length() == 26){
            numberOfWellFormed += 1;
        }
        else if(factories.length() == 1){
            numberOfWellFormed += 1;
        }



        //test if bag is well-formed
        String bag =sharedState.substring(B,B+11);
        try {
            int numOfaInBag = Integer.valueOf(bag.substring(1,3));
            int numOfbInBag = Integer.valueOf(bag.substring(3,5));
            int numOfcInBag = Integer.valueOf(bag.substring(5,7));
            int numOfdInBag = Integer.valueOf(bag.substring(7,9));
            int numOfeInBag = Integer.valueOf(bag.substring(9,11));
            if(numOfaInBag <= 20 & numOfbInBag <= 20 & numOfcInBag <= 20 & numOfdInBag <= 20 & numOfeInBag <= 20 ){
                if(sharedState.substring(B,D).length() == bag.length()){
                    numberOfWellFormed += 1; //plus one if bag is well-formed
                }
            }
        } catch (Exception e) {
            return false;
        }



        //test if discard is well-formed
        String discard = sharedState.substring(D,D+11);
        try {
            int numOfaInDiscard = Integer.valueOf(discard.substring(1,3));
            int numOfbInDiscard = Integer.valueOf(discard.substring(3,5));
            int numOfcInDiscard = Integer.valueOf(discard.substring(5,7));
            int numOfdInDiscard = Integer.valueOf(discard.substring(7,9));
            int numOfeInDiscard = Integer.valueOf(discard.substring(9,11));
            if(numOfaInDiscard <= 20 & numOfbInDiscard <= 20 & numOfcInDiscard <= 20 & numOfdInDiscard <= 20 & numOfeInDiscard <=20){
                if(sharedState.substring(D,sharedState.length()).length() == discard.length()){
                    numberOfWellFormed +=1; //plus one if discard is well-formed
                }
            }
        } catch (Exception e) {
            return false;
        }




        if(numberOfWellFormed == 3){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Given a playerState, determine if it is well-formed.
     * Note: you don't have to consider validity for this task.
     * A playerState is composed of individual playerStrings.
     * A playerState is well-formed if it satisfies the following conditions.
     * <p>
     * A playerString follows this pattern: [player][score][mosaic][storage][floor]
     * where [player], [score], [mosaic], [storage] and [floor] are replaced by
     * a corresponding substring as described below.
     * Each playerString is sorted by Player i.e. Player A appears before Player B.
     * <p>
     * 1. [player] The player substring is one character 'A' to 'D' -
     * representing the Player
     * <p>
     * 2. [score] The score substring is one or more digits between '0' and '9' -
     * representing the score
     * <p>
     * 3. [mosaic] The Mosaic substring begins with a 'M'
     * Which is followed by *up to* 25 3-character strings.
     * Each 3-character string is defined as follows:
     * 1st character is 'a' to 'e' - representing the tile colour.
     * 2nd character is '0' to '4' - representing the row.
     * 3rd character is '0' to '4' - representing the column.
     * The Mosaic substring is ordered first by row, then by column.
     * That is, "a01" comes before "a10".
     * <p>
     * 4. [storage] The Storage substring begins with an 'S'
     * and is followed by *up to* 5 3-character strings.
     * Each 3-character string is defined as follows:
     * 1st character is '0' to '4' - representing the row - each row number must only appear once.
     * 2nd character is 'a' to 'e' - representing the tile colour.
     * 3rd character is '0' to '5' - representing the number of tiles stored in that row.
     * Each 3-character string is ordered by row number.
     * <p>
     * 5. [floor] The Floor substring begins with an 'F'
     * and is followed by *up to* 7 characters in alphabetical order.
     * Each character is 'a' to 'f' - where 'f' represents the first player token.
     * There is only one first player token.
     * <p>
     * An entire playerState for 2 players might look like this:
     * "A20Ma02a13b00e42S2a13e44a1FaabbeB30Mc01b11d21S0e12b2F"
     * If we split player A's string into its substrings, we get:
     * [A][20][Ma02a13b00e42][S2a13e44a1][Faabbe].
     *
     * @param playerState the player state string
     * @return True if the playerState is well-formed,
     * false if the playerState is not well-formed
     * TASK 3
     */
    public static boolean isPlayerStateWellFormed(String playerState) {
        // FIXME Task 3
        return false;
    }

    /**
     * Given the gameState, draw a *random* tile from the bag.
     * If the bag is empty, refill the the bag with the discard pile and then draw a tile.
     * If the discard pile is also empty, return 'Z'.
     *
     * @param gameState the current game state
     * @return the tile drawn from the bag, or 'Z' if the bag and discard pile are empty.
     * TASK 5
     */
    public static char drawTileFromBag(String[] gameState) {
        // FIXME Task 5
        String a = gameState[0];
        int b = a.indexOf("B");
        int c = a.indexOf("D");
        String d = a.substring(b,b+11);
        String e = a.substring(c);
        if (d.equals("B0000000000") && e.equals("D0000000000")){
            return 'Z';
        }
        if (d.equals("B0000000000") && !e.equals("D0000000000")){
            d = "B"+ e.substring(1);
        }
        if (!d.equals("B0000000000")){
            int a1 = Integer.parseInt(d.substring(1,3));
            int b1 = Integer.parseInt(d.substring(3,5));
            int c1 = Integer.parseInt(d.substring(5,7));
            int d1 = Integer.parseInt(d.substring(7,9));
            int e1 = Integer.parseInt(d.substring(9));
            String x = String.valueOf('a').repeat(a1) + String.valueOf('b').repeat(b1) + String.valueOf('c').repeat(c1)
                    + String.valueOf('d').repeat(d1) + String.valueOf('e').repeat(e1);
            return x.toCharArray()[(int) (Math.random() * x.length())];
        }
        return 'Z';
    }

    /**
     * Given a state, refill the factories with tiles.
     * If the factories are not all empty, return the given state.
     *
     * @param gameState the state of the game.
     * @return the updated state after the factories have been filled or
     * the given state if not all factories are empty.
     * TASK 6
     */
    public static String[] refillFactories(String[] gameState) {
        // FIXME Task 6
        return null;
    }

    /**
     * Given a gameState for a completed game,
     * return bonus points for rows, columns, and sets.
     *
     * @param gameState a completed game state
     * @param player    the player for whom the score is to be returned
     * @return the number of bonus points awarded to this player for rows,
     * columns, and sets
     * TASK 7
     */
    public static int getBonusPoints(String[] gameState, char player) {
        // FIXME Task 7
        return -1;
    }

    /**
     * Given a valid gameState prepare for the next round.
     * 1. Empty the floor area for each player and adjust their score accordingly (see the README).
     * 2. Refill the factories from the bag.
     * * If the bag is empty, refill the bag from the discard pile and then
     * (continue to) refill the factories.
     * * If the bag and discard pile do not contain enough tiles to fill all
     * the factories, fill as many as possible.
     * * If the factories and centre contain tiles other than the first player
     * token, return the current state.
     *
     * @param gameState the game state
     * @return the state for the next round.
     * TASK 8
     */
    public static String[] nextRound(String[] gameState) {
        // FIXME TASK 8
        return null;
    }

    /**
     * Given an entire game State, determine whether the state is valid.
     * A game state is valid if it satisfies the following conditions.
     * <p>
     * [General]
     * 1. The game state is well-formed.
     * 2. There are no more than 20 of each colour of tile across all player
     * areas, factories, bag and discard
     * 3. Exactly one first player token 'f' must be present across all player
     * boards and the centre.
     * <p>
     * [Mosaic]
     * 1. No two tiles occupy the same location on a single player's mosaic.
     * 2. Each row contains only 1 of each colour of tile.
     * 3. Each column contains only 1 of each colour of tile.
     * [Storage]
     * 1. The maximum number of tiles stored in a row must not exceed (row_number + 1).
     * 2. The colour of tile stored in a row must not be the same as a colour
     * already found in the corresponding row of the mosaic.
     * <p>
     * [Floor]
     * 1. There are no more than 7 tiles on a single player's floor.
     * [Centre]
     * 1. The number of tiles in the centre is no greater than 3 * the number of empty factories.
     * [Factories]
     * 1. At most one factory has less than 4, but greater than 0 tiles.
     * Any factories with factory number greater than this factory must contain 0 tiles.
     *
     * @param gameState array of strings representing the game state.
     *                  state[0] = sharedState
     *                  state[1] = playerStates
     * @return true if the state is valid, false if it is invalid.
     * TASK 9
     */
    public static boolean isStateValid(String[] gameState) {
        // FIXME Task 9
        return false;
    }

    /**
     * Given a valid gameState and a move, determine whether the move is valid.
     * A Drafting move is a 4-character String.
     * A Drafting move is valid if it satisfies the following conditions:
     * <p>
     * 1. The specified factory/centre contains at least one tile of the specified colour.
     * 2. The storage row the tile is being placed in does not already contain a different colour.
     * 3. The corresponding mosaic row does not already contain a tile of that colour.
     * Note that the tile may be placed on the floor.
     * </p>
     * <p>
     * A Tiling move is a 3-character String.
     * A Tiling move is valid if it satisfies the following conditions:
     * 1. The specified row in the Storage area is full.
     * 2. The specified column does not already contain a tile of the same colour.
     * 3. The specified location in the mosaic is empty.
     * 4. If the specified column is 'F', no valid move exists from the
     * specified row into the mosaic.
     * </p>
     *
     * @param gameState the game state.
     * @param move      A string representing a move.
     * @return true if the move is valid, false if it is invalid.
     * TASK 10
     */
    public static boolean isMoveValid(String[] gameState, String move) {
        // FIXME Task 10
        return false;
    }

    /**
     * Given a gameState and a move, apply the move to the gameState.
     * If the move is a Tiling move, you must also update the player's score.
     * If the move is a Tiling move, you must also empty the remaining tiles
     * into the discard.
     * If the move is a Drafting move, you must also move any remaining tiles
     * from the specified factory into the centre.
     * If the move is a Drafting move and you must put tiles onto the floor,
     * any tiles that cannot fit on the floor are placed in the discard with
     * the following exception:
     * If the first player tile would be placed into the discard, it is instead
     * swapped with the last tile in the floor, when the floor is sorted
     * alphabetically.
     *
     * @param gameState the game state.
     * @param move      A string representing a move.
     * @return the updated gameState after the move has been applied.
     * TASK 11
     */
    public static String[] applyMove(String[] gameState, String move) {
        // FIXME Task 11
        return null;
    }

    /**
     * Given a valid game state, return a valid move.
     *
     * @param gameState the game state
     * @return a move for the current game state.
     * TASK 13
     */
    public static String generateAction(String[] gameState) {
        // FIXME Task 13
        return null;
        // FIXME Task 15 Implement a "smart" generateAction()
    }
}
