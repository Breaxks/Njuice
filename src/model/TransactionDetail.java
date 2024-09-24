package model;

public class TransactionDetail {
	
	private String ProductID, trID;
	private int quantity;
	public String getProductID() {
		return ProductID;
	}
	public void setProductID(String productID) {
		ProductID = productID;
	}
	public String getTrID() {
		return trID;
	}
	public void setTrID(String trID) {
		this.trID = trID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public TransactionDetail(String productID, String trID, int quantity) {
		super();
		ProductID = productID;
		this.trID = trID;
		this.quantity = quantity;
	}
	
	

}
