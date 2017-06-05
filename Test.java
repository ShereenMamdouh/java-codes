
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ActivitySelection as = new ActivitySelection();
		
		while(as.hasNext())
		{
			System.out.println(as.getNextActivity().getName());
		}

	}

}
