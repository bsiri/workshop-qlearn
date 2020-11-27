package org.henix.workshop.qlearn.ai

import org.henix.workshop.qlearn.concepts.Move
import org.henix.workshop.qlearn.concepts.Token
import org.henix.workshop.qlearn.impl.HumanPlayer
import spock.lang.Specification
import static org.henix.workshop.qlearn.MockHelper.*
import static org.henix.workshop.qlearn.ai.QTable.*
import static org.henix.workshop.qlearn.ai.QTable.SubjectiveState.*

class QTableTest extends Specification {

    // ****************** QTable ***********************

    def "should record the value for a given state and action"(){

        given:
        def state = grid("""
            XOX
            ..O
            O..
        """).state
        def move = move(2,2,Token.X)

        def qtable = new QTable()

        when:
        qtable.assignValueFor(state, move, 0.5f)

        then:
        qtable.getValueFor(state, move) == 0.5f
    }

    def "should find the best move for player X"(){
        given:
        def grid = grid("""
            X.O
            X..
            O..
        """)
        def allMoves = grid.getAvailableMoveFor(new HumanPlayer(Token.X))
        def expectedBest = allMoves[3]

        and:
        def qtable = new QTable()
        allMoves.each {qtable.assignValueFor(grid.state, it, 0.0f)}
        qtable.assignValueFor(grid.state, expectedBest, 1.0f)

        when:
        def actualBest = qtable.findBestMove(grid.state, allMoves);

        then:
        expectedBest == actualBest


    }


    // ************ StateAction tests ******************

    def "should create a StateAction for player X"(){

        given:
        def grid = grid("""
            XOO
            .O.
            .X.
        """)
        def move = new Move(2,0, Token.X)

        when:
        StateAction sa = StateAction.from(grid.state, move)

        then:
        sa == new StateAction([MINE, OPPONENT, OPPONENT,
                               NEUTRAL, OPPONENT, NEUTRAL,
                               NEUTRAL, MINE, NEUTRAL] as SubjectiveState[],
                        6)

    }


    def "should create a StateAction for player O"(){
        given:
        def grid = grid("""
               .O.
               XOX
               X..
        """)
        def move = new Move(2,1, Token.O)

        when:
        StateAction sa = StateAction.from(grid.state, move)

        then:
        sa == new StateAction([NEUTRAL, MINE, NEUTRAL,
                               OPPONENT, MINE, OPPONENT,
                               OPPONENT, NEUTRAL, NEUTRAL] as SubjectiveState[],
                        7)


    }
}
