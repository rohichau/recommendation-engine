package com.gameberry.sample.recommendation.models;

public class CostTracking {

	public CostTracking(int type, int noOfOrders) {
		super();
		this.type = type;
		this.noOfOrders = noOfOrders;
	}

	private int type;
	private int noOfOrders;

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the noOfOrders
	 */
	public int getNoOfOrders() {
		return noOfOrders;
	}

	/**
	 * @param noOfOrders the noOfOrders to set
	 */
	public void setNoOfOrders(int noOfOrders) {
		this.noOfOrders = noOfOrders;
	}

}