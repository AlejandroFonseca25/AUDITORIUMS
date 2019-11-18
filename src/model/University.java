package model;

import java.time.LocalDateTime;
import java.util.Random;

public class University
{
	//Attributes
	Auditorium[] auditoriums;

	//Methods
	public University ()
	{
		auditoriums = new Auditorium[20];
	}

	public boolean addAuditorium(String name, String location, String eventName, boolean occupied, int rows)
	{
		boolean done = false;

		Chair[][] chairs = new Chair[rows][]; 

		for (int i = 0; i < auditoriums.length && !done; i++)
		{
			if (auditoriums[i] == null)
			{
				Auditorium auditorium = new Auditorium(name,location,eventName,occupied, chairs);
				auditoriums[i] = auditorium;
				done = true;
			}
		}

		return done;	
	}

	public boolean validateAuditorium(String name, String location)
	{
		boolean good = true;

		for (int i = 0; i < auditoriums.length && good;i++)
		{
			if (auditoriums[i] != null)
			{
				if (name.equalsIgnoreCase(auditoriums[i].getName()) )
				{
					good = false;
				}
			}
		}

		return good;
	}

	public void addChairs (int[] chairsInRow, String auditoriumName)
	{
		int letter = 65;
		int wtf = 0;
		char ascii;
		String asciiGood;
		String position = "";

		Chair [][] chairs = new Chair[chairsInRow.length][];

		for (int i = 0; i < chairsInRow.length; i++)
		{
			position = "";

			chairs[i] = new Chair[chairsInRow[i]];

			for (int j = 0; j < chairsInRow[i]; j++)
			{
				position = "";
				ascii = (char) letter;
				asciiGood = Character.toString(ascii);
				wtf = j + 1;
				position += asciiGood + wtf;
				chairs[i][j] = new Chair(position, false, false, "");
			}
			++letter;
		}

		for (int i = 0; i < auditoriums.length; i++)
		{
			if (auditoriums[i] != null && auditoriumName.equalsIgnoreCase(auditoriums[i].getName()))
			{
				auditoriums[i].setChairs(chairs);
			}
		}
	}

	public void addEvent (String name, int beginningHour, int endHour, int day, int month, int year, String proffesor, String faculty, String[] goodAuditoriums)
	{
		for (int i = 0; i < auditoriums.length; i++)
		{
			if (auditoriums[i] != null)
			{
				for (int j = 0; j < goodAuditoriums.length; j++)
				{
					if (goodAuditoriums[j] != null)
					{
						if (goodAuditoriums[j].equalsIgnoreCase(auditoriums[i].getName()))
						{
							auditoriums[i].addEvent(name,beginningHour,endHour,day,month,year,proffesor,faculty,0);
						}
					}
				}
			}
		}
	}

	public boolean validateTime (int beginning, int end)
	{
		boolean good = true;

		if (beginning >= end || (end - beginning) < 2 || (end - beginning) > 12)
		{
			return false;
		}

		else
		{
			return true;
		}
	}

	public boolean checkAuditoriumUsage (String auditoriumName, int beginning, int end, int day, int month, int year)
	{
		int[] comps = new int[5];
		Event[] events;
		boolean good = true;
		boolean itEntered = false;

		for (int i = 0; i < auditoriums.length && good; i++)
		{
			if (auditoriums[i] != null)
			{	
				if (auditoriumName.equalsIgnoreCase(auditoriums[i].getName()))
				{
					itEntered = true;
					events = auditoriums[i].getEvents();

					for (int j = 0; j < events.length && good; j++)
					{
						if (events[j] != null)
						{
							comps[0] = events[j].getBeginning().getHour();
							comps[1] = events[j].getEnd().getHour();
							comps[2] = events[j].getEnd().getDayOfMonth();
							comps[3] = events[j].getEnd().getMonthValue();
							comps[4] = events[j].getEnd().getYear();
								
							if (day == comps[2] && month == comps[3] && year == comps[4])
							{	
								if (beginning == comps[1] || beginning == comps[0] || end == comps[0] || end == comps[1] || (beginning > comps[0] && beginning < comps[1]) || (end > comps[0] && end < comps[1]))
								{
									good = false;
								}
							}
						}
					}
				}
			}
		}

		if (good && !itEntered)
		{
			good = false;
		}

		if (good)
		{
			System.out.println("Im true");
		}

		else
		{
			System.out.println("Im false lol");
		}

		return good;
	}

