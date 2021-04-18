package comp1110.ass2;

import gittest.A;
import gittest.B;
import gittest.C;

import java.lang.reflect.Array;
import java.util.*;

public class Azul {
    // Add some static variables.
    static final int BAG_STATE_LENGTH = 11;
    static final int FACTORY_NUM = 5; // 2 - 5, 3 - 7, 4 - 9
    static final int TILES_NUM_IN_FACTORY = 4;
    /**
     * Given a shared state string, determine if it is well-formed.
     * Note: you don't need to consider validity for this task.
     * A sharedState is well-formed if it satisfies the following conditions.
     * <p>
     * [turn][factories][centre][bag][discard]
     * where [turn][factories], [centre], [bag] and [discard] are replaced by the
     * corresponding small string as described below.
     * <p>
     * 0. [turn] The Turn substring is one character 'A'-'D' representing a
     * player, which indicates that it is this player's turn to make the next
     * drafting move. (In a two-player game, the turn substring can only take
     * the values 'A' or 'B').
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

        if(numOfInvalidFactories == 0 & zeroFactories.length() == 5 & oneFactories.length() == 5 & twoFactories.length() ==5
        & threeFactories.length() ==5 & fourFactories.length() ==5 & factories.length() == 26){
            numberOfWellFormed += 1;
        }
        else if(factories.length() == 1){
            numberOfWellFormed += 1;
        }

        //test if center is well-formed
        String center = sharedState.substring(C,B);
        for(int n = 1; n < center.length()-1;n++){
            if(Integer.valueOf(center.toCharArray()[n]) > Integer.valueOf(center.toCharArray()[n+1])){
                return false;
            }
        }

        //test if bag is well-formed
        String bag =sharedState.substring(B,B+11);
        try {
            int numOfBag = Integer.valueOf(bag.substring(1,11));
            if(sharedState.substring(B,D).length() == bag.length()){
                numberOfWellFormed += 1; //plus one if bag is well-formed
            }
        } catch (Exception e) {
            return false;
        }



        //test if discard is well-formed
        String discard = sharedState.substring(D,D+11);
        try {
            int numOfaInDiscard = Integer.valueOf(discard.substring(1,11));
            if(sharedState.substring(D,sharedState.length()).length() == discard.length()){
                numberOfWellFormed +=1; //plus one if discard is well-formed
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
     * Tool 1: isPlayerChar
     * Given a char, determine if it is representing a player('A' to 'D')
     * @param c the input char
     * @return if it is representing a player('A' to 'D')
     */
    public static boolean isPlayerChar(char c) {
        return c == 'A' || c == 'B' || c == 'C' || c == 'D';
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
        // Task 3 Fixed

        if (playerState.isEmpty())
            return false; // return false, if it is an empty String.

        if (!isPlayerChar(playerState.charAt(0))) {
            return false; // return false, if the first letter isn't represent a player
        }
        // May rewrite by using String.split() method.

        int[] begins = new int[4]; // Up to 4 players
        int cnt = 0;
        for (int i = 0; i < playerState.length(); i++) {
            if (isPlayerChar(playerState.charAt(i))) {
                begins[cnt] = i;
                cnt++;
            }
        }
        String[] players = new String[cnt];
        for (int i = 0; i < cnt; i++) {
            players[i] = playerState.substring(begins[i], i == cnt - 1 ? playerState.length() : begins[i + 1]);
        }

        boolean isWellFormed = true;
        for (String player : players) {
            if (!isPlayerWellFormed(player)) {
                isWellFormed = false;
            }
        }

        return isWellFormed;
    }

