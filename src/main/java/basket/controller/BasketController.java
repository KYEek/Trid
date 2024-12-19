package basket.controller;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BasketController extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(super.checkLogin(request)) {
			System.out.println("로그인 되어짐");
		}
		else {
			System.out.println("로그인 안되어짐");
		}
		
		super.setRedirect(false);
		super.setViewPage(Constants.BASKET_PAGE);

	}

}
