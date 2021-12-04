import AVLTree.IAVLNode;

public class MoreAVL {
	
	public static void main(String[] args) {
		int[] check = {7,6,5,4,3,2,1};
		System.out.println(changes(check));
	}
	
	IAVLNode FINGER_MAX;

	private void updateFingerMax() { // Time Complexity: O(log n)
		if (this.empty()) {
			this.FINGER_MAX = null;
		}
		IAVLNode current = this.getRoot();
		while (current.getRight().isRealNode()) {
			current = current.getRight();
		}
		this.FINGER_MAX = current;
	}

	public static int changes(int[] a) {
		int n = a.length;
		int res = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i+1; j < n; j++) {
				if (a[i] > a[j]) {
					res += 1;
				}
			}
		}
		return res;
	}
	
	
	//put in insert and delete
	this.updateFingerMax();

	
}
