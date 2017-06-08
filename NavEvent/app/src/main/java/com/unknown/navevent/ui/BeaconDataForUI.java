package com.unknown.navevent.ui;

//This class wll be obsolete in the final Version and only is for testing purposes, should not be used due to that /\well its not
public class BeaconDataForUI {//// TODO: 27.05.2017 Rename The File to not test
	private double xCord;
	private double yCord;
	private String displayedText;
	private boolean isSelected = false;

	BeaconDataForUI(double x, double y) {
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

}