/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chess.system;

import chess.ChessMatch;

/**
 *
 * @author 90913370100
 */
public class ChessSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ChessMatch chessMatch = new ChessMatch();
        UI.printBoard(chessMatch.getPieces());
        
        
        
        
    }
    
}
