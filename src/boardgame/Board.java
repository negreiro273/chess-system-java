/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boardgame;

/**
 *
 * @author 90913370100
 */
public class Board {
  
    private int rows;
    private int columns;    
    private Piece[][] pieces;/*Isso aqui e uma Matrix*/

    public Board(int rows, int columns) {
        
        if(rows < 1 || columns < 1){
           throw new BoardException("Error Creating board: there must be at least 1 row and 1 column");
        }
        
        this.rows = rows;
        this.columns = columns;
        this.pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Piece piece(int row, int column){
          if(!positionExists(row,column)){
             throw new BoardException("Postion not on the board"); 
          }
        return pieces[row][column];        
    }
    
    public Piece piece(Position position){
        
        if(!positionExists(position)){
             throw new BoardException("Postion not on the board"); 
          }
        return pieces[position.getRow()][position.getColumn()];        
    }
    
    public void placePiece(Piece piece, Position position){
        
        if(thereIsApiece(position)){
            throw new BoardException("There is already a piece on position "+position); 
        }
        
        pieces[position.getRow()][position.getColumn()] = piece;   
        piece.position = position;
    }
    
    private boolean positionExists(int row, int column){
       return row >= 0 && row < rows && column >= 0 && column < columns;
    }
    
    public boolean positionExists(Position position){
       return positionExists(position.getRow(), position.getColumn());
    }
    
    public boolean thereIsApiece(Position position){    
       if(!positionExists(position)){
             throw new BoardException("Postion not on the board"); 
          }
        return  piece(position) != null;    
    }
    
    
    
    
    
}
