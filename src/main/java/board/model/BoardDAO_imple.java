package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
					   + " question_isprivate, question_status, question_registerday, "
					   + " ROW_NUMBER() OVER (ORDER BY question_registerday desc) AS row_num "
					   + " from tbl_question "
					   + " order by row_num asc ";
			
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

	
	
	// Q&A 게시핀에 질문을 등록하는 메소드
	@Override
	public int registerMember(BoardDTO board, int pk_member_no) throws SQLException {
		int result = 0;
		
		try {
		
		conn = ds.getConnection();
		  
		String sql = " insert into tbl_question (pk_question_no, fk_member_no, question_title, question_content, question_answer, question_isprivate, question_status, question_registerday) "
				   + " values(pk_question_no_seq.NEXTVAL, ?, ?, ?, null, ?, 0, SYSDATE) ";

		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, pk_member_no);
		pstmt.setString(2, board.getQuestion_title());
		pstmt.setString(3, board.getQuestion_content());
		pstmt.setInt(4, board.getQuestion_isprivate());
		
		result = pstmt.executeUpdate();
		
		} finally {
			close();
		}
		
		return result;
	} // end of Q&A 게시핀에 질문을 등록하는 메소드 ----
	
	
	
	// Q&A 게시판에 질문을 상세조회하는 메소드
	@Override
	public BoardDTO go_detail(Map<String, String> paraMap) throws SQLException {

		BoardDTO boardDTO = null;
		
		String question_isprivate = paraMap.get("question_isprivate");
		
		try {
			conn = ds.getConnection();
			
			String sql = " select question_title, question_content, question_answer "
					   + " from tbl_question "
					   + " where pk_question_no = ?";
			
			if(question_isprivate.equals("0")) {
				// "전체공개" 인 글은 로그인 하지 않아도 조회가 가능
				sql += " and question_isprivate = 0 ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, paraMap.get("pk_question_no"));
			}
			
			else {
				// "비공개" 인 글은 로그인 후에 조회가 가능 
				
				sql += " and fk_member_no = ? and question_isprivate = 1 ";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, paraMap.get("pk_question_no"));
				pstmt.setString(2, paraMap.get("pk_member_no"));
			}
			
			rs = pstmt.executeQuery();
			
			
			if (rs.next()) {
				boardDTO = new BoardDTO();
				boardDTO.setQuestion_title(rs.getString("question_title"));
				boardDTO.setQuestion_content(rs.getString("question_content"));
				boardDTO.setQuestion_answer(rs.getString("question_answer"));
			}
			
			
		} finally {
			close();
		}
		
		return boardDTO;
	}// end of Q&A 게시판에 질문을 상세조회하는 메소드 


	


	
	
	
	
	
}
