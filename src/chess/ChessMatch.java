/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

/**
 *
 * @author 90913370100
 */
public class ChessMatch {
    
    private Board board;
    
    public ChessMatch(){
       this.board = new Board(8, 8);
       initialSetup();
    }
    
    public ChessPiece[][] getPieces(){
        
       ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
       for (int i = 0; i < board.getRows(); i++){
         for (int j = 0; j < board.getColumns(); j++)  {
           mat[i][j] = (ChessPiece) board.piece(i, j);             
         }
       }       
       return mat;       
    }
    
    
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        
        validateSourcePosition(source);
        validateTargetPosition(couser, target);
        
        Piece capturedPiece = makeMove(source,target);
        return (ChessPiece) capturedPiece;
    }
    
    private Piece makeMove(Position source, Position target){
       
       Piece p             = board.removePiece(source);
       Piece capturedPiece = board.removePiece(target);
       board.placePiece(p, target);
       return capturedPiece;        
        
    }
    
    
    private void validateSourcePosition(Position position){
        if(!board.thereIsApiece(position)){
             throw new ChessException("there is not piece on source Position"); 
        }
        if(!board.piece(position).isThereAnyPossibleMove()){
             throw new ChessException("There is not possible moves for the chosen piece");         
        }
    }
    
    
    private void validateTargetPosition(Position source, Position target){

      if(!board.piece(source).possibleMove(target))  {
         throw new ChessException("there chosen piece can't move to target position.");  
      }        
        
    }
    
    
    
    private void placeNewPiece(char column, int row, ChessPiece piece){
        
      board.placePiece(piece, new ChessPosition(column, row).toPosition()  );
        
    }
    
    
    private void initialSetup(){
      
       // board.placePiece(new Rook(board,Color.WHITE), new Position(2, 1));
       // board.placePiece(new King(board,Color.BLACK), new Position(0, 4));
      //  board.placePiece(new King(board,Color.WHITE), new Position(7, 4));
      
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
        
        
    }
}
