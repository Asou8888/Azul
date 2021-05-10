package comp1110.ass2;

/**
 * @author Ruizheng Shen
 * @date 2021.5.9
 */

import comp1110.ass2.member.NewMosaic;
import comp1110.ass2.member.Tile;
import comp1110.ass2.member.TileType;
import gittest.A;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Method List of NewMosaic:
 *     1. Constructor
 *     2. encode() & getCode()
 */
public class NewMosaicTest {

    /*    Creators of test cases.
     *     1. normal cases.
     *     2. empty cases.
     *     3. full cases.
     *     4. wrong cases.
     */
    /**
     * Create Normal test cases for NewMosaic.
     * @return NewMosaic Array.
     */
    private NewMosaic[] createNormalCases() {
        NewMosaic[] cases = new NewMosaic[5]; // 5 normal cases.
        cases[0] = new NewMosaic(new Tile[][]{
                new Tile[]{null, null, null, new Tile(TileType.Purple), null},
                new Tile[]{null, null, null, new Tile(TileType.Green),null},
                new Tile[]{null, new Tile(TileType.Orange),null, new Tile(TileType.Red), new Tile(TileType.Green)},
                new Tile[]{null,null,null,null,null},
                new Tile[]{null,null,null,null,null}
        });
        cases[1] = new NewMosaic(new Tile[][]{
                new Tile[]{null, new Tile(TileType.Green), null, new Tile(TileType.Purple), null},
                new Tile[]{new Tile(TileType.Red), new Tile(TileType.Blue), new Tile(TileType.Green), null, new Tile(TileType.Purple)},
                new Tile[]{null, new Tile(TileType.Red), null, null, new Tile(TileType.Orange)},
                new Tile[]{null, null, null, new Tile(TileType.Blue), null},
                new Tile[]{null, null, null, null, new Tile(TileType.Blue)}
        });
        cases[2] = new NewMosaic(new Tile[][]{
                new Tile[]{new Tile(TileType.Blue), null, null, null, null},
                new Tile[]{new Tile(TileType.Red), null, null, new Tile(TileType.Orange), null},
                new Tile[]{new Tile(TileType.Purple), null, null, null, null},
                new Tile[]{new Tile(TileType.Orange), new Tile(TileType.Purple), null, null, new Tile(TileType.Green)},
                new Tile[]{null, null, null, null, new Tile(TileType.Blue)}
        });
        cases[3] = new NewMosaic(new Tile[][]{
                new Tile[]{null, null, new Tile(TileType.Red), null, new Tile(TileType.Green)},
                new Tile[]{new Tile(TileType.Purple), null, null, new Tile(TileType.Blue), null},
                new Tile[]{null, null, null, null, new Tile(TileType.Red)},
                new Tile[]{null, null, null, null, null},
                new Tile[]{new Tile(TileType.Green), null, null, null, null}
        });
        cases[4] = new NewMosaic(new Tile[][]{
                // TODO
                new Tile[]{new Tile(TileType.Purple), null, null, null, null},
                new Tile[]{new Tile(TileType.Red), null, new Tile(TileType.Blue), null, null},
                new Tile[]{null, new Tile(TileType.Green), null, null, null},
                new Tile[]{null, new Tile(TileType.Red), null, null, null},
                new Tile[]{null, new Tile(TileType.Blue), null, null, null}
        });
        return cases;
    }
    private String[] expectNormalCodes() {
        String[] expected = new String[5]; // 5 normal cases
        expected[0] = "Md03b13c21e23b24";
        expected[1] = "Mb01d03e10a11b12d14e21c24a33a44";
        expected[2] = "Ma00e10c13d20c30d31b34a44";
        expected[3] = "Me02b04d10a13e24b40";
        expected[4] = "Md00e10a12b21e31a41";
        return expected;
    }
    private TileType[][] expectNormalRowColor() {
        // TODO
        TileType[][] expected = new TileType[][]{
                new TileType[]{TileType.Purple}, // Colors in row 0 of test case 0
                new TileType[]{TileType.Red, TileType.Blue, TileType.Green, TileType.Purple}, // Colors in row 1 of test case 1
                new TileType[]{TileType.Purple}, // Colors in row 2 of test case 2
                new TileType[]{}, // Colors in row 3 of test case 3
                new TileType[]{TileType.Blue} // Colors in row 4 of test case 4
        };
        return expected;
    }
    private TileType[][] expectNormalColColor() {
        // TODO
        TileType[][] expected = new TileType[][]{
                new TileType[]{},
                new TileType[]{TileType.Green, TileType.Blue, TileType.Red},
                new TileType[]{},
                new TileType[]{TileType.Blue},
                new TileType[]{}
        };
        return expected;
    }

