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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author 90913370100
 */
public class ChessMatch {
    
    private int turn;
    private Color currentPlayer;
    private Board board;
    private  boolean check;
    private  boolean checkMate;
    
    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces   = new ArrayList<>();
    
    
    public ChessMatch(){
       this.board = new Board(8, 8);
       this.turn = 1;
       this.currentPlayer = Color.WHITE;
       initialSetup();
    }
   
    
    
    public boolean getcheckMate() {
        return checkMate;
    }

   
    public int getTurn() {
        return turn;
    }

    public Color getCurrentPalyer() {
        return currentPlayer;
    } 
    
    public boolean getCheck() {
		return check;
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
    
    public boolean[][] possibleMoves(ChessPosition sourcePossition){
        Position position = sourcePossition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();    
    }
    
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        
        Piece capturedPiece = makeMove(source,target);
        
        if (testCheck(currentPlayer)) {
	     undoMove(source, target, capturedPiece);
	     throw new ChessException("You can't put yourself in check");
        }
        
        check = (testCheck(opponent(currentPlayer))) ? true : false;
        
        
        if(testCheckMate(opponent(currentPlayer))){
            checkMate = true;
        }
        nexTurn();
        
        return (ChessPiece) capturedPiece;
    }
    
    private Piece makeMove(Position source, Position target){
       
       ChessPiece p             = (ChessPiece)board.removePiece(source);
       p.increaseMoveCount();
       Piece capturedPiece = board.removePiece(target);
       board.placePiece(p, target);
       
       if(capturedPiece != null){
         piecesOnTheBoard.remove(capturedPiece);
         capturedPieces.add(capturedPiece);
       }
       
       return capturedPiece;        
        
    }
    
    private void undoMove(Position source, Position target, Piece capturedPiece){    
      ChessPiece p = (ChessPiece)board.removePiece(target);
      p.decreaseMoveCount();
      board.placePiece(p, source);
      
      if(capturedPiece != null){
        board.placePiece(capturedPiece, target);
        capturedPieces.remove(capturedPiece);        
        piecesOnTheBoard.add(capturedPiece);
                
      }
        
        
    }
    
    
    
    private void validateSourcePosition(Position position){
        if(!board.thereIsApiece(position)){
             throw new ChessException("there is not piece on source Position"); 
        }
        
        if(getCurrentPalyer() != ((ChessPiece)board.piece(position)).getColor()){
            throw new ChessException("The chosen piece is not yours..."); 
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
    
    private void nexTurn(){
      this.turn++;
      this.currentPlayer = ( getCurrentPalyer() == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
    
    
    private Color opponent(Color  color){
         return (color == Color.WHITE)? Color.BLACK : Color.WHITE;
    }
    
    private ChessPiece king(Color color){
    
      List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
       
      for(Piece p : list){
           if(p instanceof King){
            return (ChessPiece)p;
           }      
      }
        throw new IllegalStateException("There is no "+ color+ " King on the board");
    }
    
    private boolean testCheck(Color color){
    
      Position kingPosition = king(color).getChessPosition().toPosition();
      List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
    }
    
     private boolean testCheckMate(Color color){
         
         if(!testCheck(color)){
           return false;
         }
         
         List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
         
         for(Piece p : list){
           boolean[][] mat = p.possibleMoves();
             for (int i = 0; i < board.getRows(); i++) {
                 for (int j = 0; j < board.getColumns(); j++) {
                     if(mat[i][j]){
                      Position source = ((ChessPiece)p).getChessPosition().toPosition();
                      Position target = new Position(i,j);
                      Piece capturedPice = makeMove(source, target);
                      boolean testCheck = testCheck(color);
                      undoMove(source, target, capturedPice);
                      if(!testCheck){
                        return false;
                      }
                     }
                 }
             }
           
         }
         return true;
     }
    
    private void placeNewPiece(char column, int row, ChessPiece piece){
        
      board.placePiece(piece, new ChessPosition(column, row).toPosition()  );
      piecesOnTheBoard.add(piece);
        
    }
    
    
    private void initialSetup(){
      
       // board.placePiece(new Rook(board,Color.WHITE), new Position(2, 1));
       // board.placePiece(new King(board,Color.BLACK), new Position(0, 4));
      //  board.placePiece(new King(board,Color.WHITE), new Position(7, 4));
      /*
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
        */
        
        placeNewPiece('h', 7, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        
        placeNewPiece('b', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        
    }
}
