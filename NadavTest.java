import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;


public class NadavTest {
    public static void autoTel(int n, int S, boolean print) {
        /*
        AVLTree[] forest = new AVLTree[n];
        for (int i=0; i<n; i++) {
            AVLTree randomTree = new AVLTree();
            int treeSize = ThreadLocalRandom.current().nextInt(0, S);
            for (int j=0; j<treeSize; j++) {
                int key = ThreadLocalRandom.current().nextInt(0, 10000);
                randomTree.insert(key, Integer.toString(key));
            }
            if (randomTree.size() != treeSize) System.out.println("ERROR in size: tree #" + i + "has size" +randomTree.size() + "and should have " + treeSize);
            forest[i] = randomTree;
        }



        for (int i=0; i<n; i++) {
            if (print) Fahn.treePrinter(forest[i].getRoot());
            for (int j=i+1; j<n; j++) {
                AVLTree.IAVLNode DR_X = forest[0].new AVLNode(90000011, "DR X", null, forest[0].VIRTUAL_NODE, forest[0].VIRTUAL_NODE);
                if (print) Fahn.treePrinter(forest[j].getRoot());
                forest[i].join(DR_X, forest[j]);
                if (print) Fahn.treePrinter(forest[i].getRoot());
                forest[i].split(DR_X.getKey());
            }


        }

         */
    }



    public static void main(String[] args) {
        AVLTree.IAVLNode ttttt = null;

        autoTel(1000,5000, true);

        AVLTree t0 = new AVLTree();
        //System.out.println(t0.getRoot().getHeight());

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

        //Fahn.treePrinter(t1.getRoot());







        //System.out.println(Arrays.toString(t1.keysToArray()));
        //System.out.println(Arrays.toString(t1.infoToArray()));
        //System.out.println(t1.size());

        /*
        System.out.println(t1.getRoot());
        System.out.println(t1.getRoot().getLeft().getValue());
        System.out.println(t1.getRoot().getLeft().getRight().getValue());

         */



        AVLTree[] arr = t1.split(22);
        //Fahn.treePrinter(arr[0].getRoot());


        System.out.println(arr[0].getRoot().getValue());
        System.out.println(arr[0].getRoot().getLeft().getValue());
        System.out.println(arr[0].getRoot().getRight().getRight().getValue());
        System.out.println(arr[0].getRoot().getValue());






        System.out.println(arr[0].size());
        System.out.println(arr[1].size());



        System.out.println(Arrays.toString((arr[0]).infoToArray()));
        System.out.println(Arrays.toString((arr[1]).infoToArray()));





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

        System.out.println(t2.size());

        AVLTree[] ttt2 = t2.split(-5555);
        Fahn.treePrinter(ttt2[0].getRoot());
        Fahn.treePrinter(ttt2[1].getRoot());




        System.out.println(Arrays.toString(t2.keysToArray()));
        System.out.println(Arrays.toString(t2.infoToArray()));
        System.out.println(t2.getRoot().getHeight());


        AVLTree.IAVLNode x = t2.new AVLNode(-30, "DR X", null, t1.VIRTUAL_NODE, t1.VIRTUAL_NODE);
        //t1.join(x, t0);
        //System.out.println(Arrays.toString(t1.keysToArray()));

        /*

        System.out.println(t2.join(x, t1));
        System.out.println(Arrays.toString(t2.keysToArray()));
        System.out.println(Arrays.toString(t2.infoToArray()));
        System.out.println(t2.getRoot().getHeight());





        System.out.println(t1.join(x, t2));

        /*AVLTree.IAVLNode t = t2.root;
        while (t != null) {
            System.out.println(t.getValue());
            t = t.getRight();
        }




        AVLTree.IAVLNode ttt = t2.getRoot();
        //System.out.println(ttt.getRight().getKey());

        System.out.println(Arrays.toString(t1.keysToArray()));
        System.out.println(Arrays.toString(t1.infoToArray()));
        System.out.println(t1.getRoot().getHeight());
        System.out.println(t1.size());
        System.out.println(t1.delete(-40000));
        System.out.println(t1.infoToArray().length);
        System.out.println(t1.size());










*/

        System.out.println("\n \n \n Done!");





    }
}
