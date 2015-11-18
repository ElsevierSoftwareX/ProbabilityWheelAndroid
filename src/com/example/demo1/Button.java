package com.example.demo1;

public class Button {
	private float xCoord;	//x is leftmost on x-axis
	private float yCoord;	//y is top on y-axis
	private float length;
	private float height;
	private boolean touched;
	private boolean cursor;
	private boolean touched_yet;	//button touched for the first time (for removing "short label #"
	
	public Button(float x, float y, float Leng, float height)
	{
		this.setLength(Leng);
		this.setxCoord(x);
		this.setyCoord(y);
		this.setHeight(height);
		this.setTouched(false);
		this.setCursor(false);
		this.setTouched_yet(false);
		
	}	
	public void setButton(float x, float y, float Leng, float height)
	{
		this.setLength(Leng);
		this.setxCoord(x);
		this.setyCoord(y);
		this.setHeight(height);
	}
	
	public void setCursor( boolean cur)
	{
		this.cursor = cur;
	}
	
	public boolean getCursor()
	{
		return this.cursor;
	}
//	public static void todo()
//	{
//		int p =0;
//	}
	public float getxCoord() {
		return xCoord;
	}
	public void setxCoord(float xCoord) {
		this.xCoord = xCoord;
	}
	public float getyCoord() {
		return yCoord;
	}
	public void setyCoord(float yCoord) {
		this.yCoord = yCoord;
	}
	public float getLength() {
		return length;
	}
	public void setLength(float length) {
		this.length = length;
	}
	public boolean isTouched() {
		return touched;
	}
	public void setTouched(boolean touched) {
		this.touched = touched;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public boolean isTouched_yet() {
		return touched_yet;
	}
	public void setTouched_yet(boolean touched_yet) {
		this.touched_yet = touched_yet;
	}
	
	
	
	
}
