/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chess.system;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
        List<ChessPiece> captured = new ArrayList<>();
        
        
        
                
        while(!chessMatch.getcheckMate()){       
            try {
                    UI.clearScreen();
                    UI.printMatch(chessMatch,captured);
                    System.out.println();
                    System.out.print("Source: ");
                    ChessPosition source = UI.readChessPosition(sc);

                    boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                    UI.clearScreen();
                    UI.printBoard(chessMatch.getPieces(), possibleMoves);
                    
                    System.out.println();
                    System.out.print("Target: ");
                    ChessPosition target = UI.readChessPosition(sc);

                    ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
                    
                    if(capturedPiece != null){
                      captured.add(capturedPiece);
                    }
                    
                   if(chessMatch != null) {
                       System.out.println("Enter piece for Promotion (B/N/R/Q): ");  
                       String type = sc.nextLine();
                       chessMatch.replacePromotedPiece(type);
                   }
                    
                    

            } catch (ChessException e) {
                
                System.out.println(e.getMessage());
                sc.nextLine();    
                
            }catch (InputMismatchException e) {
                
                System.out.println(e.getMessage());
                sc.nextLine();   
                
            }
   

        }
        UI.clearScreen();
        UI.printMatch(chessMatch, captured);
        
        
    }
    
}
