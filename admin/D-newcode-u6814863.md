# New Code for Deliverable D2D

## < u6814863 > < Ruizheng Shen >

For Deliverable D2D, I contributed the following new statements of original code:

- Added the [encode()](https://gitlab.cecs.anu.edu.au/u7205634/comp1110-ass2-tue12k/-/blob/master/src/comp1110/ass2/member/Storage.java#L73-94) function to Storage class.
- Added the [isRowFull()](https://gitlab.cecs.anu.edu.au/u7205634/comp1110-ass2-tue12k/-/blob/master/src/comp1110/ass2/member/Storage.java#L100-106) function to Storage class.
- Added the [isRowColorSame()](https://gitlab.cecs.anu.edu.au/u7205634/comp1110-ass2-tue12k/-/blob/master/src/comp1110/ass2/member/Storage.java#L115-129) function to Storage class.
- Added the [isRowEmpty()](https://gitlab.cecs.anu.edu.au/u7205634/comp1110-ass2-tue12k/-/blob/master/src/comp1110/ass2/member/Storage.java#L136-141) function to Storage class.

(Follow the example give above to list at least 20 lines of original code contributions made by you, but not substantially more; choose your best. Notice that the example above links directly to the code as well as providing a brief description.   Please follow that example to link to your code.  You can create the link by browsing your code in gitlab, and then clicking on the line number of the first line, and then shift-clicking on the line number of the last line in the region you want to select.  If you do that correctly, the URL for that selection will be in the navigation bar of your browser.  After you commit and push your statement, you should check that all of the links are correctly working.)

    /**
     * The Storage String is composed of 3-character substrings representing the tiles in the storage area. These are ordered
     * numerically by row number.
     *
     * The first character is "S"
     *
     * The remaining characters are in groups of three in the pattern: {row number}{tile}{number of tiles}
     *
     * row number 0 - 4.
     * tile a - e.
     * number of tiles stored in that row from 1 - 5.
     * The maximum number of tiles in a given row is equal to (row number + 1).
     * @return a String of code
     */
    private String encode() {
        StringBuilder code = new StringBuilder("S");
        for (int i = 0; i < STORAGE_ROW_NUM; i++) {
            // count the number of tiles in this row
            int cnt = 0;
            String colorChar = "";
            for (int j = 0; j < STORAGE_ROW_LENGTH[i]; j++) {
                if (this.tiles[i][j] != null) {
                    cnt++; // if there is a tile in position(i, j), add the count by 1.
                    colorChar = this.tiles[i][j].getCode(); // record the tile's color of this row.
                }
            }
            if (cnt != 0) {
                // if this row is not empty, encode this row.
                // 'i': the row number
                // 'colorChar': a - e
                // 'cnt': number of tiles stored in row[i]
                code.append(i).append(colorChar).append(cnt);
            }
        }
        return code.toString();
    }