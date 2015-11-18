package com.example.demo1;

public class Knob 
{
	private float xCoord;
	private float yCoord;
	private float xCenter;
	private float yCenter;
	private float length;
	private float arcDeg;
	private boolean touched;
	private boolean on;
	
	private float old_xCoord;
	private float old_yCoord;
	private float old_arcDeg;
	
	public Knob()
	{
		this.setLength(0);
		this.setxCenter(0);
		this.setyCenter(0);
		this.setxCoord(0);
		this.setyCoord(0);
		this.setArcDeg(0);
		this.setTouched(false);
		this.setOn(false);
	}

	public Knob(float x, float y, float xCtr, float yCtr, float Leng, float arcDeg, boolean touch, boolean on)
	{
		this.setLength(Leng);
		this.setxCenter(xCtr);
		this.setyCenter(yCtr);
		this.setxCoord(x);
		this.setyCoord(y);
		this.setArcDeg(arcDeg);
		this.setTouched(touch);
		this.setOn(on);
	}
	
	public void setKnob(float x, float y, float xCtr, float yCtr, float Leng, float arcDeg, boolean touch, boolean on)
	{
		this.setLength(Leng);
		this.setxCenter(xCtr);
		this.setyCenter(yCtr);
		this.setxCoord(x);
		this.setyCoord(y);
		this.setArcDeg(arcDeg);
		this.setTouched(touch);
		this.setOn(on);
	}
	
	public void update(float x, float y, Knob a)
	{
		this.old_xCoord = this.xCoord;
		this.old_yCoord = this.yCoord;
		this.old_arcDeg = this.arcDeg;
		
		if(x>=a.xCenter&&y<=a.yCenter){//this if statement checks if the knob is in the upper right quarter of the circle
     		a.arcDeg = (float) Math.abs((Math.atan2(a.yCenter-(y+10),(x+10)-a.xCenter)*(180/Math.PI))); //this calculated the angle between the line connected to the knob and the other line pointing at the 3 o'clock position. The angle is positive going counter-clock wise.
     	 
     	 }else if(x>=a.xCenter && y>=a.yCenter){//this checks if the knob is in the lower right quarter of the circle
     		a.arcDeg = (float) Math.abs((360 + (Math.atan2(-((y+10)-a.yCenter),((x+10)-a.xCenter))*(180/Math.PI))));//this calculated the angle between the line connected to the knob and the other line pointing at the 3 o'clock position. The angle is positive going counter-clock wise.
     		 
     	 }else if(x<=a.xCenter && y>=a.yCenter){//this checks if the knob is in the lower left quarter of the circle
     		a.arcDeg = (float)Math.abs((360 + (Math.atan2(-((y+10)-a.yCenter),-(a.xCenter-(x+10)))*(180/Math.PI))));//this calculated the angle between the line connected to the knob and the other line pointing at the 3 o'clock position. The angle is positive going counter-clock wise.
        	 
     	 }else{//if all of the above checks fail, then the knob is in the upper left quarter 
     		a.arcDeg = (float)Math.abs((Math.atan2(a.yCenter-(y+10),-(a.xCenter-(x+10)))*(180/Math.PI)));//this calculated the angle between the line connected to the knob and the other line pointing at the 3 o'clock position. The angle is positive going counter-clock wise.       	      		 
     	 }
     			
		a.xCoord = (float)(a.xCenter+a.length*(Math.cos((a.arcDeg)/360*(2*Math.PI)))-10);
        a.yCoord = (float)(a.yCenter-a.length*(Math.sin((a.arcDeg)/360*(2*Math.PI))))-10;		
	}
	
	public void undo ()
	{
		this.xCoord = this.old_xCoord;
		this.yCoord = this.old_yCoord;
		this.arcDeg = this.old_arcDeg;
	}
	
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
	public float getxCenter() {
		return xCenter;
	}
	public void setxCenter(float xCenter) {
		this.xCenter = xCenter;
	}
	public float getyCenter() {
		return yCenter;
	}
	public void setyCenter(float yCenter) {
		this.yCenter = yCenter;
	}
	public float getLength() {
		return length;
	}
	public void setLength(float length) {
		this.length = length;
	}
	public float getArcDeg() {
		return arcDeg;
	}
	public void setArcDeg(float arcDeg) {
		this.arcDeg = arcDeg;
	}
	public boolean isTouched() {
		return touched;
	}
	public void setTouched(boolean touched) {
		this.touched = touched;
	}
	public boolean isOn() {
		return on;
	}
	public void setOn(boolean on) {
		this.on = on;
	}
	public void updateCoordinates() {
		
		this.xCoord = (float)(this.xCenter+this.length*(Math.cos((this.arcDeg)/360*(2*Math.PI)))-10);
        this.yCoord = (float)(this.yCenter-this.length*(Math.sin((this.arcDeg)/360*(2*Math.PI))))-10;
	}	
}
