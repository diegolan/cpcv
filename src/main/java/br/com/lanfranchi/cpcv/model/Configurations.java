package br.com.lanfranchi.cpcv.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Configurations {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String password;
	private String name;
	private String telephone;
	private String address;
	private boolean printClient;
	private boolean printOrderType;
	private boolean printPaymentType;
	private Integer topSpaces;
	private Integer bottomSpaces;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isPrintClient() {
		return printClient;
	}

	public void setPrintClient(boolean printClient) {
		this.printClient = printClient;
	}

	public boolean isPrintOrderType() {
		return printOrderType;
	}

	public void setPrintOrderType(boolean printOrderType) {
		this.printOrderType = printOrderType;
	}

	public boolean isPrintPaymentType() {
		return printPaymentType;
	}

	public void setPrintPaymentType(boolean printPaymentType) {
		this.printPaymentType = printPaymentType;
	}

	public int getTopSpaces() {
		if (topSpaces == null) {
			return 1;
		}
		return topSpaces;
	}

	public void setTopSpaces(int topSpaces) {
		this.topSpaces = topSpaces;
	}

	public int getBottomSpaces() {
		if (bottomSpaces == null) {
			return 1;
		}
		return bottomSpaces;
	}

	public void setBottomSpaces(int bottomSpaces) {
		this.bottomSpaces = bottomSpaces;
	}

}
