package eceep.user.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.LinkedMap;

public class UserMenu {
	private List<UserMenuGroup> menus;
	
	public UserMenu(){
		menus = new ArrayList<UserMenuGroup>();
	}

	public List<UserMenuGroup> getMenus() {
		if(this.menus == null)
			this.menus = new ArrayList<UserMenuGroup>();
		
		return menus;
	}
}
