package eceep.user.domain;

import java.util.List;

public class ApplicationMenu {
    private String menuTitle;
    private List<ApplicationMenuLeaf> menuLeafs;
    
    public class ApplicationMenuLeaf {
    	private String menuText;
        private String pageUrl;
        private boolean visible;
        private boolean enabled;
    }
}
