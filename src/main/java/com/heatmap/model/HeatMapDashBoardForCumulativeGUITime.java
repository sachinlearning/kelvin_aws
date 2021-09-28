package com.heatmap.model;

public class HeatMapDashBoardForCumulativeGUITime implements Comparable<HeatMapDashBoardForCumulativeGUITime> {

	private String trxCode;
	private Double individualPercentageForGuiTime;
	private Double cumlativeValueForGuiTime;

	public String getTrxCode() {
		return trxCode;
	}

	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}

	public Double getIndividualPercentageForGuiTime() {
		return individualPercentageForGuiTime;
	}

	public void setIndividualPercentageForGuiTime(Double individualPercentageForGuiTime) {
		this.individualPercentageForGuiTime = individualPercentageForGuiTime;
	}

	public Double getCumlativeValueForGuiTime() {
		return cumlativeValueForGuiTime;
	}

	public void setCumlativeValueForGuiTime(Double cumlativeValueForGuiTime) {
		this.cumlativeValueForGuiTime = cumlativeValueForGuiTime;
	}

	@Override
	public int compareTo(HeatMapDashBoardForCumulativeGUITime hmdb) {
		int returnValue = hmdb.getIndividualPercentageForGuiTime().compareTo(this.getIndividualPercentageForGuiTime());
		if (returnValue == 0)
			return this.getTrxCode().compareTo(hmdb.getTrxCode());
		return returnValue;
	}
}
