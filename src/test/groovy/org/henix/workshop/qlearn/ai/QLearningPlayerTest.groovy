package org.henix.workshop.qlearn.ai

import org.henix.workshop.qlearn.concepts.Move
import org.henix.workshop.qlearn.concepts.Token
import org.henix.workshop.qlearn.game.TrainingSession
import org.henix.workshop.qlearn.impl.MatrixGrid
import spock.lang.Specification
import spock.lang.Unroll

import static org.henix.workshop.qlearn.concepts.CellState.*
import static org.henix.workshop.qlearn.MockHelper.*

class QLearningPlayerTest extends Specification{



    def "in competitive mode, a qlearning player should consistently choose the best move"(){
        given:
            def qtable = Mock(QTable)
            def grid = new MatrixGrid();
            def competitor = new QLearningPlayer(Token.X, qtable, 0.0f, 0.0f, 1.01f, true)


        when:
            100.times {competitor.nextMove(grid.state, grid.getAvailableMoveFor(competitor))}

        then:
            100 * qtable.findBestMove(_,_)
    }

    def "in learning mode, a qlearning player should explore according to its greediness"(){

        given:
            def qtable = Mock(QTable)
            def grid = new MatrixGrid();
            def learner = new QLearningPlayer(Token.X, qtable, 0.0f, 0.0f, 0.5f, false)

        when:
            100.times {learner.nextMove(grid.state, grid.getAvailableMoveFor(learner))}

        then:
            (40..60) * qtable.findBestMove(_,_)


    }


}
