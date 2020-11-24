package weighter.dao;

import static weighter.constants.DBConstants.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import weighter.util.Log4jLogger;

public class CreateAccountDAO {

	private  static Log4jLogger logger =  Log4jLogger.getInstance();

		//ユーザID重複チェック
		public static boolean CheckDuplicateAccount(String userId)
		{
			Connection conn = null;

			try{
				//JDBCドライバ読み込み
				Class.forName(DRIVER_CLASS);

				//DBへ接続
				conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS);

				//SELECT文準備
				String sql = "SELECT COUNT(ID) AS COUNT FROM USER_INFO WHERE USERNAME = ? ";
				PreparedStatement pStmt = conn.prepareStatement(sql);

				//SQL文の「？」部分置換
				pStmt.setString(1, userId);

				//SQL　実行
				ResultSet rs = pStmt.executeQuery();

				//カウント値確認
				rs.next();
				if(rs.getInt("COUNT") == 0){
					return true;
				}
				else
				{
					return false;
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
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
		}

	//ユーザー登録
	public static boolean CreateAccount(String userId, String password)
	{
		Connection conn = null;
		//SELECT文準備
		StringBuilder sql = new StringBuilder();

		try{
			//JDBCドライバ読み込み
			Class.forName(DRIVER_CLASS);

			//DBへ接続
			conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS);


			sql.append("INSERT INTO USER_INFO VALUES((SELECT MAX(ID) FROM USER_INFO) +  1, ?, ?)");
			//ログインタイプにより、パスワードの設定をするかどうか判断する。

			PreparedStatement pStmt = conn.prepareStatement(sql.toString());

			//SQL文の「？」部分置換
			pStmt.setString(1, userId);
			pStmt.setString(2, password);

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
}
