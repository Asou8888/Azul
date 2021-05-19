package comp1110.ass2;

import comp1110.ass2.member.*;
import gittest.A;
import gittest.B;
import gittest.C;
import javafx.geometry.Pos;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;

public class Azul {
    // Add some static variables.
    static final int BAG_STATE_LENGTH = 11;
    static final int FACTORY_NUM = 5; // 2 - 5, 3 - 7, 4 - 9
    static final int TILES_NUM_IN_FACTORY = 4;

    /**
     * Split the shared state string.
     * Author: Ruizheng Shen, Date: 2021.4.27
     * @param state (an array of 2 strings)
     * @return the splited string.
     */
    public static String[] splitSharedState(String[] state) {
        // TODO: test
        String sharedState = state[0]; // the shared state {Turn}{Factory}{Centre}{Bag}{Discard}

        // Split the shared state
        // {Turn} state
        int pointer = 0; // the pointer record the current visited position in String sharedState.
        String turn = sharedState.substring(pointer, pointer + 1); // take the first character. {Turn}
        pointer++; // move the pointer to the factory.

        // {Factory} state
        // pointer now points to 'F' in sharedState.
        int endFactory = sharedState.substring(pointer).indexOf('C') + pointer; // find the ending position of factory.
        String factory = sharedState.substring(pointer, endFactory);
        pointer = endFactory; // move the pointer to the centre.

        // {Centre} state
        int endCentre = sharedState.substring(pointer).indexOf('B') + pointer; // find the ending position of centre.
        String centre = sharedState.substring(pointer, endCentre);
        pointer = endCentre; // move the pointer to the bag.

        // {Bag} state
        int endBag = sharedState.substring(pointer).indexOf('D') + pointer; // find the ending position of bag.
        String bag = sharedState.substring(pointer, endBag);
        pointer = endBag; // move the pointer to the discard.

        // {Discord} state
        String discord = sharedState.substring(pointer); // The remaining is the discord.
        return new String[]{turn, factory, centre, bag, discord};
    }

    /**
     * split the player state into a map \<String, String\>,
     * The String is the player String("A"-"D")
     * The String[] is the player state. {Score}{Mosaic}{Storage}{Floor}.
     * @param state
     * @return the map from player String to player state.
     */
    public static HashMap<String, String[]> splitPlayerState(String[] state) {
        // TODO: test
        // the player state {Player}{Score}{Mosaic}{Storage}{Floor}
        String[] playerStates = state[1].split("[ABCD]"); // get the player state from state.(split by regex: "[ABCD]")
        String[] player = new String[4];
        int playerCnt = 0;
        // find the number of player
        if (state[1].indexOf('A') >= 0) {
            player[playerCnt] = "A";
            playerCnt++;
        }
        if (state[1].indexOf('B') >= 0) {
            player[playerCnt] = "B";
            playerCnt++;
        }
        if (state[1].indexOf('C') >= 0) {
            player[playerCnt] = "C";
            playerCnt++;
        }
        if (state[1].indexOf('D') >= 0) {
            player[playerCnt] = "D";
            playerCnt++;
        }
        HashMap<String, String[]> splitPlayerState = new HashMap<>();
        int i = 0;
        for (String playerState: playerStates) {
            if (playerState == "") continue;
            int pointer = 0; // the pointer record the current visiting location.

            // {Score} state
            int endScore = playerState.indexOf('M'); // find the ending position of score.
            String score = playerState.substring(pointer, endScore);
            pointer = endScore; // move the pointer to the Mosaic.

            // {Mosaic} state
            int endMosaic = playerState.indexOf('S'); // find the ending position of mosaic.
            String mosaic = playerState.substring(pointer, endMosaic);
            pointer = endMosaic; // move the pointer to the Storage.

            // {Storage} state
            int endStorage = playerState.indexOf('F'); // find th ending position of storage.
            String storage = playerState.substring(pointer, endStorage);
            pointer = endStorage; // move the pointer to the floor.

            // {Floor} state
            String floor = playerState.substring(pointer);

            String[] statesForThisPlayer = new String[]{
                    score, mosaic, storage, floor
            };
            splitPlayerState.put(player[i], statesForThisPlayer); // map this player with its state.
            i++;
        }
        return splitPlayerState;
    }


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
        if (F > C) return false; // if C appears in the sharedstate before F, return false;
        String factories = sharedState.substring(F,C);
        int zero = 0;
        int zeroCnt = 0;
        int one = 0;
        int oneCnt = 0;
        int two = 0;
        int twoCnt = 0;
        int three = 0;
        int threeCnt = 0;
        int four = 0;
        int fourCnt = 0;
        for(int n = 0 ; n < factories.length();n++){
            if(factories.toCharArray()[n] == '0'){
                if (zeroCnt == 0) zeroCnt++;
                else return false; // if more than one '0' in the factory state, return false;
                zero = n;
            }
            else if(factories.toCharArray()[n] == '1'){
                if (oneCnt == 0) oneCnt++;
                else return false;
                one = n;
            }
            else if(factories.toCharArray()[n] == '2'){
                if (twoCnt == 0) twoCnt++;
                else return false;
                two = n;
            }
            else if(factories.toCharArray()[n] == '3'){
                if (threeCnt == 0) threeCnt++;
                else return false;
                three = n;
            }
            else if(factories.toCharArray()[n] == '4'){
                if (fourCnt == 0) fourCnt++;
                else return false;
                four = n;
            }
        }
        if(F == 0){
            return false;
        }

        // FIXME: test
        if (factories.equals("")) {
            return false; // the factory string is empty, return false;
        }
        // FIXME

        ArrayList<Integer> numIndexOfFactory = new ArrayList<>(); // store the index found in shared state
        for (int i = 0; i < factories.length(); i++) {
            if (Character.isDigit(factories.charAt(i))) {
                // This character is a digit.
                numIndexOfFactory.add(factories.charAt(i) - '0');
            }
        }
        // if there's more than 5 digits is found in the sharedstate, return false;
        if (numIndexOfFactory.size() > 5) return false;

        String[] factoryArray = new String[numIndexOfFactory.size()];
        for (int i = 0; i < numIndexOfFactory.size(); i++) {
            if (i != numIndexOfFactory.size() - 1 && numIndexOfFactory.get(i) >= numIndexOfFactory.get(i + 1)) {
                return false; // number order
            }
            if (numIndexOfFactory.get(i) >= 5) return false; // no number is bigger than 5.
        }
        for (int i = 0; i < factoryArray.length; i++) {
            if (i != factoryArray.length - 1) {
                int start  = factories.indexOf(String.valueOf(numIndexOfFactory.get(i)));
                int end = factories.indexOf(String.valueOf(numIndexOfFactory.get(i + 1)));
                factoryArray[i] = factories.substring(start,end);
            } else {
                int start  = factories.indexOf(String.valueOf(numIndexOfFactory.get(i)));
                factoryArray[i] = factories.substring(start);
            }
        }
         int numOfValidFac = 0;

        for(String fac:factoryArray){
            if(fac.length() == 5){
                numOfValidFac += 1;
            }
        }


        if(numOfValidFac == numIndexOfFactory.size()){
            numberOfWellFormed += 1;
        }
        else if(factories.length() == 1){
            numberOfWellFormed += 1;
        }

        if(factories.contains("g")){
            numberOfWellFormed -= 1;
        }


        //test if center is well-formed
        String center = sharedState.substring(C,B);
        for(int n = 1; n < center.length()-1;n++){
            if(Integer.valueOf(center.toCharArray()[n]) > Integer.valueOf(center.toCharArray()[n+1])){
                return false;
            }
        }

