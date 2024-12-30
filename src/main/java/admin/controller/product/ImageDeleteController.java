package admin.controller.product;

import java.io.PrintWriter;
import java.sql.SQLException;

import common.Constants;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

public class ImageDeleteController extends AbstractController {
	
	ProductDAO productDAO = new ProductDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		
		if(!super.checkAdminLogin(request)) {
			return;
		}
		
		if("POST".equalsIgnoreCase(method)) {
			String imageNo = request.getParameter("imageNo");
			
			try {
				int result = productDAO.deleteProductImage(imageNo);

				String message = result == 1 ? "success" : "failed";
				
				super.setJsonResponse(true);

			    response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    
			    String jsonData = "{\"message\":\"" + message + "\"}";
			    
			    PrintWriter out = response.getWriter();
			    out.print(jsonData);
			    out.flush();
				
			} catch (SQLException e) {
				e.printStackTrace();
				super.setRedirect(true);
				super.setViewPage(Constants.ERROR_URL);
			}

		}
		else {
			super.handleMessage(request, Constants.INVALID_REQUEST, Constants.ADMIN_PRODUCT_MANAGE_URL);
		}
		
	}

}
