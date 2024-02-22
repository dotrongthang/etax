package com.woori.etax.api.home;

import com.woori.etax.api.upload.FileUploadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

	private String pattern = "YYYYMM";

	@Value("${pathUpload}")
	private String PATH_UPLOAD;

	@Value("${pathDownload}")
	private String PATH_DOWNLOAD;

	@Value("${resultFileName}")
	private String RES_FILE_NM;

	@GetMapping(value =  {"/home", "/uploadFile"})
	public String home(HttpServletRequest request, Model model) throws IOException {

		try{
			//get time upload
			Date now = new Date();
			DateFormat df = new SimpleDateFormat(pattern);
			String month = df.format(now);

			System.out.println("Home page: Start loading");

			//get all file in directory

			//check folder upload exists
			File storedPath = new File(PATH_UPLOAD);
			if (!storedPath.exists()){
				storedPath.mkdirs();
			}

//			//check folder download exists
			File storedPathD = new File(PATH_DOWNLOAD);
			if (!storedPathD.exists()){
				storedPathD.mkdirs();
			}


//		Path uploadDirectory = Paths.get(PRE_FILE_NAME +  "/" + month + "/");
			//get list upload
			Path uploadDirectory = Paths.get(PATH_UPLOAD);

			List<FileUploadResponse> fileUploadResponseList = new ArrayList<>();
			Files.list(uploadDirectory).forEach(file -> {
				if (!file.getFileName().toString().equalsIgnoreCase("list.dat")) {
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

			//get list download
			Path downloadDirectory = Paths.get(PATH_DOWNLOAD);

			List<FileUploadResponse> fileDownloadResponseList = new ArrayList<>();
			Files.list(downloadDirectory).forEach(file -> {
				if (file.getFileName().toString().contains(".csv")) {
					FileUploadResponse response = new FileUploadResponse();
					response.setCount(fileDownloadResponseList.size() + 1);
					response.setFileName(file.getFileName().toString());
					try {
						response.setSize( Files.size(Paths.get(PATH_DOWNLOAD + file.getFileName().toString())));
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					fileDownloadResponseList.add(response);

				}
			});

//			FileUploadResponse responseDown = new FileUploadResponse();
//
//			responseDown.setFileName("testDown.xlsx");
//			responseDown.setSize(200);
//			responseDown.setCount(1);

			model.addAttribute("countUpload", fileUploadResponseList.size());
			model.addAttribute("result", fileUploadResponseList);
			model.addAttribute("countDownload", fileDownloadResponseList.size());
			model.addAttribute("resultDownload", fileDownloadResponseList);

		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return "home";
	}
}
