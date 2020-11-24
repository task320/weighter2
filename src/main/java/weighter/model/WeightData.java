package weighter.model;

import java.util.Date;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

//体重関連データ　ユーザID,日付,体重,コメント
public class WeightData implements Serializable{
	private static final long serialVersionUID = 1L;

	//フィールド
	private String id;
	private Date date;
	private double weight;
	private StringBuilder comment;

	//コンストラクタ
	public WeightData(String id, Date date, Double weight, StringBuilder comment)
	{
		this.id = id;
		this.date = date;
		this.weight = weight;
		this.comment = comment;
	}

	//プロパティ
	public String getId(){return id;}
	public Date getDate(){return date;}
	public double getWeight(){return weight;}
	public StringBuilder getComment(){return comment;}

	//Dataをフォーマットに従った形で文字列を返す。(yyyy-MM-dd)
	public String GetDataFormat1()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(getDate());

	}

	//Weightを画面表示ように整形(0.0)
	public String GetDisplayWeight()
	{
		DecimalFormat df = new DecimalFormat("0.0");
		return df.format(getWeight());
	}



}
