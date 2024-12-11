/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chess.system;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.InputMismatchException;
import java.util.Scanner;

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
        Scanner sc = new Scanner(System.in);
        
        
                
        while(true){       
            try {
                    UI.clearScreen();
                    UI.printBoard(chessMatch.getPieces());
                    System.out.println();
                    System.out.print("Source: ");
                    ChessPosition source = UI.readChessPosition(sc);

                    System.out.println();
                    System.out.print("Target: ");
                    ChessPosition target = UI.readChessPosition(sc);

                    ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

            } catch (ChessException e) {
                
                System.out.println(e.getMessage());
                sc.nextLine();    
                
            }catch (InputMismatchException e) {
                
                System.out.println(e.getMessage());
                sc.nextLine();   
                
            }
   

        }
        
        
        
    }
    
}
