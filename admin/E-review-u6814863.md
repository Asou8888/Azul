## Code Review

Reviewed by: Ruizheng Shen, u6814863

Reviewing code written by: Yixin Ge, u6616522

Component: Azul.isStateValid method.

### Comments 

 - What are the best features of this code?
<p>
   This method is implemented by dividing it into multiple small modules, by ensuring the correctness of small modules, the correctness of this method is ensured.
</p>

 - Is the code well-documented?
<p>
   The code is well-documented, by explaining what all those variables and boolean values stand for and how they work in the method.
Also, every modules have explanations on what is going on on each steps.
</p>

 - Is the program decomposition(class and method structure) appropriate?
<p>
   Yes, the program is well-decomposition. 
</p>

 - Does it follow Java code conventions (for example, are methods and variables properly named), and is the style consistent throughout?
<p>
   Yes, all the names of variables and methods is named properly.
</p>

 - If you suspect an error in the code, suggest a particular situation in which the program will not function correctly.
<p>
   I have suspected that the noMoreThan20inShared method might be wrong, cause using String.substring(int index) method, would gain uncertainty.
Only using the first index in this method, the end of the output String would be uncertained. But after I recommended, she changed the code into a proper form.
</p>