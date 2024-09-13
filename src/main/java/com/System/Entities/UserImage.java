package com.System.Entities;

import jakarta.persistence.Basic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

//@Entity
//@Table(name = "user_images")
public class UserImage {

    @Id
    @Column(nullable = false, unique = true)
    private String email;  // Email as the primary key to associate with User

    @Lob
    @Basic(fetch = FetchType.LAZY)  // Lazy loading to optimize performance
    private byte[] imageData;

    private String filename;  // To store the original filename

    private String fileType;  // To store the MIME type of the image

    // Constructors, getters, and setters

    public UserImage() {}

    public UserImage(String email, byte[] imageData, String filename, String fileType) {
        this.email = email;
        this.imageData = imageData;
        this.filename = filename;
        this.fileType = fileType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
