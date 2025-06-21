package Placementprep.LLDs.TicTacToe;

import java.util.Scanner;

public class Game {
    private Board board;
    private Player[] players;
    private int currentPlayerIdx;

    public Game(Player p1, Player p2) {
        board = new Board();
        players = new Player[]{p1, p2};
        currentPlayerIdx = 0;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        GameStatus status = GameStatus.IN_PROGRESS;

        while (status == GameStatus.IN_PROGRESS) {
            board.print();
            Player currentPlayer = players[currentPlayerIdx];
            System.out.println(currentPlayer.getName() + "'s turn. Enter row and column (0-2): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (!board.placeSymbol(row, col, currentPlayer.getSymbol())) {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            if (board.checkWin(currentPlayer.getSymbol())) {
                status = GameStatus.WIN;
                board.print();
                System.out.println(currentPlayer.getName() + " wins!");
            } else if (board.isFull()) {
                status = GameStatus.DRAW;
                board.print();
                System.out.println("It's a draw!");
            } else {
                currentPlayerIdx = 1 - currentPlayerIdx; // Switch turn
            }
        }
        scanner.close();
    }
}
