package weighter.dao;

import static weighter.constants.DBConstants.*;
import static weighter.constants.WeightData.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import weighter.model.WeightData;
import weighter.util.DateUtil;
import weighter.util.Log4jLogger;

public class WeightDataDAO {

	private  static Log4jLogger logger =  Log4jLogger.getInstance();

	//体重データを登録
	public static boolean InsertWeightData(WeightData data)
	{
		Connection conn = null;

		//SELECT文準備
		StringBuilder sql = new StringBuilder();

		try{
			//JDBCドライバ読み込み
			Class.forName(DRIVER_CLASS);

			//DBへ接続
			conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS);


			sql.append("INSERT INTO WEIGHT_DATA VALUES(");
			sql.append(data.getId() + ",");
			sql.append(String.valueOf(data.getWeight()) + ",");
			sql.append("\'" + data.getComment().toString() + "\'" + ",");
			sql.append("\'" + DateUtil.toSqlDate(data.getDate()).toString() + "\'");
			sql.append(");");

			PreparedStatement pStmt = conn.prepareStatement(sql.toString());

			System.out.println(sql.toString());


			//SQL　実行
			int result = pStmt.executeUpdate();

			if(result != 1)
				return false;
		}
		catch(SQLException e)
		{
			logger.ErrorLog(sql.toString());
			logger.ErrorLog(e.getMessage());
			return false;
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			if(conn != null)
			{
				try
				{
					conn.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	//体重データを登録
		public static boolean UpdateWeightData(WeightData data)
		{
			Connection conn = null;

			//SELECT文準備
			StringBuilder sql = new StringBuilder();

			try{
				//JDBCドライバ読み込み
				Class.forName(DRIVER_CLASS);

				//DBへ接続
				conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS);


				sql.append("UPDATE  WEIGHT_DATA");
				sql.append(" SET WEIGHT = " + data.getWeight());
				sql.append(" WHERE ID =  \'" + data.getId() + "\' AND INSERT_DATE = \'"+ DateUtil.toSqlDate(data.getDate()).toString() +"\'");
				PreparedStatement pStmt = conn.prepareStatement(sql.toString());

				System.out.println(sql.toString());


				//SQL　実行
				int result = pStmt.executeUpdate();

				if(result != 1)
					return false;
			}
			catch(SQLException e)
			{
				logger.ErrorLog(sql.toString());
				logger.ErrorLog(e.getMessage());
				return false;
			}
			catch(ClassNotFoundException e)
			{
				e.printStackTrace();
				return false;
			}
			finally
			{
				if(conn != null)
				{
					try
					{
						conn.close();
					}
					catch(SQLException e)
					{
						e.printStackTrace();
					}
				}
			}

			return true;
		}

	//今日登録された体重を取得
	public static Double GetTodayWeight(String userId)
	{
		Connection conn = null;

		//SELECT文準備
		StringBuilder sql = new StringBuilder();

		try{
			//JDBCドライバ読み込み
			Class.forName(DRIVER_CLASS);

			//DBへ接続
			conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS);

			sql.append("SELECT ");
			sql.append("WEIGHT ");
			sql.append("FROM WEIGHT_DATA ");
			sql.append("WHERE ");
			sql.append("ID = \'" + userId + "\' AND ");
			sql.append("INSERT_DATE = \'" + DateUtil.toSqlDate(new Date()).toString() + "\'");

			PreparedStatement pStmt = conn.prepareStatement(sql.toString());

			System.out.println(sql.toString());

			//SQL　実行
			ResultSet result = pStmt.executeQuery();

			//今日の体重
			result.next();
			return result.getDouble("WEIGHT");

		}
		catch(SQLException e)
		{
			logger.ErrorLog(sql.toString());
			logger.ErrorLog(e.getMessage());
			return NOTHING_WEIGHT;
		}
		catch(ClassNotFoundException e)
		{
			logger.ErrorLog(e.getMessage());
			e.printStackTrace();
			return NOTHING_WEIGHT;
		}
		finally
		{
			if(conn != null)
			{
				try
				{
					conn.close();
				}
				catch(SQLException e)
				{
					logger.ErrorLog(e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	//過去30日分の体重データを取得
		public static List<WeightData> GetPast30DaysWeight(String userId, Date startDate, Date endDate)
		{
			Connection conn = null;

			//体重データリスト
			List<WeightData> weightDataList = new ArrayList<WeightData>();

			//SELECT文準備
			StringBuilder sql = new StringBuilder();

			try{
				//JDBCドライバ読み込み
				Class.forName(DRIVER_CLASS);

				//DBへ接続
				conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS);

				sql.append("SELECT ");
				sql.append("ID, INSERT_DATE, WEIGHT, COMMENT ");
				sql.append("FROM WEIGHT_DATA ");
				sql.append("WHERE ");
				sql.append("ID = \'" + userId + "\' AND ");
				sql.append("INSERT_DATE >= \'"+ DateUtil.toSqlDate(startDate).toString() + "\' AND INSERT_DATE <= \'"+ DateUtil.toSqlDate(endDate).toString() +"\'" );

				PreparedStatement pStmt = conn.prepareStatement(sql.toString());

				System.out.println(sql.toString());

				//SQL　実行
				ResultSet result = pStmt.executeQuery();

				//今日の体重
				while(result.next())
				{
					//String ->　StringBuilder
					StringBuilder comment = new StringBuilder();
					comment.append(result.getString("COMMENT"));

					//体重データ
					WeightData data =
							new WeightData(result.getString("ID"),
									result.getDate("INSERT_DATE"),
									result.getDouble("WEIGHT"),
									comment
									);

					//リスト追加
					weightDataList.add(data);

				}

				return weightDataList;

			}
			catch(SQLException e)
			{
				logger.ErrorLog(sql.toString());
				logger.ErrorLog(e.getMessage());
				return null;
			}
			catch(ClassNotFoundException e)
			{
				logger.ErrorLog(e.getMessage());
				e.printStackTrace();
				return null;
			}
			finally
			{
				if(conn != null)
				{
					try
					{
						conn.close();
					}
					catch(SQLException e)
					{
						logger.ErrorLog(e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
}
