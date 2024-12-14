package board.domain;

public class BoardDTO {

    private int pk_question_no;	// 질문일련번호
    
    private int fk_member_no;	// 회원일련번호 외래키
    
    private String question_title;	// 제목
    
    private String question_content;	// 내용
    
    private String question_answer;	// 답변
    
    private int question_isprivate;	// 비공개여부 (비공개 or 공개)
    
    private int question_status;	// 답변상태 (답변 중 or 완료)
    
    private String question_registerday;	// 등록일자

    
    
    ////////////////////////////////////////////////////////////////////////////////
    
    
    
	public int getPk_question_no() {
		return pk_question_no;
	}

	public void setPk_question_no(int pk_question_no) {
		this.pk_question_no = pk_question_no;
	}

	
	public int getFk_member_no() {
		return fk_member_no;
	}

	public void setFk_member_no(int fk_member_no) {
		this.fk_member_no = fk_member_no;
	}

	
	public String getQuestion_title() {
		return question_title;
	}

	public void setQuestion_title(String question_title) {
		this.question_title = question_title;
	}

	
	public String getQuestion_content() {
		return question_content;
	}

	public void setQuestion_content(String question_content) {
		this.question_content = question_content;
	}

	
	public String getQuestion_answer() {
		return question_answer;
	}

	public void setQuestion_answer(String question_answer) {
		this.question_answer = question_answer;
	}

	
	public int getQuestion_isprivate() {
		return question_isprivate;
	}

	public void setQuestion_isprivate(int question_isprivate) {
		this.question_isprivate = question_isprivate;
	}

	
	public int getQuestion_status() {
		return question_status;
	}

	public void setQuestion_status(int question_status) {
		this.question_status = question_status;
	}

	
	public String getQuestion_registerday() {
		return question_registerday;
	}
	
	public void setQuestion_registerday(String question_registerday) {
		this.question_registerday = question_registerday;
	}
    
    
	
    
    
    
    
    
}