    /**
     * Create Empty test cases for NewMosaic.
     * @return NewMosaic Array
     */
    private NewMosaic[] createEmptyCases() {
        // TODO
        NewMosaic[] cases = new NewMosaic[]{
                new NewMosaic(new Tile[][]{
                        new Tile[]{null, null, null, null, null},
                        new Tile[]{null, null, null, null, null},
                        new Tile[]{null, null, null, null, null},
                        new Tile[]{null, null, null, null, null},
                        new Tile[]{null, null, null, null, null}
                })
        };
        return cases;
    }
    private String[] expectEmptyCodes() {
        String[] expected = new String[]{
                "M"
        };
        return expected;
    }

    /**
     * Create Full test cases for NewMosaic.
     * @return NewMosaic Array
     */
    private NewMosaic[] createFullCases() {
        // TODO
        NewMosaic[] cases = new NewMosaic[2];
        cases[0] = new NewMosaic(new Tile[][]{
                new Tile[]{new Tile(TileType.Blue), new Tile(TileType.Green), new Tile(TileType.Orange), new Tile(TileType.Purple), new Tile(TileType.Red)},
                new Tile[]{new Tile(TileType.Red), new Tile(TileType.Blue), new Tile(TileType.Green), new Tile(TileType.Orange), new Tile(TileType.Purple)},
                new Tile[]{new Tile(TileType.Purple), new Tile(TileType.Red), new Tile(TileType.Blue), new Tile(TileType.Green), new Tile(TileType.Orange)},
                new Tile[]{new Tile(TileType.Orange), new Tile(TileType.Purple), new Tile(TileType.Red), new Tile(TileType.Blue), new Tile(TileType.Green)},
                new Tile[]{new Tile(TileType.Green), new Tile(TileType.Orange), new Tile(TileType.Purple), new Tile(TileType.Red), new Tile(TileType.Blue)}
        });
        cases[1] = new NewMosaic(new Tile[][]{
                new Tile[]{new Tile(TileType.Green), new Tile(TileType.Orange), new Tile(TileType.Purple), new Tile(TileType.Red), new Tile(TileType.Blue)},
                new Tile[]{new Tile(TileType.Orange), new Tile(TileType.Purple), new Tile(TileType.Red), new Tile(TileType.Blue), new Tile(TileType.Green)},
                new Tile[]{new Tile(TileType.Purple), new Tile(TileType.Red), new Tile(TileType.Blue), new Tile(TileType.Green), new Tile(TileType.Orange)},
                new Tile[]{new Tile(TileType.Red), new Tile(TileType.Blue), new Tile(TileType.Green), new Tile(TileType.Orange), new Tile(TileType.Purple)},
                new Tile[]{new Tile(TileType.Blue), new Tile(TileType.Green), new Tile(TileType.Orange), new Tile(TileType.Purple), new Tile(TileType.Red)},
        });
        return cases;
    }

    /**
     * Create Wrong test cases for NewMosaic.
     * @return NewMosaic Array
     */
    private NewMosaic[] createWrongCases() {
        // TODO
        return null;
    }

