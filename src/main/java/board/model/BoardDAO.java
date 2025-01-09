package board.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import board.domain.BoardDTO;
import common.domain.PagingDTO;


public interface BoardDAO {

	// Q&A 게시판의 전체 행 개수를 불러오는 메소드
	int selectTotalRowCount(PagingDTO pagingDTO) throws SQLException;
	
	// Q&A 게시판으로 이동했을 때 리스트를 페이징 처리해서 가져오는 메소드
	List<BoardDTO> selectQuestionList(PagingDTO pagingDTO) throws SQLException;
	
	// Q&A 게시판에 질문을 등록하는 메소드
	int insertQuestionRegister(BoardDTO board, int pk_member_no) throws SQLException;
	
	// Q&A 게시판에 질문을 상세조회하는 메소드
	BoardDTO selectQuestionDetail(Map<String, String> paraMap) throws SQLException;

	// 관리자 //
	
	// 관리자 Q&A 관리페이지의 전체 행 개수를 불러오는 메소드
	int selectTotalRowCountByAdmin(Map<String, Object> paraMap) throws SQLException;
	
	// 관리자 Q&A 관리페이지 질문 리스트를 조회하는 메소드
	List<BoardDTO> selectQuestionListByAdmin(Map<String, Object> paraMap) throws SQLException;
	
	// 관리자 Q&A 게시판 질문을 상세조회하는 메소드
	BoardDTO selectQuestionDetailByAdmin(String questionNo) throws SQLException;
	
	// Q&A 게시판의 질문에 대해 답변 및 답변 상태를 수정하는 메소드
	int updateQuestion(Map<String, String> paraMap) throws SQLException;

	// 관리자 일주일간 미답변 질문 리스트
	List<Map<String, String>> selectWeekUnansweredQuestionList() throws SQLException;
	
}
