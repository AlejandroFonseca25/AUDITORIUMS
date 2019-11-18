package model;

public class Chair
{
	//Attributes
	String position;
	boolean occupied;
	boolean damaged;
	String damageDescription;

	//Methods
	public Chair (String position, boolean occupied, boolean damaged, String damageDescription)
	{
		this.position = position;	
		this.occupied = occupied;
		this.damaged = damaged;
		this.damageDescription = damageDescription;
	}
	
	public String getPosition ()
	{
		return position;
	}

	public boolean getOccupied ()
	{
		return occupied;
	}

	public boolean getDamaged ()
	{
		return damaged;
	}
}