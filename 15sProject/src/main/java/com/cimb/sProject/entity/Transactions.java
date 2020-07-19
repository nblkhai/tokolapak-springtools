package com.cimb.sProject.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Transactions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int totalPrice;
	private boolean statusPayment;
	private String trasactionDate;
	private String trasactionDateDone;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public boolean isStatusPayment() {
		return statusPayment;
	}
	public void setStatusPayment(boolean statusPayment) {
		this.statusPayment = statusPayment;
	}
	public String getTrasactionDate() {
		return trasactionDate;
	}
	public void setTrasactionDate(String trasactionDate) {
		this.trasactionDate = trasactionDate;
	}
	public String getTrasactionDateDone() {
		return trasactionDateDone;
	}
	public void setTrasactionDateDone(String trasactionDateDone) {
		this.trasactionDateDone = trasactionDateDone;
	}
	
	
}
