package br.com.lanfranchi.cpcv.model;

public class Item {
	
	private Product item;
	private double quantity;
	private double total;

	public Product getItem() {
		return this.item;
	}

	public void setItem(Product item) {
		this.item = item;
	}

	public double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
