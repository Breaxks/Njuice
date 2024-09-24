package model;

public class MsProduct {
	
	private String productName, productMerk; 
	private int produckPrice, productStock;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductMerk() {
		return productMerk;
	}
	public void setProductMerk(String productMerk) {
		this.productMerk = productMerk;
	}
	public int getProduckPrice() {
		return produckPrice;
	}
	public void setProduckPrice(int produckPrice) {
		this.produckPrice = produckPrice;
	}
	public int getProductStock() {
		return productStock;
	}
	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}
	public MsProduct(String productId, String productName, String productMerk, int produckPrice, int productStock) {
		super();
		this.productName = productName;
		this.productMerk = productMerk;
		this.produckPrice = produckPrice;
		this.productStock = productStock;
	}
	
	

}
