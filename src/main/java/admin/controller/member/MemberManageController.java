package admin.controller.member;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.Constants;
import common.controller.AbstractController;
import common.domain.PagingDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberDTO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

/*
 * 관리자 회원 관리 컨트롤러
 */
public class MemberManageController extends AbstractController {
	
	private final MemberDAO memberDAO;
	
	public MemberManageController() {
		this.memberDAO = new MemberDAO_imple(); // MemberDAO 초기화
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod(); // HTTP 메소드

		// 관리자 접근 유효성 검사
		if (!super.checkAdminLogin(request)) {
			return;
		}

		// POST 요청인 경우 동일 URL에 GET 요청으로 리다이렉트
		if ("POST".equalsIgnoreCase(method)) {
			super.setRedirect(true);
			super.setViewPage(Constants.ADMIN_MEMBER_MANAGE_URL);
		}
		// GET 요청인 경우
		else {
			int curPage = 1; // 현재 페이지 초기화

			// curPage가 정수인지 예외처리
			try {
				curPage = Integer.parseInt(request.getParameter("curPage"));
			} catch (NumberFormatException e) {}
			
			Map<String, Object> paraMap = createParaMap(request);  // URL 파라미터에서 받은 값을 Map에 저장
			
			try {
				int totalRowCount = memberDAO.selectTotalRowCountByAdmin(paraMap); // 전체 행 개수 조회
				
				PagingDTO pagingDTO = new PagingDTO(curPage, totalRowCount); // 페이징 관련 정보가 저장된 DTO 생성
				
				paraMap.put("pagingDTO", pagingDTO);
				
				List<MemberDTO> memberList = memberDAO.selectMemberListByAdmin(paraMap); // 회원정보 리스트 조회
				
				request.setAttribute("memberList", memberList); // 회원 리스트 정보
				request.setAttribute("pagingDTO", pagingDTO); // 페이징 정보
				request.setAttribute("paraMap", paraMap); // 이전 요청의 필터링 및 카테고리 정보 map

				super.setRedirect(false);
				super.setViewPage(Constants.ADMIN_MEMBER_MANAGE_JSP);

			} catch (SQLException e) {
				e.printStackTrace();
				super.handleServerError();
			}

		}

	}
	
	/*
	 * URL 파라미터에서 받은 값을 Map에 저장하여 반환하는 메소드
	 */
	private Map<String, Object> createParaMap(HttpServletRequest request) {
		Map<String, Object> paraMap = new HashMap<>();
		
		paraMap.put("searchType", request.getParameter("searchType")); // 검색 타입 0: 회원명, 1: 이메일
		paraMap.put("searchWord", request.getParameter("searchWord")); // 검색어
		paraMap.put("sortCategory", request.getParameter("sortCategory")); // 정렬 타입 0: 회원명 오름차순, 1: 회원명 내림차순, 2:가입일 오름차순, 3: 가입일 내림차순
		paraMap.put("memberGender", request.getParameter("memberGender")); // 성별 0:여자 1:남자
		paraMap.put("memberIdle", request.getParameter("memberIdle")); // 회원 휴면 상태 1 : 비휴면, 0 :휴면 (6개월 기준)
		paraMap.put("memberStatus", request.getParameter("memberStatus")); // 회원 상태 1 : 활성,  0: 탈퇴
		paraMap.put("dateMin", request.getParameter("dateMin")); // 최소 일자
		paraMap.put("dateMax", request.getParameter("dateMax")); // 최대 일자
		
		return paraMap;
	}

}
