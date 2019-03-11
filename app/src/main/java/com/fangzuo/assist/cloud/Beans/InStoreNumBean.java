package com.fangzuo.assist.cloud.Beans;

public class InStoreNumBean {
	public String FItemID;
	public String FStockID;
	public String FStockPlaceID;
	public String FBatchNo;
	public String FKFDate;
	public String FKFPeriod;

	public InStoreNumBean(String FItemID, String FStockID, String FStockPlaceID, String FBatchNo) {
		this.FItemID = FItemID;
		this.FStockID = FStockID;
		this.FStockPlaceID = FStockPlaceID;
		this.FBatchNo = FBatchNo;
	}
}
