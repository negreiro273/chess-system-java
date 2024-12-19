/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;


/**
 *
 * @author 90913370100
 */
public abstract class ChessPiece extends Piece{
    
    private Color color;
    private int moveCount;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    public int getmoveCount() {
        return moveCount;
    }
        
    public void increaseMoveCount(){
        this.moveCount++;
    } 

    public void decreaseMoveCount(){
       this.moveCount--;
    } 
   
    
    public ChessPosition getChessPosition(){
      return ChessPosition.fromPosition(position);  
    }
    
    
    protected boolean isThereOpponentPiece(Position position){
        ChessPiece p = (ChessPiece)getBoard().piece(position);
        return p != null && p.getColor() != color;
    }

   
    
    
}