    @Test
    public void testGetCode() {
        System.out.println("Test getCode().");
        // normal test cases
        System.out.println("Normal cases Testing.....");
        NewMosaic[] testcases = createNormalCases();
        String[] expectedcases = expectNormalCodes();
        for (int i = 0; i < testcases.length; i++) {
            assertEquals(expectedcases[i], testcases[i].getCode());
        }

        // empty test cases
        testcases = createEmptyCases();
        expectedcases = expectEmptyCodes();
        for (int i = 0; i < testcases.length; i++) {
            assertEquals(expectedcases[i], testcases[i].getCode());
        }
    }

    @Test
    public void testEmpty() {
        System.out.println("Test isEmpty().");
        // not empty cases
        System.out.println("Not empty cases testing.....");
        NewMosaic[] testcases = createNormalCases();
        for (NewMosaic testcase : testcases) {
            assertFalse(testcase.isEmpty());
        }
        // empty cases
        System.out.println("Empty cases testing.....");
        testcases = createEmptyCases();
        for (NewMosaic testcase: testcases) {
            assertTrue(testcase.isEmpty());
        }
    }

    @Test
    public void testRowFull() {
        // TODO
        System.out.println("Test isRowFull.");
        // not full

        // empty
        // full
        // TODO
    }

    @Test
    public void testScore() {
        // TODO
        System.out.println("Test score.");
    }

    @Test
    public void testRowColorList() {
        System.out.println("Test rowColorList method.");
        // normal test cases
        System.out.println("Test normal cases.....");
        NewMosaic[] testcases = createNormalCases();
        TileType[][] expected = expectNormalRowColor();
        for (int i = 0; i < testcases.length; i++) {
            System.out.println("Test case " + i);
            // for each test case, test the length of return array and the elements.
            System.out.println("    Expect Length: " + expected[i].length);
            System.out.println("    Actual Length: " + testcases[i].rowColorList(i).size());
            assertEquals(expected[i].length, testcases[i].rowColorList(i).size());
            System.out.println("    Check return list......");
            for (int j = 0; j < expected[i].length; j++) {
                System.out.println("        expected Color: " + expected[i][j] + ", Actual get: " + testcases[i].rowColorList(i).get(j));
                assertEquals(expected[i][j], testcases[i].rowColorList(i).get(j));
            }
            System.out.println();
        }
        // empty test cases
        System.out.println("Test Empty cases.....");
        testcases = createEmptyCases();
        for (NewMosaic testcase : testcases) {
            for (int j = 0; j < 5; j++) {
                System.out.println("    Test row " + j);
                System.out.println("        Expect Length: 0");
                System.out.println("        Actual Length: " + testcase.rowColorList(j).size());
                assertEquals(0, testcase.rowColorList(j).size());
            }
        }
    }

    @Test
    public void testColColorList() {
        System.out.println("Test colColorList method.");
        // normal test cases
        System.out.println("Test normal cases.....");
        NewMosaic[] testcases = createNormalCases();
        TileType[][] expected = expectNormalColColor();
        for (int i = 0; i < testcases.length; i++) {
            System.out.println("Test case " + i);
            // for each test case, test the length of return array and the elements.
            System.out.println("    Expect Length: " + expected[i].length);
            System.out.println("    Actual Length: " + testcases[i].colColorList(i).size());
            assertEquals(expected[i].length, testcases[i].colColorList(i).size());
            System.out.println("    Check return list......");
            for (int j = 0; j < expected[i].length; j++) {
                System.out.println("        expected Color: " + expected[i][j] + ", Actual get: " + testcases[i].colColorList(i).get(j));
                assertEquals(expected[i][j], testcases[i].colColorList(i).get(j));
            }
            System.out.println();
        }
        // empty test cases
        System.out.println("Test Empty cases.....");
        testcases = createEmptyCases();
        for (NewMosaic testcase : testcases) {
            for (int j = 0; j < 5; j++) {
                System.out.println("    Test column " + j);
                System.out.println("        Expect Length: 0");
                System.out.println("        Actual Length: " + testcase.colColorList(j).size());
                assertEquals(0, testcase.colColorList(j).size());
            }
        }
    }


}
