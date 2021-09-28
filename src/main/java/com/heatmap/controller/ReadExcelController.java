package com.heatmap.controller;

import java.io.IOException;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.heatmap.constants.AppConstant;
import com.heatmap.excel.ExcelActivity;
import com.heatmap.model.UserDetails;
import com.heatmap.model.HeatMapDashBoard;
import com.heatmap.model.UploadedJsonData;
import com.heatmap.service.UploadJsonDataService;
import com.heatmap.service.UserDetailsService;
import com.heatmap.util.ExceptionUtil;
import com.heatmap.util.PropertiesFileUtility;

@RestController
public class ReadExcelController {

	private static final Logger logger = LoggerFactory.getLogger(ReadExcelController.class);

	@Autowired
	private ExcelActivity excelActivity;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UploadJsonDataService uploadJsonDataService;

	@Autowired
	private ExceptionUtil expUtil;

	@Autowired
	private PropertiesFileUtility propFileUtil;

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping(value = "/api/upload", produces = "application/json")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile, HttpSession session) {
		logger.info("Executing method uploadFile of class ReadExcelController");
		if (session.getAttribute(AppConstant.USEREMAIL) == null)
			return new ResponseEntity<>("User has not logged in", HttpStatus.OK);
		List<HeatMapDashBoard> lhmdb = null;
		if (uploadfile.isEmpty()) {
			return new ResponseEntity<>("Kindly select a file!", HttpStatus.NOT_FOUND);
		}
		try {
			String extensionOfFile = excelActivity.getExtension(uploadfile.getOriginalFilename());
			if (extensionOfFile.equalsIgnoreCase("xls") || extensionOfFile.equalsIgnoreCase("xlsx")) {
				Map<String, String> columnIndex = validatingHeader(uploadfile.getInputStream());
				if (columnIndex.get(AppConstant.ERROR) != null) {
					return new ResponseEntity<>(columnIndex.get(AppConstant.ERROR), HttpStatus.BAD_REQUEST);
				}
				excelActivity.readDescriptionFileForTrxCode();
				lhmdb = excelActivity.readDataFromExcel(uploadfile.getInputStream(), columnIndex);
			} else {
				return new ResponseEntity<>("Kindly upload an excel file", HttpStatus.BAD_REQUEST);
			}
		} catch (InvalidFormatException | IOException | RuntimeException e) {
			logger.error(expUtil.getExceptionMessage(e));
			return new ResponseEntity<>("Data is not in the required format", HttpStatus.BAD_REQUEST);
		}
		String email = (String) session.getAttribute(AppConstant.USEREMAIL);
		saveTheData(email, lhmdb);
		if (lhmdb.isEmpty()) {
			return new ResponseEntity<>("No Data Found", HttpStatus.OK);
		}

		return new ResponseEntity<>(lhmdb, HttpStatus.OK);
	}

	public JsonArray convertToJSONArray(List<?> hmDashBoard) {
		logger.info("Executing method convertToJSONArray of class ReadExcelController");
		Gson gson = new GsonBuilder().create();
		return gson.toJsonTree(hmDashBoard).getAsJsonArray();
	}

	public void saveTheData(String email, List<HeatMapDashBoard> lhmdb) {
		logger.info("Executing method saveTheData of class ReadExcelController");
		UserDetails userDetails = userDetailsService.findByEmail(email);
		UploadedJsonData uploadedJsonData = uploadJsonDataService.findByUserDetails(userDetails);
		if (uploadedJsonData == null) {
			uploadedJsonData = new UploadedJsonData();
			uploadedJsonData.setCreatedOn(new Date());
		}
		if (userDetails.getUniqueId() == null)
			userDetails.setUniqueId(Base64.encodeBase64String((email + System.currentTimeMillis()).getBytes()));
		uploadedJsonData.setUpdatedOn(new Date());
		uploadedJsonData.setJson(convertToJSONArray(lhmdb).toString());

		userDetailsService.saveFileUploadData(userDetails, uploadedJsonData);
	}

	@GetMapping("/api/hello")
	public String getHello() {
		return "Hello HeatMap!";
	}

	public Map<String, String> validatingHeader(InputStream uploadStream) throws IOException, InvalidFormatException {
		Map<String, String> columnIndex = new HashMap<>();
		logger.info("Executing method validatingHeader of class ReadExcelController");
		Workbook wb = excelActivity.getWorkBook(uploadStream);
		Sheet sheet = wb.getSheetAt(AppConstant.SHEETNOOFEXCELTOREAD);
		Row headerRow = sheet.getRow(AppConstant.HEADERROW);
		if (headerRow == null) {
			wb.close();
			columnIndex.put(AppConstant.ERROR,
					propFileUtil.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, "noHeader"));
		} else {
			wb.close();
			columnIndex.putAll(getHeaderColumnIndex(headerRow.cellIterator(), columnIndex));
			if (columnIndex.isEmpty()) {
				columnIndex.put(AppConstant.ERROR,
						propFileUtil.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, "noColumnFound"));
			} else {
				String checkValue = checkingColumn(columnIndex);
				if (!checkValue.equals(""))
					columnIndex.put(AppConstant.ERROR, checkValue);
			}
		}
		return columnIndex;
	}

	public Map<String, String> getHeaderColumnIndex(Iterator<Cell> cells, Map<String, String> columnIndex) {
		logger.info("Executing method getHeaderColumnIndex of class ReadExcelController");
		String tcode = propFileUtil.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, AppConstant.TCODE);
		String tcount = propFileUtil.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, AppConstant.TCOUNT);
		String gtime = propFileUtil.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, AppConstant.GTIME);
		while (cells.hasNext()) {
			Cell cell = cells.next();
			if (cell.getCellTypeEnum() == CellType.STRING) {
				String headerValue = cell.getStringCellValue();
				if (tcode.contains(headerValue.trim())) {
					columnIndex.put(AppConstant.TCODE, String.valueOf(cell.getColumnIndex()));
				} else if (tcount.contains(headerValue.trim())) {
					columnIndex.put(AppConstant.TCOUNT, String.valueOf(cell.getColumnIndex()));
				} else if (gtime.contains(headerValue.trim())) {
					columnIndex.put(AppConstant.GTIME, String.valueOf(cell.getColumnIndex()));
				}
			}
		}
		return columnIndex;
	}

	public String checkingColumn(Map<String, String> columnIndex) {
		logger.info("Executing method checkColumn of class ReadExcelController");
		String columnNotFound = "";
		if (columnIndex.get(AppConstant.TCODE) == null)
			columnNotFound += "Transaction code ";
		if (columnIndex.get(AppConstant.TCOUNT) == null)
			columnNotFound += "Tx Count ";
		if (columnIndex.get(AppConstant.GTIME) == null)
			columnNotFound += "GUI Time ";
		if (!columnNotFound.equals("")) {
			return propFileUtil.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, "someColumnNotFound")
					.replace("columnNotFound", columnNotFound);
		}
		return columnNotFound;
	}
}
