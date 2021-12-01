import java.util.ArrayList;

public class Fahn {//prints the tree level by level until the last virtual node
    // V - virtual node
    //N - null
    public static void treePrinter(AVLTree.IAVLNode root){
        ArrayList<AVLTree.IAVLNode> currList = new ArrayList<>();
        currList.add(root);
        int level = root.getHeight();

        while (currList.size() > 0) {

            String space = "  ";

            for (int i = 0; i < level; i++) {
                space = space + space;
            }
            level--;
            System.out.print(space);


            ArrayList<AVLTree.IAVLNode> childrenList = new ArrayList<>();

            for (AVLTree.IAVLNode node: currList) {
                if (node != null && node.isRealNode()) {
                    System.out.print(node.getValue() + space);
                    childrenList.add(node.getLeft());
                    childrenList.add(node.getRight());
                }
                else if (node != null) {
                    System.out.print("V");
                    System.out.print(space);
                    childrenList.add(null);
                    childrenList.add(null);

                }
                else { //node == null
                    System.out.print("N");
                    System.out.print(space);

                    childrenList.add(null);
                    childrenList.add(null);
                }

            }
            boolean onlyNull = true;

            for (int i = 0; i < childrenList.size(); i++) {
                if (childrenList.get(i) != null) {
                    onlyNull = false;
                    break;
                }
            }
            if (onlyNull) {
                break;
            }
            currList = childrenList;
            childrenList = new ArrayList<>();

            System.out.println();
            System.out.println();
        }
        System.out.println("\n \n \n");
    }

}