    /**
     * Tool 2: isPlayerWellFormed
     * determine if this player's code is well-formed.
     * @param playerState, the input player's code
     * @return if this player's code is well-formed.
     */
    public static boolean isPlayerWellFormed(String playerState) {
        int pointer = 0;
        // 1. The player substring is one character 'A' to 'D' - representing the player.
        String player = playerState.substring(pointer, pointer + 1);
        if (!(player.equals("A") || player.equals("B") || player.equals("C") || player.equals("D")))
            return false;

        pointer++; // move the pointer to the beginning of next substring.

        // 2. The score substring is one or more digits between '0' and '9' - representing the score.
        int scoreCnt = 0;
        while (('0' <= playerState.charAt(pointer)) && (playerState.charAt(pointer) <= '9')) {
            pointer++;
            scoreCnt++;
        }
        if (scoreCnt < 1) return false;

        // 3. The Mosaic substring begins with a 'M', which is followed by up-to-25 3-character strings.
        // First begins with 'M'
        if (!(playerState.charAt(pointer) == 'M'))
            return false;

        pointer++;
        // check tuples
        /* Rules:
            1st character is 'a' to 'e' - representing the tile colour.
            2nd character is '0' to '4' - representing the row.
            3rd character is '0' to '4' - representing the column.
         */
        while (pointer + 3 <= playerState.length()) {
            String thisTile = playerState.substring(pointer, pointer + 3);
            boolean isFirstCharValid = 'a' <= thisTile.charAt(0) && thisTile.charAt(0) <= 'e';
            boolean isSecondCharValid = '0' <= thisTile.charAt(1) && thisTile.charAt(1) <= '4';
            boolean isThirdCharValid = '0' <= thisTile.charAt(2) && thisTile.charAt(2) <= '4';
            if (isFirstCharValid && isSecondCharValid && isThirdCharValid) {
                pointer = pointer + 3; // move on to the next tuple.
            } else {
                break;
            }
        }

        // 4. The Storage substring begins with an 'S' and is followed by *up to* 5 3-character strings.
        if (!(playerState.charAt(pointer) == 'S')) {
            return false;
        }

        pointer++;

        // check tuples
        /* Rules:
            1st character is '0' to '4' - representing the row - each row number must only appear once.
            2nd character is 'a' to 'e' - representing the tile colour.
            3rd character is '0' to '5' - representing the number of tiles stored in that row.
         */
        while (pointer + 3 <= playerState.length()) {
            String thisTile = playerState.substring(pointer, pointer + 3);
            boolean isFirstCharValid = '0' <= thisTile.charAt(0) && thisTile.charAt(0) <= '4';
            boolean isSecondCharValid = 'a' <= thisTile.charAt(1) && thisTile.charAt(1) <= 'e';
            boolean isThirdCharValid = '0' <= thisTile.charAt(2) && thisTile.charAt(2) <= '5';
            if (isFirstCharValid && isSecondCharValid && isThirdCharValid) {
                pointer = pointer + 3; // move on to the next tuple.
            } else {
                break;
            }
        }

        // 5. The Floor substring begins with an 'F' and is followed by *up to*
        //    7 characters in alphabetical order.
        if (!(playerState.charAt(pointer) == 'F'))
            return false;

        pointer++;

        // Rules: Each character is 'a' to 'f' - where 'f' represents the first player token.
        int tokenCnt = 0;
        int tileCnt = 0;
        for (int i = pointer; i < playerState.length(); i++) {
            if ('a' <= playerState.charAt(i) && playerState.charAt(i) <= 'f') {
                tileCnt++;
            } else {
                return false;
            }
            if (playerState.charAt(i) == 'f') {
                tokenCnt++;
            }
        }

        return tileCnt <= 7 && tokenCnt <= 1;
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
        a = a.substring(3);
        int b = a.indexOf("B");
        int c = a.indexOf("D");
        String d = a.substring(b, b + 11);
        String e = a.substring(c);
        if (d.equals("B0000000000") && e.equals("D0000000000")) {
            return 'Z';
        }
        if (d.equals("B0000000000") && !e.equals("D0000000000")) {
            d = "B" + e.substring(1);
        }
        if (!d.equals("B0000000000")) {
            int a1 = Integer.parseInt(d.substring(1, 3));
            int b1 = Integer.parseInt(d.substring(3, 5));
            int c1 = Integer.parseInt(d.substring(5, 7));
            int d1 = Integer.parseInt(d.substring(7, 9));
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
        if (gameState.length == 0) return null;

        // TODO: judge if the share State well formed.

        String sharedState = gameState[0].substring(1);
        // record the current character, to deal with the problem of same code of character 'B' and bag 'B'.
        StringBuilder curCharacter = new StringBuilder(gameState[0].substring(0, 1));

        // read factories' states(find the start of the factory state and the end of the factory state)
        int FIndex = sharedState.indexOf('F');
        int CIndex = sharedState.indexOf('C');
        int BIndex = sharedState.indexOf('B'); //  get the bag index.
        int DIndex = sharedState.indexOf('D'); //  get the discard index.
        String factories = sharedState.substring(FIndex, CIndex);

        // check whether all factories are empty
        if (factories.length() != 1) {
            // if not all factories are empty, return given state.
            return gameState;
        }

        // check whether the center is empty
        String center = sharedState.substring(CIndex, BIndex);
        if (center.length() != 1) {
            // first determine whether the center is empty.
            if (!(center.length() == 2 && center.charAt(center.length() - 1) == 'f'))
                // If the center has only one tile 'first player', then the factories should be refilled.
                return gameState;
        }


        // if all the factories are empty, refill the factories with tiles.
        String bag = sharedState.substring(BIndex, DIndex);
        String discard = sharedState.substring(DIndex);
        boolean isBagRefilled = false; // record whether the bag has been refilled by discard.
        StringBuilder newFactories = new StringBuilder("F"); // build a new factories state.
        // These are the numbers of different tiles in the bag.
        int aNum = Integer.parseInt(bag.substring(1, 3)); // number of 'a' tiles
        int bNum = Integer.parseInt(bag.substring(3, 5)); // number of 'b' tiles
        int cNum = Integer.parseInt(bag.substring(5, 7)); // number of 'c' tiles
        int dNum = Integer.parseInt(bag.substring(7, 9)); // number of 'd' tiles
        int eNum = Integer.parseInt(bag.substring(9, 11)); // number of 'e' tiles
        int[] numArray = new int[]{aNum, bNum, cNum, dNum, eNum};
        int totalCnt = aNum + bNum + cNum + dNum + eNum; // record the number of tiles, in order to fill up the bag from discord.
        char[] tileArray = new char[]{'a', 'b', 'c', 'd', 'e'};
        for (int i = 0; i < FACTORY_NUM; i++) {
            newFactories.append(i); //  add the number of the factory in the new built state
            char[] tiles = new char[4]; // in order to sort the tiles after picking from bag
            for (int j = 0; j < TILES_NUM_IN_FACTORY; j++) {
                if (totalCnt == 0) {
                    // refill the bag
                    bag = discard.replace('D', 'B');
                    // reassign the bag's state to variables.
                    aNum = Integer.parseInt(bag.substring(1, 3));
                    bNum = Integer.parseInt(bag.substring(3, 5));
                    cNum = Integer.parseInt(bag.substring(5, 7));
                    dNum = Integer.parseInt(bag.substring(7, 9));
                    eNum = Integer.parseInt(bag.substring(9, 11));
                    numArray[0] = aNum;
                    numArray[1] = bNum;
                    numArray[2] = cNum;
                    numArray[3] = dNum;
                    numArray[4] = eNum;
                    totalCnt = aNum + bNum + cNum + dNum + eNum;
                    discard = "D0000000000"; // update the discard's state.
                    isBagRefilled = true;
                }
                Random randNumGenerator = new Random();
                int tileType = randNumGenerator.nextInt(5); // randomly choose a tile
                while (numArray[tileType] <= 0)
                    tileType = randNumGenerator.nextInt(5); // repick
                numArray[tileType]--;
                totalCnt--;
                tiles[j] = tileArray[tileType];
            }
            Arrays.sort(tiles);
            newFactories.append(tiles); // add the sorted tile code to current factory state.
        }

        // Update the bag state
        StringBuilder newBag = new StringBuilder("B"); // build a new bag state
        for (int j : numArray) {
            if (j < 10) {
                newBag.append("0").append(j);
            } else {
                newBag.append(j);
            }
        }

        // build the new shared state. Replacing the old factories state and bag state with new ones.
        StringBuilder newSharedState = new StringBuilder(sharedState);
        newSharedState.replace(newSharedState.indexOf("F"), newSharedState.indexOf("C"), newFactories.toString());
        newSharedState.replace(newSharedState.indexOf("B"), newSharedState.indexOf("D"), newBag.toString());
        if (isBagRefilled)
            newSharedState.replace(newSharedState.indexOf("D"), newSharedState.length(), discard);
        gameState[0] = curCharacter.append(newSharedState).toString(); // adding the current character in the front of the shared state.
        return gameState;
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
     */public static String[] nextRound(String[] gameState) {
        String sharedState = gameState[0];
        String privateState = gameState[1];
        int Fa = 0;
        int C = 0;
        int B = 0;
        int D = 0;

        for (int i = 0; i < sharedState.length(); i++) {
            if (sharedState.toCharArray()[i] == 'F') {
                Fa = i;
            } else if (sharedState.toCharArray()[i] == 'C') {
                C = i;
            } else if (sharedState.toCharArray()[i] == 'B') {
                B = i;
            } else if (sharedState.toCharArray()[i] == 'D') {
                D = i;
            }
        }//find the index of Fa(factories in sharedState) C(center) B(bag) D(discard)

        String factories = sharedState.substring(Fa, C);
        String center = sharedState.substring(C, B);

        int Ap = 0;
        int Bp = 0;
        for (int i = 0; i < privateState.length(); i++) {
            if (privateState.toCharArray()[i] == 'A') {
                Ap = i;
            } else if (privateState.toCharArray()[i] == 'B') {
                Bp = i;
            }
        }//break the privateState into A player and B player

        String APlayer = privateState.substring(Ap, Bp);
        String BPlayer = privateState.substring(Bp, privateState.toCharArray().length);
        int numafloor = 0;
        int numbfloor = 0;
        for (int i = 0; i < APlayer.toCharArray().length; i++) {
            if (APlayer.toCharArray()[i] == 'F') {
                numafloor = i;
            }
        }
        for (int i = 0; i < BPlayer.toCharArray().length; i++) {
            if (BPlayer.toCharArray()[i] == 'F') {
                numbfloor = i;
            }
        }//find the index of floor in A player and B player respectively
        String AFloor = APlayer.substring(numafloor, APlayer.toCharArray().length);
        String BFloor = BPlayer.substring(numbfloor, BPlayer.toCharArray().length);


        //reset the Discard with the tiles remove from floor
        String discard = sharedState.substring(D, sharedState.toCharArray().length);
        int atile = Integer.parseInt(discard.substring(1, 3));
        int btile = Integer.parseInt(discard.substring(3, 5));
        int ctile = Integer.parseInt(discard.substring(5, 7));
        int dtile = Integer.parseInt(discard.substring(7, 9));
        int etile = Integer.parseInt(discard.substring(9, 11));

        for (int i = 1; i < AFloor.toCharArray().length; i++) {
            if (AFloor.toCharArray()[i] == 'a') {
                atile += 1;
            }
            if (AFloor.toCharArray()[i] == 'b') {
                btile += 1;
            }
            if (AFloor.toCharArray()[i] == 'c') {
                ctile += 1;
            }
            if (AFloor.toCharArray()[i] == 'd') {
                dtile += 1;
            }
            if (AFloor.toCharArray()[i] == 'e') {
                etile += 1;
            }

        }
        for (int i = 1; i < BFloor.toCharArray().length; i++) {
            if (BFloor.toCharArray()[i] == 'a') {
                atile += 1;
            }
            if (BFloor.toCharArray()[i] == 'b') {
                btile += 1;
            }
            if (BFloor.toCharArray()[i] == 'c') {
                ctile += 1;
            }
            if (BFloor.toCharArray()[i] == 'd') {
                dtile += 1;
            }
            if (BFloor.toCharArray()[i] == 'e') {
                etile += 1;
            }

        }
        String atiles = String.valueOf(atile);
        String btiles = String.valueOf(btile);
        String ctiles = String.valueOf(ctile);
        String dtiles = String.valueOf(dtile);
        String etiles = String.valueOf(etile);

        if (atile < 10) {
            atiles = String.valueOf("0" + atile);
        }
        if (btile < 10) {
            btiles = String.valueOf("0" + btile);
        }
        if (ctile < 10) {
            ctiles = String.valueOf("0" + ctile);
        }
        if (dtile < 10) {
            dtiles = String.valueOf("0" + dtile);
        }
        if (etile < 10) {
            etiles = String.valueOf("0" + etile);
        }
        discard = "D" + atiles + btiles + ctiles + dtiles + etiles;


        String[] newGameState = new String[2];

        if (!isFull(APlayer) && !isFull(BPlayer)) {
            privateState = emptyFloor(APlayer) + emptyFloor(BPlayer);
            if (factories.length() == 1 && center.length() == 1) {
                if(storageIsAvia(APlayer) || storageIsAvia(BPlayer)) {
                    newGameState[0] = sharedState;
                    newGameState[1] = privateState;
                    return newGameState;
                }
                else {
                    if (APlayer.contains("f")) {
                        sharedState = "A" + sharedState.substring(1, sharedState.toCharArray().length);
                    } else {
                        sharedState = "B" + sharedState.substring(1, sharedState.toCharArray().length);
                    }
                    newGameState[0] = sharedState;
                    newGameState[1] = privateState;
                    return refillFactories(newGameState);
                }

            } else {
                newGameState[0] = sharedState;
                newGameState[1] = privateState;
                return newGameState;
            }

        } else {
            privateState = emptyFloorwithBonus(gameState,APlayer) + emptyFloorwithBonus(gameState,BPlayer);
            newGameState[0] = sharedState.substring(0, C + 1) + "f" + sharedState.substring(C + 1, sharedState.toCharArray().length - 11) + discard;
            newGameState[1] = privateState;
            return newGameState;
        }


    }
    public static String emptyFloor(String privateState) {
        int F = 0;
        int M = 0;
        for (int i = 0; i < privateState.toCharArray().length; i++) {
            if (privateState.toCharArray()[i] == 'F') {
                F = i;
            }
            if (privateState.toCharArray()[i] == 'M') {
                M = i;
            }
        }
        int minusScore = 0;
        String floor = privateState.substring(F, privateState.toCharArray().length);
        if (floor.toCharArray().length == 2) {
            minusScore += 1;
        } else if (floor.toCharArray().length == 3) {
            minusScore += 2;
        } else if (floor.toCharArray().length == 4) {
            minusScore += 4;
        } else if (floor.toCharArray().length == 5) {
            minusScore += 6;
        } else if (floor.toCharArray().length == 6) {
            minusScore += 8;
        } else if (floor.toCharArray().length == 7) {
            minusScore += 11;
        } else if (floor.toCharArray().length >= 8) {
            minusScore += 14;
        }

        int score = Integer.parseInt(privateState.substring(1, M)) - minusScore;
        if (score < 0) {
            return String.valueOf(privateState.toCharArray()[0]) + "0" + privateState.substring(M, F + 1);
        } else {
            return String.valueOf(privateState.toCharArray()[0]) + String.valueOf(score) + privateState.substring(M, F + 1);
        }
    }

    public static String emptyFloorwithBonus(String[] gamestate,String privateState) {
        int F = 0;
        int M = 0;
        for (int i = 0; i < privateState.toCharArray().length; i++) {
            if (privateState.toCharArray()[i] == 'F') {
                F = i;
            }
            if (privateState.toCharArray()[i] == 'M') {
                M = i;
            }
        }
        int minusScore = 0;
        String floor = privateState.substring(F, privateState.toCharArray().length);
        if (floor.toCharArray().length == 2) {
            minusScore += 1;
        } else if (floor.toCharArray().length == 3) {
            minusScore += 2;
        } else if (floor.toCharArray().length == 4) {
            minusScore += 4;
        } else if (floor.toCharArray().length == 5) {
            minusScore += 6;
        } else if (floor.toCharArray().length == 6) {
            minusScore += 8;
        } else if (floor.toCharArray().length == 7) {
            minusScore += 11;
        } else if (floor.toCharArray().length >= 8) {
            minusScore += 14;
        }

        int score = Integer.parseInt(privateState.substring(1, M)) - minusScore;
        if (score < 0) {
            return String.valueOf(privateState.toCharArray()[0]) + getBonusPoints(gamestate,privateState.toCharArray()[0]) + privateState.substring(M, F + 1);
        } else {
            return String.valueOf(privateState.toCharArray()[0]) + (score+getBonusPoints(gamestate,privateState.toCharArray()[0]))+ privateState.substring(M, F + 1);
        }
    }


    public static Boolean isFull(String privateState){
        int M = 0;
        int F = 0;
        for(int i = 0;i < privateState.toCharArray().length;i++){
            if(privateState.toCharArray()[i] == 'M'){
                M = i;
            }
            if(privateState.toCharArray()[i] == 'F'){
                F = i;
            }
        }
        privateState = privateState.trim();
        String number = "";
        if(privateState != null && !"".equals(privateState)) {
            for (int i = 0; i < privateState.length(); i++) {
                if (privateState.charAt(i) >= 48 && privateState.charAt(i) <= 57) {
                    number += privateState.charAt(i);
                }
            }
        }
        if(number.contains("0001020304")  || number.contains("1011121314") || number.contains("2021222324") || number.contains("3031323334") || number.contains("4041424344")||number.contains("5051525354")){
            return true;
        }
        else {
            return false;
        }

    }

    public static boolean storageIsAvia(String privateState) {
        int S = 0;
        int F = 0;
        for (int i = 0; i < privateState.toCharArray().length; i++) {
            if (privateState.toCharArray()[i] == 'S') {
                S = i;
            }
            if (privateState.toCharArray()[i] == 'F') {
                F = i;
            }
        }
        String storage = privateState.substring(S, F);
        List<String> number = new ArrayList<String>();
        for (int i = 0; i < storage.length()-2;) {
            number.add(storage.substring(i + 1, i + 2) + storage.substring(i + 3, i + 4));
            i = i + 3;
        }
        if (number.contains("01") || number.contains("12") || number.contains("23") || number.contains("34") || number.contains("45")) {
            return true;
        } else {
            return false;
        }
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

/*
    public static void main(String[] args) {
        String[] testTask6 = new String[]{
                "AFCB1915161614D0000000000",
                "A0MS0d11c22b33e44e1FefB0MS0a11b22d33c2F"
        };
        System.out.println("Test: ");
        for (String s: testTask6) {
            System.out.println(s);
        }
        System.out.println("After refilling: ");
        testTask6 = refillFactories(testTask6);
        for (String s: testTask6) {
            System.out.println(s);
        }
    }

 */

}
