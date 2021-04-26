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

Well down. You guys have made good use of Git and all statement have been submitted correctly. Most of the tasks have been
finished except for the task 4. I could see you could break placement strings into tile placements, but you also need to
display images of pieces, draw images of the game areas and translate piece positions to appropriate x and y positions in the 
window. About the overall design, I would say your initial design is pretty good, but you need to use it in the following weeks.
Currently, your overall solution is still heavily based on String. Making use of your skeleton design could help you solve
this assignment easier in the future. Also, it would be better if you could have some inline comments with your code so that 
people can easily follow your idea. One future note is you could check the difference between `==` and `.equals()`, and try
to understand when we should use which one.