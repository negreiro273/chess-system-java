/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;
import chess.pieces.knight;
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
    private ChessPiece enPassantUlnerable;
    
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
	
    public ChessPiece getEnPassantUlnerable() {
         return enPassantUlnerable;
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
        
        ChessPiece movedPiece = (ChessPiece)board.piece(target);
        
        
        check = (testCheck(opponent(currentPlayer))) ? true : false;
        
        
        if(testCheckMate(opponent(currentPlayer))){
            checkMate = true;
        }else{
           nexTurn();
        }
        
        if(movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)){           
          enPassantUlnerable = movedPiece;     
        }
        else{  enPassantUlnerable = null;  }
        
        
        
        
        
        return (ChessPiece)capturedPiece;
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
       
       if(p instanceof King && target.getColumn() == source.getColumn() + 2){
           Position sourceT = new Position(source.getRow(),source.getColumn() + 3);
           Position targetT = new Position(source.getRow(),source.getColumn() + 1);
           ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
           board.placePiece(rook, targetT);
           rook.increaseMoveCount();
       }
       
           
       if(p instanceof King && target.getColumn() == source.getColumn() - 2){
           Position sourceT = new Position(source.getRow(),source.getColumn() - 4);
           Position targetT = new Position(source.getRow(),source.getColumn() - 1);
           ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
           board.placePiece(rook, targetT);
           rook.increaseMoveCount();
       }
       
       
       if(p  instanceof Pawn){
         if(source.getColumn() != target.getColumn() && capturedPiece == null){
            Position pawnPosition;            
            if(p.getColor() == Color.WHITE){
               pawnPosition = new Position(target.getRow() + 1, target.getColumn());
            }else{ pawnPosition = new Position(target.getRow() - 1, target.getColumn());}
            
            capturedPiece = board.removePiece(pawnPosition);
            capturedPieces.add(capturedPiece);
            piecesOnTheBoard.remove(capturedPiece);          
            
         }       
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
      
      
       if(p instanceof King && target.getColumn() == source.getColumn() + 2){
           Position sourceT = new Position(source.getRow(),source.getColumn() + 3);
           Position targetT = new Position(source.getRow(),source.getColumn() + 1);
           ChessPiece rook = (ChessPiece)board.removePiece(targetT);
           board.placePiece(rook, sourceT);
           rook.decreaseMoveCount();
       }
       
           
       if(p instanceof King && target.getColumn() == source.getColumn() - 2){
           Position sourceT = new Position(source.getRow(),source.getColumn() - 4);
           Position targetT = new Position(source.getRow(),source.getColumn() - 1);
           ChessPiece rook = (ChessPiece)board.removePiece(targetT);
           board.placePiece(rook, sourceT);
           rook.decreaseMoveCount();
       }
      
        if(p  instanceof Pawn){
         if(source.getColumn() != target.getColumn() && capturedPiece == enPassantUlnerable){
             ChessPiece pawn = (ChessPiece)board.removePiece(target);
             
             Position pawnPosition;            
            if(p.getColor() == Color.WHITE){
               pawnPosition = new Position(3, target.getColumn());
            }else{ pawnPosition = new Position(4, target.getColumn());}
            board.placePiece(pawn, pawnPosition);
         }       
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
        
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new knight(board, Color.WHITE));       
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));
        
        
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
        
        
        
        
    }
}
