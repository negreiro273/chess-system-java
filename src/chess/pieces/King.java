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
public class King extends ChessPiece{
    
    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position){
       ChessPiece p = (ChessPiece)getBoard().piece(position);
       return p == null || p.getColor() != getColor();    
    }
    
    
    @Override
    public boolean[][] possibleMoves() {
        
         boolean[][]mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; 
         
         Position p  = new Position(0,0); 
         
        //Above
        p.setValues(position.getRow() - 1, position.getColumn()); 
         if(getBoard().positionExists(p)&& canMove(p)){          
           mat[p.getRow()][p.getColumn()] = true;
         }
         // below    
         p.setValues(position.getRow() + 1, position.getColumn()); 
         if(getBoard().positionExists(p)&& canMove(p)){           
           mat[p.getRow()][p.getColumn()] = true;
         }
         // Left
         p.setValues(position.getRow(), position.getColumn() - 1); 
         if(getBoard().positionExists(p)&& canMove(p)){           
           mat[p.getRow()][p.getColumn()] = true;
         }
         // right
         p.setValues(position.getRow(), position.getColumn() + 1); 
         if(getBoard().positionExists(p)&& canMove(p)){           
           mat[p.getRow()][p.getColumn()] = true;
         }
        
         // nw
         p.setValues(position.getRow() - 1, position.getColumn() - 1); 
         if(getBoard().positionExists(p)&& canMove(p)){           
           mat[p.getRow()][p.getColumn()] = true;
         }
         
         // ne
         p.setValues(position.getRow() - 1, position.getColumn() + 1); 
         if(getBoard().positionExists(p)&& canMove(p)){           
           mat[p.getRow()][p.getColumn()] = true;
         }
         // sw
         p.setValues(position.getRow() + 1, position.getColumn() - 1); 
         if(getBoard().positionExists(p)&& canMove(p)){           
           mat[p.getRow()][p.getColumn()] = true;
         }
         // se
         p.setValues(position.getRow() + 1, position.getColumn() + 1); 
         if(getBoard().positionExists(p)&& canMove(p)){           
           mat[p.getRow()][p.getColumn()] = true;
         }
         
         
        return mat;
    }
    
    
    
}
