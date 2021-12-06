import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Experiment {
    public static int[][] sprout(int i, boolean rand) { //Creates new tree with specified size
        int N = (int) (1000 * Math.pow(2,i));
        int[] keys = new int[N];
        int searches = 0;
        if (rand) {
            AVLTree randomTree = new AVLTree();
            AVLTree randomTree2 = new AVLTree();

            Random random = new Random();
            for (int j=0; j<N;) {
                int key = random.nextInt(400*N);
                keys[j] = key;
                //int c = randomTree.Minsert(key, Integer.toString(key));
                int c = randomTree.insert(key, Integer.toString(key));
                randomTree2.insert(key, Integer.toString(key));

                if (c>=0) {
                    searches += c;
                    j++;
                }
            }
            if (randomTree.size() != N) System.out.println("Tree has size " +randomTree.size() + "and should have " + N);
            //return new int[]{searches, changes(keys)};
            int r = random.nextInt(N-1);
            AVLTree[] s = randomTree.split(keys[r]);
            //int sum1 = randomTree.splitjoinsum;
            //int avg1 = sum1/randomTree.splitjoincounter;
            //int max1 = randomTree.splitjoinmax;


            AVLTree[] ssss =  randomTree2.split(getLeftMax(randomTree2.getRoot()));
            //int sum2 = randomTree2.splitjoinsum;
            //int avg2 = sum2/randomTree2.splitjoincounter;
            //int max2 = randomTree2.splitjoinmax;


            //s[0].join(randomTree.MSearch(r), s[1]);
            //s[0].updateFingerMax();
            //s[0].split(getLeftMax(s[0].getRoot()));
            //int sss2 = s[0].splitjoincounter;
           // s[0].splitjoincounter = 0;
            //return new int[][]{{avg1, max1},{avg2, max2}};




        }
        else {
            AVLTree reverseTree = new AVLTree();
            for (int j=N; j>0; j--){
                int c = reverseTree.insert(j, Integer.toString(j));
                keys[N-j] = j;
                if (c>=0) {
                    searches += c;
                    j++;
                }

            }
            if (reverseTree.size() != N) System.out.println("Tree has size " +reverseTree.size() + " instead of " + N);        }
            //return new int[]{searches, changes(keys)};
        return null;
        }



    public static int[] sprout(int i) { //Creates new tree with specified size
        int N = (int) (1000 * Math.pow(2, i));
        AVLTree randomTree = new AVLTree();
        Random random = new Random();
        for (int j = 0; j < N; ) {
            int key = random.nextInt(400 * N);
            //int c = randomTree.Minsert(key, Integer.toString(key));
            int c = randomTree.insert(key, Integer.toString(key));
            if (c >= 0) {
                j++;
            }
        }
        //return new int[]{searches, changes(keys)};
        int r = random.nextInt(N - 1);
        AVLTree.IAVLNode b = randomTree.getRoot();
        randomTree.split(r);
        return null;


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

    public static int getLeftMax(AVLTree.IAVLNode root) {
        AVLTree.IAVLNode r = root;
        r = r.getLeft();
        while (r.getRight().isRealNode()) r= r.getRight();
        return r.getKey();
    }


    public static void main (String[] args) {
        /*
        for (int i =1; i<=5; i++) {
            System.out.println("Experiment #" +i + ":");
            System.out.println("Results for reverse sorted: " + Arrays.toString(sprout(i, false)));
            System.out.println("Results for random: " + Arrays.toString(sprout(i, true)) +"\n \n");
        }

         */


        for (int i =1; i<=8; i++) {
            System.out.println("Experiment #" +i + ":");
            int[][] arr = sprout(i, true);
            if (i==5) {
                for (int j=0; j<50; j++) {
                    System.out.print(j);
                    int[][] arr1 = sprout(i, true);
                }
            }
            //System.out.println("Results for random split: \n avg: " + arr[0][0] + " and max is: " + arr[0][1]);
            //System.out.println("Results for max split: \n avg: " + arr[1][0] + " and max is: " + arr[1][1] + "\n\n");
        }


        //sprout(7);
    }


}

