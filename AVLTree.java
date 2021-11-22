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
  	int inOrderCounter = 0;
  	int rebalancingCounter = 0;

  	public AVLTree() { // Time Complexity: O(1)
  		this.root = null;
  	}
	
  /**
   * public boolean empty()
   *
   * Returns true if and only if the tree is empty.
   *
   */
	public boolean empty() { // Time Complexity: O(1)
		if (this.getRoot() == null) {
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
  public String search(int k) { // Time Complexity: O(log n)
	   if (this.empty()) {
		   return null;
	   }	  
	  IAVLNode current = this.getRoot();
	  while (current != VIRTUAL_NODE) {
		  if (current.getKey() == k) {
			  return current.getValue();
		  }
		  else if (current.getKey() < k) {
			  current = current.getRight();
		  }
		  else {
			  current = current.getLeft();
		  }
	  }
	  return null;
  }

  private void updateHeight(IAVLNode node) {  // Time Complexity: O(1)
	  node.setHeight(1 + Math.max(node.getLeft().getHeight(), node.getRight().getHeight()));
  }
  
  private IAVLNode position(int k) {  // Time Complexity: O(log n)
	  IAVLNode current = this.getRoot();
	  IAVLNode dest_parent = current;
	  while (current != VIRTUAL_NODE) {
		  dest_parent = current;
		  if (current.getKey() == k) {
			  return current;
		  }
		  else if (current.getKey() < k) {
			  current = current.getRight();
		  }
		  else {
			  current = current.getLeft();
		  }
	  }
	  return dest_parent;
  }
  
  private int getBF(IAVLNode node) { // Time Complexity: O(1)
	  return (node.getLeft().getHeight() - node.getRight().getHeight());
  }

  
  private void promote() { // Time Complexity: O(1)
	  rebalancingCounter += 1;
  }

  
  private void rightRotation(IAVLNode node) { // Time Complexity: O(1)
	  IAVLNode new_parent = node.getLeft();
	  IAVLNode former_right = new_parent.getRight();
	  IAVLNode ancestor = node.getParent();
	  if (ancestor == null) {
		  this.root = new_parent;
	  }
	  else if (ancestor.getLeft() == node) {
		  ancestor.setLeft(new_parent);
	  }
	  else {
		  ancestor.setRight(new_parent);
	  }
	  new_parent.setRight(node);
	  node.setLeft(former_right);
	  if (former_right != VIRTUAL_NODE) {
		  former_right.setParent(node);
	  }
	  node.setParent(new_parent);
	  new_parent.setParent(ancestor);
	  updateHeight(node);
	  updateHeight(new_parent);
	  rebalancingCounter += 1;
  }

  
  private void leftRotation(IAVLNode node) { // Time Complexity: O(1)
	  IAVLNode new_parent = node.getRight();
	  IAVLNode former_left = new_parent.getLeft();
	  IAVLNode ancestor = node.getParent();
	  if (ancestor == null) {
		  this.root = new_parent;
	  }
	  else if (ancestor.getLeft() == node) {
		  ancestor.setLeft(new_parent);
	  }
	  else {
		  ancestor.setRight(new_parent);
	  }
	  new_parent.setLeft(node);
	  node.setRight(former_left);
	  if (former_left != VIRTUAL_NODE) {
		  former_left.setParent(node);
	  }
	  node.setParent(new_parent);
	  new_parent.setParent(ancestor);
	  updateHeight(node);
	  updateHeight(new_parent);
	  rebalancingCounter += 1;
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
   public int insert(int k, String i) { // Time Complexity: O(log n)
	   IAVLNode new_node = new AVLNode(k, i, null, VIRTUAL_NODE, VIRTUAL_NODE);
	   if (this.empty()) {
		   this.root = new_node;
		   return 0;
	   }
	   
	   IAVLNode dest_parent = position(k);
	   if (dest_parent.getKey() == k) {
		   return -1;
	   }

	   new_node.setParent(dest_parent);
	   if (dest_parent.getKey() < k) {
		   dest_parent.setRight(new_node);
	   }
	   else { // dest_parent.getKey() > k
		   dest_parent.setLeft(new_node);
	   }
	   
	   IAVLNode current = dest_parent;
	   while (current != null) {
		   int BF = getBF(current);
		   int prev_height = current.getHeight();
		   updateHeight(current);
		   int curr_height = current.getHeight();
		   if (Math.abs(BF) < 2 && prev_height == curr_height) {
			   current = current.getParent();
			   continue;
		   }
		   else if (Math.abs(BF) < 2) { // promote
			   promote();
			   current = current.getParent();
		   }
		   else { // rotation/s then terminate
			   if (BF == -2) {
				   IAVLNode r_son = current.getRight();
				   int R_SON_BF = getBF(r_son);
				   if (R_SON_BF == -1) {
					   leftRotation(current);
				   }
				   else { // (R_SON_BF == 1)
					   rightRotation(r_son);
					   leftRotation(current);
				   }
			   }
			   else { // (BF == 2)
				   IAVLNode l_son = current.getLeft();
				   int L_SON_BF = getBF(l_son);
				   if (L_SON_BF == 1) {
					   rightRotation(current);
				   }
				   else { // (L_SON_BF == -1)
					   leftRotation(l_son);
					   rightRotation(current);
				   }
			   }
			   break;
		   }
	   }
	  
	  int res = rebalancingCounter;
	  rebalancingCounter = 0;
	  return res;
   }

   private void removeLeaf(IAVLNode father, IAVLNode toBeRemoved) { // Time Complexity: O(log n)
		  if (father != null && father.getLeft() == toBeRemoved) {
			  father.setLeft(VIRTUAL_NODE);
			  while (father != null) {
				  updateHeight(father);
				  father = father.getParent();
			  }
			  
		  }
		  else if (father != null && father.getRight() == toBeRemoved) {
			  father.setRight(VIRTUAL_NODE);
			  while (father != null) {
				  updateHeight(father);
				  father = father.getParent();
			  }
		  }
		  else {
			  this.root = null;
		  }
   }
   
   private void removeByPass(IAVLNode father, IAVLNode toBeRemoved) { // Time Complexity: O(log n)
	   if (father != null && father.getLeft() == toBeRemoved) {
		   if (toBeRemoved.getLeft() == VIRTUAL_NODE) {
			   father.setLeft(toBeRemoved.getRight());
		   }
		   else {
			   father.setLeft(toBeRemoved.getLeft());
		   }
		   if (father.getLeft() != VIRTUAL_NODE) {
			   father.getLeft().setParent(father);
		   }
		   while (father != null) {
			   updateHeight(father);
			   father = father.getParent();
			   }

	   }
	   else if (father != null && father.getRight() == toBeRemoved) {
		   if (toBeRemoved.getLeft() == VIRTUAL_NODE) {
			   father.setRight(toBeRemoved.getRight());
		   }
		   else {
			   father.setRight(toBeRemoved.getLeft());
		   }
		   if (father.getRight() != VIRTUAL_NODE) {
			   father.getRight().setParent(father);
		   }
		   while (father != null) {
			   updateHeight(father);
			   father = father.getParent();
			   }
	   }
	   else if (father == null && toBeRemoved.getLeft() == VIRTUAL_NODE) {
		   this.root = toBeRemoved.getRight();
	   }
	   else { // (father == null && toBeRemoved.getRight() == VIRTUAL_NODE)
		   this.root = toBeRemoved.getLeft();
	   }

   }

   private IAVLNode successor(IAVLNode node) { // Time Complexity: O(log n)
	   if (node.getRight() != VIRTUAL_NODE) {
		   IAVLNode current = node.getRight();
		   while (current.getLeft() != VIRTUAL_NODE) {
			   current = current.getLeft();
		   }
		   return current;
	   }
	   
	   IAVLNode current = node;
	   IAVLNode father = node.getParent();
	   while (father != null && current == father.getRight()) {
		   current = father;
		   father = current.getParent();
	   }
	   if (father == null) {
		   return null;
	   }
	   return father;
   }
   
   private void replaceNeighbors(IAVLNode father, IAVLNode son) { // Time Complexity: O(1)
	   if (father.getRight() == son) {
		   father.setRight(father);   
	   }
	   else {
		   father.setLeft(father);
	   }
	   son.setParent(son);
   }
   
   
   private void replace(IAVLNode toBeRemoved, IAVLNode successor) { // Time Complexity: O(1)
	   if (toBeRemoved.getParent() == successor) {
		   replaceNeighbors(successor, toBeRemoved);
	   }
	   else if (successor.getParent() == toBeRemoved) {
		   replaceNeighbors(toBeRemoved, successor);
	   }
	   
	   IAVLNode new_parent = toBeRemoved.getParent();
	   IAVLNode new_right = toBeRemoved.getRight();
	   IAVLNode new_left = toBeRemoved.getLeft();
	   int new_height = toBeRemoved.getHeight();
	   
	   IAVLNode old_parent = successor.getParent();
	   IAVLNode old_right = successor.getRight();
	   IAVLNode old_left = successor.getLeft();
	   int old_height = successor.getHeight();
	   
	   successor.setParent(new_parent);
	   successor.setRight(new_right);
	   successor.setLeft(new_left);
	   successor.setHeight(new_height);
	   
	   toBeRemoved.setParent(old_parent);
	   toBeRemoved.setRight(old_right);
	   toBeRemoved.setLeft(old_left);
	   toBeRemoved.setHeight(old_height);
	   
	   if (successor.getParent() == null) {
		   this.root = successor;
	   }
	   else if (successor.getParent().getLeft() == toBeRemoved) {
		   successor.getParent().setLeft(successor);
	   }
	   else {
		   successor.getParent().setRight(successor);
	   }
	   
	   if (toBeRemoved.getParent() == null) {
		   this.root = toBeRemoved;
	   }
	   else if (toBeRemoved.getParent().getLeft() == successor) {
		   toBeRemoved.getParent().setLeft(toBeRemoved);
	   }
	   else {
		   toBeRemoved.getParent().setRight(toBeRemoved);
	   }
	   
	   successor.getLeft().setParent(successor); // toBeRemoved had originally 2 children
	   successor.getRight().setParent(successor);
	   
	   if (toBeRemoved.getRight() != VIRTUAL_NODE) { // toBeRemoved (now) doesn't have a left child
		   toBeRemoved.getRight().setParent(toBeRemoved);
	   }
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
   public int delete(int k) { // Time Complexity: O(log n)
	   if (this.empty()) {
		   return -1;
	   }
	   
	   IAVLNode node_position = position(k);
	   if (node_position.getKey() != k) { // key was not found in tree
		   return -1;
	   }
	   
	   IAVLNode current = node_position.getParent(); // current is the deleted node's father
	   
	   if (node_position.getLeft() == VIRTUAL_NODE && node_position.getRight() == VIRTUAL_NODE) { // delete leaf
		   removeLeaf(current, node_position);
	   }
	   
	   else if (node_position.getLeft() == VIRTUAL_NODE || node_position.getRight() == VIRTUAL_NODE) { // delete bypass
		   removeByPass(current, node_position);
	   }
	   
	   else { // replace, then delete
		   IAVLNode successor = successor(node_position);
		   if (successor != null) { // replace
			   replace(node_position, successor);
		   }
		   
		   if (node_position.getRight() == VIRTUAL_NODE) { // node now has no left child
			   removeLeaf(node_position.getParent(), node_position);
		   }
		   else { // (node_position.getRight() != VIRTUAL_NODE)
			   removeByPass(node_position.getParent(), node_position);
		   }
	   }
	   
	   while (current != null) {
		   int BF = getBF(current);
		   int prev_height = current.getHeight();
		   current.setHeight(1 + Math.max(current.getLeft().getHeight(), current.getRight().getHeight()));
		   int curr_height = current.getHeight();
		   if (Math.abs(BF) < 2 && prev_height == curr_height) {
			   current = current.getParent();
			   continue;
		   }
		   else if (Math.abs(BF) < 2) { // promote
			   promote();
			   current = current.getParent();
		   }
		   else { // rotation/s
			   if (BF == -2) {
				   IAVLNode r_son = current.getRight();
				   int R_SON_BF = getBF(r_son);
				   if (R_SON_BF == -1 || R_SON_BF == 0) { // +extra case
					   leftRotation(current);
				   }
				   else { // (R_SON_BF == 1)
					   rightRotation(r_son);
					   leftRotation(current);
				   }
			   }
			   else { // (BF == 2)
				   IAVLNode l_son = current.getLeft();
				   int L_SON_BF = getBF(l_son);
				   if (L_SON_BF == 1 || L_SON_BF == 0) { // +extra case
					   rightRotation(current);
				   }
				   else { // (L_SON_BF == -1)
					   leftRotation(l_son);
					   rightRotation(current);
				   }
			   }
			   current = current.getParent(); // without termination
		   }
	   }
	  
	  int res = rebalancingCounter;
	  rebalancingCounter = 0;
	  return res;

   }

   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty.
    */
   public String min() { // Time Complexity: O(log n)
	   if (this.empty()) {
		   return null;
	   }
	   IAVLNode current = this.getRoot();
	   while (current.getLeft() != VIRTUAL_NODE) {
		   current = current.getLeft();
	   }
	   return current.getValue();
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty.
    */
   public String max() { // Time Complexity: O(log n)
	   if (this.empty()) {
		   return null;
	   }
	   IAVLNode current = this.getRoot();
	   while (current.getRight() != VIRTUAL_NODE) {
		   current = current.getRight();
	   }
	   return current.getValue();
   }

   private void inOrderKeys(IAVLNode node, int[] array) { // Time Complexity: O(n)
	   if (node == VIRTUAL_NODE) {
		   return;
	   }
	   
	   inOrderKeys(node.getLeft(), array);
	   array[inOrderCounter] = node.getKey();
	   inOrderCounter += 1;
	   inOrderKeys(node.getRight(), array);
	   
   }
   
   
  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray() { // Time Complexity: O(n)
	   if (this.empty()) {
		   int[] res = {};
		   return res;
	   }
	   
	   int[] res = new int[this.size()];
	   inOrderKeys(this.getRoot(), res);
	   inOrderCounter = 0;
       return res;
  }

  private void inOrderValues(IAVLNode node, String[] array) { // Time Complexity: O(n)
	   if (node == VIRTUAL_NODE) {
		   return;
	   }
	   
	   inOrderValues(node.getLeft(), array);
	   array[inOrderCounter] = node.getValue();
	   inOrderCounter += 1;
	   inOrderValues(node.getRight(), array);
	   
  }
  
  
  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray() { // Time Complexity: O(n)
	   if (this.empty()) {
		   String[] res = {};
		   return res;
	   }
	   
	   String[] res = new String[this.size()];
	   inOrderValues(this.getRoot(), res);
	   inOrderCounter = 0;
       return res;
  }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    */
   public int size() { // Time Complexity: O(1) /////////////// modify!!!
	   return 20; // to be replaced by student code
   }
   
   /**
    * public int getRoot()
    *
    * Returns the root AVL node, or null if the tree is empty
    */
   public IAVLNode getRoot() { // Time Complexity: O(1)
	   return this.root;
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
   public AVLTree[] split(int x) { // Time Complexity: O(log n)
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
   	public int join(IAVLNode x, AVLTree t) { // Time Complexity: O(log n)
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
	  	
	  	public AVLNode() { // Time Complexity: O(1)
	  		this.key = -1;
	  		this.height = -1;
	  	}
	  	
	  	public AVLNode(int key, String info, IAVLNode parent, IAVLNode left_child, IAVLNode right_child) { // Time Complexity: O(1)
	  		this.key = key;
	  		this.info = info;
	  		setParent(parent);
	  		setLeft(left_child);
	  		setRight(right_child);
	  		setHeight(1 + Math.max(left_child.getHeight(), right_child.getHeight()));
	  	}

	  
		public int getKey() { // Time Complexity: O(1)
			return this.key;
		}
		
		public String getValue() { // Time Complexity: O(1)
			return this.info;
		}
		
		public void setLeft(IAVLNode node) { // Time Complexity: O(1)
			this.left = node;
		}
		
		public IAVLNode getLeft() { // Time Complexity: O(1)
			return this.left;
		}
		
		public void setRight(IAVLNode node) { // Time Complexity: O(1)
			this.right = node;
		}
		
		public IAVLNode getRight() { // Time Complexity: O(1)
			return this.right;
		}
		
		public void setParent(IAVLNode node) { // Time Complexity: O(1)
			this.parent = node;
		}
		
		public IAVLNode getParent() { // Time Complexity: O(1)
			return this.parent;
		}
		
		public boolean isRealNode() { // Time Complexity: O(1)
			if ((this.key == -1) && (this.height == -1)) {
				return false;
			}
			return true;
		}
	    
		public void setHeight(int height) { // Time Complexity: O(1)
	      this.height = height;
	    }
		
	    public int getHeight() { // Time Complexity: O(1)
	      return this.height;
	    }
  }

}
