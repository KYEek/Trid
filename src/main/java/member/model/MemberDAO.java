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

   // 비밀번호 찾기(이메일을 입력 받아서 해당 사용자가 존재하는지 유무를 알려준다)
   boolean isUserExist(Map<String, String> paraMap) throws SQLException;

   // 회원 탈퇴를 처리해주는 메소드
   int memberDelete(Map<String, String> paraMap) throws SQLException;



   // 이메일을 수정 해주는 메소드
   int updateEmail(MemberDTO member) throws SQLException;
   
   // 회원정보 수정시 email 중복검사(현재 해당 사용자가 사용중인 email 이라면 true, 새로운 비밀번호이라면 false) 
   boolean emailDuplicateCheck2(Map<String, String> paraMap) throws SQLException;

   // email 중복검사 (tbl_member 테이블에서 email 이 존재하면 true 를 리턴해주고, email 이 존재하지 않으면 false 를 리턴한다)
   boolean emailDuplicateCheck(String email) throws SQLException;

   // 현재 비밀번호를 알아와서 값과 비교해주는 페이지
   boolean currentPwdCheck(MemberDTO member) throws SQLException;

   // 전화번호를 수정해주는 메소드
   int updateMobile(MemberDTO member) throws SQLException;

   // 새로운 전화번호가 tbl_member 에서 사용중인 전화번호인지 체크해주는 메소드
   boolean MobileDuplicateCheck(Map<String, String> paraMap) throws SQLException;

   // 비밀번호 변경을 처리해주는 메소드
   int updatePwdEnd(MemberDTO member) throws SQLException;

   // 관리자 회원 리스트 조회 시 전체 회원 수를 구하는 메소드
   int selectTotalRowCountByAdmin(Map<String, Object> paraMap) throws SQLException;

   // 관리자 회원 리스트 조회 메소드
   List<MemberDTO> selectMemberListByAdmin(Map<String, Object> paraMap) throws SQLException;
   
   // 관리자 회원 상세조회 메소드
   MemberDTO selectMemberByAdmin(String memberNo) throws SQLException;
   
   // 관리자 회원 로그인 기록 행 개수
   int selectLoginHistoryTotalRowCount(String memberNo) throws SQLException;

   // 관리자 회원 로그인 기록 리스트 조회 메소드
   List<Map<String, String>> selectLoginHistoryByAdmin(Map<String, Object> paraMap) throws SQLException;
	
}
