package util;

public class StringUtil {

	// 생성자 노출 방지
	private StringUtil() {};
	
	// 공백 과 NULL 을 체크하는 메소드
	public static boolean isBlank(String str) {
		return (str == null) || (str.isBlank());
	};
	
}