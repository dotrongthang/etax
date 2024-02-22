package com.woori.etax.api.download;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloadUtil {
	private Path foundFile;

//	private final String FILE_RESULT = "result";
	
	public Resource getFileAsResource(String pathDownload, String resultFileName) throws IOException {
		Path uploadDirectory = Paths.get(pathDownload);
		
		Files.list(uploadDirectory).forEach(file -> {
			if (file.getFileName().toString().contains(resultFileName)) {
				foundFile = file;
			}
		});
		
		if (foundFile != null) {
			return new UrlResource(foundFile.toUri());
		}

		return null;
	}
}
