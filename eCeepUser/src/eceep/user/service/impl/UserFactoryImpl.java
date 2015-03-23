package eceep.user.service.impl;

import eceep.user.service.User;
import eceep.user.service.UserFactory;

class UserFactoryImpl implements UserFactory {
	private static UserFactory userFactory;

	public static UserFactory getInstance() {
		if (userFactory == null)
			userFactory = new UserFactoryImpl();

		return userFactory;
	}

	@Override
	public User getUser() {
		return new UserService();
	}

}
