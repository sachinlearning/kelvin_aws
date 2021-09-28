package com.heatmap.excel;

import java.io.File;


import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heatmap.constants.AppConstant;
import com.heatmap.model.HeatMapDashBoard;
import com.heatmap.model.HeatMapDashBoardForCumulativeGUITime;
import com.heatmap.util.ExceptionUtil;
import com.heatmap.util.PropertiesFileUtility;

@Component
public class ScatterChartActivity {

	private static final Logger logger = LoggerFactory.getLogger(ScatterChartActivity.class);

	private DecimalFormat df2 = new DecimalFormat("#.##");

	private String mCritical = null;
	private String critical = null;
	private String lCritical = null;

	@Autowired
	private ExceptionUtil expUtil;
	
	@Autowired
	private PropertiesFileUtility propFileUtil;

	public Set<HeatMapDashBoard> addAllData(Set<HeatMapDashBoard> hmDashBoards, double sumOftrxCount,
			double sumOfGuiTime) {
		logger.info("Executing method addAllData of class ScatterChartActivity");
		Set<HeatMapDashBoardForCumulativeGUITime> heatMapSet = new TreeSet<>();
		if (!hmDashBoards.isEmpty()) {
			double cumalativeValueForTrxCount = 0;
			for (HeatMapDashBoard hmdb : hmDashBoards) {
				HeatMapDashBoardForCumulativeGUITime hmdb2 = new HeatMapDashBoardForCumulativeGUITime();
				hmdb2.setTrxCode(hmdb.getTrxCode());
				hmdb.setIndividualPercentageForTrxCount(
						Double.parseDouble(df2.format(getIndividualPercantage(hmdb.getTrxCount(), sumOftrxCount))));
				double individualPercentageForGuiTime = Double
						.parseDouble(df2.format(getIndividualPercantage(hmdb.getGuiTime(), sumOfGuiTime)));
				hmdb.setIndividualPercentageForGuiTime(individualPercentageForGuiTime);
				hmdb2.setIndividualPercentageForGuiTime(individualPercentageForGuiTime);
				cumalativeValueForTrxCount += hmdb.getIndividualPercentageForTrxCount();
				hmdb.setCumlativeValueForTrxCount(Double.parseDouble(df2.format(cumalativeValueForTrxCount)));
				heatMapSet.add(hmdb2);
			}
			Map<String, HeatMapDashBoardForCumulativeGUITime> tMap = settingCumulativeGUITime(heatMapSet);
			setCriticalityValue();
			for (HeatMapDashBoard hmdb : hmDashBoards) {
				HeatMapDashBoardForCumulativeGUITime h2 = tMap.get(hmdb.getTrxCode());
				double cumalativeValueForTcount = hmdb.getCumlativeValueForTrxCount();
				double cumalativeValueForGuiTimes = h2.getCumlativeValueForGuiTime();
				hmdb.setCumlativeValueForGuiTime(Double.parseDouble(df2.format(cumalativeValueForGuiTimes)));

				if ((cumalativeValueForTcount >= 0 && cumalativeValueForTcount < 80)
						|| (cumalativeValueForGuiTimes >= 0 && cumalativeValueForGuiTimes < 80))
					hmdb.setColorCode(mCritical);
				else if (cumalativeValueForTcount >= 80 && cumalativeValueForTcount < 90)
					hmdb.setColorCode(critical);
				else
					hmdb.setColorCode(lCritical);
			}
		}
		return hmDashBoards;
	}

	public double getIndividualPercantage(double trxCount, double totalSum) {
		if (totalSum == 0)
			return 0;
		return (trxCount / totalSum) * 100;
	}

	

	public Map<String, HeatMapDashBoardForCumulativeGUITime> settingCumulativeGUITime(
			Set<HeatMapDashBoardForCumulativeGUITime> hmdb2) {
		logger.info("Executing method of settingCumulativeGUITime of class ScatterChartActivity");
		Map<String, HeatMapDashBoardForCumulativeGUITime> tMap = new HashMap<>();
		double cumulativeValueForGUITime = 0;
		for (HeatMapDashBoardForCumulativeGUITime hmd : hmdb2) {
			cumulativeValueForGUITime += hmd.getIndividualPercentageForGuiTime();
			hmd.setCumlativeValueForGuiTime(cumulativeValueForGUITime);
			tMap.put(hmd.getTrxCode(), hmd);
		}
		return tMap;
	}

	public Map<String, String> readDataForDescriptionFromExcel(File descriptionFile) {
		logger.info("Executing method readDataForDescriptionFromExcel of class ScatterChartActivity");
		Map<String, String> getDescription = new HashMap<>();
		String cellValue = null;
		String key = null;
		String value = null;
		try (Workbook workbook = WorkbookFactory.create(descriptionFile)) {
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = firstSheet.iterator();
			int noOfRows = 0;
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				if (noOfRows > 0) {
					int i = 0;
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						if (cell.getCellTypeEnum() == CellType.STRING) {
							cellValue = cell.getStringCellValue();
							if (i == 0)
								key = cellValue;
							else
								value = cellValue;
						}
						i++;
					}
					getDescription.put(key, value);
				}
				noOfRows++;
			}
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			logger.error(expUtil.getExceptionMessage(e));		}
		return getDescription;
	}

	public void setCriticalityValue() {
		logger.info("Executing method setCriticalityValue of class ScatterChartActivity");
		String colorCode = propFileUtil.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, "color");
		if (!(colorCode == null || colorCode.trim().equals(""))) {
			String[] colorStr = colorCode.split("~");
			mCritical = colorStr[0];
			critical = colorStr[1];
			lCritical = colorStr[2];
		} else {
			mCritical = AppConstant.MOSTCRITICAL;
			critical = AppConstant.CRITICAL;
			lCritical = AppConstant.LEASTCRITICAL;
		}
	}
}