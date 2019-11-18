package model;

import java.time.LocalDateTime;

public class Event
{
	//Attributes
	String name;
	LocalDateTime beginning;
	LocalDateTime end;
	String proffesor;
	String faculty;
	int assistants;

	//Methods
	public Event (String name, LocalDateTime beginning, LocalDateTime end, String proffesor, String faculty, int getAssistants)
	{
		this.name = name;
		this.beginning = beginning;
		this.end = end;
		this.proffesor = proffesor;
		this.faculty = faculty;
		this.assistants = assistants;
	}

	public String getName ()
	{
		return name;
	}

	public LocalDateTime getBeginning ()
	{
		return beginning;
	}

	public LocalDateTime getEnd ()
	{
		return end;
	}

	public int getAssistants ()
	{
		return assistants;
	}
}