package comp1110.ass2.member;

import gittest.A;
import javafx.scene.Group;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author Ruizheng Shen
 * @since 2021.4.29
 */
public class Factories {
    private static int factoryNum;
    private Factory[] factories;

    /* Members for javafx */
    private final Group factoryView = new Group();

    /**
     * Constructor: decide the number of factories based on player numbers.
     * @param playerNum
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
     * @param factory
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
    public void decode(String factory) {
        // TODO: check validity.
        String[] split = splitFactoryState(factory); //  get the split factory code.
        for (int i = 0; i < factoryNum; i++) {
            this.factories[i].decode(split[i]);
        }
    }

    /**
     * Split the factory state into each factory.
     * @param factory
     * @return split factory state
     */
    String[] splitFactoryState(String factory) {
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
     * @param num
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

    public Factory getFactory(int num){
        return factories[num];
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Factories: \n");
        for (int i = 0; i < factoryNum; i++) {
            s.append("factory " + i + ": ").append(this.factories[i]).append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        // test case 1, test decode(), normal case
        Factories fs1 = new Factories("F0abbe2ccdd");
        System.out.println(fs1);
        // test case 2, test decode(), empty case
        Factories fsEmpty = new Factories("F");
        System.out.println(fsEmpty);
        // test case 3, test decode(), edging case
        Factories fsEdge1 = new Factories("F4dddd");
        System.out.println(fsEdge1);
        // test case 4, test decode() & getColors(), normal case
        Factories fs2 = new Factories("F0cdde1bbbe2abde3cdee4bcce");
        System.out.println(fs2);
        for (int i = 0; i < 5; i++) {
            // factory 0 - 4
            ArrayList<TileType> colors = fs2.getColors(i);
            colors.forEach(t -> {
                System.out.print(t + " ");
            });
            System.out.println();
        }
    }
}
