package me.washcar.wcnc.global.definition;

public class Regex {

	public static final String MEMBER_ID = "^[a-zA-Z0-9_-]{8,32}$";

	public static final String PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[\\s\\S]{8,32}$";

	public static final String TELEPHONE = "^010([0-9]{3,4})([0-9]{4})$";

	public static final String PIN_NUMBER = "^\\d{6}$";

	public static final String SLUG = "^[a-zA-Z0-9-]{4,32}$";

	public static final String NAME = "^[a-zA-Z0-9가-힇_-]{1,12}$";

	public static final String UUID_V4 = "^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

}
