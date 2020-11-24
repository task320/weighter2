package weighter.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static weighter.constants.Display.*;

import weighter.util.DateUtil;
import weighter.dao.WeightDataDAO;

public class MainLogic {

	//registFlgによりInsertまたは、Updateを行うか判断する。
	public static boolean ProcGateWeightData(String id, Date date, Double weight, StringBuilder comment, int registFlg)
	{
		if(registFlg == 1)
		{return UpdateWeightData(id, date, weight, comment);}
		else
		{return InsertWeightData(id, date, weight, comment);}
	}

	//体重データを登録する。
	public static boolean InsertWeightData(String id, Date date, Double weight, StringBuilder comment) throws NumberFormatException,NullPointerException
	{
		WeightData data = new WeightData(id, date, weight, comment);
		if(!WeightDataDAO.InsertWeightData(data))
		{
			System.out.println("Failed INSERT weight data.");
			return false;
		}
		return true;
	}

	//体重データの更新を行う
	public static boolean UpdateWeightData(String id, Date date, Double weight, StringBuilder comment) throws NumberFormatException,NullPointerException
	{
		WeightData data = new WeightData(id, date, weight, comment);
		if(!WeightDataDAO.UpdateWeightData(data))
		{
			System.out.println("Failed UPDATE weight data.");
			return false;
		}
		return true;
	}

	//今日登録された体重を取得
	public static Double GetTodayWeight(String userId)
	{
		return WeightDataDAO.GetTodayWeight(userId);
	}

	//過去30日分の体重を取得
	public static List<WeightData> CreatePast30DaysWeightData(String userId)
	{
		//体重データリスト
		List<WeightData> past30DaysWeightData = new ArrayList<WeightData>();

		Date endDate = DateUtil.getDateNow();
		Date startDate = DateUtil.PullDays(endDate, DISPLAY_WEIGHT_DATA_COUNT);
		List<WeightData> weightDataList = WeightDataDAO.GetPast30DaysWeight(userId, startDate, endDate);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		for(Date i = endDate; 0 <= i.compareTo(startDate); i = DateUtil.PullDays(i, 1))
		{
			if(weightDataList.size() != 0)
			{
				boolean findFlg = false;

				for(WeightData data: weightDataList)
				{
					if(sdf.format(i).equals(sdf.format(data.getDate())))
					{
						past30DaysWeightData.add(data);
						findFlg = true;
						break;
					}
				}

				if(!findFlg)
					past30DaysWeightData.add(new WeightData("blank",i,0.0,new StringBuilder("登録なし")));
			}
			else
			{
				past30DaysWeightData.add(new WeightData("blank",i,0.0,new StringBuilder("登録なし")));
			}
		}
		return past30DaysWeightData;
	}
}