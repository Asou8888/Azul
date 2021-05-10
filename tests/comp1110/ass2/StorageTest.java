package comp1110.ass2;

import comp1110.ass2.member.Storage;
import comp1110.ass2.member.Tile;
import comp1110.ass2.member.TileType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit Test class for Storage
 * Following Methods need to be tested.
 *  *      1. getCode(), encode()
 *  *      2. decode(), using viewTest
 *  *      3. placeTiles(), isPlaceValid()
 *  *      4. isRowFull(), isRowEmpty()
 *  *      5. rowColor()
 *  *      6. emptyRow()
 *  *      7. emptySpace()
 *  *      8. move()
 */
public class StorageTest {

    private static final int STORAGE_ROW_NUM = 5;

    // Normal test cases
    private Storage[] createNormalCases() {
        Storage[] cases = new Storage[5]; // 5 normal cases
        cases[0] = new Storage(new Tile[][]{
                new Tile[]{new Tile(TileType.Red)},
                new Tile[]{null, new Tile(TileType.Green)},
                new Tile[]{new Tile(TileType.Blue), new Tile(TileType.Blue), new Tile(TileType.Blue)},
                new Tile[]{null, null, new Tile(TileType.Purple), new Tile(TileType.Purple)},
                new Tile[]{new Tile(TileType.Orange), new Tile(TileType.Orange), new Tile(TileType.Orange), new Tile(TileType.Orange), new Tile(TileType.Orange)}
        });
        cases[1] = new Storage(new Tile[][]{
                new Tile[]{null},
                new Tile[]{new Tile(TileType.Green), new Tile(TileType.Green)},
                new Tile[]{new Tile(TileType.Blue), new Tile(TileType.Blue), new Tile(TileType.Blue)},
                new Tile[]{null, null, null, null},
                new Tile[]{null, null, null, null, null}
        });
        cases[2] = new Storage(new Tile[][]{
                new Tile[]{null},
                new Tile[]{null, new Tile(TileType.Red)},
                new Tile[]{null, null, new Tile(TileType.Green)},
                new Tile[]{null, null, null, null},
                new Tile[]{null, null, new Tile(TileType.Blue), new Tile(TileType.Blue), new Tile(TileType.Blue)}
        });
        cases[3] = new Storage(new Tile[][]{
                new Tile[]{new Tile(TileType.Orange)},
                new Tile[]{new Tile(TileType.Red), new Tile(TileType.Red)},
                new Tile[]{null, new Tile(TileType.Blue), new Tile(TileType.Blue)},
                new Tile[]{null, null, new Tile(TileType.Purple), new Tile(TileType.Purple)},
                new Tile[]{null, null, null, null, new Tile(TileType.Green)}
        });
        cases[4] = new Storage(new Tile[][]{
                new Tile[]{null},
                new Tile[]{null, null},
                new Tile[]{null, null, null},
                new Tile[]{new Tile(TileType.Green), new Tile(TileType.Green), new Tile(TileType.Green), new Tile(TileType.Green)},
                new Tile[]{new Tile(TileType.Purple), new Tile(TileType.Purple), new Tile(TileType.Purple), new Tile(TileType.Purple), new Tile(TileType.Purple)}
        });
        return cases;
    }
    private String[] expectedNormalCodes() {
        return new String[]{
                "S0e11b12a33d24c5",
                "S1b22a3",
                "S1e12b14a3",
                "S0c11e22a23d24b1",
                "S3b44d5"
        };
    }
    private boolean[][] expectedRowFull() {
        return new boolean[][]{
                new boolean[]{true, false, true, false, true},
                new boolean[]{false, true, true, false, false},
                new boolean[]{false, false, false, false, false},
                new boolean[]{true, true, false, false, false},
                new boolean[]{false, false, false, true, true}
        };
    }
    private boolean[][] expectedRowEmpty() {
        return new boolean[][]{
                new boolean[]{false, false, false, false, false},
                new boolean[]{true, false, false, true, true},
                new boolean[]{true, false, false, true, false},
                new boolean[]{false, false, false, false, false},
                new boolean[]{true, true, true, false, false}
        };
    }
    private TileType[][] expectedRowColor() {
        return new TileType[][]{
                new TileType[]{TileType.Red, TileType.Green, TileType.Blue, TileType.Purple, TileType.Orange},
                new TileType[]{null, TileType.Green, TileType.Blue, null, null},
                new TileType[]{null, TileType.Red, TileType.Green, null, TileType.Blue},
                new TileType[]{TileType.Orange, TileType.Red, TileType.Blue, TileType.Purple, TileType.Green},
                new TileType[]{null, null, null, TileType.Green, TileType.Purple}
        };
    }
    private Tile[][] createValidTileListToBePlaced() {
        return new Tile[][]{
                new Tile[]{new Tile(TileType.Green)}, // place in cases[0], row 1
                new Tile[]{new Tile(TileType.Purple)}, // place in cases[1], row 0
                new Tile[]{new Tile(TileType.Blue), new Tile(TileType.Blue)}, // place in cases[2], row 4
                new Tile[]{new Tile(TileType.Green), new Tile(TileType.Green)}, // place in cases[3], row 4
                new Tile[]{new Tile(TileType.Blue), new Tile(TileType.Blue)} // place in cases[4], row 2
        };
    }
    private Tile[][] createInvalidTileListToBePlaced() {
        return new Tile[][]{
                new Tile[]{new Tile(TileType.Red)}, // place in cases[0], row 0(already full)
                new Tile[]{new Tile(TileType.Red), new Tile(TileType.Red)}, // place in cases[1], row 0(this row is too short)
                new Tile[]{new Tile(TileType.Red), new Tile(TileType.Red)}, // place in cases[2], row 1(remain space in not enough)
                new Tile[]{new Tile(TileType.Green)}, // place in cases[3], row 3(color conflict)
                new Tile[]{new Tile(TileType.Red)} // place in cases[4], row 2(color conflict)
        };
    }

