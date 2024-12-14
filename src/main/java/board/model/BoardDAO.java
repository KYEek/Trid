package board.model;

import java.sql.SQLException;
import java.util.List;

import board.domain.BoardDTO;


public interface BoardDAO {

	// Q&A 게시판으로 이동했을 때 불러오는 메소드
	List<BoardDTO> questionSelect() throws SQLException;
	
	
	
}
