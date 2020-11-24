package weighter.model;

import java.sql.ResultSet;

public class SqlResultReport {

	//SQL処理に関するエラー
	private boolean procError = false;
	//SQL実行でえられた結果
	private ResultSet executeResult = null;

	//コンストラクタ
	SqlResultReport(boolean procError, ResultSet executeResult){
		this.procError = procError;
		this.executeResult = executeResult;
	}

	//ResultSet.Next()
	public boolean Next(){
		boolean existRow = true;
		try{
		if(executeResult == null){
			return false;
		}

		}
		catch(Exception e ){
			existRow = false;
		}

		return existRow;
	}

}
