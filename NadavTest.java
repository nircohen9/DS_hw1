import java.util.Arrays;

public class NadavTest {
    public static void main(String[] args) {
        AVLTree t1 = new AVLTree();
        //AVLTree.IAVLNode n1 = t1.new AVLNode();
        t1.insert(14, "Oak");
        t1.insert(12, "Birch");
        t1.insert(18, "Pine");
        t1.insert(22, "Sycamore");
        t1.insert(101, "Baobab");
        t1.insert(-22, "Maple");
        t1.insert(55, "Pecan");
        t1.insert(9999, "Elderberry");

        System.out.println(Arrays.toString(t1.keysToArray()));
        System.out.println(Arrays.toString(t1.infoToArray()));
        System.out.println(t1.getRoot().getHeight());


        AVLTree t2 = new AVLTree();
        //AVLTree.IAVLNode n1 = t1.new AVLNode();
        t2.insert(-100, "Cherry");
        t2.insert(-101, "Peach");
        t2.insert(-111, "Grape");
        t2.insert(-1000, "Blueberry");
        t2.insert(-1001, "Honeysuckle");
        t2.insert(-50, "Cinnamon");
        t2.insert(-1040, "Plum");
        t2.insert(-9999, "Grapefruit");
        t2.insert(-5555, "Watermelon");
        t2.insert(-5000, "Rhubarb");
        t2.insert(-4000, "Melon");
        t2.insert(-40000, "Kiwi");
        t2.insert(-40010, "Banana");
        t2.insert(-40020, "Orange");




        System.out.println(Arrays.toString(t2.keysToArray()));
        System.out.println(Arrays.toString(t2.infoToArray()));
        System.out.println(t2.getRoot().getHeight());


        AVLTree.IAVLNode x = t2.new AVLNode(-30, "DR X", null, t1.VIRTUAL_NODE, t1.VIRTUAL_NODE);

        /*

        System.out.println(t2.join(x, t1));
        System.out.println(Arrays.toString(t2.keysToArray()));
        System.out.println(Arrays.toString(t2.infoToArray()));
        System.out.println(t2.getRoot().getHeight());

         */




        System.out.println(t1.join(x, t2));

        /*AVLTree.IAVLNode t = t2.root;
        while (t != null) {
            System.out.println(t.getValue());
            t = t.getRight();
        }

         */
        AVLTree.IAVLNode ttt = t2.getRoot();
        //System.out.println(ttt.getRight().getKey());

        System.out.println(Arrays.toString(t1.keysToArray()));
        System.out.println(Arrays.toString(t1.infoToArray()));
        System.out.println(t1.getRoot().getHeight());






        System.out.println("\n \n \n Done!");

    }
}
