/**
 * This is the DirectoryTree class
 */
public class DirectoryTree 
{
	private DirectoryNode root;
	private DirectoryNode cursor;
	
	/**
	 * Creates an empty Directory Tree with a root
	 */
	public DirectoryTree()
	{
		root = new DirectoryNode("root", false, null);
		cursor = root;
	}
	
	/**
	 * Moves the cursor to the root node of the tree
	 */
	public void resetCursor()
	{
		cursor = root;
	}
	
	/**
	 * Moves the cursor to the directory with the name indicated
	 * @param name the name of the directory
	 * @throws NotADirectoryException Thrown if the node with the indicated name 
	 * is a file, as files cannot be selected by the cursor, or cannot be found
	 */
	public void changeDirectory(String name) throws NotADirectoryException
	{
		DirectoryNode[] theChildren = cursor.getAllChildren();
		int numChild = cursor.size();
		for(int i = 0; i < numChild; i++ )
		{
			if(theChildren[i].getName().equals(name))
				if(theChildren[i].getIsFile())
					throw new NotADirectoryException();
				else
				{
					cursor = theChildren[i];
					break;
				}		
		}
	}
	
	/**
	 * Gets the current cursor
	 * @return the cursor
	 */
	public DirectoryNode getCursor()
	{
		return cursor;
	}
	
	/**
	 * Gets the root
	 * @return the root node
	 */
	public DirectoryNode getRoot()
	{
		return root;
	}
	

	/**
	 * Presents the current path of the cursor
	 * @return the path of the cursor
	 */
	public String presentWorkingDirectory()
	{
		return cursor.getNodePath();
	}
	
	/**
	 * Prints out all the children of the cursor
	 * @return a string with all the children
	 */
	public String listDirectory()
	{
		String str = "";
		for(int i = 0; i < cursor.size(); i++)
			str += cursor.getDirectoryNode(i).getName() + " ";
		return str;
	}
	
	/**
	 * Prints out the sub tree of the cursor
	 */
	public void printDirectoryTree()
	{
		cursor.preOrderPrint("|-");
		
	}
	
	/**
	 * Creates a directory and adds it to the current directory
	 * @param name the name of the directory
	 * @throws IllegalArgumentException when name contains a blank space or "/"
	 * @throws FullDirectoryexception when the current directory is filled to max capacity
	 */
	public void makeDirectory(String name) throws IllegalArgumentException, FullDirectoryException
	{
		if(name.contains(" ") || name.contains("/"))
			throw new IllegalArgumentException();
		if(cursor.size() == 10)
			throw new FullDirectoryException();
		DirectoryNode newChild = new DirectoryNode(name,false,cursor);
		try
		{
			cursor.addChild(newChild);
		} 
		catch (NotADirectoryException e)
		{
		}
		
	}
	
	/**
	 * Creates a file and adds it to the current directory
	 * @param name the name of the file
	 * @throws IllegalArgumentException when name contains a blank space or "/"
	 * @throws FullDirectoryexception when the current directory is filled to max capacity
	 */
	public void makeFile(String name) throws IllegalArgumentException, FullDirectoryException
	{
		if(name.contains(" ") || name.contains("/"))
			throw new IllegalArgumentException();
		if(cursor.size() == 10)
			throw new FullDirectoryException();
		DirectoryNode newChild = new DirectoryNode(name,true,cursor);
		try
		{
			cursor.addChild(newChild);
		} 
		catch (NotADirectoryException e)
		{
		}
	}
	
	/**
	 * Advances the cursor up one if cursor is not equal to the root
	 */
	public void moveCursorUp()
	{
		if(cursor.getParent() != null)
			cursor = cursor.getParent();
		else 
			System.out.println("ERROR: Already at root director");
	}
	
	/**
	 * Changes the cursor given an absolute path
	 * @param path the absolute path 
	 * @throws IllegalArgumentException when the path does not exist
	 * @throws NotADirectoryException when the path isn't a directory
	 */
	public void changeDirectoryAbsPath(String path) throws IllegalArgumentException, NotADirectoryException
	{
		String[] thePaths = path.split("/");
		DirectoryNode pointer = cursor;
		cursor = root;
		for(int i = 1; i < thePaths.length; i++)
		{
			try
			{
				String currentPath = presentWorkingDirectory();
				changeDirectory(thePaths[i]);
				if(presentWorkingDirectory().equals(currentPath))
					throw new IllegalArgumentException();
			} 
			catch (NotADirectoryException e)
			{
				cursor = pointer;
				throw new NotADirectoryException();
			}
			catch (IllegalArgumentException e) 
			{
				cursor = pointer;
				throw new IllegalArgumentException();
			}
			
		}
	}
	
	/**
	 * Finds the path of the given file/directory name
	 * @param name the name of the file/directory
	 */
	public void find(String name)
	{
		String[] curPath = new String[1];
		if(!root.containsName(name))
			System.out.println("ERROR: No such file exits");
		root.find(name,curPath, 1);
			
	}
	
	/**
	 * Moves a node from a given path to the destination
	 * @param src the source path
	 * @param dsc the destination path
	 */
	public void move(String src, String dsc) 
	{
		String[] srcPath = src.split("/");
		String[] dscPath = dsc.split("/");
		DirectoryNode srcPointer = root;
		DirectoryNode dscPointer = root;
		for(int i = 0; i < dscPath.length ; i++)
		{
			dscPointer = dscPointer.advanceNode(srcPointer, dscPath[i]);
			if(dscPointer == null)
			{
				System.out.println("The source path was not found");
				return;
			}
		}
		if(dscPointer.size() == 10)
		{
			System.out.println("ERROR:Present Directory is Full");
			return;
		}
		for(int i = 0; i < srcPath.length ; i++)
		{
			srcPointer = srcPointer.advanceNode(srcPointer, srcPath[i]);
			if(srcPointer == null)
			{
				System.out.println("The source path was not found");
				return;
			}
		}
		dscPointer.setNodeChildren(dscPointer.size(), srcPointer);
		DirectoryNode srcParent = srcPointer.getParent();
		for(int i = 0; i < srcParent.size(); i++)
		{
			if(srcParent.getDirectoryNode(i).getName().equals(srcPointer.getName()))
			{
				srcParent.setNodeChildren(i, null);
				break;
			}
		}
		srcPointer.setParent(dscPointer);
	}
	

	
	

	


	
	
}
