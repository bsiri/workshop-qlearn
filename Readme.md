Series : Tic-Tac-Toe
--------------------

A series of exercises around the game Tic-Tac-Toe in which we explore and see where it goes. 
The history is tagged at the begining of each stage for easier navigation; sometimes intermediate tags 
have been placed in-between stages as the solution unfolds if you need hints.  
 
The series begin at the first tag : 'step1-complete-the-game'

#### Step 1 : Complete the game.

*tag : step1-complete-the-game*

In this state the code merely shows a console game of TicTacToe. The AI opponent is pretty stupid : all it does is playing 
random moves. It should not be so hard to beat, but there is one problem : the code doesn't implement the victory condition. 
All you can do is fill the grid until the game declares a draw. Your first task is to fix that.

The faulty class is `impl.MatrixGrid`, in which you have to fill the missing code. The test class `MatrixGridTest` 
will help you assess the viability of your solution.

When you're done, try to beat StupidAI. Easy right ? Now try to achieve a draw. It's much harder (and much more fun !).

