/**
 * This is the DirectoryNode class
 */
public class DirectoryNode 
{
	private String name;
	private DirectoryNode[] childNodes;
	private int size;
	private DirectoryNode left;
	private DirectoryNode right;
	private DirectoryNode middle;
	private boolean isFile;
	private DirectoryNode parent;
	
	/**
	 * Create an empty DirectoryNode
	 */
	public DirectoryNode()
	{
		name = "";
		childNodes = new DirectoryNode[10];
		size = 0;
		isFile = false;
	}
	
	/**
	 * Creates a DirectoryNode given a name and directory type
	 * @param theName the name of the directory
	 * @param file the directory type
	 * @param theParent the parent node, null if the root
	 */
	public DirectoryNode(String theName, Boolean file, DirectoryNode theParent)
	{
		name = theName;
		isFile = file;
		childNodes = new DirectoryNode[10];
		parent = theParent;
		size = 0;
	}
	/**
	 * Gets the left child node
	 * @return the left child node
	 */
	public DirectoryNode getLeft()
	{
		return left;
	}

	/**
	 * Gets the right child node
	 * @return the right child node
	 */
	public DirectoryNode getRight() 
	{
		return right;
	}

	/**
	 * Gets the middle child node
	 * @return the middle child node
	 */
	public DirectoryNode getMiddle() 
	{
		return middle;
	}
	
	/**
	 * Gets the parent child node
	 * @return the parent node
	 */
	public DirectoryNode getParent() 
	{
		return parent;
	}
	
	/**
	 * Gets the name of the node
	 * @return the node Name;
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Checks if the node is a boolean
	 * @return true if it is a file, false otherwise
	 */
	public boolean getIsFile()
	{
		return isFile;
	}
	
	/**
	 * Gets the node of the child given the index
	 * @param index the index of the child
	 * @return the DirectoryNode child at the given index
	 */
	public DirectoryNode getDirectoryNode(int index)
	{
		return childNodes[index];
	}
	
	/**
	 * Gets the Array that contains all the children
	 * @return the array with the children
	 */
	public DirectoryNode[] getAllChildren()
	{
		return childNodes;
	}
	
	/**
	 * Returns the number of children
	 * @return the current number of children
	 */
	public int size()
	{
		return size;
	}
	
	/**
	 * Sets the name of the node
	 * @param newName the new Name of the node
	 */
	public void setName(String newName)
	{
		name = newName;
	}
	
	/**
	 * Sets the left child node
	 * @param left the child node
	 */
	public void setLeft(DirectoryNode left) 
	{
		this.left = left;
	}

	/**
	 * Sets the right child node
	 * @param right the child node
	*/
	public void setRight(DirectoryNode right)
	{
		this.right = right;
	}
	
	/**
	 * Sets the middle child node
	 * @param middle the child node
	 */
	public void setMiddle(DirectoryNode middle)
	{
		this.middle = middle;
	}
	
	/**
	 * Sets the parent for the current node
	 * @param parent the parent node
	 */
	public void setParent(DirectoryNode parent)
	{
		this.parent = parent;
	}
	
	/**
	 * Sets the if the node is a file
	 * @param b the boolean to set isFile
	 */
	public void setFile(boolean b)
	{
		isFile = b;
	}
	/**
	 * Sets the children node at the given index
	 * @param index the index of the child node
	 * @param newChild the new child node
	 */
	public void setNodeChildren(int index, DirectoryNode newChild)
	{
		if(childNodes[index] == null)
		{
			size++;
			childNodes[index] = newChild;
		}	
		if(newChild == null && childNodes[index] != null)
		{
			size--;
			for(int i = index ;i < childNodes.length - 1; i++)
				childNodes[i] = childNodes[i+1];		
		}
		
	}
	
	/**
	 * Adds a child node to current DirectoryNode
	 * @param newChild the new child node to add
	 * @throws FullDirectoryException thrown if the current node is a file
	 * @throws NotADirectoryException thrown if all child references of this directory are occupied
	 */
	public void addChild(DirectoryNode newChild) throws FullDirectoryException, NotADirectoryException
	{
		if(size == 10)
			throw new FullDirectoryException();
		if(isFile)
			throw new NotADirectoryException();
		setNodeChildren(size, newChild);
	}
	
	/**
	 * Prints out the subtree of the current node in preorder traversal
	 * @param indent the number of indents
	 */
	public void preOrderPrint(String indent)
	{
		if(isFile)
		{
			String str = indent.substring(0,indent.indexOf("|"));
			str += " " + indent.substring(indent.indexOf("-"));
			System.out.println(str + name);
		}
		else
			System.out.println(indent + name);
		indent = "    " + indent;
		for(int i = 0; i < size; i++)
		{
			childNodes[i].preOrderPrint(indent);
		}
	}
	
	/**
	 * Finds a given file/directory then prints out the path
	 * @param name the file/directory to fin
	 * @param curPath the array storing the path to the file
	 * @param length the length of curPath[]
	 */
	public void find(String name, String[] curPath, int length)
	{
		curPath[length - 1] = this.name;
		String newPath[]= new String[length + 1];
		for(int i = 0; i < curPath.length ; i++)
			newPath[i] = curPath[i];
		length++;
		if(this.name.equals(name))
		{
			for(int i = 0; i < curPath.length; i++)
			{
				if(i == curPath.length - 1)
					System.out.print(curPath[i]);
				else 
					System.out.print(curPath[i] + "/");
			}
			System.out.println();
		}
		else
			for(int i = 0; i < size; i++)
			{
				if(childNodes[i].containsName(name))
				{	
					childNodes[i].find(name, newPath, length);
				}
			}
		
	}
	
	/**
	 * Checks if the current subtree contains a file/directory
	 * @param name the name of the file/directory
	 * @return true if within the subtree, false otherwise
	 */
	public boolean containsName(String name)
	{
		if(this.name.equals(name))
			return true;
		for(int i = 0; i < size; i++)
			if(childNodes[i].containsName(name))
				return true;
		return false;
	}
	
	/**
	 * Gets the current path of the node to the root
	 * @return the path to the root
	 */
	public String getNodePath()
	{
		DirectoryNode pointer = this;
		String str = "";
		while(pointer != null)
		{
			if(pointer.getName().equals("root"))
				str = pointer.getName() + str;
			else 
				str = "/" + pointer.getName() + str;
			pointer = pointer.getParent();
		}
		return str;	
	}
	
	/**
	 * Advances the node down the tree to the child that matches name
	 * @param root the start of the node
	 * @param name the name of the child
	 * @return the DirectoryNode child that matches name
	 */
	public DirectoryNode advanceNode(DirectoryNode root ,String name)
	{
		if(root.getName().equals(name))
			return root;
		for(DirectoryNode n : childNodes)
		{
			if(n.getName().equals(name))
				return n;
		}
		return null;
	}
}
