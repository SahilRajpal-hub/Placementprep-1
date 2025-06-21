package Placementprep.LLDs.practice.TicTacToe;

public class Board {
    PlayingPiece[][] playingPieces;
    int size;

    Board(int size){
        this.size = size;
        this.playingPieces = new PlayingPiece[size][size];
    }

    void printGame(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(playingPieces[i][j]!=null) System.out.print(playingPieces[i][j].pieceType);
                else System.out.print(" ");
                System.out.print(" |");
            }
            System.out.println();
        }
    }

    boolean isWinner(PieceType pieceType){
        // row wise check
        for(int row=0; row<size; row++){

            boolean isWin = true;
            for(int j=0; j<size; j++){
                if(playingPieces[row][j]==null || playingPieces[row][j].pieceType!=pieceType){
                    isWin = false;
                    break;
                }
            }
            if(isWin) return true;

        }

        // col wise check
        for(int col=0; col<size; col++){

            boolean isWin = true;
            for(int j=0; j<size; j++){
                if(playingPieces[j][col]==null || playingPieces[j][col].pieceType!=pieceType){
                    isWin = false;
                    break;
                }
            }
            if(isWin) return true;

        }

        // diagonal right to left check
        boolean isWin = true;
        for(int i=0; i<size; i++){
            if(playingPieces[i][i]==null || playingPieces[i][i].pieceType!=pieceType){
                isWin=false;
                break;
            }
        }
        if(isWin) return true;

        
        // diagonal left to right
        isWin = true;
        for(int i=0; i<size; i++){
            if(playingPieces[i][size-i-1]==null || playingPieces[i][size-i-1].pieceType!=pieceType){
                isWin=false;
                break;
            }
        }
        if(isWin) return true;

        
        return false;

    }
}
