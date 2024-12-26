package basket.domain;

import member.domain.MemberDTO;
import product.domain.ColorDTO;
import product.domain.ImageDTO;
import product.domain.ProductDTO;
import product.domain.ProductDetailDTO;

public class BasketDTO {

	
	 int pk_basket_no;    
	 int fk_member_no;
	 int fk_product_detail_no;
	 int basket_quantity;
	 
	 MemberDTO fk_member;
	 ColorDTO color;
	 ImageDTO image;
	 ProductDTO pruduct;
	 ProductDetailDTO product_detail;
	 
	 
	 
	 
	public int getPk_basket_no() {
		return pk_basket_no;
	}
	public void setPk_basket_no(int pk_basket_no) {
		this.pk_basket_no = pk_basket_no;
	}
	public int getFk_member_no() {
		return fk_member_no;
	}
	public void setFk_member_no(int fk_member_no) {
		this.fk_member_no = fk_member_no;
	}
	public int getFk_product_detail_no() {
		return fk_product_detail_no;
	}
	public void setFk_product_detail_no(int fk_product_detail_no) {
		this.fk_product_detail_no = fk_product_detail_no;
	}
	public int getBasket_quantity() {
		return basket_quantity;
	}
	public void setBasket_quantity(int basket_quantity) {
		this.basket_quantity = basket_quantity;
	}
	public MemberDTO getFk_member() {
		return fk_member;
	}
	public void setFk_member(MemberDTO fk_member) {
		this.fk_member = fk_member;
	}
	public ColorDTO getColor() {
		return color;
	}
	public void setColor(ColorDTO color) {
		this.color = color;
	}
	public ImageDTO getImage() {
		return image;
	}
	public void setImage(ImageDTO image) {
		this.image = image;
	}
	public ProductDTO getPruduct() {
		return pruduct;
	}
	public void setPruduct(ProductDTO pruduct) {
		this.pruduct = pruduct;
	}
	public ProductDetailDTO getProduct_detail() {
		return product_detail;
	}
	public void setProduct_detail(ProductDetailDTO product_detail) {
		this.product_detail = product_detail;
	}
	 
	 

	
	
}
