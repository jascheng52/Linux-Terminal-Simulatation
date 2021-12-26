import java.util.Scanner;
public class BashTerminal
{
	/**
	 * Main method of BashTerminal
	 */
	public static void main(String[] args)
	{
		boolean running = true;
		Scanner usrInput = new Scanner(System.in);
		String input = "";
		DirectoryTree fileSystem = new DirectoryTree();
		String user = "[113502781@host]: $ ";
		System.out.println("Starting Bash Terminal");
		while (running)
		{	
			System.out.print(user);
			input = usrInput.nextLine();
			if(input.equals("pwd"))
				System.out.println(fileSystem.presentWorkingDirectory());
			else if(input.contains("ls"))
			{
				if(input.equals("ls"))
					System.out.println(fileSystem.listDirectory());
				else if(input.equals("ls -R"))
					fileSystem.printDirectoryTree();
				else
					System.out.println("Invalid Command. Please try again");
			}
			else if(input.contains("cd "))
			{	
				if(input.substring(0,3).equals("cd "))
				{
					if(input.substring(3).equals("/"))
						fileSystem.resetCursor();
					else if(input.substring(3).equals(".."))
						fileSystem.moveCursorUp();
					else 
					{
						String argument = input.substring(3);
						try
						{
							if(argument.equals("root"))
								fileSystem.resetCursor();
							else if(argument.contains("root/"))
								fileSystem.changeDirectoryAbsPath(argument);
							else
							{
								String curPath = fileSystem.presentWorkingDirectory();
								fileSystem.changeDirectoryAbsPath(curPath + "/" + argument);
								if(curPath.equals(fileSystem.presentWorkingDirectory()))
									throw new IllegalArgumentException();
							}
						} 
						catch (NotADirectoryException e)
						{
							System.out.println("ERROR: Cannot change directory into a file");
						}
						catch (IllegalArgumentException e) 
						{
							System.out.println("ERROR: No such directory named " + argument);
						}
					}
				}
			}
			else if (input.contains("mkdir "))
			{
				if(input.substring(0,6).equals("mkdir "))
				{
					try
					{
						String name = input.substring(6);
						if(name.isEmpty())
							throw new IllegalArgumentException();
						fileSystem.makeDirectory(name);
					} 
					catch (FullDirectoryException e)
					{
						System.out.println("ERROR: Present directory is full");
					}
					catch (IllegalArgumentException e) 
					{
						System.out.println("Invalid name. Please try again");
					}
				}
			}
			else if (input.contains("touch "))
			{
				if(input.substring(0,6).equals("touch "))
				{
					try
					{
						String name = input.substring(6);
						if(name.isEmpty())
							throw new IllegalArgumentException();
						fileSystem.makeFile(name);
					} 
					catch (FullDirectoryException e)
					{
						System.out.println("ERROR: Present directory is full");
					}
					catch (IllegalArgumentException e) 
					{
						System.out.println("Invalid name. Please try again");
					}
				}
			}
			else if(input.contains("find "))
			{
				if(input.substring(0,5).equals("find "))
					fileSystem.find(input.substring(5));
			}
			else if(input.contains("mv "))
			{
				if(input.substring(0,3).equals("mv "))
				{
					String argument = input.substring(3);
					int secIndex = argument.indexOf(" root");
					if(secIndex < 0 || argument.indexOf("root") < 0)
						System.out.println("Invalid Arguments. Please try again");
					else
					{
						String src = argument.substring(0, secIndex);
						String dsc = argument.substring(secIndex + 1);
						if(dsc.indexOf(src) > -1)
							System.out.println("Invalid Arguments. Please try again");
						else
							fileSystem.move(src, dsc);
					}
				}
			}
			else if(input.equals("exit"))
			{
				running = false;
				usrInput.close();
				System.out.println("Program terminating succesfully...");
			}
			else
				System.out.println("Invalid Command. Please try again");
		}
	}
	

}
