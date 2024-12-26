/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

/**
 *
 * @author 90913370100
 */
public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;
    
    
    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }  
    
    
    
    
    @Override
    public boolean[][] possibleMoves() {
        
        boolean[][]mat = new boolean[getBoard().getRows()][getBoard().getColumns()]; 
        
        Position p  = new Position(0,0); 
        
        if(getColor() == Color.WHITE){
          p.setValues(position.getRow() - 1, position.getColumn());
          if(getBoard().positionExists(p)&& !getBoard().thereIsApiece(p)){
              mat[p.getRow()][p.getColumn()] = true;
          }
          
          p.setValues(position.getRow() - 2, position.getColumn());
          Position p2 = new Position(position.getRow() - 1, position.getColumn());
          if(getBoard().positionExists(p)&& !getBoard().thereIsApiece(p) &&
                  getBoard().positionExists(p2)&& !getBoard().thereIsApiece(p2)&& getmoveCount() == 0){
               mat[p.getRow()][p.getColumn()] = true;
          }
          
          p.setValues(position.getRow() - 1, position.getColumn() - 1);
          if(getBoard().positionExists(p)&& isThereOpponentPiece(p)){
              mat[p.getRow()][p.getColumn()] = true;
          } 
          
          p.setValues(position.getRow() - 1, position.getColumn() + 1);
          if(getBoard().positionExists(p)&& isThereOpponentPiece(p)){
              mat[p.getRow()][p.getColumn()] = true;
          } 
          
          if(position.getRow() == 3  ){
            Position left = new Position(position.getRow(), position.getColumn() - 1);
            
            if(getBoard().positionExists(left) &&
                    isThereOpponentPiece(left)  &&
                            getBoard().piece(left) == chessMatch.getEnPassantUlnerable() ){
                mat[left.getRow()- 1][left.getColumn()] = true;
          
            }
            Position right = new Position(position.getRow(), position.getColumn() + 1);
            
            if(getBoard().positionExists(right) &&
                    isThereOpponentPiece(right)  &&
                            getBoard().piece(right) == chessMatch.getEnPassantUlnerable() ){
                mat[right.getRow()- 1][right.getColumn()] = true;
          
            }
            
          
          }
          
          
          
        }else{
            
         p.setValues(position.getRow() + 1, position.getColumn());
          if(getBoard().positionExists(p)&& !getBoard().thereIsApiece(p)){
              mat[p.getRow()][p.getColumn()] = true;
          }
          
          p.setValues(position.getRow()  + 2, position.getColumn());
          Position p2 = new Position(position.getRow()  + 1, position.getColumn());
          if(getBoard().positionExists(p)&& !getBoard().thereIsApiece(p) &&
                  getBoard().positionExists(p2)&& !getBoard().thereIsApiece(p2)&& getmoveCount() == 0){
               mat[p.getRow()][p.getColumn()] = true;
          }
          
          p.setValues(position.getRow()  + 1, position.getColumn() - 1);
          if(getBoard().positionExists(p)&& isThereOpponentPiece(p)){
              mat[p.getRow()][p.getColumn()] = true;
          } 
          
          p.setValues(position.getRow()  + 1, position.getColumn() + 1);
          if(getBoard().positionExists(p)&& isThereOpponentPiece(p)){
              mat[p.getRow()][p.getColumn()] = true;
          } 
        }
        
            if(position.getRow() == 4  ){
                Position left = new Position(position.getRow(), position.getColumn() - 1);

                if(getBoard().positionExists(left) &&
                        isThereOpponentPiece(left)  &&
                                getBoard().piece(left) == chessMatch.getEnPassantUlnerable() ){
                    mat[left.getRow()+ 1][left.getColumn()] = true;

                }
                Position right = new Position(position.getRow(), position.getColumn() + 1);

                if(getBoard().positionExists(right) &&
                        isThereOpponentPiece(right)  &&
                                getBoard().piece(right) == chessMatch.getEnPassantUlnerable() ){
                    mat[right.getRow()+ 1][right.getColumn()] = true;

                }


            }
          
        
        
        
     return mat;
    }

    @Override
    public String toString() {
        return "p";
    }
    
    
    
    
    
    
    
    
    
}
