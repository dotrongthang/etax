package com.woori.etax.api.upload;

import com.woori.etax.dto.FileUploadAndDownload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class FileUploadController {

	@Value("${prefixFileName}")
	private String PRE_FILE_NAME;

	private String pattern = "YYYYMM";

	@Value("${pathUpload}")
	private String PATH_UPLOAD;

	@Value("${resultFileName}")
	private String RES_FILE_NM;

	@Value("${datFileName}")
	private String DAT_FILE_NM;

	@PostMapping("/uploadFile")
//	public ResponseEntity<List<FileUploadResponse>> uploadFile(
	public String uploadFile(
			@RequestParam("file") MultipartFile[] multipartFiles , Model model) throws IOException {

		//get time upload
		Date now = new Date();
		DateFormat df = new SimpleDateFormat(pattern);
		String month = df.format(now);

		List<FileUploadResponse> fileUploadResponseList = new ArrayList<>();
		List<String> fileNames = new ArrayList<>();
		//upload file
		int countUpload = 0;
		if (multipartFiles != null && multipartFiles.length > 0){
			int i = 1;
			for (MultipartFile multipartFile : multipartFiles){
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				long size = multipartFile.getSize();

				System.out.println("Upload page: Start read file: " + fileName);

				int uploadFile = FileUploadUtil.saveFile(fileName, multipartFile, PATH_UPLOAD);
				if (uploadFile != 0){
					FileUploadResponse response = new FileUploadResponse();
					response.setFileName(fileName);
					response.setSize(size);
					response.setCount(i);
					i++;
					fileUploadResponseList.add(response);
					fileNames.add(fileName);
				}
			}
			countUpload = i - 1;
		}

		//get all file in directory
//		Path uploadDirectory = Paths.get(PRE_FILE_NAME +  "/" + month + "/");
		Path uploadDirectory = Paths.get(PATH_UPLOAD);
//		Path uploadDirectory = Paths.get("C:\\update\\etax");

		Files.list(uploadDirectory).forEach(file -> {
			if (!fileNames.contains(file.getFileName().toString()) && !file.getFileName().toString().equalsIgnoreCase("list.dat")) {
				FileUploadResponse response = new FileUploadResponse();
				response.setCount(fileUploadResponseList.size() + 1);
				response.setFileName(file.getFileName().toString());
				try {
					response.setSize( Files.size(Paths.get(PATH_UPLOAD + file.getFileName().toString())));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				fileUploadResponseList.add(response);

			}
		});

		//write dat file
		File out = new File(PATH_UPLOAD + DAT_FILE_NM);
		try {
				try (PrintWriter pw = new PrintWriter(out)) {
					fileUploadResponseList.stream().forEach(r->{
//						pw.println(PRE_FILE_NAME  + month + "/" + r.getFileName());
						pw.println(PRE_FILE_NAME  + "/" + r.getFileName());
					});
				}
			// Write data to 'out'
		} catch (Exception e){
			e.printStackTrace();
		}

		FileUploadResponse responseDown = new FileUploadResponse();
//		responseDown.setFileName(RES_FILE_NM);
//		responseDown.setSize(200);
//		responseDown.setCount(1);

		model.addAttribute("countUpload", fileUploadResponseList.size());
		model.addAttribute("result", fileUploadResponseList);
		model.addAttribute("countDownload", 0);

		model.addAttribute("resultDownload", responseDown);

		return "uploadFile";
	}

}
