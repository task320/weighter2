package weighter.model;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	private String id; //ユーザーID
	private String userName; //ユーザー名

	public User(String id, String userName)
	{
		this.id = id;
		this.userName = userName;
	}

	public String getId(){return id;}
	public String getUserName(){return userName;}
}
