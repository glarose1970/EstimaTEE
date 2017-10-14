package com.suncoastsoftware.estimateepro.model;

/**
 * Created by Command Center on 10/7/2017.
 */

public class Estimate {

	public String custID = "";
	public String estimateID = "";
	public String title = "";
	public String description = "";
	public String notes = "";

	public long quantity;
	public double total_price;
	public double per_piece_price;
	public double shop_base_charge;
	public double screen_charge;
	public int num_colors;

	public String due_date = "";

	public String shirt_sizes = "";

	public boolean both_sides;

	public Estimate() {
	}

	public Estimate(String custID, String estimateID, String title, String description,
					String notes, long quantity, double total_price, double per_piece_price,
					double shop_base_charge, double screen_charge, int num_colors,
					String due_date, String shirt_sizes, boolean both_sides) {

		this.custID = custID;
		this.estimateID = estimateID;
		this.title = title;
		this.description = description;
		this.notes = notes;
		this.quantity = quantity;
		this.total_price = total_price;
		this.per_piece_price = per_piece_price;
		this.shop_base_charge = shop_base_charge;
		this.screen_charge = screen_charge;
		this.num_colors = num_colors;
		this.due_date = due_date;
		this.shirt_sizes = shirt_sizes;
		this.both_sides = both_sides;
	}

	public String getCustID() {
		return custID;
	}

	public void setCustID(String custID) {
		this.custID = custID;
	}

	public String getEstimateID() {
		return estimateID;
	}

	public void setEstimateID(String estimateID) {
		this.estimateID = estimateID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public double getTotal_price() {
		return total_price;
	}

	public void setTotal_price(double total_price) {
		this.total_price = total_price;
	}

	public double getPer_piece_price() {
		return per_piece_price;
	}

	public void setPer_piece_price(double per_piece_price) {
		this.per_piece_price = per_piece_price;
	}

	public double getShop_base_charge() {
		return shop_base_charge;
	}

	public void setShop_base_charge(double shop_base_charge) {
		this.shop_base_charge = shop_base_charge;
	}

	public double getScreen_charge() {
		return screen_charge;
	}

	public void setScreen_charge(double screen_charge) {
		this.screen_charge = screen_charge;
	}

	public int getNum_colors() {
		return num_colors;
	}

	public void setNum_colors(int num_colors) {
		this.num_colors = num_colors;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public String getShirt_size() {
		return shirt_sizes;
	}

	public void setShirt_size(String shirt_size) {
		this.shirt_sizes = shirt_size;
	}

	public boolean isBoth_sides() {
		return both_sides;
	}

	public void setBoth_sides(boolean both_sides) {
		this.both_sides = both_sides;
	}
}