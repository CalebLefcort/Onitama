package edu.gonzaga;

import java.util.ArrayList;

import edu.gonzaga.CardDeck.Card;

public class Board {
    private char[][] boardStartValues = {{'b','0','0','0','r'},{'b','0','0','0','r'},{'B','0','0','0','R'},{'b','0','0','0','r'},{'b','0','0','0','r'}};
    private Square[][] board;
    private int size;
    private ArrayList<Coordinate> destinations = new ArrayList<Coordinate>();
    private Card curCard;
    private Coordinate curPiece;

    public Board(int size){
        this.size = size;
        this.board = new Square[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                board[i][j] = new Square();
            }
        }
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                this.board[i][j].setPiece(boardStartValues[i][j]);
            }
        }
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
        if (board[x1][y1].getPiece() == board[x2][y2].getPiece())
            return false;
        if (board[x1][y1].getPiece() == 'b' && board[x2][y2].getPiece() == 'B')
            return false;
        if (board[x1][y1].getPiece() == 'B' && board[x2][y2].getPiece() == 'b')
            return false;
        if (board[x1][y1].getPiece() == 'r' && board[x2][y2].getPiece() == 'R')
            return false;
        if (board[x1][y1].getPiece() == 'R' && board[x2][y2].getPiece() == 'r')
            return false;
        return true;
    }

    public int makeMove(Coordinate destCoord){
        if (checkValidMove(curPiece, destCoord)){
            char piece = board[curPiece.getX()][curPiece.getY()].getPiece();
            char dest = board[destCoord.getX()][destCoord.getY()].getPiece();
            board[destCoord.getX()][destCoord.getY()].setPiece(piece);
            board[curPiece.getX()][curPiece.getY()].setPiece('0');
            return dest;
        }
        return -1;
    }

    public void generateDestinations(){
        destinations.clear();
        int x1 = curPiece.getX();
        int y1 = curPiece.getY();
        if (board[x1][y1].getPlayer() == 2){
            for (int i = 0; i < curCard.getInvMoves().size(); i++){
                Coordinate temp = new Coordinate(x1 + curCard.getMoves().get(i).getX(), y1 + curCard.getMoves().get(i).getY());
                destinations.add(temp);
            }
        }
        else if (board[x1][y1].getPlayer() == 1){
            for (int i = 0; i < curCard.getMoves().size(); i++){
                Coordinate temp = new Coordinate(x1 + curCard.getInvMoves().get(i).getX(), y1 + curCard.getInvMoves().get(i).getY());
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

    public boolean isPiece(Coordinate cord){
        if(getBoardVal(cord) == '0'){
            return false;
        }
        return true;
    }

    public ArrayList<Coordinate> getDest(){
        return destinations;
    }
    
    public void chooseDestination(int choice){
        if(destinations.isEmpty() != true)
            makeMove(destinations.get(choice));
    }

    public void setCurCard(Card curCard) {
        this.curCard = curCard;
    }

    public void setCurPiece(Coordinate newCurPiece){
        if (curPiece != null){
            Square temp1 = getSquare(curPiece);
            temp1.setSelected(false);
        }
        this.curPiece = newCurPiece;
        Square temp = getSquare(curPiece);
        temp.setSelected(true);
    }

    public Integer getNumDest() {
        return destinations.size();
    }

    public Square getSquare(Coordinate cord){
        return board[cord.getX()][cord.getY()];
    }

    private char getBoardVal(Coordinate cord){
        return board[cord.getX()][cord.getY()].getPiece();
    }

    public void boardButtonPressed(Coordinate cord){
        if(isPiece(cord)){
            if(curPiece == null){
                setCurPiece(cord);
            }  
            else if(getSquare(cord).getPlayer() == getSquare(curPiece).getPlayer()){
                setCurPiece(cord);
                for(int i = 0; i < destinations.size(); i++){
                    Square temp = getSquare(destinations.get(i));
                    temp.setPossible(false);
                }
            }
        }
        else{ 
                if (getSquare(cord).getPossible()){
                    makeMove(cord);
                    for(int i = 0; i < destinations.size(); i++){
                        Square temp = getSquare(destinations.get(i));
                        temp.setPossible(false);
                    }
                }
            }
        if(curCard != null && curPiece != null){
            generateDestinations();
            //System.out.println(destinations.size());
            for(int i = 0; i < destinations.size(); i++){
                Square temp = getSquare(destinations.get(i));
                temp.setPossible(true);
            }
        }
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

    public Square[][] getBoard() {
        return board;
    }
}
