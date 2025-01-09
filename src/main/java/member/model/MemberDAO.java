package member.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.domain.MemberDTO;


public interface MemberDAO {

   // 회원가입을 해주는 메소드(tbl_member 테이블에 insert)
   int registerMember(MemberDTO member) throws SQLException;

   // 로그인 처리를 해주는 메소드
   MemberDTO login(Map<String, String> paraMap) throws SQLException;

   // 회원 탈퇴를 처리해주는 메소드
   int memberDelete(Map<String, String> paraMap) throws SQLException;
   
   // 비밀번호 찾기(이메일을 입력 받아서 해당 사용자가 존재하는지 유무를 알려준다)
   boolean isUserExist(Map<String, String> paraMap) throws SQLException;

   

   					// =====  (Update Email) ===== // 
   // 이메일 수정 전, 현재 비밀번호로 인증해주는 절차를 위해 사용자의 현재 비밀번호를 알아오는 메소드
   boolean currentPwdCheck(Map<String, String> paraMap) throws SQLException;

   //email 수정시 변경하고자 하는 이메일이 tbl_member 테이블에서 사용중인지 아닌지 여부 알아오기 위한 email 중복검사 메소드
   boolean emailDuplicateCheck(String email) throws SQLException;

   // 이메일을 수정 해주는 메소드
   int updateEmail(Map<String , String> paraMap) throws SQLException;

   // 전화번호를 수정해주는 메소드
   int updateMobile(MemberDTO member) throws SQLException;

   // 비밀번호 변경을 처리해주는 메소드
   int updatePwdEnd(Map<String , String> paraMap) throws SQLException;

   // 비밀번호 찾기를 한 후 비밀번호 수정해주는 메소드
   int updateFindPwdEnd(Map<String, String> paraMap) throws SQLException;
   
   // 관리자 회원 리스트 조회 시 전체 회원 수를 구하는 메소드
   int selectTotalRowCountByAdmin(Map<String, Object> paraMap) throws SQLException;

	
	// 회원가입시 전화번호 중복검사 (tbl_member 테이블에서 mobile 이 존재하면 true 를 리턴해주고, mobile 이 존재하지 않으면 false 를 리턴한다)
	boolean mobileDuplicateCheck(String mobile) throws SQLException;
   
	
   // 관리자 회원 리스트 조회 메소드
   List<MemberDTO> selectMemberListByAdmin(Map<String, Object> paraMap) throws SQLException;
   
   // 관리자 회원 상세조회 메소드
   MemberDTO selectMemberByAdmin(String memberNo) throws SQLException;
   
   // 관리자 회원 로그인 기록 행 개수
   int selectLoginHistoryTotalRowCount(String memberNo) throws SQLException;

   // 관리자 회원 로그인 기록 리스트 조회 메소드
   List<Map<String, String>> selectLoginHistoryByAdmin(Map<String, Object> paraMap) throws SQLException;
   
   // 휴면 상태를 해제해주는 메소드
   int UpdateMemberIdle(String meberNo, String clientip) throws SQLException;

   // 일주일간 사용자 접속 수 
   List<Map<String, String>> selectWeekLoginUserList() throws SQLException;
	
}
