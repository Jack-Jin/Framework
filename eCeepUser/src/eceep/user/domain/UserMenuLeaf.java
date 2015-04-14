package eceep.user.domain;

public class UserMenuLeaf {
	private String menuText;
    private String pageUrl;
    private boolean isVisible;
    
	public String getMenuText() {
		return menuText;
	}
	public void setMenuText(String menuText) {
		this.menuText = menuText;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public boolean isIsVisible() {
		return isVisible;
	}
	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
