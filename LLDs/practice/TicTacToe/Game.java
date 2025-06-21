package Placementprep.LLDs.practice.TicTacToe;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class Game {
    Board board;
    Deque<Player> players;
    Scanner scanner;
    
    Game(){
        scanner = new Scanner(System.in);
        players = new LinkedList<>();
    }

    void intializeGame(){
        this.board = new Board(3);
        System.out.println("Enter the name of player 1 : ");
        String firstPlayerName = scanner.nextLine();
        System.out.println("Enter the name of player 2 : ");
        String secondPlayerName = scanner.nextLine();
        players.add(new Player(firstPlayerName,new PlayingPieceX()));
        players.add(new Player(secondPlayerName,new PlayingPieceY()));
    }

    
    void startGame(){
        boolean hasSomeoneWin = false;
        Player winPlayer = null;
        int totalPossibleTurns = 9;
        while(hasSomeoneWin!=true && totalPossibleTurns!=0){
            board.printGame();
            Player player = players.pop();
            System.out.println(player.name + "'s turn. Enter the coordinate of your mark : ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            if(board.playingPieces[x][y]!=null){
                System.out.println("Invalid move, please try again!");
                players.addFirst(player);
                continue;
            }
            board.playingPieces[x][y]=player.playingPiece;
            totalPossibleTurns--;

            if(board.isWinner(player.playingPiece.pieceType)){
                hasSomeoneWin=true;
                winPlayer=player;
            }else{
                players.addLast(player);
            }


        }

        if(hasSomeoneWin){
            System.out.println("Congratulations!!!!! to "+winPlayer.name);
        }else{
            System.out.println("Game got tie.");
        }

        scanner.close();

    }
}
