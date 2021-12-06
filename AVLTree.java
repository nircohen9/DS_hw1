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
	String MIN = null;
	String MAX = null;
	int inOrderCounter = 0;
	int rebalancingCounter = 0;

	public AVLTree() { // Create an empty tree; Time Complexity: O(1)
		this.root = null;
	}

	public AVLTree(IAVLNode root) { //Create tree from root; Time Complexity: O(1)
		this.root = root;
		if (this.root.getParent() != null) {//make this an independent tree.
			IAVLNode p = this.root.getParent();
			if (p.getRight() == root) { //p is right parent
				p.setRight(this.VIRTUAL_NODE);
			}
			else { //p is left parent
				p.setLeft(this.VIRTUAL_NODE);
			}
			updateHeight(p);
			p.updateSize();
			this.root.setParent(null); //sever this root from its parent
		}
		if (!root.isRealNode()) {//if root is null: create an empty tree (this is expected behavior)
			this.root = null;
			return;
		}
		this.root.updateSize();
		updateHeight(root);
	}

	/**
	 * public boolean empty()
	 *
	 * Returns true if and only if the tree is empty.
	 *
	 */
	public boolean empty() { // Time Complexity: O(1)
		return this.getRoot() == null;
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
		while (current.isRealNode()) {
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

	private int updateHeight(IAVLNode node) {  // Time Complexity: O(1)
		node.setHeight(1 + Math.max(node.getLeft().getHeight(), node.getRight().getHeight()));
		return node.getHeight();
	}

	private IAVLNode position(int k) {  // Time Complexity: O(log n)
		//finds the appropriate position for a new element to be inserted
		IAVLNode current = this.getRoot();
		IAVLNode dest_parent = current;
		while (current.isRealNode()) {
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
		this.rebalancingCounter += 1;
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
		if (former_right.isRealNode()) {
			former_right.setParent(node);
		}
		node.setParent(new_parent);
		new_parent.setParent(ancestor);
		updateHeight(node);
		node.updateSize();
		updateHeight(new_parent);
		new_parent.updateSize();
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
		if (former_left.isRealNode()) {
			former_left.setParent(node);
		}
		node.setParent(new_parent);
		new_parent.setParent(ancestor);
		updateHeight(node);
		node.updateSize();
		updateHeight(new_parent);
		new_parent.updateSize();
		rebalancingCounter += 1;
	}

	private void balanceUp(IAVLNode v) { // Time Complexity: O(log n)
		IAVLNode current = v;
		while (current != null) {
			int BF = getBF(current); //check BF after operation to ascertain compliance with inv
			if (Math.abs(BF) < 2 && current.getHeight() != updateHeight(current)) { //Node is compliant with AVL inv
				promote();
			}
			else { //rotate to solve imbalance and restore inv
				if (BF == -2) { //tilts right
					IAVLNode r_son = current.getRight();
					int R_SON_BF = getBF(r_son);
					if (R_SON_BF == -1 || R_SON_BF ==0) { //tree is right-heavy
						leftRotation(current);
					}
					else if (R_SON_BF == 1) { //tree is right-left heavy
						rightRotation(r_son);
						leftRotation(current);
					}
				}
				else if (BF == 2) { //tilts left
					IAVLNode l_son = current.getLeft();
					int L_SON_BF = getBF(l_son);
					if (L_SON_BF == 1 || L_SON_BF==0) { //tree is left heavy
						rightRotation(current);
					}
					else if (L_SON_BF == -1){ //tree is left-right heavy
						leftRotation(l_son);
						rightRotation(current);
					}
				}
			}
			current.updateSize();
			updateHeight(current);
			current = current.getParent(); //step-up and verify balance upwards
		}
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
			new_node.updateSize();
			return 0;
		}

		IAVLNode dest_parent = position(k);
		if (dest_parent.getKey() == k) {  //key already exists in tree
			return -1;
		}

		new_node.setParent(dest_parent);
		if (dest_parent.getKey() < k) {
			dest_parent.setRight(new_node);
		}
		else { // dest_parent.getKey() > k
			dest_parent.setLeft(new_node);
		}

		balanceUp(dest_parent); //performs promotions and rotations as necessary to restore balance

		this.updateMin();
		this.updateMax();
		int res = rebalancingCounter;
		rebalancingCounter = 0;
		return res;
	}

	private void removeLeaf(IAVLNode father, IAVLNode toBeRemoved) { // Time Complexity: O(1)
		if (father != null && father.getLeft() == toBeRemoved) {
			father.setLeft(VIRTUAL_NODE);

		}
		else if (father != null && father.getRight() == toBeRemoved) {
			father.setRight(VIRTUAL_NODE);
		}
		else {
			this.root = null;
		}
	}

	private void removeByPass(IAVLNode father, IAVLNode toBeRemoved) { // Time Complexity: O(1)
		if (father != null && father.getLeft() == toBeRemoved) {
			if (!toBeRemoved.getLeft().isRealNode()) {
				father.setLeft(toBeRemoved.getRight());
			}
			else {
				father.setLeft(toBeRemoved.getLeft());
			}
			if (father.getLeft().isRealNode()) {
				father.getLeft().setParent(father);
			}

		}
		else if (father != null && father.getRight() == toBeRemoved) {
			if (!toBeRemoved.getLeft().isRealNode()) {
				father.setRight(toBeRemoved.getRight());
			}
			else {
				father.setRight(toBeRemoved.getLeft());
			}
			if (father.getRight().isRealNode()) {
				father.getRight().setParent(father);
			}
		}
		else if (father == null && !toBeRemoved.getLeft().isRealNode()) {
			this.root = toBeRemoved.getRight();
		}
		else { // (father == null && toBeRemoved.getRight() == VIRTUAL_NODE)
			this.root = toBeRemoved.getLeft();
		}

	}

	private IAVLNode successor(IAVLNode node) { // Time Complexity: O(log n)
		if (node.getRight().isRealNode()) {
			IAVLNode current = node.getRight();
			while (current.getLeft().isRealNode()) {
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

		if (toBeRemoved.getRight().isRealNode()) { // toBeRemoved (now) doesn't have a left child
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

		if (!node_position.getLeft().isRealNode() && !node_position.getRight().isRealNode()) { // delete leaf
			removeLeaf(current, node_position);
		}

		else if (!node_position.getLeft().isRealNode() || !node_position.getRight().isRealNode()) { // delete bypass
			removeByPass(current, node_position);
		}

		else { // replace, then delete
			IAVLNode successor = successor(node_position);
			if (successor != null) { // replace
				replace(node_position, successor);
			}

			current = node_position.getParent();
			if (!node_position.getRight().isRealNode()) { // node now has no left child
				removeLeaf(current, node_position);
			}
			else { // (node_position.getRight() != VIRTUAL_NODE)
				removeByPass(current, node_position);
			}
		}

		balanceUp(current);

		this.updateMin();
		this.updateMax();
		int res = rebalancingCounter;
		rebalancingCounter = 0;
		return res;

	}

	private void updateMin() { // Time Complexity: O(log n)
		if (this.empty()) {
			this.MIN = null;
		}
		IAVLNode current = this.getRoot();
		while (current.getLeft().isRealNode()) {
			current = current.getLeft();
		}
		this.MIN = current.getValue();
	}

	private void updateMax() { // Time Complexity: O(log n)
		if (this.empty()) {
			this.MAX = null;
		}
		IAVLNode current = this.getRoot();
		while (current.getRight().isRealNode()) {
			current = current.getRight();
		}
		this.MAX = current.getValue();
	}



	/**
	 * public String min()
	 *
	 * Returns the info of the item with the smallest key in the tree,
	 * or null if the tree is empty.
	 */
	public String min() { // Time Complexity: O(1)
		return this.MIN;
	}

	/**
	 * public String max()
	 *
	 * Returns the info of the item with the largest key in the tree,
	 * or null if the tree is empty.
	 */
	public String max() { // Time Complexity: O(1)
		return this.MAX;
	}

	private void inOrderKeys(IAVLNode node, int[] array) { // Time Complexity: O(n)
		if (!node.isRealNode()) {
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
		if (!node.isRealNode()) {
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
	public int size() { // Time Complexity: O(1)
		if (this.empty()) return 0;
		return this.getRoot().getSize();
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
		IAVLNode X = position(x); //finds the splitting point with key==x;
		AVLTree tiny = new AVLTree(X.getLeft()); //keys < x
		AVLTree HUGE = new AVLTree(X.getRight()); //x < keys

		while (X != null) {
			IAVLNode P = X.getParent();
			if (X.getKey() < x) { //L Tree
				X.setRight(VIRTUAL_NODE); //make X a floating node
				tiny.join(X, new AVLTree(X.getLeft())); //join the existing tree with any 'small' trees up the stem.

			}
			else if (X.getKey() > x) { //R Tree
				X.setLeft(VIRTUAL_NODE); //make X a floating node.
				HUGE.join(X, new AVLTree(X.getRight())); //join the existing tree with any 'big' trees up the stem.
			}
			X = P;
		}
		return new AVLTree[]{tiny,HUGE};
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
		if (t.empty() && this.empty()) {
			this.root = x;
			return 1;
		}
		else if (t.empty()) { //Join to empty tree
			joinAsymmetric(t, this, x);
			return this.getRoot().getHeight() + 2;
		}
		else if (this.empty()) { //join from empty tree
			joinAsymmetric(this,t,x);
			this.root = t.getRoot();
			return t.getRoot().getHeight() + 2;
		}

		updateHeight(this.getRoot()); t.updateHeight(t.getRoot());
		int h1 = this.getRoot().getHeight();
		int h2 = t.getRoot().getHeight();



		if (h1 == h2) { //simple join (equal heights)
			if (this.getRoot().getKey()>x.getKey()) { //'this' is right tree
				x.setRight(this.getRoot());
				this.getRoot().setParent(x);
				x.setLeft(t.getRoot());
				t.getRoot().setParent(x);

			}
			else { //'t' is right tree
				x.setRight(t.getRoot());
				t.getRoot().setParent(x);
				x.setLeft(this.getRoot());
				this.getRoot().setParent(x);

			}
			x.setParent(null);
			x.setHeight(h1 + 1);
			x.updateSize();
			this.root = x;
		}

		else if (h1 < h2) { //'t' is Taller
			joinAsymmetric(this, t, x);
			this.root = t.getRoot();
		}

		else { //'this' is taller
			joinAsymmetric(t, this, x);
		}

		return Math.abs(h1-h2) + 1;
	}


	private static void joinAsymmetric(AVLTree Short, AVLTree Tall,IAVLNode x) { // Time Complexity: O(log n)
		//We assume: rank(Short) <= rank(Tall)
		IAVLNode b = Tall.getRoot();
		boolean TallBigger = Tall.getRoot().getKey() > x.getKey();

		if (Short.empty()) {//Join between non-empty tree and empty tree (X is never empty)
			if (TallBigger) { //Tall is right tree
				while (b.getHeight() > 0) { //Travel to leftmost leaf
					if (b.getLeft().isRealNode()) b = b.getLeft();
					else b=b.getRight();
				}

				if (b.getParent() != null) {//Tall is multi-node
					IAVLNode c = b.getParent();
					x.setRight(b);
					b.setParent(x);
					x.setLeft(Tall.VIRTUAL_NODE);
					c.setLeft(x);
					x.setParent(c);
					x.setHeight(1);
					Tall.balanceUp(c);
				}
				else { //Tall is root only
					x.setRight(b);
					b.setParent(x);
					Tall.balanceUp(b);
				}
			}
			else { //Tall is left tree
				while (b.getHeight() > 0) { //Travel to Rightmost leaf
					if (b.getRight().isRealNode()) b = b.getRight();
					else b = b.getLeft();
				}
				if (b.getParent() != null) {//Tall is multi-node
					IAVLNode c = b.getParent();
					x.setLeft(b);
					b.setParent(x);
					x.setRight(Tall.VIRTUAL_NODE);
					c.setRight(x);
					x.setParent(c);
					x.setHeight(1);
					Tall.balanceUp(c);
				}
				else { //Tall is root only
					x.setLeft(b);
					b.setParent(x);
					Tall.balanceUp(b);
				}

			}
			return;
		}
		//from now on, trees are necessarily non-empty

		if (TallBigger) { //keys(Short) < keys(Tall) --> Tall is right tree
			while (b.getHeight() > Short.getRoot().getHeight()) { //Travel to rank(b) <= rank(Short)
				if (b.getLeft().isRealNode()) b = b.getLeft();
				else b = b.getRight();
			}
			//Connecting Short and Tall
			IAVLNode c = b.getParent(); //rebalancing origin
			x.setRight(b);
			b.setParent(x);
			x.setLeft(Short.getRoot());
			Short.getRoot().setParent(x);
			x.updateSize();
			c.setLeft(x);
			x.setParent(c);
			int k = Short.getRoot().getHeight();
			x.setHeight(k+1);
			if (c.getKey() < b.getKey()) c.setRight(Tall.VIRTUAL_NODE);
			Tall.balanceUp(c);			//Restoring balance to the Tree and adhering to the inv

		}
		else { //keys(short) > keys(Tall) --> Tall is left tree
			while (b.getHeight() > Short.getRoot().getHeight()) { //Travel to rank(b) <= rank(Short)
				if (b.getRight().isRealNode()) b = b.getRight();
				else b=b.getLeft();
			}
			//Connecting Short and Tall
			IAVLNode c = b.getParent(); //rebalancing origin
			x.setLeft(b);
			b.setParent(x);
			x.setRight(Short.getRoot());
			Short.getRoot().setParent(x);
			x.updateSize();
			c.setRight(x);
			x.setParent(c);
			int k = Short.getRoot().getHeight();
			x.setHeight(k+1);
			if (c.getKey() > b.getKey()) c.setLeft(Tall.VIRTUAL_NODE);
			Tall.balanceUp(c);			//Restoring balance to the Tree and adhering to the inv
		}
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

		public int getSize();
		public void updateSize();

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
		int size;
		IAVLNode parent;
		IAVLNode left;
		IAVLNode right;

		public AVLNode() { // Create Virtual Node; Time Complexity: O(1)
			this.key = -1;
			this.height = -1;
			this.size = 0;
		}

		public AVLNode(int key, String info, IAVLNode parent, IAVLNode left_child, IAVLNode right_child) { // Time Complexity: O(1)
			this.key = key;
			this.info = info;
			this.size = 1;
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
			if (node.isRealNode()) node.setParent(this);

		}

		public IAVLNode getLeft() { // Time Complexity: O(1)
			return this.left;
		}

		public void setRight(IAVLNode node) { // Time Complexity: O(1)
			this.right = node;
			if (node.isRealNode()) node.setParent(this);
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
			return (this.key != -1) || (this.height != -1);
		}

		public void setHeight(int height) { // Time Complexity: O(1)
			this.height = height;
		}

		public int getHeight() { // Time Complexity: O(1)
			return this.height;
		}

		public int getSize() { // Time Complexity: O(1)
			return this.size;
		}

		public void updateSize() { // Time Complexity: O(1)
			this.size = this.left.getSize() + this.right.getSize() +1;
		}

	}
}