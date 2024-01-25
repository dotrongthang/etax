package com.woori.etax.api.download;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownloadUtil {
	private Path foundFile;

	private final String FILE_RESULT = "testDown.xlsx";
	
	public Resource getFileAsResource() throws IOException {
		Path uploadDirectory = Paths.get("C:\\update\\etax");
		
		Files.list(uploadDirectory).forEach(file -> {
			if (file.getFileName().toString().startsWith(FILE_RESULT)) {
				foundFile = file;
				return;
			}
		});
		
		if (foundFile != null) {
			return new UrlResource(foundFile.toUri());
		}

		return null;
	}
}
