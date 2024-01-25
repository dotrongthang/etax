package com.woori.etax.dto;

public class FileUploadAndDownload {

    String fileName;

    String filePath;

    public FileUploadAndDownload() {
    }

    public FileUploadAndDownload(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
