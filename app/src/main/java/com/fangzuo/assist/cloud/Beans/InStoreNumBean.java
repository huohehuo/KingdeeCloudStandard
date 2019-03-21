package com.fangzuo.assist.cloud.Beans;

public class InStoreNumBean {
	public String FItemID;
	public String FStockID;
	public String FStockPlaceID;
	public String FBatchNo;
	public String FKFDate;
	public String FKFPeriod;
	public String FOwnerID;//货主id

	public InStoreNumBean(String FItemID, String FStockID, String FStockPlaceID, String FBatchNo) {
		this.FItemID = FItemID;
		this.FStockID = FStockID;
		this.FStockPlaceID = FStockPlaceID;
		this.FBatchNo = FBatchNo;
	}
	public InStoreNumBean(String FItemID, String FStockID, String FStockPlaceID, String FBatchNo,String orgid) {
		this.FItemID = FItemID;
		this.FStockID = FStockID;
		this.FStockPlaceID = FStockPlaceID;
		this.FBatchNo = FBatchNo;
		this.FOwnerID = orgid;
	}
}
