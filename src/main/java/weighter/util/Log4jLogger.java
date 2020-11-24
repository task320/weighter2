package weighter.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Log4jLogger {

	private static  Log4jLogger instance =  new Log4jLogger();

	private final Logger logger = LogManager.getLogger(Log4jLogger.class);

	//ログ出力を行うかどうかのフラグ
	private final boolean outputLogFlg = true;

	//コンストラクタ
	private Log4jLogger(){	}

	//シングルトン
	public  static Log4jLogger getInstance(){
		return instance;
	}

	//レベルINFOのログを出力
	public void InfoLog(String log){
		if(outputLogFlg)
			logger.info(log);
	}

	//レベルERRORのログ出力
	public void ErrorLog(String log){
		if(outputLogFlg)
			logger.error(log);
	}
}
