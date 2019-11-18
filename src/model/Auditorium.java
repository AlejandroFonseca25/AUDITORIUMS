package model;

import java.time.LocalDateTime;
public class Auditorium
{
	//Attributes
	String name;
	String location;
	String eventName;
	boolean occupied;
	Chair[][] chairs;
	Event[] events;

	//Methods
	public Auditorium (String name, String location, String eventName, boolean occupied, Chair[][] chairs)
	{
		this.name = name;
		this.location = location;
		this.eventName = eventName;
		this.occupied = occupied;
		this.chairs = chairs;
		events = new Event[6];

	}

	public void addEvent (String name, int beginningHour, int endHour, int day, int month, int year, String proffesor, String faculty, int assistants)
	{
		boolean done = false;

		LocalDateTime beginning = LocalDateTime.of(year,month,day,beginningHour,00);
		LocalDateTime end = LocalDateTime.of(year,month,day,endHour,00);

		Event temp = new Event(name,beginning,end,proffesor,faculty,assistants);

		for (int i = 0; i < events.length && !done; i++)
		{
			if (events[i] == null)
			{
				events[i] = temp;
				done = true;
			}
		}	
	}

	public String getName ()
	{
		return name;
	}

	public Event[] getEvents ()
	{
		return events;
	}

	public Chair[][] getChairs ()
	{
		return chairs;
	}

	public void setChairs (Chair[][] chairs)
	{
		this.chairs = chairs;
	}	
}