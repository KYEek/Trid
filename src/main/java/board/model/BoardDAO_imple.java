package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import board.domain.BoardDTO;


public class BoardDAO_imple implements BoardDAO {

	private DataSource ds;	// DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 생성자
	public BoardDAO_imple() {
		try {
		    Context initContext = new InitialContext();
		    Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/semioracle");
		    conn = ds.getConnection();
		} catch(NamingException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}// end of public BoardDAO_imple() ----- 생성자 끝 -----
	
	
	// 사용한 자원을 반납하는 close() 메소드 생성
	private void close() {
		try {
			if(rs != null)	 	{rs.close(); rs=null;}
			if(pstmt != null) 	{pstmt.close(); pstmt=null;}
			if(conn != null)	{conn.close(); conn=null;}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}// end of private void close() --------


	
	// Q&A 게시판으로 이동했을 때 불러오는 메소드
	@Override
	public List<BoardDTO> questionSelect() throws SQLException {

		List<BoardDTO> questionList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select pk_question_no, fk_member_no, question_title, question_content, "
					   + " question_isprivate, question_status, question_registerday "
					   + " from tbl_question ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				BoardDTO boardDTO = new BoardDTO();
				boardDTO.setPk_question_no(rs.getInt("pk_question_no"));
				boardDTO.setFk_member_no(rs.getInt("fk_member_no"));
				boardDTO.setQuestion_title(rs.getString("question_title"));
				boardDTO.setQuestion_content(rs.getString("question_content"));
				boardDTO.setQuestion_isprivate(rs.getInt("question_isprivate"));
				boardDTO.setQuestion_status(rs.getInt("question_status"));
				boardDTO.setQuestion_registerday(rs.getString("question_registerday"));
				
				questionList.add(boardDTO);
				
			}// end of while(rs.nex()) ---------
			
		} finally {
			close();
		}
		
		return questionList;
	}
	
	
	
	
	
	
}
