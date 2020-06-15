package spark;
import java.io.Serializable;

public class Ecommerce implements Serializable {

	private String event_time;
	private String event_type;
	private String product_id;
	private String category_id ;
	private String category_code ;
	private String  brand ;
	private float  price ;
	private String  user_id ;
	private String  user_session;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public String getEvent_time() {
		return event_time;
	}



	public void setEvent_time(String event_time) {
		this.event_time = event_time;
	}



	public String getEvent_type() {
		return event_type;
	}



	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}



	public String getProduct_id() {
		return product_id;
	}



	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}



	public String getCategory_id() {
		return category_id;
	}



	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}



	public String getCategory_code() {
		return category_code;
	}



	public void setCategory_code(String category_code) {
		this.category_code = category_code;
	}



	public String getBrand() {
		return brand;
	}



	public void setBrand(String brand) {
		this.brand = brand;
	}



	public float getPrice() {
		return price;
	}



	public void setPrice(float price) {
		this.price = price;
	}



	public String getUser_id() {
		return user_id;
	}



	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}



	public String getUser_session() {
		return user_session;
	}



	public void setUser_session(String user_session) {
		this.user_session = user_session;
	}
	
	
	
	
	
	
	

}
