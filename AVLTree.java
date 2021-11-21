/**
 *
 * AVLTree
 *
 * An implementation of an AVL Tree with
 * distinct integer keys and info.
 *
 */

public class AVLTree {
	
	IAVLNode root;
  	final IAVLNode VIRTUAL_NODE = new AVLNode();

  	public AVLTree() {
  		this.root = null;
  	}
	
  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   */
	public boolean empty() {
		if (this.root == null) {
			return true;
		}
		return false;
	}

 /**
   * public String search(int k)
   *
   * Returns the info of an item with key k if it exists in the tree.
   * otherwise, returns null.
   */
  public String search(int k) {
	  IAVLNode pointer = this.root;
	  while (pointer != null) {
		  if (pointer.getKey() == k) {
			  return pointer.getValue();
		  }
		  else if (pointer.getKey() < k) {
			  pointer = pointer.getRight();
		  }
		  else {
			  pointer = pointer.getLeft();
		  }
	  }
	  return null;
  }

  /**
   * public int insert(int k, String i)
   *
   * Inserts an item with key k and info i to the AVL tree.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {
	   if (this.empty()) {
		   this.root = new AVLNode(k, i, null, VIRTUAL_NODE, VIRTUAL_NODE);
	   }
	   //else if ()
	   //else {
		   
	   //}
	  return 420;	// to be replaced by student code
   }

  /**
   * public int delete(int k)
   *
   * Deletes an item with key k from the binary tree, if it is there.
   * The tree must remain valid, i.e. keep its invariants.
   * Returns the number of re-balancing operations, or 0 if no re-balancing operations were necessary.
   * A promotion/rotation counts as one re-balance operation, double-rotation is counted as 2.
   * Returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k) {
	   return 421;	// to be replaced by student code
   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min() {
	   return "minDefaultString"; // to be replaced by student code
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max() {
	   return "maxDefaultString"; // to be replaced by student code
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray() {
        return new int[33]; // to be replaced by student code
  }

  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray() {
        return new String[55]; // to be replaced by student code
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    */
   public int size() {
	   return 422; // to be replaced by student code
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    */
   public IAVLNode getRoot() {
	   return null;
   }
   
   /**
    * public AVLTree[] split(int x)
    *
    * splits the tree into 2 trees according to the key x. 
    * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
    * 
	* precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
    * postcondition: none
    */   
   public AVLTree[] split(int x) {
	   return null; 
   }
   
   /**
    * public int join(IAVLNode x, AVLTree t)
    *
    * joins t and x with the tree. 	
    * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
	*
	* precondition: keys(t) < x < keys() or keys(t) > x > keys(). t/tree might be empty (rank = -1).
    * postcondition: none
    */   
   	public int join(IAVLNode x, AVLTree t) {
   		return -1;
   	}

	/** 
	 * public interface IAVLNode
	 * ! Do not delete or modify this - otherwise all tests will fail !
	 */
	public interface IAVLNode{	
		public int getKey(); // Returns node's key (for virtual node return -1).
		public String getValue(); // Returns node's value [info], for virtual node returns null.
		public void setLeft(IAVLNode node); // Sets left child.
		public IAVLNode getLeft(); // Returns left child, if there is no left child returns null.
		public void setRight(IAVLNode node); // Sets right child.
		public IAVLNode getRight(); // Returns right child, if there is no right child return null.
		public void setParent(IAVLNode node); // Sets parent.
		public IAVLNode getParent(); // Returns the parent, if there is no parent return null.
		public boolean isRealNode(); // Returns True if this is a non-virtual AVL node.
    	public void setHeight(int height); // Sets the height of the node.
    	public int getHeight(); // Returns the height of the node (-1 for virtual nodes).
	}

	/** 
    * public class AVLNode
    *
    * If you wish to implement classes other than AVLTree
    * (for example AVLNode), do it in this file, not in another file. 
    * 
    * This class can and MUST be modified (It must implement IAVLNode).
    */
	public class AVLNode implements IAVLNode {
	  	
	  	int key;
	  	String info;
	  	int height;
	  	IAVLNode parent;
	  	IAVLNode left;
	  	IAVLNode right;
	  	
	  	public AVLNode() {
	  		this.key = -1;
	  		this.height = -1;
	  	}
	  	
	  	public AVLNode(int key, String info, IAVLNode parent, IAVLNode left_child, IAVLNode right_child) {
	  		this.key = key;
	  		this.info = info;
	  		setParent(parent);
	  		setLeft(left_child);
	  		setRight(right_child);
	  		setHeight(1 + Math.max(left_child.getHeight(), right_child.getHeight()));
	  	}

	  
		public int getKey() {
			return this.key;
		}
		
		public String getValue() {
			return this.info;
		}
		
		public void setLeft(IAVLNode node) {
			this.left = node;
		}
		
		public IAVLNode getLeft() {
			return this.left;
		}
		
		public void setRight(IAVLNode node) {
			this.right = node;
		}
		
		public IAVLNode getRight() {
			return this.right;
		}
		
		public void setParent(IAVLNode node) {
			this.parent = node;
		}
		
		public IAVLNode getParent() {
			return this.parent;
		}
		
		public boolean isRealNode() {
			if ((this.key == -1) && (this.height == -1)) {
				return false;
			}
			return true;
		}
	    
		public void setHeight(int height) {
	      this.height = height;
	    }
		
	    public int getHeight() {
	      return this.height;
	    }
  }

}
