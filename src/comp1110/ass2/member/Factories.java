package comp1110.ass2.member;

import java.util.ArrayList;

/**
 * @author Ruizheng Shen
 * @since 2021.4.29
 */
public class Factories {
    private static int factoryNum;
    private Factory[] factories;

    /**
     * Constructor: decide the number of factories based on player numbers.
     * @param playerNum
     */
    Factories(int playerNum) {
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
    Factories() {
        factoryNum = 5;
        factories = new Factory[factoryNum];
        for (int i = 0; i < factoryNum; i++) {
            factories[i] = new Factory();
        }
    }

    /**
     * Constructor: create factories by factory state code
     * @param factory
     */
    Factories(String factory) {
        factoryNum = 5;
        this.factories = new Factory[factoryNum];
        for (int i = 0; i < factoryNum; i++) {
            this.factories[i] = new Factory();
        }
        decode(factory);
    }

    /**
     * encode the current state in factories.
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
     * @return the state code of factory
     */
    public String getCode() {
        return encode();
    }

    /**
     * decode the factory state to tiles in factories,(default player number: 2)
     * @param factory
     * @return return false if the code invalid
     */
    public boolean decode(String factory) {
        char sign = factory.charAt(0);
        if (sign != 'F') return false; // if the first char is not 'F', the code is invalid.
        // find the number inside the factory string
        int pointer = 1;
        while (pointer < factory.length()) {
            if ('0' <= factory.charAt(pointer) && factory.charAt(pointer) <= '9') {
                int num = factory.charAt(pointer) - '0';
                pointer++;
                int end = num + 1;
                while (end < factory.length()) {
                    if (end < 'a' || end > 'z') {
                        break;
                    }
                    end++;
                }
                this.factories[num].decode(factory.substring(pointer, end));
                pointer = end;
            } else {
                // this char is not a number, return false;
                return false;
            }
        }
        return true;
    }
}
