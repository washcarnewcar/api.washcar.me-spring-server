package me.washcar.wcnc.global.def;

public class Regex {

	// @Pattern 어노테이션엔 constant만 쓸 수 있대서 enum을 사용하지 못했습니다.. 더 좋은 방법 있으면 공유 부탁드립니다.
	public static final String MEMBER_ID = "^[a-zA-Z0-9_-]{8,32}$";
	public static final String MEMBER_ID_MSG = "아이디는 8~32자의 영문 대소문자, 숫자, -, _만 사용 가능합니다.";

	public static final String PASSWORD = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[\\s\\S]{8,32}$";
	public static final String PASSWORD_MSG = "비밀번호는 8~32자의 영문 대소문자, 숫자를 모두 포함하여 주세요.";

	public static final String TELEPHONE = "^01([016789])([0-9]{3,4})([0-9]{4})$";
	public static final String TELEPHONE_MSG = "휴대폰 번호 형식에 맞지 않습니다.";

	public static final String TOKEN = "^\\d{6}$";
	public static final String TOKEN_MSG = "인증번호 형식에 맞지 않습니다.";

	public static final String SLUG = "^[a-zA-Z0-9-]{4,32}$";
	public static final String SLUG_MSG = "4~32자의 영문 대소문자, 숫자, - 만 사용 가능합니다.";

	public static final String NAME = "^[a-zA-Z0-9가-힇_-]{1,12}$";
	public static final String NAME_MSG = "닉네임은 1~12자의 영문, 한글, 숫자, -, _ 만 사용 가능합니다.";
}
