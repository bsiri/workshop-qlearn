package org.henix.workshop.qlearn.impl


import org.henix.workshop.qlearn.concepts.CellState
import org.henix.workshop.qlearn.concepts.GameOutcome
import org.henix.workshop.qlearn.concepts.Move
import org.henix.workshop.qlearn.concepts.Player
import org.henix.workshop.qlearn.concepts.Token
import spock.lang.Specification
import static org.henix.workshop.qlearn.concepts.CellState.*


class MatrixGridTest extends Specification{

    def "should pretty print"(){

        expect:
        def grid = grid("""
            X.O
            .OX
            O.X
        """).toString() === """\
 |0|1|2
-+-+-+-
0|X| |O
-+-+-+-
1| |O|X
-+-+-+-
2|O| |X
-+-+-+-
"""
    }

    def "should play a move"(){
        given:
        def grid = grid("""
            X.O
            ..O
            ...
        """)

        when:
        grid.play(2,2, Token.X)

        then:
        grid.toString() == """\
 |0|1|2
-+-+-+-
0|X| |O
-+-+-+-
1| | |O
-+-+-+-
2| | |X
-+-+-+-
"""

    }

    def "should refuse to play a move on a cell that was already played"(){

        when:
        grid("""
    X..
    ...
    ...
""").play(0,0,O)

        then:
        thrown RuntimeException
    }

    def "should return the list of available moves for both players"(){

        given:
        def grid = grid("""
        X.X
        O..
        .O.
""")

        and:
        def playerX = Mock(Player){
            getToken() >> Token.X
        }
        def playerO = Mock(Player){
            getToken() >> Token.O
        }

        when:
        def x_moves = grid.getAvailableMoveFor(playerX)
        def o_moves = grid.getAvailableMoveFor(playerO)

        then:
        x_moves == [ move(0,1,Token.X), move(1,1,Token.X), move(1,2,Token.X), move(2,0,Token.X), move(2,2,Token.X) ]
        o_moves == [ move(0,1,Token.O), move(1,1,Token.O), move(1,2,Token.O), move(2,0,Token.O), move(2,2,Token.O) ]


    }


    def "should return a copy of of the grid state"(){
        given:
        def grid = grid("""
                   XOX
                   OXO
                   OXO
                """)

        when:
        def alteredCopy = grid.getState()
        alteredCopy[1][2] = CellState.X
        def newFreshCopy = grid.getState()

        then:
        alteredCopy == [[X,O,X],
                        [O,X,X],
                        [O,X,O]] as CellState[][]

        newFreshCopy == [[X,O,X],
                         [O,X,O],
                         [O,X,O]] as CellState[][]


    }

    // ************ victory conditions ************


    def "should detect victory when a line is complete"(){

        expect:

        grid("""
            XXX
            .OO
            ...
        """).outcome == GameOutcome.PLAYER_X_WINS

    }

    def "should detect victory when a column is complete"(){
        expect:
        grid("""
            .XO
            X.O
            .XO
        """).outcome == GameOutcome.PLAYER_O_WINS
    }

    def "should detect victory when diagonal is complete"(){
        expect:
        grid("""
            O.X
            .O.
            .XO
        """).outcome == GameOutcome.PLAYER_O_WINS
    }


    def "should detect victory when the other diagonal is complete"(){
        expect:
        grid("""
            .OX
            .X.
            XO.
        """).outcome == GameOutcome.PLAYER_X_WINS
    }

    def "should detect that the game is a draw"(){
        expect:
        grid("""
            OOX
            XXO
            OOX
        """).outcome == GameOutcome.DRAW
    }

    def "should detect that the game is not over yet"(){
        given:
        def grid = grid("""
                ..X
                OX.
                O..
""")
        when:
        def outcome = grid.outcome

        then:
        outcome == GameOutcome.NOT_OVER_YET
        grid.gameOver == false


    }




    // ************ scaffolding *********************

    def grid(template){
        def lines = template.split('\n')
                            .collect { it.trim() }
                            .findAll { it != "" }

        def cellstates= []
        lines.eachWithIndex { line, idx ->
            def tokens = line.split('')
            cellstates[idx] = tokens.collect{ state(it) }
        }

        return new MatrixGrid(cellstates as CellState[][])
    }

    def state(token){
        switch(token){
            case "X": return CellState.X
            case "O": return CellState.O
            case ".": return CellState.EMPTY
        }
    }

    def move(x,y,token){
        new Move(x,y,token)
    }
}