        if (sharedState.indexOf('B') < 0) return false; // check whether there's 'B' in sharedstate.
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
        String a = gameState[1];
        int bonus = 0;
        String playerString = String.valueOf(player);
        if (!playerString.equals("A")) {
            int b = a.indexOf("B");
            a = a.substring(b);
        }
        int c = a.indexOf("M");
        int d = a.indexOf("S");
        String e = a.substring(c + 1, d);
        int row1 = 0;
        int col1 = 0;
        while (row1 < 5) {
            String g = String.valueOf(row1) + String.valueOf(col1);
            if (e.contains(g)) {
                if (col1 == 4) {
                    bonus = bonus + 2;
                    col1 = 0;
                    row1++;
                } else {
                    col1++;
                }
            } else {
                col1 = 0;
                row1++;
            }
        }
        int row2 = 0;
        int col2 = 0;
        while (col2 < 5) {
            String g = String.valueOf(row2) + String.valueOf(col2);
            if (e.contains(g)) {
                if (row2 == 4) {
                    bonus = bonus + 7;
                    row2 = 0;
                    col2++;
                } else {
                    row2++;
                }
            } else {
                row2 = 0;
                col2++;
            }
        }
        int times = 0;
        int index = 0;
        String aS = e;
        String bS = e;
        String cS = e;
        String dS = e;
        String eS = e;
        int an = 0;
        int bn = 0;
        int cn = 0;
        int dn = 0;
        int en = 0;
        while (index < 5) {
            while (an < 5) {
                if (aS.contains("a")) {
                    times++;
                    int aT = aS.indexOf("a");
                    aS = aS.substring(aT + 1);
                    if (times == 5) {
                        bonus = bonus + 10;
                    }
                }
                an += 1;
            }
            times = 0;
            index++;
            while (bn < 5) {
                if (bS.contains("b")) {
                    times++;
                    int bT = bS.indexOf("b");
                    bS = bS.substring(bT + 1);
                    if (times == 5) {
                        bonus = bonus + 10;
                    }
                }
                bn += 1;
            }
            times = 0;
            index++;
            while (cn < 5) {
                if (cS.contains("c")) {
                    times++;
                    int cT = cS.indexOf("c");
                    cS = cS.substring(cT + 1);
                    if (times == 5) {
                        bonus = bonus + 10;
                    }
                }
                cn += 1;
            }
            times = 0;
            index++;
            while (dn < 5) {
                if (dS.contains("d")) {
                    times++;
                    int dT = dS.indexOf("d");
                    dS = dS.substring(dT + 1);
                    if (times == 5) {
                        bonus = bonus + 10;
                    }
                }
                dn += 1;
            }
            times = 0;
            index++;
            while (en < 5) {
                if (eS.contains("e")) {
                    times++;
                    int eT = eS.indexOf("e");
                    eS = eS.substring(eT + 1);
                    if (times == 5) {
                        bonus = bonus + 10;
                    }
                }
                en += 1;
                index++;
            }

        }
        return bonus;
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
         * 1. The game state is well-formed. (completed)
         * 2. There are no more than 20 of each colour of tile across all player
         * areas, factories, bag and discard
         * 3. Exactly one first player token 'f' must be present across all player
         * boards and the centre. (completed)
         * <p>
         * [Mosaic]
         * 1. No two tiles occupy the same location on a single player's mosaic.
         * 2. Each row contains only 1 of each colour of tile.
         * 3. Each column contains only 1 of each colour of tile.
         * [Storage]
         * 1. The maximum number of tiles stored in a row must not exceed (row_number + 1).
         * 2. The colour of tile stored in a row must not be the same as a colour
         * already found in the corresponding row of the mosaic. (completed)
         * <p>
         * [Floor]
         * 1. There are no more than 7 tiles on a single player's floor.(completed)
         * [Centre]
         * 1. The number of tiles in the centre is no greater than 3 * the number of empty factories. (completed)
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
        if (!(isSharedStateWellFormed(gameState[0]) && isPlayerStateWellFormed(gameState[1]))){ return  false;}
        String[] sharedState = splitSharedState(gameState);
        String factory = sharedState[1]; //String for factory
        String centre = sharedState[2]; //String for center
        String bag = sharedState[3]; //String for bag
        String discard = sharedState[4]; // String for discard
        HashMap<String, String[]> splitPlayerState = splitPlayerState(gameState);
        String mosaicA = splitPlayerState.get("A")[1]; // mosaic for playerA
        String storageA = splitPlayerState.get("A")[2]; // storage for playerA
        String floorA = splitPlayerState.get("A")[3]; //floor for playerA
        String mosaicB = splitPlayerState.get("B")[1]; // mosaic for playerB
        String storageB = splitPlayerState.get("B")[2]; // storage for playerB
        String floorB = splitPlayerState.get("B")[3]; // floor for playerB
        return (tileOnFloor(floorA) && tileOnFloor(floorB) &&
                tileInCenter(centre,factory) && tileStorageAndMosaic(storageA,mosaicA) &&
                tileStorageAndMosaic(storageB,mosaicB) && containOneF(centre,mosaicA,storageA,floorA,mosaicB,storageB,floorB) &&
                noMoreThan20(centre,factory,bag,discard,mosaicA,storageA,floorA,mosaicB,storageB,floorB) &&
                extraTileInSto(storageA) && extraTileInSto(storageB));
    }

    /** 2. There are no more than 20 of each colour of tile across all player
     * areas, factories, bag and discard*/
    public static boolean noMoreThan20 (String cen, String fac, String bag,String dis,String mosA,String stoA,String flrA,String mosB,String stoB,String flrB){
        if (dis.length() > 11) return false;
        int a = noMoreThan20InShared(cen,fac,bag,dis)[0] + noMoreThan20InPlayer(mosA,stoA,flrA)[0] + noMoreThan20InPlayer(mosB,stoB,flrB)[0];
        int b = noMoreThan20InShared(cen,fac,bag,dis)[1] + noMoreThan20InPlayer(mosA,stoA,flrA)[1] + noMoreThan20InPlayer(mosB,stoB,flrB)[1];
        int c = noMoreThan20InShared(cen,fac,bag,dis)[2] + noMoreThan20InPlayer(mosA,stoA,flrA)[2] + noMoreThan20InPlayer(mosB,stoB,flrB)[2];
        int d = noMoreThan20InShared(cen,fac,bag,dis)[3] + noMoreThan20InPlayer(mosA,stoA,flrA)[3] + noMoreThan20InPlayer(mosB,stoB,flrB)[3];
        int e = noMoreThan20InShared(cen,fac,bag,dis)[4] + noMoreThan20InPlayer(mosA,stoA,flrA)[4] + noMoreThan20InPlayer(mosB,stoB,flrB)[4];
        return(a == 20 && b==20 && c == 20 && d ==20 && e == 20); // return true only each tile has 20 amount.
    }

    /** return amount of a,b,c,d,e in list in factories,bag,discard.*/
    public static int[] noMoreThan20InShared (String centre,String factory,String bag, String discard){
        int[] ints = new int[5];
        ints[0] = cantainChar(centre,"a") + Integer.parseInt(bag.substring(1,3)) + Integer.parseInt(discard.substring(1,3)) + cantainChar(factory,"a");
        ints[1] = cantainChar(centre,"b") + Integer.parseInt(bag.substring(3,5)) + Integer.parseInt(discard.substring(3,5)) + cantainChar(factory,"b");
        ints[2] = cantainChar(centre,"c") + Integer.parseInt(bag.substring(5,7)) + Integer.parseInt(discard.substring(5,7)) + cantainChar(factory,"c");
        ints[3] = cantainChar(centre,"d") + Integer.parseInt(bag.substring(7,9)) + Integer.parseInt(discard.substring(7,9)) + cantainChar(factory,"d");
        ints[4] = cantainChar(centre,"e") + Integer.parseInt(bag.substring(9, 11)) + Integer.parseInt(discard.substring(9, 11)) + cantainChar(factory,"e");
        return ints;} //the list contain amount of each tile.

    /**return amount of a char present in a string.*/
    public static int cantainChar (String str, String cha){
         int cnt = 0; // count number of char
         for(int i =0; i < str.length(); i++ ) {
             int n = str.indexOf(cha,i);
             if (n == i) {
                 cnt++;
             }
         }
         return cnt;
     }
    /** return amount of a,b,c,d,e in list in mosaic,storage,floor.*/
    public static int[] noMoreThan20InPlayer (String mosaic,String storage,String floor){
        int[] intsPlayer = new int[5]; //list for amount of a,b,c,d,e.
        intsPlayer[0] = cantainChar(mosaic,"a") + cantainChar(floor,"a") + tileInStorage(storage,"a");
        intsPlayer[1] = cantainChar(mosaic,"b") + cantainChar(floor,"b") + tileInStorage(storage,"b");
        intsPlayer[2] = cantainChar(mosaic,"c") + cantainChar(floor,"c") + tileInStorage(storage,"c");
        intsPlayer[3] = cantainChar(mosaic,"d") + cantainChar(floor,"d") + tileInStorage(storage,"d");
        intsPlayer[4] = cantainChar(mosaic,"e") + cantainChar(floor,"e") + tileInStorage(storage,"e");
        return intsPlayer; //
    }
    public static int tileInStorage (String storage, String cha) {
        int cnt = 0; // count number of char in string
        for(int i = 1; i < storage.length(); i = i + 3){
            int n = storage.indexOf(cha,i);
            if (n-1 == i){
                cnt = cnt + storage.charAt(i + 2) - '0';
            }
        }
        return cnt;
    }
     /**3. Exactly one first player token 'f' must be present across all player
         * boards and the centre. */
    public static boolean containOneF (String cent, String mosA,String stoA,String flrA,String mosB,String stoB,String flrB){
        return((containFInCentre(cent) + containOneFInPlayer(mosA,stoA,flrA) + containOneFInPlayer(mosB,stoB,flrB)) < 2);
    }
    /** return whether center contains 'f'.*/
    public static int containFInCentre (String centre) {
        int fcnt = 0; // add 1 if there is 'f' in center
        for (int j = 0; j < centre.length(); j ++){
            int m = centre.indexOf("f",j);
            if (j == m ) {
                fcnt ++;
            }
        }
        return fcnt;
    }
    /** return whether player state contains 'f'.*/
    public  static  int containOneFInPlayer (String mosaic, String storage, String floor) {
        int fcnt1 = 0; // add 1 if 'f' presents in mosaic.
        for (int j = 0; j < mosaic.length(); j ++){
            int m = mosaic.indexOf("f",j);
            if (j == m ) {
                fcnt1 ++;
            }
        }
        int fcnt2 = 0; // add 1 if 'f' presents in floor.
        for (int j = 0; j < floor.length(); j ++){
            int m = floor.indexOf("f",j);
            if (j == m ) {
                fcnt2 ++;
            }
        }
        int fcnt3 = tileInStorage(storage,"f"); // add 1 if 'f' presents in storage
        return ( fcnt1 + fcnt2 + fcnt3 );
    }
    /** [Storage]
     * 1. The maximum number of tiles stored in a row must not exceed (row_number + 1).*/
    public static boolean extraTileInSto (String storage){
        for (int i = 1; i < storage.length();i = i+3){
            int row = storage.charAt(i) - '0'; // represent row
            int num = storage.charAt(i+2) - '0'; // represent quantity
            if (row + 1 < num) {return false;}
        }
        return true;
    }
    /** [Centre]
    /1. The number of tiles in the centre is no greater than 3 * the number of empty factories.*/
    public static boolean tileInCenter (String center, String factory) {
        int cnt = 0; // number of tiles in centre
        for (int i = 1; i < center.length();i++){
            cnt++;
            if (center.charAt(i) == 'f'){
                cnt--;
            }
        }
        int num = 0; // number of factory contains tile
        for(int j = 1; j < factory.length(); j++){
            if (factory.charAt(j) == 'a'|| factory.charAt(j) == 'b' && factory.charAt(j) == 'c'||
                    factory.charAt(j) == 'd' || factory.charAt(j) == 'e'){
                num++;
            }
        }
        return cnt <= (20 - num )/4 * 3;
    }
    public  static  boolean tileInRowStorage (String storage){
        Storage str = new Storage();
        str.decode(storage);

        return false;
    }
    /** 2. The colour of tile stored in a row must not be the same as a colour
     already found in the corresponding row of the mosaic.*/
    public  static  boolean tileStorageAndMosaic (String storage, String mosaic){
        Storage str = new Storage();
        NewMosaic mos = new NewMosaic();
        str.decode(storage);
        mos.decode(mosaic);
        int cnt = 0; //add 1 if there is a row in Storage has same color tile in mosaic.
        for (int i = 0; i < 5;i++) {
            TileType tileColor = str.rowColor(i);
            ArrayList<TileType> tileMosaicColor = mos.rowColorList(i);
            if (tileMosaicColor.contains(tileColor)){
                cnt++;
            }
        }
        return cnt ==0; // true only when all different color tile in a row between storage and mosaic.
    }

    /** [Floor]
    /* 1. There are no more than 7 tiles on a single player's floor.*/
    public static boolean tileOnFloor (String floor){
        return (floor.length()<9);
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
        // determine whether the game state valid first.
        // TODO: if (!isStateValid(gameState)) return false;

        /* If move.length == 4, then it is a draftmove;
         * If move.length == 3, then it is a tilemove
         */
        if (move.length() == 4) {
            // do draft move
            char player = move.charAt(0);
            // check player(2 player consequence)
            if (player != 'A' && player != 'B')
                return false;

            char factoryOrCenter = move.charAt(1);
            // check factory or centre
            if (!(factoryOrCenter == 'C' || factoryOrCenter == '0' || factoryOrCenter == '1' || factoryOrCenter == '2' || factoryOrCenter == '3' || factoryOrCenter == '4'))
                return false;

            char tileColor = move.charAt(2);
            // check tile color
            if (!(tileColor == 'a' || tileColor == 'b' || tileColor == 'c' || tileColor == 'd' || tileColor == 'e')) {
                // not a tile of any color
                return false;
            }

            char rowOrFloor = move.charAt(3);
            // check row or floor
            if (!(rowOrFloor == 'F' || rowOrFloor == '0' || rowOrFloor == '1' || rowOrFloor == '2' || rowOrFloor == '3' || rowOrFloor == '4'))
                return false;

            String[] splitSharedState = splitSharedState(gameState); // split the shared state.

            // check whose turn
            if (splitSharedState[0].charAt(0) != player) {
                return false;
            }
            //  1. The specified factory/centre contains at least one tile of the specified colour.
            if (factoryOrCenter == 'C') {
                // if it is centre.
                String centre = splitSharedState[2]; // The 3nd String is the centre state.
                // TODO: interact with class Centre?
                boolean isColorValid = false;
                for (int i = 1; i < centre.length(); i++) {
                    // check whether there's a tile is the specified color.
                    if (centre.charAt(i) == tileColor) {
                        isColorValid = true;
                        break;
                    }
                }
                if (!isColorValid)
                    return false; // no specified tile is found.
                Center c = new Center(); // the center
                c.decode(centre); // decode the center
            } else {
                int num = factoryOrCenter - '0';
                String factory = splitSharedState[1]; // The 2nd String is the factory state.
                // TODO: interact with class storage/ factory?
                Factories fs = new Factories(factory); //  create the factories according to the factory code.
                Tile t = new Tile(tileColor);
                if (fs.getColors(num) == null || !fs.getColors(num).contains(t.getTileType())) {
                    // if the input number of factory is invalid / the factory does not have the tiles of this color.
                    return false;
                }
            }

            // 2. The storage row the tile is being placed in does not already contain a different colour.
            HashMap<String, String[]> splitPlayerState = splitPlayerState(gameState); // get the split player state
            String[] playerState = splitPlayerState.get(String.valueOf(player)); // get the player state from the map("player");
            if (rowOrFloor != 'F') {
                int row = rowOrFloor - '0';
                // storage
                String storage = playerState[2]; // The storage state is stored in the 3rd element.
                // TODO: interact with class Storage?
                Storage s = new Storage();
                s.decode(storage); // decode the state, turn it into the tiles array in class Storage.
                Tile t = new Tile(tileColor); // the tile need to put into the storage
                if (!s.isRowColorSame(t, row)) {
                    // does not have the same color
                    return false;
                } else {
                    // has the same color or this row in storage is empty, then check whether there's enough space to place tiles.
                    if (s.isRowFull(row)) {
                        // the row should not be full.
                        return false;
                    }
                    // TODO: up to here.
                    // 3. The corresponding mosaic row does not already contain a tile of that colour.
                    String mosaic = playerState[1]; // The mosaic state is stored in the 2nd element.
                    NewMosaic m = new NewMosaic(mosaic);
                    ArrayList<TileType> colors = m.rowColorList(row); // get the colors of the row in mosaic.
                    for (TileType color: colors) {
                        if (t.getTileType() == color) {
                            return false; // the row in mosaic has the same color.
                        }
                    }
                }
            }
            return true;
        } else if (move.length() == 3) {
            // do tile move
            char player = move.charAt(0);
            char rowChar = move.charAt(1);
            char colChar = move.charAt(2);

            // check player(2 player consequence)
            if (player != 'A' && player != 'B')
                return false;

            // check row
            if (!(rowChar == '0' || rowChar == '1' || rowChar == '2' || rowChar == '3' || rowChar == '4')) {
                return false;
            }
            int row = rowChar - '0';

            // check column
            if (!(colChar == '0' || colChar == '1' || colChar == '2' || colChar == '3' || colChar == '4' || colChar == 'F')) {
                return false;
            }
            int col = colChar == 'F' ? colChar : colChar - '0';

            // check whose turn
            String[] splitSharedState = splitSharedState(gameState);
            if (player != splitSharedState[0].charAt(0)) {
                return false;
            }

            if (colChar != 'F') {
                // 1. The specified row in the Storage area is full.
                HashMap<String, String[]> splitPlayerState = splitPlayerState(gameState); // split the player state
                String[] playerState = splitPlayerState.get(String.valueOf(player)); // get the player state of this player
                String storage = playerState[2]; // The storage state is stored at the 3rd place.
                Storage s = new Storage();
                s.decode(storage);
                if (!s.isRowFull(row)) {
                    return false; // If this row is not full, return false;
                }
                // 2. The specified column does not already contain a tile of the same colour.
                String mosaic = playerState[1]; // The mosaic state is stored at the 2nd place.
                NewMosaic m = new NewMosaic(mosaic);
                TileType rowColor = s.rowColor(row); // get the color of the tiles in this row of storage.
                ArrayList<TileType> colors = m.colColorList(col);
                for (TileType color : colors) {
                    if (rowColor == null || rowColor == color) {
                        // The row in storage is empty/ the col in mosaic has the same color as this row.
                        return false;
                    }
                }
                // 3. The specified location in the mosaic is empty.
                if (!m.isEmpty(row, col)) {
                    return false;
                }
            } else {
                // 4. If the specified column is 'F', no valid move exists from the specified row into the mosaic.
                // find whether there's a valid move from storage to mosaic
                HashMap<String, String[]> splitPlayerState = splitPlayerState(gameState); // split the player state
                String[] playerState = splitPlayerState.get(String.valueOf(player)); // get the player state of this player
                String storage = playerState[2]; // The storage state is stored at the 3rd place.
                Storage s = new Storage();
                s.decode(storage);
                String mosaic = playerState[1];
                NewMosaic m = new NewMosaic(mosaic);
                // check the row in storage
                if (s.isRowFull(row)) {
                    // this row is full, check the same row in mosaic.
                    TileType colorInStorage = s.rowColor(row); // get the tile color of this row in storage.
                    ArrayList<TileType> rowColorsInMosaic = m.rowColorList(row);
                    // if there's a same color tile in this row in mosaic, then the tilemove of this row in invalid.
                    if (!rowColorsInMosaic.contains(colorInStorage)) {
                        // does not have the same color tile
                        // check whether there's an empty space in this row, and then check whether the columns have the same color tile.
                        for (int j = 0; j < 5; j++) {
                            if (m.isEmpty(row, j)) {
                                ArrayList<TileType> colColorsInMosaic = m.colColorList(j);
                                if (!colColorsInMosaic.contains(colorInStorage)) {
                                    // this column does not contain the same color tile, so this space in mosaic is valid.
                                    // Thus there's valid tile move from storage to mosaic, the move to floor should be invalid.
                                    return false;
                                }
                            }
                        }
                    }
                } else {
                    // TODO: [ask in piazza, case: B0F, why this is false]if the row is not full, return false
                    return false;
                }
            }
            return true;
        } else {
            // invalid move
            return false;
        }
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
        int A = gameState[1].indexOf("A");
        int B = gameState[1].indexOf("B");
        if(move.length() == 3){ //the move is tile move (with 3 character)
            return Tilingmove(gameState,move);
        }
        else {  //the move is tile move (with 4 character)
            return Draftingmove(gameState,move);
        }
    }

    public static String[] Tilingmove(String[] gameState,String move){
        int A = gameState[1].indexOf("A");
        int B = gameState[1].indexOf("B");
        int row = Integer.parseInt(String.valueOf(move.charAt(1)));
        int column = Integer.parseInt(String.valueOf(move.charAt(2)));
        String playerState = "";
        int D = gameState[0].indexOf("D");
        String discard = gameState[0].substring(D);
        if(move.charAt(0)=='A'){
            playerState = gameState[1].substring(A,B);
        }else {
            playerState = gameState[1].substring(B);
        }
        int M = playerState.indexOf("M");
        int S = playerState.indexOf("S");
        int F = playerState.indexOf("F");
        String mosaic = playerState.substring(M,S);
        String storage = playerState.substring(S,F);
        NewMosaic newmosaic = new NewMosaic(mosaic);


        Storage s = new Storage();
        s.decode(storage);
        newmosaic.move(s.charRowColor(row),row,column);  // move the tile from storage to mosaic
        int plusscore = newmosaic.score(row,column);  // find the additional score

        //change the string of discard with additional tiles
        if(s.charRowColor(row) == 'a'){
            int num = Integer.parseInt(discard.substring(1,3));  //find the number of a tile
            String newscore = String.valueOf(num+row);  //plus with new additional tile
            if(num + row < 10){
                newscore = "0" + (num+row);
            }
            discard = "D" + newscore + discard.substring(3);  //restring discard
        }
        if(s.charRowColor(row) == 'b'){
            int num = Integer.parseInt(discard.substring(3,5));
            String newscore = String.valueOf(num+row);
            if(num + row < 10){
                newscore = "0" + (num+row);
            }
            discard = "D" + discard.substring(1,3) + newscore+ discard.substring(5);
        }
        if(s.charRowColor(row) == 'c'){
            int num = Integer.parseInt(discard.substring(5,7));
            String newscore = String.valueOf(num+row);
            if(num + row < 10){
                newscore = "0" + (num+row);
            }
            discard = "D" + discard.substring(1,5) + newscore+ discard.substring(7);
        }
        if(s.charRowColor(row) == 'd'){
            int num = Integer.parseInt(discard.substring(7,9));
            String newscore = String.valueOf(num+row);
            if(num + row < 10){
                newscore = "0" + (num+row);
            }
            discard = "D" + discard.substring(1,7) + newscore+ discard.substring(9);
        }
        if(s.charRowColor(row) == 'e'){
            int num = Integer.parseInt(discard.substring(9,11));
            String newscore = String.valueOf(num+row);
            if(num + row < 10){
                newscore = "0" + (num+row);
            }
            discard = "D" + discard.substring(1,9) + newscore+ discard.substring(11);
        }

        s.emptyRow(row);  //clear storage (the tiles have been moved into mosaic

        if(move.charAt(0)=='A'){
            int score = Integer.parseInt(playerState.substring(1,M));
            gameState[1] = "A"+String.valueOf(score+plusscore) + newmosaic.getCode() + s.getCode() + gameState[1].substring(F);
            String Aplayer = "A"+String.valueOf(score+plusscore) + newmosaic.getCode() + s.getCode() + gameState[1].substring(F,B);
            if(storageIsAvia(Aplayer)){
                //if storage is still have tiles which can be move, round doesnt change
                gameState[0] = gameState[0].substring(0,D) +discard;
            }else {
                //change round if this is no tiles could move
                gameState[0] = "B" + gameState[0].substring(1,D) +discard;
            }
        }else {
            int score = Integer.parseInt(playerState.substring(1,M));
            gameState[1] = gameState[1].substring(0,B) + "B" + String.valueOf(score+plusscore) + newmosaic.getCode() + s.getCode() + playerState.substring(F);
            String Bplayer = "B" + String.valueOf(score+plusscore) + newmosaic.getCode() + s.getCode() + playerState.substring(F);
            if(storageIsAvia(Bplayer)){
                //if storage is still have tiles which can be move, round doesnt change
                gameState[0] = gameState[0].substring(0,D) +discard;
            }else {
                ////change round if this is no tiles could move
                gameState[0] = "A" + gameState[0].substring(1,D) +discard;
            }
        }

        return gameState;
    }

    public static String[] Draftingmove(String[] gameState,String move) {
        String sharedState = gameState[0];
        int A = gameState[1].indexOf("A");
        int B = gameState[1].indexOf("B");
        String playerState = "";
        if (move.charAt(0) == 'A') {
            playerState = gameState[1].substring(A, B);
        } else {
            playerState = gameState[1].substring(B);
        }

        String[] a = splitSharedState(gameState);


        int Fa = sharedState.indexOf("F");
        int Cen = sharedState.indexOf("C");
        int D = sharedState.indexOf("D");
        int S = playerState.indexOf("S");
        int F = playerState.indexOf("F");
        String bag = a[3];       //the code of bag
        String center = a[2];    //the code of center
        String discard = a[4];   //the code of discard
        String storage = playerState.substring(S, F);
        String floor = playerState.substring(F);
        Discard discard1 = new Discard();
        discard1.decode(discard);
        Floor floor1 = new Floor();
        floor1.decode(floor);
        Storage storage1 = new Storage();
        storage1.decode(storage);
        String factoryString = sharedState.substring(Fa, Cen);
        Factories factories = new Factories(factoryString);
        String[] newgameState = new String[2];
        if (Character.isDigit(move.charAt(1))) {
            int numOfFactory = move.charAt(1) - '0';
            String factoryCode = factories.getFactory(numOfFactory).getCode();
            int numOfTiles = factories.getFactory(numOfFactory).tileNum(move.charAt(2));
            Factory factory1 = new Factory();
            factory1.decode(factoryCode);
            String otherTiles = factory1.deleteTile(move.charAt(2));  // remove other tiles from factories
            Center center1 = new Center();

            center1.decode(center);
            center1.addTile(otherTiles);  //add the otherTiles into center

            if (Character.isDigit(move.charAt(3))) {
                /**
                 * factory to storage
                 */
                int numOfStorageEmptySpace = storage1.emptySpace(move.charAt(3) - '0');
                int numOfFloorEmptySpace = floor1.emptyNum();
                storage1.move(move.charAt(2), (move.charAt(3) - '0'), numOfTiles);  //move tiles to storage
                if (numOfTiles > numOfStorageEmptySpace) {  //if storage if full, drop others into floor
                    int moreTile = numOfTiles - numOfStorageEmptySpace;
                    floor1.placeTile(move.charAt(2), moreTile);
                    if (moreTile > numOfFloorEmptySpace) {  // if floor is full, drop others into discard
                        discard1.placeTiles(move.charAt(2), moreTile - numOfFloorEmptySpace);
                    }
                }

                if (gameState[0].charAt(0) == 'A') {
                    factories.clear(move.charAt(1) - '0'); // clear the factories
                    newgameState[0] = "B" + factories.getCode() + sortChar(center1.getCode()) + bag + discard1.getCode();
                    newgameState[1] = playerState.substring(0, S) + storage1.getCode() + sortChar(floor1.getCode()) + gameState[1].substring(B);
                } else {
                    factories.clear(move.charAt(1) - '0');  //clear the factories
                    newgameState[0] = "A" + factories.getCode() + sortChar(center1.getCode()) + bag + discard1.getCode();
                    newgameState[1] = gameState[1].substring(0, B) + playerState.substring(0,S)+ storage1.getCode() + sortChar(floor1.getCode());
                }


            } else {
                /**
                 * factory to floor
                 */
                int numOfFloorEmptySpace = floor1.emptyNum();
                floor1.placeTile(move.charAt(2), numOfTiles);
                if (numOfTiles > numOfFloorEmptySpace) { //if floor is full, drop the tiles to discard
                    int moreTile = numOfTiles - numOfFloorEmptySpace;
                    discard1.placeTiles(move.charAt(2), moreTile); //add other tile to discard
                }
                if (gameState[0].charAt(0) == 'A') {

                    factories.clear(move.charAt(1) - '0'); // clear the factories
                    newgameState[0] = "B" + factories.getCode() + center1.getCode() + bag + discard1.getCode();
                    newgameState[1] = playerState.substring(0, S) + storage1.getCode() + sortChar(floor1.getCode()) + gameState[1].substring(B);
                } else {
                    factories.clear(move.charAt(1) - '0'); // clear the factories
                    newgameState[0] = "A" + factories.getCode() + center1.getCode() + bag + discard1.getCode();
                    newgameState[1] = gameState[1].substring(0, B) + playerState.substring(0, S) + storage1.getCode() + sortChar(floor1.getCode());
                }

            }

        } else {
            Center center1 = new Center();
            center1.decode(center);
            int numOfTiles = center1.tileNum(move.charAt(2));
            if (Character.isDigit(move.charAt(3))) {
                /**
                 * center to storage
                 */
                int numOfEmptyStorage = storage1.emptySpace(move.charAt(3) - '0');
                int numOfFloorEmptySpace = floor1.emptyNum();
                storage1.move(move.charAt(2), (move.charAt(3) - '0'), numOfTiles);

                String center2 = center1.deleteTile(move.charAt(2));
                if(center2.contains("f")){ //if there is a first player in center
                    center2 = center2.substring(0,center2.length()-1);  //drop the 'f' from center
                    floor1.placeTile('f', 1); //add 'f' in floor
                }
                if (numOfTiles > numOfEmptyStorage) { //if storage is full, add other tiles to floor
                    int moreTile = numOfTiles - numOfEmptyStorage;
                    floor1.placeTile(move.charAt(2), moreTile);

                    if (moreTile > numOfFloorEmptySpace) { //if floor is full, add others to discard
                        discard1.placeTiles(move.charAt(2), moreTile - numOfFloorEmptySpace);

                    }
                }
                if (gameState[0].charAt(0) == 'A') {
                    if(factories.getCode().length() == 1 && center2.length() ==1){
                        //if there is not tiles in factories or center, dont change round
                        newgameState[0] = "A" + factories.getCode() + sortChar(center2) + bag + discard1.getCode();
                        newgameState[1] = playerState.substring(0, S)+ storage1.getCode() + sortChar(floor1.getCode()) + gameState[1].substring(B);
                    }
                    else { // if there is, change round
                        newgameState[0] = "B" + factories.getCode() + sortChar(center2) + bag + discard1.getCode();
                        newgameState[1] = playerState.substring(0, S)+ storage1.getCode() + sortChar(floor1.getCode()) + gameState[1].substring(B);
                    }


                } else {
                    if(factories.getCode().length() == 1 && center2.length() ==1){
                        //if there is not tiles in factories or center, dont change round
                        newgameState[0] = "B" + factories.getCode() + sortChar(center2) + bag + discard1.getCode();
                        newgameState[1] = gameState[1].substring(0, B) + playerState.substring(0, S) + storage1.getCode() + sortChar(floor1.getCode());
                    }
                    else {
                        //if there is, change round
                        newgameState[0] = "A" + factories.getCode() + sortChar(center2) + bag + discard1.getCode();
                        newgameState[1] = gameState[1].substring(0, B) + playerState.substring(0, S) + storage1.getCode() + sortChar(floor1.getCode());
                    }

                }


            } else {
                /**
                 * center to floor
                 */
                int numOfFloorEmptySpace = floor1.emptyNum();
                floor1.placeTile(move.charAt(2), numOfTiles);
                String center2 = center1.deleteTile(move.charAt(2)); //delete other tiles from center
                if(center2.contains("f")){ //if there is a first player in center
                    center2 = center1.getCode().substring(0,center2.length()-1); //remove 'f' from center
                    floor1.placeTile('f', 1); // add 'f' to floor
                    if (numOfTiles >= numOfFloorEmptySpace) { //if floor is full
                        int moreTile = numOfTiles - numOfFloorEmptySpace;
                        discard1.placeTiles(move.charAt(2), moreTile+1); //add other tiles to discard
                        String f = sortChar(floor1.getCode()); //sort the string
                        discard1.replaceTile(f.charAt(f.length()-2)); //change 'f' and the last tile in floor
                        floor1.replaceTile('f');
                    }
                }
                else {
                    if (numOfTiles > numOfFloorEmptySpace) {
                        //if floor is full add to discard
                        int moreTile = numOfTiles - numOfFloorEmptySpace;
                        discard1.placeTiles(move.charAt(2), moreTile - numOfFloorEmptySpace);
                    }
                }


                if (gameState[0].charAt(0) == 'A') {
                    if(factories.getCode().length() == 1 && center2.length() ==1){
                        newgameState[0] = "A" + factories.getCode() + sortChar(center2) + bag + discard1.getCode();
                        newgameState[1] = playerState.substring(0, S) + storage1.getCode() + sortChar(floor1.getCode()) + gameState[1].substring(B);
                    }
                    else {
                        newgameState[0] = "B" + factories.getCode() + sortChar(center2) + bag + discard1.getCode();
                        newgameState[1] = playerState.substring(0, S) + storage1.getCode() + sortChar(floor1.getCode()) + gameState[1].substring(B);
                    }

                } else {
                    if(factories.getCode().length() == 1 && center2.length() ==1){
                        newgameState[0] = "B" + factories.getCode() + center2 + bag + discard1.getCode();
                        newgameState[1] = gameState[1].substring(0, B) + playerState.substring(0, S) + storage1.getCode() + sortChar(floor1.getCode());
                    }
                    else {
                        newgameState[0] = "A" + factories.getCode() + center2 + bag + discard1.getCode();
                        newgameState[1] = gameState[1].substring(0, B) + playerState.substring(0, S) + storage1.getCode() + sortChar(floor1.getCode());
                    }

                }

            }
        }
        return newgameState;
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


    /**
     * Given a valid game state, return a valid move.
     *
     * @param gameState the game state
     * @return a move for the current game state.
     * TASK 13
     */
    public static String generateAction(String[] gameState) {
        // FIXME Task 13
        String[] sharedState = splitSharedState(gameState); // split the shared state
        HashMap<String, String[]> playerStateMap = splitPlayerState(gameState); // split the player state
        String player = whoseTurn(gameState); // get whose turn.
        String[] playerState = playerStateMap.get(player); // get this player's state.
        Random rand = new Random();

        /*  find out if factories and center are empty  */
        //  check whether all factories are empty
        String factories = sharedState[1]; // the factories state is in the 2nd String.
        Factories fac = new Factories(factories); // decode factories.
        //  check whether center is empty.
        String center = sharedState[2]; // the center state is in the 3rd String.
        Center cen = new Center(center); // decode center.
        // if factories and center are empty, make tile move.
        if (fac.isEmpty() && cen.isEmpty()) {
            /*  find out possible tileMove  */
            // find the full row in storage.
            String storage = playerState[2]; // storage state is placed in the 3rd place.
            Storage s = new Storage(storage); // decode storage.
            ArrayList<String> possibleTileMoves = new ArrayList<>();
            for (int i = 0; i < Storage.STORAGE_ROW_NUM; i++) {
                // check each row in storage, if a row is full, find possible place in mosaic to place tiles.
                if (s.isRowFull(i)) {
                    String mosaic = playerState[1]; // mosaic state is placed in the 2nd place.
                    NewMosaic mos = new NewMosaic(mosaic); // decode mosaic
                    boolean doFindAPlaceInMosaic = false;
                    for (int j = 0; j < NewMosaic.MOSAIC_WIDTH; j++) {
                        if (mos.isPlaceValid(new Tile(s.rowColor(i)), i, j)) {
                            String newTileMove = player + i + j; // encode tile move.
                            possibleTileMoves.add(newTileMove); // add the new tile move to possible tile move list.
                            doFindAPlaceInMosaic = true;
                        }
                    }
                    if (!doFindAPlaceInMosaic) {
                        // if there's no valid tile move from this row in storage, then move those tiles to the floor.
                        String newTileMove = player + i + "F";
                        possibleTileMoves.add(newTileMove);
                    } else {
                        // if do find one, make the tileMoves for this row.
                        return possibleTileMoves.get(rand.nextInt(possibleTileMoves.size()));
                    }
                }
            }
            return possibleTileMoves.get(rand.nextInt(possibleTileMoves.size())); // randomly choose a move from valid moves.
        } else {
            /*  find out possible draft move  */
            String storage = playerState[2];
            String mosaic = playerState[1];
            Storage s = new Storage(storage);
            NewMosaic mos = new NewMosaic(mosaic);
            ArrayList<String> possibleDraftMove = new ArrayList<>();
            // find possible draft moves from factories.
            for (int i = 0; i < Factories.factoryNum; i++) {
                // skip those empty factories.
                if (!fac.isEmpty(i)) {
                    // i-th factory is not empty.
                    ArrayList<TileType> colors = fac.getColors(i); // get the colors from factory[i]
                    for (TileType color: colors) {
                        // for each color in this factory, find possible draftMove
                        boolean hasValidMoveToStorage = false;
                        for (int j = 0; j < Storage.STORAGE_ROW_NUM; j++) {
                            // check each row in storage
                            if (!s.isRowEmpty(j)) {
                                // if j-th row in storage is not empty, find if there's a color conflict.
                                if (!s.isRowFull(j) && s.rowColor(j) == color) {
                                    // no color conflict in storage, then check if there's a color conflict in mosaic.
                                    if (!mos.isColorExisted(j, color)) {
                                        // no color conflict in mosaic, then this move is valid.
                                        String newDraftMove = player + i + Tile.tileType2colorChar(color) + j;
                                        hasValidMoveToStorage = true;
                                        possibleDraftMove.add(newDraftMove);
                                    }
                                }
                            } else {
                                // if j-th row in storage is empty, then just check the mosaic.
                                if (!mos.isColorExisted(j, color)) {
                                    // no color conflict in mosaic, then this move is valid.
                                    String newDraftMove = player + i + Tile.tileType2colorChar(color) + j;
                                    hasValidMoveToStorage = true;
                                    possibleDraftMove.add(newDraftMove);
                                }
                            }
                        }
                        if (!hasValidMoveToStorage) {
                            // if no valid move to storage, then move the tiles to floor.
                            String newDraftMove = player + i + Tile.tileType2colorChar(color) + "F";
                            possibleDraftMove.add(newDraftMove);
                        }
                    }
                }
            }

            // find possible draft moves from center.
            HashSet<TileType> colors = cen.getColors(); // list out the colors in the center.
            for (TileType color: colors) {
                if (color == TileType.FirstPlayer) {
                    // Only the move to floor is valid.
                    String newDraftMove = player + "C" + Tile.tileType2colorChar(color) + "F";
                    possibleDraftMove.add(newDraftMove);
                } else {
                    // for each color except first player, find valid moves.
                    boolean hasValidMoveToStorage = false;
                    for (int j = 0; j < Storage.STORAGE_ROW_NUM; j++) {
                        // check each row in storage
                        if (!s.isRowEmpty(j)) {
                            // if j-th row in storage is not empty, find if there's a color conflict.
                            if (!s.isRowFull(j) && s.rowColor(j) == color) {
                                // no color conflict in storage, then check if there's a color conflict in mosaic.
                                if (!mos.isColorExisted(j, color)) {
                                    // no color conflict in mosaic, then this move is valid.
                                    String newDraftMove = player + "C" + Tile.tileType2colorChar(color) + j;
                                    hasValidMoveToStorage = true;
                                    possibleDraftMove.add(newDraftMove);
                                }
                            }
                        } else {
                            // if j-th row in storage is empty, then just check the mosaic.
                            if (!mos.isColorExisted(j, color)) {
                                // no color conflict in mosaic, then this move is valid.
                                String newDraftMove = player + "C" + Tile.tileType2colorChar(color) + j;
                                hasValidMoveToStorage = true;
                                possibleDraftMove.add(newDraftMove);
                            }
                        }
                    }
                    if (!hasValidMoveToStorage) {
                        // if no valid move to storage, then move the tiles to floor.
                        String newDraftMove = player + "C" + Tile.tileType2colorChar(color) + "F";
                        possibleDraftMove.add(newDraftMove);
                    }
                }
            }
            if (possibleDraftMove.size() > 1) {
                String move = possibleDraftMove.get(rand.nextInt(possibleDraftMove.size()));
                while (move.equals("ACfF") || move.equals("BCfF")) {
                    // nobody would act like this while there are other options. So, repick the move.
                    move = possibleDraftMove.get(rand.nextInt(possibleDraftMove.size()));
                }
                return move;
            }
            return possibleDraftMove.get(rand.nextInt(possibleDraftMove.size()));
        }
        // FIXME Task 15 Implement a "smart" generateAction()
    }
    public static String whoseTurn(String[] gameState) {
        // Assuming that the input gameState is valid.
        return gameState[0].substring(0, 1); // return the first character(String) of shared state.
    }

    /**
     * Find out whether the center and factory are empty.
     *     If they are empty, then only tileMoves are available.
     * @param gameState the current game state.
     * @return whether the center and factory are empty.
     */
    public static boolean isCenterAndFactoriesEmpty(String[] gameState) {
        String[] sharedState = splitSharedState(gameState);
        //  check whether all factories are empty
        String factories = sharedState[1]; // the factories state is in the 2nd String.
        Factories fac = new Factories(factories); // decode factories.
        //  check whether center is empty.
        String center = sharedState[2]; // the center state is in the 3rd String.
        Center cen = new Center(center); // decode center.
        return fac.isEmpty() && cen.isEmpty();
    }

    /**
     * Generate all the possible moves.
     * @param gameState current game state
     * @return the list of possible moves.
     */
    public static ArrayList<String> generateMoves(String[] gameState) {
        // TODO: For Task 15: need to return all the possible moves.
        String[] sharedState = splitSharedState(gameState); // split the shared state
        HashMap<String, String[]> playerStateMap = splitPlayerState(gameState); // split the player state
        String player = whoseTurn(gameState); // get whose turn.
        String[] playerState = playerStateMap.get(player); // get this player's state.

        /*  find out if factories and center are empty  */
        //  check whether all factories are empty
        String factories = sharedState[1]; // the factories state is in the 2nd String.
        Factories fac = new Factories(factories); // decode factories.
        //  check whether center is empty.
        String center = sharedState[2]; // the center state is in the 3rd String.
        Center cen = new Center(center); // decode center.
        // if factories and center are empty, make tile move.
        if (fac.isEmpty() && cen.isEmpty()) {
            /*  find out possible tileMove  */
            // find the full row in storage.
            String storage = playerState[2]; // storage state is placed in the 3rd place.
            Storage s = new Storage(storage); // decode storage.
            ArrayList<String> possibleTileMoves = new ArrayList<>();
            for (int i = 0; i < Storage.STORAGE_ROW_NUM; i++) {
                // check each row in storage, if a row is full, find possible place in mosaic to place tiles.
                if (s.isRowFull(i)) {
                    String mosaic = playerState[1]; // mosaic state is placed in the 2nd place.
                    NewMosaic mos = new NewMosaic(mosaic); // decode mosaic
                    boolean doFindAPlaceInMosaic = false;
                    for (int j = 0; j < NewMosaic.MOSAIC_WIDTH; j++) {
                        if (mos.isPlaceValid(new Tile(s.rowColor(i)), i, j)) {
                            String newTileMove = player + i + j; // encode tile move.
                            possibleTileMoves.add(newTileMove); // add the new tile move to possible tile move list.
                            doFindAPlaceInMosaic = true;
                        }
                    }
                    if (!doFindAPlaceInMosaic) {
                        // if there's no valid tile move from this row in storage, then move those tiles to the floor.
                        String newTileMove = player + i + "F";
                        possibleTileMoves.add(newTileMove);
                    } else {
                        // if do find one, make the tileMoves for this row.
                        return possibleTileMoves;
                    }
                }
            }
            return possibleTileMoves; // return all tile moves.
        } else {
            /*  find out possible draft move  */
            String storage = playerState[2];
            String mosaic = playerState[1];
            Storage s = new Storage(storage);
            NewMosaic mos = new NewMosaic(mosaic);
            ArrayList<String> possibleDraftMove = new ArrayList<>();
            // find possible draft moves from factories.
            for (int i = 0; i < Factories.factoryNum; i++) {
                // skip those empty factories.
                if (!fac.isEmpty(i)) {
                    // i-th factory is not empty.
                    ArrayList<TileType> colors = fac.getColors(i); // get the colors from factory[i]
                    for (TileType color: colors) {
                        // for each color in this factory, find possible draftMove
                        boolean hasValidMoveToStorage = false;
                        for (int j = 0; j < Storage.STORAGE_ROW_NUM; j++) {
                            // check each row in storage
                            if (!s.isRowEmpty(j)) {
                                // if j-th row in storage is not empty, find if there's a color conflict.
                                if (!s.isRowFull(j) && s.rowColor(j) == color) {
                                    // no color conflict in storage, then check if there's a color conflict in mosaic.
                                    if (!mos.isColorExisted(j, color)) {
                                        // no color conflict in mosaic, then this move is valid.
                                        String newDraftMove = player + i + Tile.tileType2colorChar(color) + j;
                                        hasValidMoveToStorage = true;
                                        possibleDraftMove.add(newDraftMove);
                                    }
                                }
                            } else {
                                // if j-th row in storage is empty, then just check the mosaic.
                                if (!mos.isColorExisted(j, color)) {
                                    // no color conflict in mosaic, then this move is valid.
                                    String newDraftMove = player + i + Tile.tileType2colorChar(color) + j;
                                    hasValidMoveToStorage = true;
                                    possibleDraftMove.add(newDraftMove);
                                }
                            }
                        }
                        if (!hasValidMoveToStorage) {
                            // if no valid move to storage, then move the tiles to floor.
                            String newDraftMove = player + i + Tile.tileType2colorChar(color) + "F";
                            possibleDraftMove.add(newDraftMove);
                        }
                    }
                }
            }

            // find possible draft moves from center.
            HashSet<TileType> colors = cen.getColors(); // list out the colors in the center.
            for (TileType color: colors) {
                if (color == TileType.FirstPlayer) {
                    // Only the move to floor is valid.
                    String newDraftMove = player + "C" + Tile.tileType2colorChar(color) + "F";
                    possibleDraftMove.add(newDraftMove);
                } else {
                    // for each color except first player, find valid moves.
                    boolean hasValidMoveToStorage = false;
                    for (int j = 0; j < Storage.STORAGE_ROW_NUM; j++) {
                        // check each row in storage
                        if (!s.isRowEmpty(j)) {
                            // if j-th row in storage is not empty, find if there's a color conflict.
                            if (!s.isRowFull(j) && s.rowColor(j) == color) {
                                // no color conflict in storage, then check if there's a color conflict in mosaic.
                                if (!mos.isColorExisted(j, color)) {
                                    // no color conflict in mosaic, then this move is valid.
                                    String newDraftMove = player + "C" + Tile.tileType2colorChar(color) + j;
                                    hasValidMoveToStorage = true;
                                    possibleDraftMove.add(newDraftMove);
                                }
                            }
                        } else {
                            // if j-th row in storage is empty, then just check the mosaic.
                            if (!mos.isColorExisted(j, color)) {
                                // no color conflict in mosaic, then this move is valid.
                                String newDraftMove = player + "C" + Tile.tileType2colorChar(color) + j;
                                hasValidMoveToStorage = true;
                                possibleDraftMove.add(newDraftMove);
                            }
                        }
                    }
                    if (!hasValidMoveToStorage) {
                        // if no valid move to storage, then move the tiles to floor.
                        String newDraftMove = player + "C" + Tile.tileType2colorChar(color) + "F";
                        possibleDraftMove.add(newDraftMove);
                    }
                }
            }
            return possibleDraftMove;
        }
    }

    /**
     *
     * @param gameState
     * @return return true if there is one row of mosaic in A or B.
     */
    public boolean isGameEnd (String[] gameState) {
        HashMap<String, String[]> splitPlayerState = splitPlayerState(gameState);
        String mosaicA = splitPlayerState.get("A")[1]; // mosaic for playerA
        String mosaicB = splitPlayerState.get("B")[1]; // mosaic for playerB
        NewMosaic newMosaicA = new NewMosaic();
        newMosaicA.decode(mosaicA);
        NewMosaic newMosaicB = new NewMosaic();
        newMosaicB.decode(mosaicB);
        return ((!newMosaicA.isRowFull()) && (!newMosaicB.isRowFull())); //return true if there is no row that is full.
    }
    public static void main(String[] args) {
        /*
        // Test case for task 9
        String[] test = new String[] {
                "BFCB1412141614D0000000000",
                "A0MS0a11c22a33c44b5FB0MS0e11a22b33d44e5Ff"
        };
        System.out.println(isStateValid(test));
        // Test case 1 for task 13
        String[] test1 = new String[] {
                "BFCbbbccdddddeeB1913161418D0000000000",
                "A0MS0b11b23d1FbfB0MS0c11a13c1F"
        };
        System.out.println(generateAction(test1));
        System.out.println(isMoveValid(test1, "BCb4"));

        // Test case 2 for Task 13
        String[] test2 = new String[] {
                "AFCbbbB1913161418D0000000000",
                "A0MS0b11b23d14c2FbfB0MS0c11a12d33c14e2Fdd"
        };
        System.out.println(isMoveValid(test2, "ACb0"));


         */
        // test case 3 for Task 13
        String[] test3 = new String[] {
                "BF4aeeeCaacB1109101008D0005000200",
                "A7Mc03b04b12e13b21a24S0d11a12e13d44c4FdfB0Md01c02d20S0b11a22e23c34e5Faabb"
        };
        System.out.println(isMoveValid(test3, "B4a1"));
    }
        /*
        String[] testSplit = new String[]{
                "AFCB1915161614D0000000000",
                "A0MS0d11c22b33e44e1FefB0MS0a11b22d33c2FC0MS0d11c22b33e44e1FefD0MS0a11b22d33c2F"
        };
        // split player state
        HashMap<String, String[]> splitPlayerStates = splitPlayerState(testSplit);
        // visit A's floor state
        String floor = splitPlayerStates.get("A")[3]; // {Score}{Mosaic}{Storage}{Floor}
        System.out.println(floor);
        String[] gameState = {"BFCB1412141614D0000000000", "A0MS0a11c22a33c44b5FB0MS0e11a22b33d44e5Ff"};
        System.out.println(isStateValid(gameState));

         */
    /*
    public static void main(String[] args) {
        String[] testSplit = new String[]{
                "AFCB1915161614D0000000000",
                "A0MS0d11c22b33e44e1FefB0MS0a11b22d33c2FC0MS0d11c22b33e44e1FefD0MS0a11b22d33c2F"
        };
        System.out.println("Test: ");
        for (String s: testSplit) {
            System.out.println(s);
        }
        System.out.println("After split: ");
        // split shared state
        String[] sharedStates = splitSharedState(testSplit);
        System.out.print("Shared state: [");
        for (String sharedState : sharedStates) {
            System.out.print(sharedState + ", ");
        }
        System.out.println("]");
        // split player state
        HashMap<String, String[]> playerStates = splitPlayerState(testSplit);
        System.out.println("Player state: ");
        playerStates.forEach((player, states) -> {
            System.out.print("Player " + player + ": ");
            for (String s: states) {
                System.out.print(s + " ");
            }
            System.out.println();
        });
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
