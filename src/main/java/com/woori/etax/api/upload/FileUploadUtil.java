package com.woori.etax.api.upload;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

	public static int saveFile(String fileName, MultipartFile multipartFile, String pathUpload) throws IOException {
		Path uploadDirectory = Paths.get(pathUpload);
		int result = 0;
		
//		String fileCode = RandomStringUtils.randomAlphanumeric(8);
		System.out.println("Upload page: pathUpload = " + pathUpload);

		
		try (InputStream inputStream = multipartFile.getInputStream()) {
//			Path filePath = uploadDirectory.resolve(fileCode + "-" + fileName);
			System.out.println("Upload page: Start upload file: " + fileName);
			Path filePath = uploadDirectory.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			result = 1;
			
		} catch (Exception ioe) {
			ioe.printStackTrace();
			throw new IOException("Error saving uploaded file: " + fileName, ioe);
		}
		
		return result;
	}
}
