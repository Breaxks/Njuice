package model;

public class TransactionHeader {
	private String trID, id, trDate, courier;
	private int delivery;
	public String getTrID() {
		return trID;
	}
	public void setTrID(String trID) {
		this.trID = trID;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTrDate() {
		return trDate;
	}
	public void setTrDate(String trDate) {
		this.trDate = trDate;
	}
	public String getCourier() {
		return courier;
	}
	public void setCourier(String courier) {
		this.courier = courier;
	}
	public int getDelivery() {
		return delivery;
	}
	public void setDelivery(int delivery) {
		this.delivery = delivery;
	}
	public TransactionHeader(String trID, String id, String trDate, String courier, int delivery) {
		super();
		this.trID = trID;
		this.id = id;
		this.trDate = trDate;
		this.courier = courier;
		this.delivery = delivery;
	}
	
	
}
