package org.henix.workshop.qlearn

import org.henix.workshop.qlearn.concepts.CellState
import org.henix.workshop.qlearn.concepts.Move
import org.henix.workshop.qlearn.impl.MatrixGrid

class MockHelper {
    private MockHelper(){}


    def static grid(template){
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




    static def state(token){
        switch(token){
            case "X": return CellState.X
            case "O": return CellState.O
            case ".": return CellState.EMPTY
        }
    }

    static def move(x,y,token){
        new Move(x,y,token)
    }
}