    // empty cases
    private Storage[] createEmptyCases() {
        Storage[] cases = new Storage[3];
        cases[0] = new Storage(new Tile[][]{
                new Tile[]{null},
                new Tile[]{null, null},
                new Tile[]{null, null, null},
                new Tile[]{null, null, null, null},
                new Tile[]{null, null, null, null, null},
        });
        cases[1] = new Storage(new Tile[][]{
                new Tile[]{null},
                new Tile[]{new Tile(TileType.Green), new Tile(TileType.Green)},
                new Tile[]{null, null, null},
                new Tile[]{null, null, null, null},
                new Tile[]{null, null, null, null, null},
        });
        cases[2] = new Storage(new Tile[][]{
                new Tile[]{new Tile(TileType.Purple)},
                new Tile[]{null, null},
                new Tile[]{null, null, null},
                new Tile[]{null, null, null, null},
                new Tile[]{null, null, null, null, null},
        });
        return cases;
    }
    private String[] expectedEmptyCodes() {
        return new String[]{
                "S",
                "S1b2",
                "S0d1"
        };
    }

    // full cases
    private Storage[] createFullCases() {
        Storage[] cases = new Storage[2];
        cases[0] = new Storage(new Tile[][]{
                new Tile[]{new Tile(TileType.Purple)},
                new Tile[]{new Tile(TileType.Green), new Tile(TileType.Green)},
                new Tile[]{new Tile(TileType.Red), new Tile(TileType.Red), new Tile(TileType.Red)},
                new Tile[]{new Tile(TileType.Blue), new Tile(TileType.Blue), new Tile(TileType.Blue), new Tile(TileType.Blue)},
                new Tile[]{new Tile(TileType.Orange), new Tile(TileType.Orange), new Tile(TileType.Orange), new Tile(TileType.Orange), new Tile(TileType.Orange)}
        });
        cases[1] = new Storage(new Tile[][]{
                new Tile[]{new Tile(TileType.Orange)},
                new Tile[]{new Tile(TileType.Blue), new Tile(TileType.Blue)},
                new Tile[]{new Tile(TileType.Red), new Tile(TileType.Red), new Tile(TileType.Red)},
                new Tile[]{new Tile(TileType.Purple), new Tile(TileType.Purple), new Tile(TileType.Purple), new Tile(TileType.Purple)},
                new Tile[]{new Tile(TileType.Green), new Tile(TileType.Green), new Tile(TileType.Green), new Tile(TileType.Green), new Tile(TileType.Green)}
        });
        return cases;
    }
    private String[] expectedFullCodes() {
        return new String[]{
                "S0d11b22e33a44c5",
                "S0c11a22e33d44b5"
        };
    }

    /**
     * Test the isRowFull method.
     */
    @Test
    public void testIsRowFull() {
        System.out.println("Testing isRowFull().");
        Storage[] testcases = createNormalCases();
        boolean[][] expected = expectedRowFull();
        for (int i = 0; i < testcases.length; i++) {
            System.out.println("   Test case " + i);
            for (int j = 0; j < STORAGE_ROW_NUM; j++) {
                System.out.println("        Row " + j + ", expected " + expected[i][j] + ", Actual " + testcases[i].isRowFull(j));
                assertEquals(expected[i][j], testcases[i].isRowFull(j));
            }
        }
    }

