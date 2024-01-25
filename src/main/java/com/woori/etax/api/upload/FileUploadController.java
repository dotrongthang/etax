package com.woori.etax.api.upload;

import com.woori.etax.dto.FileUploadAndDownload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class FileUploadController {

	private final String PRE_FILE_NAME = "/dat/W5970/dw/etax/";

	private String pattern = "YYYYMM";

	@PostMapping("/uploadFile")
//	public ResponseEntity<List<FileUploadResponse>> uploadFile(
	public String uploadFile(
			@RequestParam("file") MultipartFile[] multipartFiles , Model model) throws IOException {

		List<FileUploadResponse> fileUploadResponseList = new ArrayList<>();
		int countUpload = 0;
		if (multipartFiles != null && multipartFiles.length > 0){
			int i = 1;
			for (MultipartFile multipartFile : multipartFiles){
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				long size = multipartFile.getSize();

				String fileCode = FileUploadUtil.saveFile(fileName, multipartFile);
				FileUploadResponse response = new FileUploadResponse();
				response.setFileName(fileName);
				response.setSize(size);
				response.setCount(i);
				i++;
				fileUploadResponseList.add(response);
			}
			countUpload = i - 1;
		}

		//get time upload
		Date now = new Date();
		DateFormat df = new SimpleDateFormat(pattern);
		String month = df.format(now);

		//write dat file
		File out = new File("C:\\update\\list.dat");
		try {
				try (PrintWriter pw = new PrintWriter(out)) {
					fileUploadResponseList.stream().forEach(r->{
						pw.println(PRE_FILE_NAME + "/" + month + "/" + r.getFileName());
					});
				}

			// Write data to 'out'
		} finally {
			// Make sure to close the file when done
//			out.close();
		}

		FileUploadResponse responseDown = new FileUploadResponse();
		responseDown.setFileName("testDown.xlsx");
		responseDown.setSize(200);
		responseDown.setCount(1);

		model.addAttribute("countUpload", countUpload);
		model.addAttribute("result", fileUploadResponseList);
		model.addAttribute("countDownload", 1);
		model.addAttribute("resultDownload", responseDown);

		return "uploadFile";
	}

}
