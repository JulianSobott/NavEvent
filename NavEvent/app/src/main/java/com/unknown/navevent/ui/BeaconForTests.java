package com.unknown.navevent.ui;

//This class wll be obsolete in the final Version and only is for testing purposes, should not be used due to that /\well its not
public class BeaconForTests {
    private int xCord;
    private int yCord;
    private String displayedText;
    private boolean isSelected=false;

    BeaconForTests(int x,int y){
        xCord=x;
        yCord=y;
    }

    public void select(boolean isSelected){
        this.isSelected=isSelected;
    }
    public void setDisplayedText(String T){
        displayedText=T;
    }
    public int getxCord(){
        return xCord;
    }
    public int getyCord(){
        return yCord;
    }
    public String getDisplayedText(){
        return displayedText;
    }
    public boolean isSelected(){
        return isSelected;
    }

}