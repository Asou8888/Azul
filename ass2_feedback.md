# Assignment 2 Feedback

> All feedback will be pushed back to this markdown file in the following weeks before the lab.
> Please feel free to discuss this with me via Piazza or Teams.

## D2B

Good job guys! Your design is quite detailed and have solid documentation to support it. Your Git log also provides
clear evidence of healthy teamwork. Here are some general feedback for this stage:

- For the documentation of the classes, having them as some block comments within java classes is enough, you do not
  need to maintain the markdown files. (BTW the javaDoc of methods you have right now is really nice.)

- Some classes now do not have a constructor, feel free to update it or if you no longer need the class, feel free to
  modify it in the future.

- You could reconsider about the design of the Tile class. Since you have several subclasses of the Tile classes, you
  could consider if it is essential to have all as a subclass.

## D2C

**Overall mark: 3**

Well down. You guys have made good use of Git and all statement have been submitted correctly. Most of the tasks have
been finished except for the task 4. I could see you could break placement strings into tile placements, but you also
need to display images of pieces, draw images of the game areas and translate piece positions to appropriate x and y
positions in the window. About the overall design, I would say your initial design is pretty good, but you need to use
it in the following weeks. Currently, your overall solution is still heavily based on String. Making use of your
skeleton design could help you solve this assignment easier in the future. Also, it would be better if you could have
some inline comments with your code so that people can easily follow your idea. One future note is you could check the
difference between `==` and `.equals()`, and try to understand when we should use which one.

## D2D

_Please check your individual mark via Streams._

- Yixin Ge: Well down, the FloorTest covers all methods of the Floor class. The only thing that has not been tested is a
  constructor, which you could work on in the future. The new code you created have reasonable implementation, the only
  thing is your git commit message is not clear enough to show what you have done within the commit.

- Xiao Xu: The MosaicTest covers most of the methods you have in the Mosaic class. I understand that you have not
  implemented `score()` and `getBonusScore()` methods. However, it would still be great if you could have separate tests
  for these methods, so that in the future when you complete them, you will know that you have done it correctly. Within
  the new code you created, I see you have some handy methods to help you finish the tasks, which is great. You could
  consider to do some refactors, like in the `encode()` method, each if statement has the following lines
  `mosaic += i / 5; mosaic += i % 5;`. You could take them out of the if statement to make your code more concise. Also,
  it is great that you made use of try catch clause, and it would be better if we could throw some error message when we
  got the exception, which will be really helpful for you to debug.

- Ruizheng Shen: The StorageTest provides a good overall testing for the Storage class. The only thing is there are
  several methods have not been covered by this test. Tests are needed before we implement the methods, so if you could
  cover all cases it would be better. Also, all your tests are in one single method. It would be better if you could
  test each method in the Storage class separately, which means if one of your methods fails, it will not affect the
  other tests. Talking about the new code you have, the only one optimization could be, within `isRowColorSame`, line
  126-130 could be simplified to one line, `return this.tiles[row][i].getTileType() == tile.getTileType();`. Other than
  that, your implementation is quite clear with solid comments. Good job! 