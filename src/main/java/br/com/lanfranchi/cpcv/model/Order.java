package br.com.lanfranchi.cpcv.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "venda")
public class Order implements Comparable<Order> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private double total;
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "order")
	private List<ItemHistory> items;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public List<ItemHistory> getItems() {
		return items;
	}

	public void setItems(List<ItemHistory> items) {
		this.items = items;
	}

	public int compareTo(Order o) {
		return String.valueOf(o.getCreatedAt()).compareToIgnoreCase(String.valueOf(this.createdAt));
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", total=" + total + ", createdAt=" + createdAt + "]";
	}
	
}
