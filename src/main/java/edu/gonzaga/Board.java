package edu.gonzaga;

import java.lang.management.PlatformManagedObject;
import java.util.ArrayList;

import edu.gonzaga.CardDeck.Card;

public class Board {
    private char[][] board = {{'b','0','0','0','r'},{'b','0','0','0','r'},{'B','0','0','0','R'},{'b','0','0','0','r'},{'b','0','0','0','r'}};
    private int size;
    private ArrayList<Coordinate> destinations = new ArrayList<Coordinate>();
    private Hand hand;
    private Card curCard;
    private Coordinate curPiece;
    private char curPlayer = 'r';
    private Boolean cardSelected = false;
    private Boolean pieceSelected = false;

    public Board(int size, Hand h){
        this.size = size;
        hand = h;
    }
    public boolean checkValidMove(Coordinate pieceCoord, Coordinate destCoord){
        int x1 = pieceCoord.getX();
        int y1 = pieceCoord.getY();
        int x2 = destCoord.getX();
        int y2 = destCoord.getY();
        //no movement
        if (pieceCoord == destCoord)
            return false;
        //out of bounds
        if (x1 >= size || x1 < 0)
            return false;
        if (y1 >= size || y1 < 0)
            return false;
        if (x2 >= size || x2 < 0)
            return false;
        if (y2 >= size || y2 < 0)
            return false;
        //same team
        if (board[x1][y1] == board[x2][y2])
            return false;
        if (board[x1][y1] == 'b' && board[x2][y2] == 'B')
            return false;
        if (board[x1][y1] == 'B' && board[x2][y2] == 'b')
            return false;
        if (board[x1][y1] == 'r' && board[x2][y2] == 'R')
            return false;
        if (board[x1][y1] == 'R' && board[x2][y2] == 'r')
            return false;
        return true;
    }

    public int makeMove(Coordinate destCoord){
        if (checkValidMove(curPiece, destCoord)){
            char piece = board[curPiece.getX()][curPiece.getY()];
            char dest = board[destCoord.getX()][destCoord.getY()];
            board[destCoord.getX()][destCoord.getY()] = piece;
            board[curPiece.getX()][curPiece.getY()] = '0';
            return dest;
        }
        return -1;
    }

    public void generateDestinations(){
        destinations.clear();
        int x1 = curPiece.getX();
        int y1 = curPiece.getY();
        if (board[x1][y1] == 'b' || board[x1][y1] == 'B' ){
            for (int i = 0; i < curCard.getInvMoves().size(); i++){
                Coordinate temp = new Coordinate(x1 + curCard.getInvMoves().get(i).getX(), y1 + curCard.getInvMoves().get(i).getY());
                destinations.add(temp);
            }
        }
        else if (board[x1][y1] == 'r' || board[x1][y1] == 'R' ){
            for (int i = 0; i < curCard.getMoves().size(); i++){
                Coordinate temp = new Coordinate(x1 + curCard.getMoves().get(i).getX(), y1 + curCard.getMoves().get(i).getY());
                destinations.add(temp);
            }
        }
        else{
            System.out.println("Choose a different spot?");
        }
        //find invalid destinations
        //ArrayList<Coordinate> invalids = new ArrayList<Coordinate>();
        for (int i = 0; i < destinations.size(); i++){
            if(checkValidMove(curPiece, destinations.get(i)) != true){
                destinations.remove(i);
                i = i-1;
            }
        }
        // //remove invalid destinations
        // for (int i = 0; i < invalids.size(); i++){
        //     destinations.remove(invalids.get(i));
        // }
    }

    public ArrayList<Coordinate> getDest(){
        return destinations;
    }
    
    public void chooseDestination(int choice){
        if(destinations.isEmpty() != true)
            makeMove(destinations.get(choice));
    }

    public void setCurCard(Card curCard) {
        if(hand.whosHand(curCard) == curPlayer) {
            this.curCard = curCard;
            cardSelected = true;
        }
    }

    public void setCurPiece(Coordinate curPiece){
        //checks to see if location selected is an available piece
        int x = curPiece.getX();
        int y = curPiece.getY();
        if(board[x][y] == curPlayer || board[x][y] == curPlayer - 32)
            this.curPiece = curPiece;
        pieceSelected = true;
    }

    public Integer getNumDest() {
        return destinations.size();
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < board.length; i++){
            ret+= "\n";
            for (int j = 0; j <board[i].length; j++){
                ret += board[j][i];
                ret += ",";
            }
        }
        return ret;
    }

    public char[][] getBoard() {
        return board;
    }

    public Card getCard() {
        return curCard;
    }

    public Boolean isCardSelected() {
        return cardSelected;
    }

    public Boolean isPieceSelected() {
        return pieceSelected;
    }


}
