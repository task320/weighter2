package weighter.model;

import weighter.dao.CreateAccountDAO;

public class CreateAccountLogic {

		public static boolean CheckDuplicateAccount(String userId)
		{
			return CreateAccountDAO.CheckDuplicateAccount(userId);
		}

		//ユーザー登録
		public static boolean CreateAccount(String userId, String password)
		{
			return CreateAccountDAO.CreateAccount(userId,  password);
		}


}
