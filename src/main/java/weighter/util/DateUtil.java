package weighter.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	//現在日時を取得する
	public static Date getDateNow()
	{
		Date now = new Date();

		return now;
	}

	//Date型→java.sql.Date型へ変換
	public static java.sql.Date toSqlDate(Date date)
	{

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return new java.sql.Date(cal.getTimeInMillis());
	}

	//Date型→Timestamp型へ変換
	public static Timestamp toTimestamp(Date date)
	{
		return new Timestamp(date.getTime());
	}

	//日付(date)に指定日数(numDays)を加算する
	public static Date AddDays(Date date, int numDays)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, numDays);
		return cal.getTime();
	}

	//日付(date)に指定日数(numDays)を加算する
	public static Date PullDays(Date date, int numDays)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -(numDays));
		return cal.getTime();
	}

}
