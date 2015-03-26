package eceep.user.service.impl;

import eceep.user.service.User;
import eceep.user.service.UserFactory;

public class UserFactoryImpl implements UserFactory {
	private static UserFactory userFactory;

	public static User getInstance() {
		if (userFactory == null)
			userFactory = new UserFactoryImpl();

		return userFactory.getUser();
	}

	@Override
	public User getUser() {
		return new UserService();
	}

}