    /**
     * Test the isRowEmpty method.
     */
    @Test
    public void testIsRowEmpty() {
        System.out.println("Testing isRowEmpty().");
        Storage[] testcases = createNormalCases();
        boolean[][] expected = expectedRowEmpty();
        for (int i = 0; i < testcases.length; i++) {
            System.out.println("   Test case " + i);
            for (int j = 0; j < STORAGE_ROW_NUM; j++) {
                System.out.println("        Row " + j + ", expected " + expected[i][j] + ", Actual " + testcases[i].isRowEmpty(j));
                assertEquals(expected[i][j], testcases[i].isRowEmpty(j));
            }
        }
    }

    /**
     * Test the rowColor() method.
     */
    @Test
    public void testRowColor() {
        // TODO
        System.out.println("Testing rowColor().");
        Storage[] testcases = createNormalCases();
        TileType[][] expected = expectedRowColor();
        for (int i = 0; i < testcases.length; i++) {
            System.out.println("   Test case " + i);
            for (int j = 0; j < STORAGE_ROW_NUM; j++) {
                System.out.println("        Row " + j + ", expected " + expected[i][j] + ", Actual " + testcases[i].rowColor(j));
                assertEquals(expected[i][j], testcases[i].rowColor(j));
            }
        }
    }

    /**
     * Test the getCode() method, including encode().
     */
    @Test
    public void testGetCode() {
        System.out.println("Test getCode().");
        // normal test case
        System.out.println("Normal case testing......");
        Storage[] testcases = createNormalCases();
        String[] expected = expectedNormalCodes();
        for (int i = 0; i < testcases.length; i++) {
            System.out.println("    Test case " + i + ": expected \"" + expected[i] + "\", Actual \"" + testcases[i].getCode() + "\"");
            assertEquals(expected[i], testcases[i].getCode());
        }
        // empty test case
        System.out.println("Empty case testing......");
        testcases = createEmptyCases();
        expected = expectedEmptyCodes();
        for (int i = 0; i < testcases.length; i++) {
            System.out.println("    Test case " + i + ": expected \"" + expected[i] + "\", Actual \"" + testcases[i].getCode() + "\"");
            assertEquals(expected[i], testcases[i].getCode());
        }
        // full test case
        System.out.println("Full case testing......");
        testcases = createFullCases();
        expected = expectedFullCodes();
        for (int i = 0; i < testcases.length; i++) {
            System.out.println("    Test case " + i + ": expected \"" + expected[i] + "\", Actual \"" + testcases[i].getCode() + "\"");
            assertEquals(expected[i], testcases[i].getCode());
        }
    }

    /**
     * Test the placeTiles method.
     */
    @Test
    public void testPlaceTiles() {
        System.out.println("Testing placeTiles().");
        // valid cases
        System.out.println("Test valid cases......");
        Storage[] testcases = createNormalCases();
        Tile[][] validCases = createValidTileListToBePlaced();
        System.out.println("    Test case 1, expected true");
        assertTrue(testcases[0].placeTiles(validCases[0], 1));
        System.out.println("    Test case 2, expected true");
        assertTrue(testcases[1].placeTiles(validCases[1], 0));
        System.out.println("    Test case 3, expected true");
        assertTrue(testcases[2].placeTiles(validCases[2], 4));
        System.out.println("    Test case 4, expected true");
        assertTrue(testcases[3].placeTiles(validCases[3], 4));
        System.out.println("    Test case 5, expected true");
        assertTrue(testcases[4].placeTiles(validCases[4], 2));

        // invalid cases
        Tile[][] invalidCases = createInvalidTileListToBePlaced();
        testcases = createNormalCases();
        System.out.println("    Test case 1, expected false, The row 0 is already full.");
        assertFalse(testcases[0].placeTiles(invalidCases[0], 0));
        System.out.println("    Test case 1, expected false, Row 0 is too short for holding 2 tiles.");
        assertFalse(testcases[1].placeTiles(invalidCases[1], 0));
        System.out.println("    Test case 1, expected false, Row 1 does not have enough empty space.");
        assertFalse(testcases[2].placeTiles(invalidCases[2], 1));
        System.out.println("    Test case 1, expected false, Row 3 color is different to the placing tiles");
        assertFalse(testcases[3].placeTiles(invalidCases[3], 3));
        System.out.println("    Test case 1, expected false, Row 4 color is different to the placing tiles");
        assertFalse(testcases[4].placeTiles(invalidCases[4], 4));
    }

    @Test
    public void testEmptyRow() {
        // TODO
    }

    @Test
    public void testEmptySpace() {
        // TODO
    }

    @Test
    public void testMove() {
        // TODO
    }
}
