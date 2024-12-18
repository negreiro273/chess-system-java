/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 *
 * @author 90913370100
 */
public class Rook extends ChessPiece{
    
    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][]mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; 
        
       Position p  = new Position(0,0);
        
        //above
        p.setValues(position.getRow() - 1, position.getColumn());        
        while(getBoard().positionExists(p) && !getBoard().thereIsApiece(p)){            
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() -1);
        }        
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true; 
        }
        
        // Left
         p.setValues(position.getRow(), position.getColumn() - 1);        
        while(getBoard().positionExists(p) && !getBoard().thereIsApiece(p)){            
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() -1);
        }        
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true; 
        }
        
          
        // right
         p.setValues(position.getRow(), position.getColumn() + 1);        
        while(getBoard().positionExists(p) && !getBoard().thereIsApiece(p)){            
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() + 1);
        }        
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true; 
        }
        
        //below
        p.setValues(position.getRow() +1, position.getColumn());        
        while(getBoard().positionExists(p) && !getBoard().thereIsApiece(p)){            
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() +1);
        }        
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true; 
        }
        
        
        return mat;
    }
    
}