	public boolean validateEventName (String name)
	{
		boolean good = true;
		String nameComp;
		Event[] events;

		for (int i = 0; i < auditoriums.length && good; i++)
		{
			if (auditoriums[i] != null)
			{
				events = auditoriums[i].getEvents();

				for (int j = 0; j < events.length && good; j++)
				{
					if (events[j] != null)
					{
						if (name.equalsIgnoreCase(events[j].getName()))
						{
							good = false;
						}
					}
				}
			}
		}

		return good;
	}

	public boolean checkActualEvents ()
	{
		LocalDateTime now = null;
		int[] time = new int[4];
		int[] comp = new int[5];
		boolean ok = false;

		for (int i = 0; i < auditoriums.length && !ok;i++)
		{
			if (auditoriums[i] != null)
			{
				Event[] events = auditoriums[i].getEvents();
			
				for (int j = 0; j < events.length && !ok; j++)
				{
					if (events[j] != null)
					{
						now = now.now();
						time[0] = now.getDayOfMonth();
						time[1] = now.getMonthValue();
						time[2] = now.getYear();
						time[3] = now.getHour();

						comp[0] = events[j].getEnd().getDayOfMonth();
						comp[1] = events[j].getEnd().getMonthValue();
						comp[2] = events[j].getEnd().getYear();
						comp[3] = events[j].getBeginning().getHour();
						comp[4] = events[j].getEnd().getHour();
						
						if (time[0] == comp[0] && time[1] == comp[1] && time[2] == comp[2])
						{
							if (time[3] < comp[4] && time[3] > comp[3])
							{
								ok = true;
							}
						}
					}
				}
			}
		}

		return ok; 
	}

	public String getActualEvents ()
	{
		String listOfEvents = "";
		LocalDateTime now = null;
		int[] time = new int[4];
		int[] comp = new int[5];

		for (int i = 0; i < auditoriums.length;i++)
		{
			if (auditoriums[i] != null)
			{
				Event[] events = auditoriums[i].getEvents();
			
				for (int j = 0; j < events.length; j++)
				{
					if (events[j] != null)
					{
						now = now.now();
						time[0] = now.getDayOfMonth();
						time[1] = now.getMonthValue();
						time[2] = now.getYear();
						time[3] = now.getHour();

						comp[0] = events[j].getEnd().getDayOfMonth();
						comp[1] = events[j].getEnd().getMonthValue();
						comp[2] = events[j].getEnd().getYear();
						comp[3] = events[j].getBeginning().getHour();
						comp[4] = events[j].getEnd().getHour();
						
						if (time[0] == comp[0] && time[1] == comp[1] && time[2] == comp[2])
						{
							if (time[3] < comp[4] && time[3] > comp[3])
							{
								listOfEvents += "\n~" + events[j].getName();
							}
						}
					}
				}
			}
		}
		return listOfEvents;	
	}

	public String printAuditorium (String name)
	{
		Chair[][] chairs; 
		String chairPrint = "";
		for (int i = 0; i < auditoriums.length; i++)
		{
			if (auditoriums[i] != null  && name.equalsIgnoreCase(auditoriums[i].getName()))
			{
				chairs = auditoriums[i].getChairs();
				
				for (int j = 0; j < chairs.length; j++)
				{
					chairPrint += "\n";

					for (int k = 0; k < chairs[j].length; k++)
					{
						chairPrint += chairs[j][k].getPosition();

						if (chairs[j][k].getDamaged())
						{
							chairPrint += "! ";
						}

						else if (chairs[j][k].getOccupied())
						{
							chairPrint += "# ";
						}

						else
						{
							chairPrint += "* ";
						}
					}
				}
			}
		}
		return chairPrint;
	}

	public Auditorium[] getAuditoriums ()
	{
		return auditoriums;
	}
}