import java.util.Scanner;

public class Heap {
	
	private Activity[] array;
	int n;
	
	public Heap(int n)
	{
		array = new Activity[n];
		this.n = n;
	}
	
	public void fillActivities()
	{
		Scanner s = new Scanner(System.in);
		for(int i = 0; i < n; i++)
		{
			Activity act;
			int st , end;
			System.out.println("Enter start time: ");
			st = s.nextInt();
			System.out.println("Enter end time: ");
			end = s.nextInt();
			System.out.println("Enter name: ");
			s.nextLine();
			act = new Activity(s.nextLine(), st, end);
		}
	}
	
	public int getLeftChild(int i) {
		return 2*i + 1;
	}
	
	public int getRightChild(int i) {
		return 2*i + 2;
	}
	
	public void minHeapify(int i) {
		int left = getLeftChild(i);
		int right = getRightChild(i);
		int min = i;
		if ((left < n) && (array[left].getEnd() < array[i].getEnd()))
			min = left;
		if ((right < n) && (array[right].getEnd() < array[min].getEnd()))
			min = right;
		if (min != i) {
			Activity temp = array[i];
			array[i] = array[min];
			array[min] = temp;
			minHeapify(min);
		}
	}
	
	public void buildMinHeap()
	{
		for(int i = n/2; i >= 0; i--)
			minHeapify(i);
	}
	
	public Activity[] heapSort()
	{
		Activity[] a = new Activity[n];
		fillActivities();
		buildMinHeap();
		int num = n;
		for(int i = 0; i < num; i++)
		{
			a[i] = array[0];
			array[0] = array[n];
			array[n] = a[i];
			n--;
			minHeapify(0);
		}
		return a;
	}
}
