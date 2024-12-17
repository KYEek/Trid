package member.model;

import java.sql.SQLException;
import java.util.Map;

import member.domain.MemberDTO;


public interface MemberDAO {

	// 회원가입을 해주는 메소드(tbl_member 테이블에 insert)
	int registerMember(MemberDTO member) throws SQLException;

	// 로그인 처리를 해주는 메소드
	MemberDTO login(Map<String, String> paraMap) throws SQLException; 
	
	
	
}
