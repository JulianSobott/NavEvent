package com.unknown.navevent.ui;

public class BeaconDataForUI {
	private double xCord;
	private double yCord;
	private String displayedText;
	private boolean isSelected = false;
	private boolean isClosest = false;
	private boolean isSpecial = false;
	private boolean isOrdinary = false;
	private int ID;

	BeaconDataForUI(int ID,double x, double y) {
		this.ID=ID;
		xCord = x;
		yCord = y;
	}

	public void select(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public void setDisplayedText(String T) {
		displayedText = T;
	}

	public double getxCord() {
		return xCord;
	}

	public double getyCord() {
		return yCord;
	}

	public String getDisplayedText() {
		return displayedText;
	}

	public boolean isSelected() {
		return isSelected;
	}
	public void setSpecial(boolean isSpecial){
		this.isSpecial=isSpecial;
	}
	public void setClosest(boolean isClosest){
		this.isClosest=isClosest;
	}
	public void setOrdinary(boolean isVisible){
		this.isOrdinary =isVisible;
	}
	public boolean isSpecial() {
		return isSpecial;
	}
	public boolean isClosest() {
		return isClosest;
	}
	public boolean isOrdinary() {
		return isOrdinary;
	}
	public int getID(){
		return ID;
	}


}