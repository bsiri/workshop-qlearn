package org.henix.workshop.qlearn.game

import org.henix.workshop.qlearn.ai.QLearningPlayer
import org.henix.workshop.qlearn.ai.QLearningPlayerBuilder
import org.henix.workshop.qlearn.concepts.Token
import spock.lang.Specification
import spock.lang.Unroll
import static org.henix.workshop.qlearn.MockHelper.*

class TrainingSessionTest extends Specification{


    def setupSpec(){
        new TrainingSession().run();
    }



    @Unroll("after training, a player should identify the best move for dataset #dataset")
    def "after training, a player should identify the best move"(){
        // in this test, both player will answer the same best moves
        // either for defensive or for offensive reasons

        given:

        def playerX = new QLearningPlayer(Token.X, QLearningPlayerBuilder.table1, 0.0f,0.0f,1.01f, true)
        def playerO = new QLearningPlayer(Token.O, QLearningPlayerBuilder.table1, 0.0f,0.0f,1.01f, true)


        when:
        def responseX = playerX.nextMove(gridtest.state, gridtest.getAvailableMoveFor(playerX))
        def responseO = playerO.nextMove(gridtest.state, gridtest.getAvailableMoveFor(playerO))

        then:
        responseX.x == expected.x
        responseX.y == expected.y
        responseO.x == expected.x
        responseO.y == expected.y

        where:
        // note about move objects : I have to supply a Token, arbitrarily X, but it has no meaning here.
        dataset << [1, 2, 3, 4, 5, 6]
        expected << [move(0,1, Token.X), move(1,1, Token.X),
                     move(2,2, Token.X), move(1,0, Token.X),
                     move(2,0, Token.X), move(1,2, Token.X)]

        gridtest << [grid("""
                        O.O
                        X..
                        .X.
                    """),
                     grid("""
                        X..
                        O..
                        O.X
                    """),
                     grid("""
                            .XO
                            ..O
                            X..
                    """),
                     grid("""
                            OX.
                            ..X
                            O..
                    """),
                     grid("""
                            X..
                            X.O
                            .O.
                    """),
                     grid("""
                            X.O
                            ...
                            .XO
                    """)
        ]

    }


}
