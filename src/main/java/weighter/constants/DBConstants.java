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

	public static final String DRIVER_CLASS = System.getenv("DRIVER_CLASS");
	public static final String JDBC_URL = System.getenv("JDBC_URL");
	public static final String DB_USER = System.getenv("DB_USER");
	public static final String DB_PASS = System.getenv("DB_PASS");
}
