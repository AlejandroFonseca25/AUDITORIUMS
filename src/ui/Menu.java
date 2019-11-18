package ui;

import model.*;
import java.util.Scanner;

public class Menu
{
	//Attributes
	private Scanner numberReader;
	private Scanner stringReader;
	private University university;

	//Methods
	public Menu ()
	{
		numberReader = new Scanner (System.in);
		stringReader = new Scanner (System.in);
		university = new University();
	}

	public static void main (String[] args)
	{
		Menu menu = new Menu();
		menu.mainMenu();
	}

	public void mainMenu ()
	{
		init();

		int decision = 0;
		boolean on = true;

		while (on)
		{
			System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("                  ||||| AUDITORIUM MANAGEMENT |||||                  ");
			System.out.println("\nSelect a function.\n");
			System.out.println("1 = Create auditorium.\n2 = Create event.\n3 = Add assistant to event.\n4 = Show chairs.\n5 = Report damaged chairs.\n6 = Calculate percentage of occupation.\n7 = Exit.");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

			decision = numberReader.nextInt();

			switch (decision)
			{
				case 1:
				{
					auditoriumCreation();
					break;
				}

				case 2: 
				{
					eventCreation();
					break;
				}

				case 3:
				{
					assignChairToUser();
					break;
				}

				case 4:
				{
					auditoriumsStatus();
					break;
				}

				case 5:
				{
					damageReport();
					break;
				}

				case 6:
				{
					calculateOcupation();
					break;
				}
				case 7:
				{
					on = false;
					break;
				}

				default:
				{
					System.out.println("\nXXXX| Invalid number |XXXX\n");
				}
			}
		}
	}

	public void auditoriumCreation ()
	{
		Auditorium[] temp = university.getAuditoriums();

		if (temp[19] == null)
		{
			System.out.println("\nEnter the following info:");
			
			System.out.println("\nAuditorium's name:");
			String name = stringReader.nextLine();

			System.out.println("\nBuilding where the auditorium's located:");
			String location = stringReader.nextLine();
		

			if (university.validateAuditorium(name,location))
			{
				System.out.println("\nNumber of chair rows:");

				int rows = numberReader.nextInt();

				if (university.addAuditorium(name,location,"",false, rows))
				{
					assignChairs(rows,name);			
				}

				else 
				{
					System.out.println("\n!!!!| Max capacity of auditoriums reached |!!!!");
				}
			}

			else
			{
				System.out.println("\n!!!!| Invalid auditorium info |!!!!");
			}
		}

		else
		{
			System.out.println("\n!!!!| Max capacity of auditoriums reached |!!!!");
		}
	}

	public void assignChairs (int rows, String auditoriumName)
	{
		boolean error = false;
		int[] chairsInRow = new int[rows]; 
		int temp = 0;

		for (int i = 0; i < rows && !error; i++)
		{
			System.out.println("\nNumber of chairs for row #" + (i + 1) + ":");

			temp = numberReader.nextInt();

			chairsInRow[i] = temp;
		}

		university.addChairs(chairsInRow,auditoriumName);

		System.out.println("\n++++| Auditorium successfully added |++++");
	}

