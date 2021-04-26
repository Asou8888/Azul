# New Code for Deliverable D2D

## < u7205634 > < Xiao Xu >

For Deliverable D2D, I contributed the following new statements of original code:

- Added the [encode()](https://gitlab.cecs.anu.edu.au/u7205634/comp1110-ass2-tue12k/-/blob/master/src/comp1110/ass2/member/Mosaic.java#L75-114) function to Mosaic class.
- Added the [colorList()](https://gitlab.cecs.anu.edu.au/u7205634/comp1110-ass2-tue12k/-/blob/master/src/comp1110/ass2/member/Mosaic.java#L129-148) function to Mosaic class.
- Added the [isRowFull()](https://gitlab.cecs.anu.edu.au/u7205634/comp1110-ass2-tue12k/-/blob/master/src/comp1110/ass2/member/Mosaic.java#L165-187) function to Mosaic class.

(Follow the example give above to list at least 20 lines of original code contributions made by you, but not substantially more; choose your best. Notice that the example above links directly to the code as well as providing a brief description.   Please follow that example to link to your code.  You can create the link by browsing your code in gitlab, and then clicking on the line number of the first line, and then shift-clicking on the line number of the last line in the region you want to select.  If you do that correctly, the URL for that selection will be in the navigation bar of your browser.  After you commit and push your statement, you should check that all of the links are correctly working.)

    
    /**
     * This method is used to determine whether there is a row full or not, if there is at least
     * one row is full, the current round will over
     * @return true if there is a row is full
     */
    public boolean isRowFull() {
        /**
         * Written by Xiao Xu 4/25/2021
         */
        String mosaic = getCode();
        String number = ""; //initialise a string
        //extract all the numbers in mosaic
        if(mosaic != null && !"".equals(mosaic)) {
            for (int i = 0; i < mosaic.length(); i++) {
                if (mosaic.charAt(i) >= 48 && mosaic.charAt(i) <= 57) {
                    number += mosaic.charAt(i);
                }
            }
        }
        //if the number string containing consecutive numbers, it means that there is at least one row is full
        //and the round should over
        if(number.contains("0001020304")  || number.contains("1011121314") || number.contains("2021222324") || number.contains("3031323334") || number.contains("4041424344")||number.contains("5051525354")){
            return true;
        }
        else {
            return false;
        }
    }
 
