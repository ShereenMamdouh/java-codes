import java.util.Scanner;

public class ActivitySelection {
	
	Activity[] activities;
	int lastEnd;
	int indexPtr;
		
	public ActivitySelection()
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Enter number of Activities: ");
		Heap h = new Heap(s.nextInt());
		
		activities = h.heapSort();
		lastEnd = 0;
		indexPtr = 0;
	}
		
	public Activity getNextActivity()
	{
		while(activities[indexPtr].start < lastEnd)
			indexPtr++;
		
		if(indexPtr > activities.length)
			return null;
		
		Activity temp = activities[indexPtr];
		indexPtr++;
		
		return temp;
	}
	
	public boolean hasNext()
	{
		if(indexPtr > activities.length)
			return false;
		return true;
	}

}