	public void eventCreation ()
	{
		System.out.println("\nEnter the following info:");

		System.out.println("\nEvent name:");
		String name = stringReader.nextLine();

		if (university.validateEventName(name))
		{

			System.out.println("\nEvent's responsible proffesor:");
			String proffesor = stringReader.nextLine();

			System.out.println("\nEvent Faculty:");
			String faculty = stringReader.nextLine();

			System.out.println("\nStarting hour (7 - 20):");
			int begginingHour = numberReader.nextInt();

			System.out.println("\nEnding hour (7 - 20):");
			int endHour = numberReader.nextInt();

			if (university.validateTime(begginingHour,endHour))
			{
				System.out.println("\nEvent day(1 - 31):");
				int day = numberReader.nextInt();

				System.out.println("\nEvent month(1 - 12):");
				int month = numberReader.nextInt();

				System.out.println("\nEvent year:");
				int year = numberReader.nextInt();

				if (day >= 1 && day <= 31 && month >= 1 && month <= 12 && year >= 2019)
				{
					boolean done = false;
					boolean doIt;
					int counter = 0;
					String[] goodAuditoriums = new String[university.getAuditoriums().length];

					while (!done)
					{
						doIt = false;

						System.out.print("\nName of the auditorium that's going to host this event (Can be more than one)\n" + counter + " auditoriums have this event.\nEnter <*> to leave:");

						String field = stringReader.nextLine();

						if (field.equalsIgnoreCase("*"))
						{
							done = true;
						}
						
						else
						{
							if (university.checkAuditoriumUsage(field, begginingHour, endHour, day, month, year))
							{
								for (int i = 0; i < goodAuditoriums.length && !doIt; i++)
								{
									if (field.equalsIgnoreCase(goodAuditoriums[i]))
									{
										doIt = true;
									}
								}

								if (!doIt)
								{
									goodAuditoriums[counter] = field;
									++counter;
									System.out.println("\n++++| Event added to Auditorium " + field + " |++++");
								}

								else
								{
									System.out.println("\n!!!!| Auditorium already choosen |!!!!");
								}
							}

							else 
							{
								System.out.println("\nXXXX| Cannot use that auditorium |XXXX");
							}
						}
					}

					if (goodAuditoriums[0] != null)
					{
						university.addEvent(name, begginingHour, endHour, day, month, year, proffesor, faculty, goodAuditoriums);
						System.out.println("\n++++| Event successfully created |++++");
					}

					else
					{
						System.out.print("\n!!!!| Process ended before assigning, no assignation produced |!!!!");
					}
				}

				else
				{
					System.out.println("\n!!!!| Invalid times |!!!!");
				}
			}
			else
			{
				System.out.println("\n!!!!| Invalid hours |!!!!");
			}
		}
		else
		{
			System.out.println("\n!!!!| Name already taken |!!!!");
		}
	}	

	public void assignChairToUser ()
	{
		if (university.checkActualEvents())
		{
			System.out.println("\nWrite the name of the event you want to add an assistant to:");
			String events = university.getActualEvents();
			System.out.println(events);

			String eventName = stringReader.nextLine();


		}

		else
		{
			System.out.println("\n!!!!| No event is currently being held |!!!!");
		}
	}

	public void auditoriumsStatus ()
	{
		System.out.println("\nWrite the name of the Auditorium you want to print:");
		String name = stringReader.nextLine();

		String chairs = university.printAuditorium(name);
		System.out.println(chairs);
	}

	public void damageReport ()
	{

	}

	public void calculateOcupation ()
	{

	}

	public void init ()
	{
		int[] chairsForVarela = new int[]{5,5,5,5,5,10,10,10,10,10};
		university.addAuditorium("Varela", "Building D", "", false, 10);
		university.addChairs(chairsForVarela,"Varela");

		int[] chairsForManuelita = new int[]{15,10,10,10,10,13,12,11,10,10,10,10,10,10,8};
		university.addAuditorium("Manuelita", "Near building A", "", false, 15);
		university.addChairs(chairsForManuelita,"Manuelita");

		int[] chairsForSidoc = new int[]{12,8,8,8,8,8,12,10,10,10,10,10};
		university.addAuditorium("Sidoc", "Near building A", "", false, 12);
		university.addChairs(chairsForSidoc,"Sidoc");

		int[] chairsForArgos = new int[]{10,10,10,8,8,8,12,12,12,12,6,2,2};
		university.addAuditorium("Argos", "Near building A", "", false, 13);
		university.addChairs(chairsForArgos,"Argos");

		int[] chairsForBdO = new int[]{12,8,8,8,8,8,12,10,10,10,10,10,10,10};
		university.addAuditorium("Banco De Occidente", "Building E", "", false, 14);
		university.addChairs(chairsForSidoc,"Banco De Occidente");


	}
}