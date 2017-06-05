
import java.util.Hashtable;
import java.util.Scanner;

/**
 * @author Sherin
 */
public class BinaryHeap implements PriorityQueue {

    private static final int DEFAULT_CAPACITY = 100;
    private int currentSize;      // Number of elements in heap
    private Comparable[] array; // The heap array
    Hashtable al = new Hashtable();
    Integer[] items = new Integer[100];

    public BinaryHeap() {
        currentSize = 0;
        array = new Comparable[DEFAULT_CAPACITY + 1];
    }
    /**
     * Construct the binary heap from an array.
     */
    public BinaryHeap(Comparable[] items) {
        currentSize = items.length;
        array = new Comparable[items.length + 1];
        for (int i = 0; i < items.length; i++) {
            array[i + 1] = items[i];
        }
        buildHeap();
    }
    /**
     * Insert into the priority queue. Duplicates are allowed. @param x the item
     * to insert ex insert value 5 .
     */
    public void insert(Comparable x) {
        // Percolate up
        int hole = ++currentSize;
         System.out.println("our x is "+ al.get(x));
        array[0] = (Comparable) al.get(x);
        System.out.println("our x is "+ al.get(String.valueOf(x)));
        for (; x.compareTo(al.get(array[hole / 2])) < 0; hole /= 2) //value inserted with parent of last node is smaller do after loop check upper parent 
        {
            array[hole] = array[hole / 2]; //put in hole the parent value
        }
        array[hole] = (Comparable) al.get(x); //after loop insert x in its place as loop will stop at correct index of x.
    } /* Find the smallest item in the priority queue.* @return the smallest item. */


    public Comparable findMin() {
        if (isEmpty()) {
            System.out.println("Empty binary heap");
        }
        return array[1];
    }
    /* Remove the smallest item from the priority queue.     * @return the smallest item. */

    public Comparable deleteMin() {
        Comparable minItem = findMin();
        array[1] = array[currentSize--];
        percolateDown(1);//remove the root node

        return minItem;
    }

    /**
     * * Establish heap order property from an arbitrary arrangement of items.*
     * Runs in linear time.
     */
    private void buildHeap() {
        for (int i = currentSize / 2; i > 0; i--) {
            percolateDown(i);//from down node to upper nodes
        }
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public int size() {
        return currentSize;
    }

    public void makeEmpty() {
        currentSize = 0;
    }

    /**
     * Internal method to percolate down in the heap. * @param hole the index at
     * which the percolate begins.
     */
    private void percolateDown(int hole) {
        int child;
        Comparable tmp = array[hole];
        for (; hole * 2 <= currentSize; hole = child)//if leftchild exsit do loop when finish put hole in left child place
        {
            child = hole * 2;
            if (child != currentSize && //if not last node
                    ((Comparable) al.get(array[child + 1])).compareTo((Comparable) al.get(array[child])) < 0) //compare right and left child if right value smaller operate on it
            {
                child++;
            }

            if (((Comparable) al.get(array[child])).compareTo((Comparable) al.get(tmp)) < 0) //compare smaller child with parent  if child smaller put it instead of parent
            {
                array[hole] = array[child];
            } else {
                break;
            }
        }
        array[hole] = tmp;// hole is the last child so put parent node in it
    }

    // Test program

    public static void main(String[] args) {
        int numItems = 10;
        BinaryHeap h1 = new BinaryHeap();
        Integer[] items = new Integer[numItems];
        int i;
        Hashtable al = new Hashtable();
        int priority;
        int value;

        for (i = 0; i < 10; i++) {
            Scanner sc = new Scanner(System.in);
            System.out.println("enter the priority then the value" + i + " :");
            priority = sc.nextInt();
            value = sc.nextInt();
                      System.out.println("so we put "+String.valueOf( priority)+ "and we put "+ String.valueOf(value) );
            al.put(String.valueOf(priority),String.valueOf(value));
            h1.insert((Comparable)priority);
            items[i] = value;
        }

        for (i = 1; i < numItems; i++) {
            if (((Integer) (h1.deleteMin())).intValue() != i) {
                System.out.println("Oops! " + i);
            }

        }
        BinaryHeap h2 = new BinaryHeap(items);
        for (i = 1; i < numItems; i++) {
            if (((Integer) (h2.deleteMin())).intValue() != i) {
                System.out.println("Oops! " + i);
            }
        }
    }

}
