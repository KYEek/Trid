package board.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import board.domain.BoardDTO;


public interface BoardDAO {

	// Q&A 게시판으로 이동했을 때 불러오는 메소드
	List<BoardDTO> questionSelect() throws SQLException;
	
	// Q&A 게시판에 질문을 등록하는 메소드
	int registerMember(BoardDTO board, int pk_member_no) throws SQLException;
	
	// Q&A 게시판에 질문을 상세조회하는 메소드
	BoardDTO go_detail(Map<String, String> paraMap) throws SQLException;
	
}
