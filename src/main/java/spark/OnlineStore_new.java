package spark;

import java.io.Serializable;

public class OnlineStore_new implements Serializable {
	private static final long serialVersionUID = 2369441390379166257L;
	private String eventTime;
	private String eventType;
	private String productId;
	private String categoryId;
	private String categoryCode;
	private String brand;
	private String price;
	private String userId;
	private String userSession;

	public OnlineStore_new(String eventTime, String eventType, String productId, String categoryId, String categoryCode,
			String brand, String price, String userId, String userSession) {
		super();
		this.eventTime = eventTime;
		this.eventType = eventType;
		this.productId = productId;
		this.categoryId = categoryId;
		this.categoryCode = categoryCode;
		this.brand = brand;
		this.price = price;
		this.userId = userId;
		this.userSession = userSession;
	}

	public String getEventTime() {
		return eventTime;
	}

	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserSession() {
		return userSession;
	}

	public void setUserSession(String userSession) {
		this.userSession = userSession;
	}

}
