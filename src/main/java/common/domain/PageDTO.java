package common.domain;

import common.Constants;

/*
 * 페이지네이션을 사용하기 위한 페이지 정보가 담긴 DTO
 */
public class PageDTO {

	private int currentPage = 1; // 현재 페이지 번호

	private int rowSize = Constants.PAGE_ROW; // 페이지 당 레코드 수

	private int pageSize = 5; // 페이지리스트에서 보여줄 페이지 개수

	private int totalRow; // 전체 행 개수

	private int firstRow; // 시작 레코드 번호

	private int lastRow; // 마지막 레코드 번호

	private int totalPage; // 총 페이지 수

	private int firstPage; // 페이지 리스트에서 시작 페이지 번호

	private int lastPage; // 페이지 리스트에서 마지막 페이지 번호

	private int endPage; // 현재 페이지 기준 마지막 페이지 번호

	public void pageSetting() {
		totalPage = (totalRow - 1) / rowSize + 1;
		firstRow = (currentPage - 1) * rowSize + 1;
		lastRow = firstRow + rowSize - 1;
		
		if (lastRow > totalRow) {
			lastRow = totalRow;
		}
			
		firstPage = (currentPage - 1) / pageSize * pageSize + 1;
		
		lastPage = firstPage + pageSize - 1;
		
		if (lastPage > totalPage) {
			lastPage = totalPage;
		}
	}

}
