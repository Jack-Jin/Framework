package eceep.user.domain;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.LinkedMap;

public class UserMenu {
	private Map<String,List<UserMenuLeaf>> menus;
	
	public UserMenu(){
		menus = new LinkedMap<String, List<UserMenuLeaf>>();
	}

	public Map<String, List<UserMenuLeaf>> getMenus() {
		return menus;
	}
}
