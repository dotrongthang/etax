package com.woori.etax.api.download;

import com.woori.etax.api.util.ConvertFileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class FileDownloadController {

	@Value("${pathDownload}")
	private String PATH_DOWNLOAD;

	@Value("${resultFileName}")
	private String RES_FILE_NM;

	@Value("${resultFileNameCsv}")
	private String RES_FILE_NM_CSV;

	@Value("${delimiter}")
	private String DELIMITER;

	private String pattern = "YYYYMM";

	@GetMapping("/downloadFile")
	public ResponseEntity<?> downloadFile() throws IOException, ParseException {
		FileDownloadUtil downloadUtil = new FileDownloadUtil();
		
		Resource resource = null;

		//check folder download exists
		File storedPathD = new File(PATH_DOWNLOAD);
		if (!storedPathD.exists()){
			storedPathD.mkdirs();
		}

		//get time upload
		Date now = new Date();
		DateFormat df = new SimpleDateFormat(pattern);
		String month = df.format(now);

		//create xml file
//		File out = new File(PATH_DOWNLOAD + "result.xml");
		ConvertFileUtil util = new ConvertFileUtil();
//		File resFile = new File(PATH_DOWNLOAD + RES_FILE_NM + month + ".xlsx");
		util.convertToExcelFile(PATH_DOWNLOAD + RES_FILE_NM_CSV + month + ".csv", PATH_DOWNLOAD + RES_FILE_NM + month + ".xlsx", DELIMITER);


		
		try {
			resource = downloadUtil.getFileAsResource(PATH_DOWNLOAD, RES_FILE_NM);
		} catch (IOException e) {
			return ResponseEntity.internalServerError().build();
		}
		
		if (resource == null) {
			return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
		}
		
		String contentType = "application/octet-stream";
		String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
				.body(resource);
	}
}
