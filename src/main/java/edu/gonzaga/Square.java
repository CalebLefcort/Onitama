package edu.gonzaga;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Square {

    char piece;
    int player;
    boolean possible;
    boolean selected;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);    

    public Square() {
        piece = '0';
        player = 0;
        possible = false;
        selected = false;
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    private void updatePiece(char newPiece) {
        char oldPiece = this.piece;
        this.piece = newPiece;
        if (newPiece == '0'){
            this.player = 0;
        }
        else if(newPiece == 'R' || newPiece == 'r'){
            this.player = 1;
        }
        else if(newPiece == 'B' || newPiece == 'b'){
            this.player = 2;
        }

        this.pcs.firePropertyChange("squarePiece", oldPiece, newPiece);
    }

    private void updatePossible(boolean p){
        boolean oldPossible = this.possible;
        this.possible = p;
        this.pcs.firePropertyChange("possible", oldPossible, p);
    }

    private void updateSelected(boolean s){
        boolean oldSelected = this.selected;
        this.selected = s;
        this.pcs.firePropertyChange("selected", oldSelected, s);
    }

    public void setSelected(boolean s){
        updateSelected(s);
    }

    public void setPiece(char newPiece) {
        this.updatePiece(newPiece);
    }

    public char getPiece() {
        return piece;
    }

    public int getPlayer() {
        return player;
    }

    public void setPossible(boolean p){
        updatePossible(p);
    }

    @Override
    public String toString() {
        return "" + piece;
    }

}