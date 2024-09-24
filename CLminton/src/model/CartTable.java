package model;

public class CartTable {
	
	private String id, productID;
	private Integer Quantity;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public Integer getQuantity() {
		return Quantity;
	}
	public void setQuantity(Integer quantity) {
		Quantity = quantity;
	}
	public CartTable(String id, String productID, Integer quantity) {
		super();
		this.id = id;
		this.productID = productID;
		Quantity = quantity;
	} 
	
	
}
