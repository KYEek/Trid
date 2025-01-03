package common.component;

import common.domain.PagingDTO;

/*
 * 페이징 처리를 하는 컴포넌트
 */
public class PagingComponent {

	// 생성자 노출 방지
	private PagingComponent() {}
	
	public static PagingDTO createPaging(int curPage, int totalRowCount) {
		PagingDTO pagingDTO = new PagingDTO(); // PagingDTO 초기화
		
		// curPage 유효성 검사
		if(curPage > totalRowCount || curPage < 1 ) {
			curPage = 1;
		}
		
		pagingDTO.setCurPage(curPage);
		pagingDTO.setTotalRowCount(totalRowCount);
		pagingDTO.setPageSize(5);
		pagingDTO.setRowSizePerPage(10);
		pagingDTO.pageSetting(); // 페이징 시 필요한 나머지 정보 계산
		
		return pagingDTO;
		
	}

}
