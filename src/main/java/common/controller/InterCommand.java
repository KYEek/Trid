package common.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface InterCommand {

	/*
	 * HTTP Method(GET, POST) 요청을 받아 실행하는 메소드
	 */
	void executeCommand(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
