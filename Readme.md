Series : Tic-Tac-Toe
--------------------

A series of exercises around the game Tic-Tac-Toe in which we explore around maching learning and see where it goes. 
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


#### Step 2 : Make a better AI.

*tag : step2-implement-qlearning*

Now the game is working. But it has another problem now : the AI is weak. To make it better we will implement QLearning. 

Your task is to implement a QLearning-based player. To guide you in this task a skeleton code has been added : 
* `QTable` and `QLearningPlayer` in the package `ai`, where the algorithm itself should be implemented,  
* `TrainingSession` in the package `game`, which controls the ai training.

QLearning uses few parameters. Alpha and Gamma are fine as they are here. In this task you only need to find a 
sensible choice for the greediness and how many rounds (epochs) should last the training. Both are defined at the top
of the `TrainingSession`.

Your task is to implement `QTable`, `QLearningPlayer` and `TrainingSession`. I recommend you do so in that order. A test 
class `TrainingSessionTest` will help you gauge the effectiveness of your implementation.

Here are a handful of questions that offer insights on the problem :
* how many states can take the grid (roughly) ?
* qlearning means putting a value on the result of a move (for a given grid state). But what really is the result of a move ?
* is playing as player X or player O the same thing or is it different ?
* a low greediness means the agent will often try random moves, while a high greediness means it will often go for the most 
efficient one. Which strategy is the most effective when training ? When playing for real ?

