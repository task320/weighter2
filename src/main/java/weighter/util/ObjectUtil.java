package weighter.util;

public class ObjectUtil {

	//Object→Double
	public static double toDouble(Object obj)
	{
		try
		{
			return Double.parseDouble((String)obj);

		}
		catch(NumberFormatException e)
		{
			throw e;
		}

	}


}
