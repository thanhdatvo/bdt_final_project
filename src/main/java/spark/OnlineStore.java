package spark;

import java.io.Serializable;
import java.sql.Timestamp;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;

public class OnlineStore implements Serializable {
	private static final long serialVersionUID = 2369441390379166257L;
	private Timestamp eventTime;
	private String eventType;
	private String productId;
	private String categoryId;
	private String categoryCode;
	private String brand;
	private Double price;
	private String userId;
	private String userSession;

	public OnlineStore(String eventTime, String eventType, String productId, String categoryId, String categoryCode,
			String brand, String price, String userId, String userSession) {
		super();
		if (!"".equals(eventTime)) {
			DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss 'UTC'");
			LocalDateTime localDate = LocalDateTime.parse(eventTime, formatter);
			this.eventTime = new Timestamp(localDate.toDateTime().getMillis());
		}

		if (!"".equals(eventType))
			this.eventType = eventType;

		if (!"".equals(productId))
			this.productId = productId;

		if (!"".equals(categoryId))
			this.categoryId = categoryId;

		if (!"".equals(categoryCode))
			this.categoryCode = categoryCode;

		if (!"".equals(brand))
			this.brand = brand;

		if (!"".equals(price))
			this.price = Double.valueOf(price);

		if (!"".equals(userId))
			this.userId = userId;

		if (!"".equals(userSession))
			this.userSession = userSession;
	}

	public Timestamp getEventTime() {
		return eventTime;
	}

	public void setEventTime(Timestamp eventTime) {
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
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
