package com.heatmap.excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
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
import com.heatmap.util.ExceptionUtil;

@Component
public class ExcelActivity {

	private static final Logger logger = LoggerFactory.getLogger(ExcelActivity.class);

	private Map<String, String> getDescriptionOfTrxCode;

	private DecimalFormat df2 = new DecimalFormat("#.##");

	@Autowired
	private ScatterChartActivity scatterChart;
	
	@Autowired
	private ExceptionUtil expUtil;

	public List<HeatMapDashBoard> readDataFromExcel(InputStream uploadStream, Map<String, String> columnIndex)
			throws InvalidFormatException, IOException {
		logger.info("Executing method method readDataFromExcel of class ExcelActivity");
		List<HeatMapDashBoard> lhmdb = null;
		Workbook workbook = getWorkBook(uploadStream);
		int columnNoOfTxCode = Integer.parseInt(columnIndex.get(AppConstant.TCODE));
		int columnNoOfTxCount = Integer.parseInt(columnIndex.get(AppConstant.TCOUNT));
		int columnNoOfGuiTime = Integer.parseInt(columnIndex.get(AppConstant.GTIME));
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		Set<HeatMapDashBoard> heatMapSet = new TreeSet<>();
		Map<String, HeatMapDashBoard> hmap = new HashMap<>();
		String key = null;
		double sumOfTrxCount = 0;
		double sumOfGuiTime = 0;
		int noOfRows = 0;
		while (iterator.hasNext()) {
			int toInsertInHashMap = 0;
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			if (noOfRows > 0) {
				HeatMapDashBoard hmDashBoardData = new HeatMapDashBoard();
				int i = 0;
				int recordExist = 0;
				menuLoop: while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellTypeEnum()) {
					case STRING:
						if (i == columnNoOfTxCode) {
							key = getTransactionCode(cell.getStringCellValue());
							if (key == null || key.trim().equals("")) {
								toInsertInHashMap = 1;
								break menuLoop;
							}
							String description = getDescriptionOfTrxCode.get(key);
							if (hmap.get(key) == null)
								hmDashBoardData.setTrxCode(key);
							else {
								hmDashBoardData = hmap.get(key);
								recordExist = 1;
							}
							hmDashBoardData.setDescription(
									description == null || description.trim().equals("") ? "Others" : description);
						}
						break;
					case NUMERIC:
						double cellValue = Double.parseDouble(this.df2.format(cell.getNumericCellValue()));
						if (i == columnNoOfTxCount) {
							if (recordExist == 1) {
								hmDashBoardData.setTrxCount(hmDashBoardData.getTrxCount() + cellValue);
							} else {
								hmDashBoardData.setTrxCount(cellValue);
							}
							sumOfTrxCount += cellValue;
						} else if (i == columnNoOfGuiTime) {
							if (recordExist == 1) {
								hmDashBoardData.setGuiTime(hmDashBoardData.getGuiTime() + cellValue);
							} else {
								hmDashBoardData.setGuiTime(cellValue);
							}
							sumOfGuiTime += cellValue;
						}
						break;
					default:
						break;
					}
					i++;
				}
				if (toInsertInHashMap == 0)
					hmap.put(key, hmDashBoardData);
			}
			noOfRows++;
		}
		heatMapSet.addAll(hmap.values());
		heatMapSet = scatterChart.addAllData(heatMapSet, sumOfTrxCount, sumOfGuiTime);
		lhmdb = new ArrayList<>(heatMapSet);
		return lhmdb;
	}

	public String getExtension(String fileName) {
		logger.info("Executing method method getExtension of class ExcelActivity");
		char ch;
		int len;
		if (fileName == null || (len = fileName.length()) == 0 || (ch = fileName.charAt(len - 1)) == '/' || ch == '\\'
				|| // in the case of a directory
				ch == '.') // in the case of . or ..
			return "";
		int dotInd = fileName.lastIndexOf('.');
		int sepInd = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
		if (dotInd <= sepInd)
			return "";
		else
			return fileName.substring(dotInd + 1).toLowerCase();
	}

	public Workbook getWorkBook(InputStream is) throws InvalidFormatException, IOException {
		logger.info("Executing method method getWorkbook of class ExcelActivity");
		return WorkbookFactory.create(is);
	}

	public void readDescriptionFileForTrxCode() {
		logger.info("Executing method readDescriptionFileForTrxCode of class ExcelActivity");
		if (getDescriptionOfTrxCode == null) {
			try {
				String decodedPath = URLDecoder
						.decode(getClass().getClassLoader().getResource("DescriptionMaster.xlsx").getFile(), "UTF-8");
				decodedPath = decodedPath.replace("file:/", "");
				decodedPath = decodedPath.replace("\\u0020", "\\ ");
				File descriptionFile = new File(decodedPath);
				getDescriptionOfTrxCode = scatterChart.readDataForDescriptionFromExcel(descriptionFile);
			} catch (UnsupportedEncodingException e) {
				logger.error(expUtil.getExceptionMessage(e));
			}
		}
	}

	public String getTransactionCode(String key) {
		logger.info("Executing method getTransactionCode of class ExcelActivity");
		if (key == null || key.trim().equals("")) {
			return null;
		}
		String keyToStore = key.trim();
		if (keyToStore.contains(" ") || keyToStore.contains("	")) {
			if (keyToStore.charAt(keyToStore.length() - 1) == 'T'
					|| keyToStore.charAt(keyToStore.length() - 1) == 't') {
				keyToStore = keyToStore.substring(0, keyToStore.length() - 1);
				key = keyToStore.replaceAll("\\s+", "");
			} else {
				return null;
			}
		} else {
			key = keyToStore;
		}
		return key;
	}

}
