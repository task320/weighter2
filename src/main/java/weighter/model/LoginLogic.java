package weighter.model;

import weighter.dao.LoginDAO;

import weighter.model.User;

public class LoginLogic {

	//ログイン認証(Userクラス返却)
	public static User Login(String userName, String password)
	{
		//ログイン情報
		return LoginDAO.Confirm(userName, password);
	}

}
