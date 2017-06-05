
public class Activity {
	int start;
	int end;
	String name;
	
	public Activity(String name, int start, int end)
	{
		this.start = start;
		this.end = end;
		this.name = name;
	}
	
	int getStart()
	{
		return start;
	}
	
	int getEnd()
	{
		return end;
	}
	
	String getName()
	{
		return name;
	}

}
