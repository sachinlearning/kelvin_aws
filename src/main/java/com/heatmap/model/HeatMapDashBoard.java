package com.heatmap.model;

public class HeatMapDashBoard implements Comparable<HeatMapDashBoard> {

	private String trxCode;
	private Double trxCount;
	private Double guiTime;
	private Double individualPercentageForTrxCount;
	private Double cumlativeValueForTrxCount;
	private Double individualPercentageForGuiTime;
	private Double cumlativeValueForGuiTime;
	private String colorCode;
	private String description;

	public String getTrxCode() {
		return trxCode;
	}

	public void setTrxCode(String trxCode) {
		this.trxCode = trxCode;
	}

	public Double getTrxCount() {
		return trxCount;
	}

	public void setTrxCount(Double trxCount) {
		this.trxCount = trxCount;
	}

	public Double getGuiTime() {
		return guiTime;
	}

	public void setGuiTime(Double guiTime) {
		this.guiTime = guiTime;
	}

	public Double getIndividualPercentageForTrxCount() {
		return individualPercentageForTrxCount;
	}

	public void setIndividualPercentageForTrxCount(Double individualPercentageForTrxCount) {
		this.individualPercentageForTrxCount = individualPercentageForTrxCount;
	}

	public Double getCumlativeValueForTrxCount() {
		return cumlativeValueForTrxCount;
	}

	public void setCumlativeValueForTrxCount(Double cumlativeValueForTrxCount) {
		this.cumlativeValueForTrxCount = cumlativeValueForTrxCount;
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

	public String getColorCode() {
		return colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	@Override
	public int compareTo(HeatMapDashBoard hmdb) {
		int returnValue = hmdb.getTrxCount().compareTo(this.getTrxCount());
		if (returnValue == 0)
			return this.getTrxCode().compareTo(hmdb.getTrxCode());
		return returnValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((trxCode == null) ? 0 : trxCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HeatMapDashBoard other = (HeatMapDashBoard) obj;
		if (trxCode == null) {
			if (other.trxCode != null)
				return false;
		} else if (!trxCode.equals(other.trxCode)) {
			return false;
		}
		return true;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
