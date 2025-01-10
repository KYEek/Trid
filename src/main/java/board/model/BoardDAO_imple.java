package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import board.domain.BoardDTO;
import common.domain.PagingDTO;
import util.StringUtil;


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


	
	// Q&A 게시판으로 이동했을 때 리스트를 페이징 처리해서 가져오는 메소드
	@Override
	public List<BoardDTO> selectQuestionList(PagingDTO pagingDTO) throws SQLException {

		List<BoardDTO> questionList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " SELECT * "
					   + " FROM ( "
					   + "    SELECT A.*, rownum AS rnum "
					   + "    FROM ( "
					   + "        SELECT pk_question_no, "
					   + "               fk_member_no, "
					   + "               question_title, "
					   + "               question_isprivate, "
					   + "               question_status, "
					   + "               question_registerday "
					   + "        FROM tbl_question "
					   + "        ORDER BY question_registerday desc "
					   + "    ) A "
					   + " ) B "
					   + " WHERE rnum BETWEEN ? AND ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, pagingDTO.getFirstRow());
			pstmt.setInt(2, pagingDTO.getLastRow());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getString("question_title"));
				
				BoardDTO boardDTO = new BoardDTO();
				
				boardDTO.setPk_question_no(rs.getInt("pk_question_no"));
				boardDTO.setFk_member_no(rs.getInt("fk_member_no"));
				boardDTO.setQuestion_title(rs.getString("question_title"));
				boardDTO.setQuestion_isprivate(rs.getInt("question_isprivate"));
				boardDTO.setQuestion_status(rs.getInt("question_status"));
				boardDTO.setQuestion_registerday(rs.getString("question_registerday"));
				
				questionList.add(boardDTO);
				
			}// end of while(rs.nex()) ---------
			
		} finally {
			close();
		}
		
		return questionList;
	}// end of Q&A 게시판으로 이동했을 때 리스트 전체를 불러오는 메소드 -------

	
	// Q&A 게시판의 전체 행 개수를 불러오는 메소드
	@Override
	public int selectTotalRowCount(PagingDTO pagingDTO) throws SQLException {
		int totalRowCount = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select count(*) AS total "
					   + " from tbl_question ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalRowCount = rs.getInt("total");
			}
			
			
		} finally {
			close();
		}
		
		return totalRowCount;
	}// end of Q&A 게시판의 전체 행 개수를 불러오는 메소드

	
	// Q&A 게시핀에 질문을 등록하는 메소드
	@Override
	public int insertQuestionRegister(BoardDTO board, int pk_member_no) throws SQLException {
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
	public BoardDTO selectQuestionDetail(Map<String, String> paraMap) throws SQLException {

		BoardDTO boardDTO = null;
		
		String question_isprivate = paraMap.get("question_isprivate");
		
		try {
			conn = ds.getConnection();
			
			String sql = " select question_title, question_content, question_answer "
					   + " from tbl_question "
					   + " where pk_question_no = ?";
			
			if("0".equals(question_isprivate)) {
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
	
	/*
	 * 관리자 Q&A 관리페이지의 전체 행 개수를 불러오는 메소드
	 */
	@Override
	public int selectTotalRowCountByAdmin(Map<String, Object> paraMap) throws SQLException {
		
		int totalRowCount = 0;
		
		String searchType = (String)paraMap.get("searchType");
		
		String searchWord = (String)paraMap.get("searchWord");
		
		String privateStatus = (String)paraMap.get("privateStatus");
		
		String answerStatus = (String)paraMap.get("answerStatus");
		
		String dateMin = (String)paraMap.get("dateMin");
		
		String dateMax = (String)paraMap.get("dateMax");
		
		int count = 0;
		
		try {
			conn = ds.getConnection();

			String sql 	= " select count(*) as total "
						+ " from tbl_question q join tbl_member m on q.fk_member_no = m.pk_member_no "
						+ " where 1=1 ";
			
			if(!StringUtil.isBlank(searchType) && !StringUtil.isBlank(searchWord)) {
				switch(searchType) {
					case "0" : {
						sql += " and question_title like '%' || ? || '%' ";
						break;
					}
					case "1" : {
						sql += " and member_name = ? ";
						break;
					}
				}
			}
			
			if(!StringUtil.isBlank(privateStatus)) {
				sql += " and question_isprivate = ? ";
			}
			
			if(!StringUtil.isBlank(answerStatus)) {
				sql += " and question_status = ? ";
			}
			
			if(!StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				sql += " and question_registerday between to_date(?, 'yyyy-mm-dd') and to_date(?, 'yyyy-mm-dd hh24:mi:ss') ";
			}
			
			if(StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				sql += " and question_registerday <= to_date(?, 'yyyy-mm-dd hh24:mi:ss') ";
			}
			
			if(!StringUtil.isBlank(dateMin) && StringUtil.isBlank(dateMax)) {
				sql += " and question_registerday >= to_date(?, 'yyyy-mm-dd') ";
			}

			pstmt = conn.prepareStatement(sql);
			
			if(!StringUtil.isBlank(searchType) && !StringUtil.isBlank(searchWord)) {
				pstmt.setString(++count, searchWord);
			}
			
			if(!StringUtil.isBlank(privateStatus)) {
				pstmt.setString(++count, privateStatus);
			}
			
			if(!StringUtil.isBlank(answerStatus)) {
				pstmt.setString(++count, answerStatus);
			}
			
			if(!StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMin);
				pstmt.setString(++count, dateMax + " 23:59:59");
			}
			
			if(StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMax + " 23:59:59");
			}
			
			if(!StringUtil.isBlank(dateMin) && StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMin);
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalRowCount = rs.getInt("total");
			}
	
		} finally {
			close();
		}
		
		return totalRowCount;
	}
	
	/*
	 * 관리자 Q&A 관리페이지 질문 리스트를 조회하는 메소드
	 */
	@Override
	public List<BoardDTO> selectQuestionListByAdmin(Map<String, Object> paraMap) throws SQLException {
		
		List<BoardDTO> boardList = new ArrayList<>();
		
		PagingDTO pagingDTO = (PagingDTO)paraMap.get("pagingDTO");
		
		String searchType = (String)paraMap.get("searchType");
		
		String searchWord = (String)paraMap.get("searchWord");
		
		String sortCategory = (String)paraMap.get("sortCategory") == null ? "" : (String)paraMap.get("sortCategory");
		
		String privateStatus = (String)paraMap.get("privateStatus");
		
		String answerStatus = (String)paraMap.get("answerStatus");
		
		String dateMin = (String)paraMap.get("dateMin");
		
		String dateMax = (String)paraMap.get("dateMax");
		
		System.out.println(searchType);
		
		try {
			conn = ds.getConnection();

			String sql 	= " SELECT * "
						+ " FROM ( "
								+ " select ROWNUM AS RN, A.* "
								+ " from "
								+ " ( "
								+ " select q.pk_question_no , q.question_title, q.question_content, q.question_answer, "
								+ " q.question_status, q.question_isprivate, q.question_registerday, "
								+ " m.pk_member_no, m.member_name "
								+ " from tbl_question q join tbl_member m on q.fk_member_no = m.pk_member_no "
								+ " where 1=1 ";
			
			if(!StringUtil.isBlank(searchType) && !StringUtil.isBlank(searchWord)) {
				switch(searchType) {
					case "0" : {
						sql += " and question_title like '%' || ? || '%' ";
						break;
					}
					case "1" : {
						sql += " and member_name = ? ";
						break;
					}
				}
			}
			
			if(!StringUtil.isBlank(privateStatus)) {
				sql += " and question_isprivate = ? ";
			}
			
			if(!StringUtil.isBlank(answerStatus)) {
				sql += " and question_status = ? ";
			}
			
			if(!StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				sql += " and question_registerday between to_date(?, 'yyyy-mm-dd') and to_date(?, 'yyyy-mm-dd hh24:mi:ss') ";
			}
			
			if(StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				sql += " and question_registerday <= to_date(?, 'yyyy-mm-dd hh24:mi:ss') ";
			}
			
			if(!StringUtil.isBlank(dateMin) && StringUtil.isBlank(dateMax)) {
				sql += " and question_registerday >= to_date(?, 'yyyy-mm-dd') ";
			}
			
			switch(sortCategory) {
				case "0" : 
					sql += " order by question_registerday desc "; 
					break;
				case "1" : 
					sql += " order by question_registerday "; 
					break;
				default : 
					sql += " order by question_registerday desc "; 
					break;
			}
				
			sql += " ) A"
					+ ") "
					+ " WHERE RN between ? and ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			int count = 0;
			
			if(!StringUtil.isBlank(searchType) && !StringUtil.isBlank(searchWord)) {
				pstmt.setString(++count, searchWord);
			}
			
			if(!StringUtil.isBlank(privateStatus)) {
				pstmt.setString(++count, privateStatus);
			}
			
			if(!StringUtil.isBlank(answerStatus)) {
				pstmt.setString(++count, answerStatus);
			}
			
			if(!StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMin);
				pstmt.setString(++count, dateMax + " 23:59:59");
			}
			
			if(StringUtil.isBlank(dateMin) && !StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMax + " 23:59:59");
			}
			
			if(!StringUtil.isBlank(dateMin) && StringUtil.isBlank(dateMax)) {
				pstmt.setString(++count, dateMin);
			}
			
			pstmt.setInt(++count, pagingDTO.getFirstRow()); // 현재 페이지의 첫 레코드의 번호
			pstmt.setInt(++count, pagingDTO.getLastRow()); // 현재 페이지의 마지막 레코드의 번호
		
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BoardDTO boardDTO = new BoardDTO();
				
				// BoardDTO 저장
				boardDTO.setPk_question_no(rs.getInt("pk_question_no"));
				boardDTO.setQuestion_title(rs.getString("question_title"));
				boardDTO.setQuestion_content(rs.getString("question_content"));
				boardDTO.setQuestion_answer(rs.getString("question_answer"));
				boardDTO.setQuestion_status(rs.getInt("question_status"));
				boardDTO.setQuestion_isprivate(rs.getInt("question_isprivate"));
				boardDTO.setQuestion_registerday(rs.getString("question_registerday"));
				boardDTO.setFk_member_no(rs.getInt("pk_member_no"));
				boardDTO.setMemberName(rs.getString("member_name"));
				
				boardList.add(boardDTO);
		
			}
			
			System.out.println(sql);
	
		} finally {
			close();
		}
		
		return boardList;
	}


	// Q&A 게시판의 질문에 대해 답변 및 답변 상태를 수정하는 메소드
	@Override
	public int updateQuestion(Map<String, String> paraMap) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql 	= " update tbl_question set question_answer = ? , question_status = 1 "
						+ " where pk_question_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("questionAnswer")); // 질문 답변
			pstmt.setString(2, paraMap.get("pkQuestionNo")); // 질문 일련번호
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
	} // Q&A 게시판의 질문에 대해 답변 및 답변 상태를 수정하는 메소드

	// 관리자가 Q&A 게시판 질문을 상세조회하는 메소드
	@Override
	public BoardDTO selectQuestionDetailByAdmin(String questionNo) throws SQLException {
		BoardDTO boardDTO = new BoardDTO();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " select q.pk_question_no, q.question_title, q.question_content, q.question_answer, "
						+ " q.question_status, q.question_isprivate, to_char(q.question_registerday, 'yyyy-mm-dd') as question_registerday, "
						+ " p.member_name "
						+ " from tbl_question q "
						+ " join tbl_member p on q.fk_member_no = p.pk_member_no "
						+ " and q.pk_question_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, questionNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardDTO.setPk_question_no(rs.getInt("pk_question_no"));
				boardDTO.setQuestion_title(rs.getString("question_title"));
				boardDTO.setQuestion_content(rs.getString("question_content"));
				
				boardDTO.setQuestion_answer(rs.getString("question_answer"));
				boardDTO.setQuestion_status(rs.getInt("question_status"));
				boardDTO.setQuestion_isprivate(rs.getInt("question_isprivate"));
				boardDTO.setQuestion_registerday(rs.getString("question_registerday"));
			}
			
		} finally {
			close();
		}
		
		return boardDTO;
	}


	// 관리자 일주일간 미답변 질문 리스트
	@Override
	public List<Map<String, String>> selectWeekUnansweredQuestionList() throws SQLException {		
		List<Map<String, String>> weekUnansweredQuestionList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " select pk_question_no, member_name, question_title, question_registerday "
						+ " from tbl_question join tbl_member on fk_member_no = pk_member_no "
						+ " where question_status = 0 "
						+ " and question_registerday between sysdate-6 and sysdate "
						+ " order by question_registerday ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
				
				map.put("questionNo", String.valueOf(rs.getInt("pk_question_no")));
				map.put("memberName", rs.getString("member_name"));
				map.put("title", rs.getString("question_title"));
				map.put("registerday", rs.getString("question_registerday"));
				
				weekUnansweredQuestionList.add(map);
			}
			
		} finally {
			close();
		}
		
		return weekUnansweredQuestionList;
	}
	
}
