package weighter.dao;

import static weighter.constants.DBConstants.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import weighter.model.User;

public class LoginDAO {

	//ログイン認証
	//return Userクラス
	public static User Confirm(String userName, String password)
	{
		Connection conn = null;

		User user = null;

		try{
			//JDBCドライバ読み込み
			Class.forName(DRIVER_CLASS);

			//DBへ接続
			conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS);

			//SELECT文準備
			String sql = "SELECT ID FROM USER_INFO WHERE USERNAME = ? AND PASSWORD = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);

			//SQL文の「？」部分置換
			pStmt.setString(1, userName);
			pStmt.setString(2, password);

			//SQL　実行
			ResultSet rs = pStmt.executeQuery();

			//結果確認
			if(rs == null)
			{
				return null;
			}

			//Userデータ作成
			rs.next();
			user = new User(rs.getString("ID"),userName);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
		catch(ClassNotFoundException e)
		{
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
					e.printStackTrace();
				}
			}
		}

		return user;
	}

}
