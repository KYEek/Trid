package member.controller;

import java.util.HashMap;

import org.json.simple.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.nurigo.java_sdk.api.Message;
import java.util.Random;

public class SmsSend extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse respone) throws Exception {
		
	  
      Random rnd = new Random(); // 인증키를 랜덤하게 생성하도록 한다.s
      
      String certification_code = ""; // 인증키는 숫자 4글자로 만들겠습니다.
      
      int randnum = 0;
      for(int i=0; i<4; i++) {
         /*
            min 부터 max 사이의 값으로 랜덤한 정수를 얻으려면 
             int rndnum = rnd.nextInt(max - min + 1) + min;
                 숫자 0 부터 9 까지 랜덤하게 1개를 만든다.
         */
         randnum = rnd.nextInt(9 - 0 + 1) + 0;
         certification_code += randnum;
         
     // 발급한 인증코드를 세션에 저장시킨다.
     HttpSession session = request.getSession();
     session.setAttribute("certification_code", certification_code);
     
      } // end of for()-----------------------------
      
	  // >> SMS발송 <<
      //   HashMap 에 받는사람번호, 보내는사람번호, 문자내용 등 을 저장한뒤 Coolsms 클래스의 send를 이용해 보냅니다.
         
      // String api_key = "발급받은 본인의 API Key";  // 발급받은 본인 API Key		
		 String api_key = "NCST8SOJC8REVIW1"; 	//이연진꺼임
		
	  // String api_secret = "발급받은 본인의 API Secret";  			 
		 String api_secret = "XPRTUXYKW2K0Y79GBZNANSU1N4EFK8VH";     //이연진꺼임
	
	  Message coolsms = new Message(api_key, api_secret);
      // net.nurigo.java_sdk.api.Message 임. 
      // 먼저 다운 받은  javaSDK-2.2.jar 와 json-simple-1.1.1.jar 를 /Trid/src/main/webapp/WEB-INF/lib/ 안에 넣어서  build 시켜야 함.
	 
	  String mobile = request.getParameter("mobile");
      String smsContent = "인증번호["+ certification_code +"]를 입력해주세요.";
		 
      
	  // == 4개 파라미터(to, from, type, text)는 필수사항이다. == //
      HashMap<String, String> paraMap = new HashMap<>();
      paraMap.put("to", mobile); 		  // 수신번호
      paraMap.put("from", "01054496012"); // 발신번호
      // 2020년 10월 16일 이후로 발신번호 사전등록제로 인해 등록된 발신번호로만 문자를 보내실 수 있습니다
      paraMap.put("type", "SMS");         // Message type ( SMS(단문), LMS(장문), MMS, ATA )
      paraMap.put("text", smsContent);    // 문자내용
      paraMap.put("app_version", "JAVA SDK v2.2"); // application name and version

      JSONObject jsobj = (JSONObject) coolsms.send(paraMap);
      jsobj.put("certification_code", certification_code);
      /*
         org.json.JSONObject 이 아니라 
         org.json.simple.JSONObject 이어야 한다.  
      */
      
      String json = jsobj.toString();
      
      // System.out.println("~~~~ 확인용 json => " + json);
      // ~~~~ 확인용 json => {"group_id":"R2GWPBT7UoW308sI","success_count":1,"error_count":0}
      
      request.setAttribute("json", json);
      
   // super.setRedirect(false);
      super.setViewPage("/WEB-INF/jsonview.jsp");       
      
	}// end of public void execute(HttpServletRequest request, HttpServletResponse respone) throws Exception-------------

}
