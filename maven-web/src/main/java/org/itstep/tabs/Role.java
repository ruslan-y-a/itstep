package org.itstep.tabs;

public enum Role {
	ADMIN,
	CLIENT,
	PRODUCT,
	MANAGER,
	CASHIER,
	COURIER;	
	
	@SuppressWarnings("unused")
	public static Role getRole(int iRole) {
		Role roles[]; roles=Role.values();
        return roles[iRole];		
	}

}
