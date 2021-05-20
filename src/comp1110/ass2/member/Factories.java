package comp1110.ass2.member;

import java.util.ArrayList;

/**
 * @author Ruizheng Shen
 * @since 2021.4.29
 */
public class Factories {
    public static int factoryNum;
    private Factory[] factories;


    /**
     * Constructor: decide the number of factories based on player numbers.
     *
     * @param playerNum the player number
     */
    public Factories(int playerNum) {
        /*  The number of factories depends on the number of players.
         *      * 2 players: 5 factories
         *      * 3 players: 7 factories
         *      * 4 players: 9 factories
         */
        switch (playerNum) {
            case 2:
                factoryNum = 5;
                factories = new Factory[factoryNum];
            case 3:
                factoryNum = 7;
                factories = new Factory[factoryNum];
            case 4:
                factoryNum = 9;
                factories = new Factory[factoryNum];
            default:
                factoryNum = 5;
                factories = new Factory[factoryNum]; // set the factory number as 5 as default.
        }
        for (int i = 0; i < factoryNum; i++) {
            factories[i] = new Factory();
        }
    }

    /**
     * Constructor: default constructor. 5 factories.
     */
    public Factories() {
        factoryNum = 5;
        factories = new Factory[factoryNum];
        for (int i = 0; i < factoryNum; i++) {
            factories[i] = new Factory();
        }
    }

    /**
     * Constructor: create factories by factory state code
     *
     * @param factory the factory state
     */
    public Factories(String factory) {
        factoryNum = 5;
        this.factories = new Factory[factoryNum];
        for (int i = 0; i < factoryNum; i++) {
            this.factories[i] = new Factory();
        }
        decode(factory);
    }


    /**
     * encode the current state in factories.
     *
     * @return factory code
     */
    private String encode() {
        StringBuilder code = new StringBuilder("F");
        for (int i = 0; i < factoryNum; i++) {
            if (!this.factories[i].isEmpty()) {
                code.append(i).append(this.factories[i].getCode());
            }
        }
        return code.toString();
    }

    /**
     * the state code visitor
     *
     * @return the state code of factory
     */
    public String getCode() {
        return encode();
    }

    /**
     * decode the factory state to tiles in factories,(default player number: 2)
     *
     * @param factory the factory state
     * @return return false if the code invalid
     */
    public void decode(String factory) {
        String[] split = splitFactoryState(factory); //  get the split factory code.
        for (int i = 0; i < factoryNum; i++) {
            this.factories[i].decode(split[i]);
        }
    }

    /**
     * Split the factory state into each factory.
     *
     * @param factory split the factory state
     * @return split factory state
     */
    public String[] splitFactoryState(String factory) {
        // Assume that the factory state is valid.
        int start = -1;
        String[] split = new String[factoryNum];
        for (int i = 0; i < factory.length(); i++) {
            if (Character.isDigit(factory.charAt(i))) {
                // If this index contains a digit/ it reach the last character
                if (start < 0) {
                    start = i; // find the first digit.
                } else {
                    // find the end of a single factory state.
                    int num = factory.charAt(start) - '0';
                    split[num] = factory.substring(start + 1, i);
                    start = i; // set the start to the new digit.
                }
            }
            if (i == factory.length() - 1) {
                // if reach the last character.
                if (start < 0) {
                    // no digit is found, the factory will not contain tiles.
                    break;
                }
                int num = factory.charAt(start) - '0';
                split[num] = factory.substring(start + 1);
            }
        }
        return split;
    }

    /**
     * return the color of factory num in ArrayList<TileType>
     *
     * @param num the number of tiles of that color.
     * @return colors
     */
    public ArrayList<TileType> getColors(int num) {
        if (num >= factoryNum) {
            // If the number input is larger than the factory numbers, the input is invalid.
            return (ArrayList<TileType>) null;
        } else {
            return this.factories[num].getColors();
        }
    }

    /**
     * draft tiles from factory according to color.
     *
     * @param color the color which player wants to draft.
     * @param num   draft from factory[num].
     * @return tiles
     */
    public Tile[] draftTile(int num, TileType color) {
        return factories[num].draftTile(color);
    }

    /**
     * clear factory[num]
     *
     * @param num the index of factory
     */
    public void clear(int num) {
        this.factories[num].clear();
    }

    /**
     * Determine whether all of these factories are empty.
     *
     * @return whether all of these factories are empty.
     */
    public boolean isEmpty() {
        for (Factory f : this.factories) {
            if (!f.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Given an index, determine whether Factory[index] is empty.
     *
     * @param index the index of the factory
     * @return whether Factory[index] is empty.
     */
    public boolean isEmpty(int index) {
        return this.factories[index].isEmpty();
    }

    public Factory getFactory(int index) {
        return factories[index];
    }

    public int tileNum(char color, int num) {
        return this.factories[num].tileNum(color);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Factories: \n");
        for (int i = 0; i < factoryNum; i++) {
            s.append("factory ").append(i).append(": ").append(this.factories[i]).append("\n");
        }
        return s.toString();
    }
}
