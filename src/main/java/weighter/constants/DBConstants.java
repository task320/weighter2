package weighter.constants;

public final class DBConstants {
	private DBConstants(){}

/*
	//ドライバークラス
	public static final String DRIVER_CLASS = "org.h2.Driver";
	//JDBC URL
	public static final String JDBC_URL = "jdbc:h2:tcp://localhost/~/test";
	//DB　ユーザ名
	public static final String DB_USER = "sa";
	//DB パスワード
	public static final String DB_PASS = "";
*/

	public static final String DRIVER_CLASS = "org.postgresql.Driver";
	public static final String JDBC_URL = "jdbc:postgresql://127.0.0.1:5432/test_db";
	public static final String DB_USER = "postgre_weighter";
	public static final String DB_PASS = "testdb";
}
