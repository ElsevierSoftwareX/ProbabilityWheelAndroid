package com.example.demo1;

public class Slice 
{
	private float xCoord;
	private float yCoord;
	private float circleSize;
	private float startAng;
	private float sweepAng;
	
	public Slice()
	{
		this.setxCoord(0);
		this.setyCoord(0);
		this.setSweepAng(0);
		this.setStartAng(0);
		this.setCircleSize(0);
		
	}
	
	public Slice(float x, float y, float size, float startAng, float sweepAng)
	{
		this.setxCoord(x);
		this.setyCoord(y);
		this.setSweepAng(sweepAng);
		this.setStartAng(startAng);
		this.setCircleSize(size);
		
	}
	
	public void setSlice(float x, float y, float size, float startAng, float sweepAng)
	{
		this.setxCoord(x);
		this.setyCoord(y);
		this.setSweepAng(sweepAng);
		this.setStartAng(startAng);
		this.setCircleSize(size);
		
	}

	public float getxCoord() 
	{
		return xCoord;
	}


	public void setxCoord(float xCoord) 
	{
		this.xCoord = xCoord;
	}

	public float getyCoord() 
	{
		return yCoord;
	}

	public void setyCoord(float yCoord) 
	{
		this.yCoord = yCoord;
	}

	public float getCircleSize()
	{
		return circleSize;
	}

	public void setCircleSize(float circleSize) 
	{
		this.circleSize = circleSize;
	}

	public float getStartAng() 
	{
		return startAng;
	}
	
	public void setStartAng(float startAng) 
	{
		this.startAng = startAng;
	}

	public float getSweepAng() 
	{
		return sweepAng;
	}

	public void setSweepAng(float sweepAng) 
	{
		this.sweepAng = sweepAng;
	}	
}
