package com.example.dms.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailDocumentModel {
    @SerializedName("document_id")
    @Expose
    private Integer documentId;
    @SerializedName("document_title")
    @Expose
    private String documentTitle;
    @SerializedName("document_description")
    @Expose
    private String documentDescription;
    @SerializedName("document_image")
    @Expose
    private String documentImage;
    @SerializedName("document_download")
    @Expose
    private String documentDownload;
    @SerializedName("document_share")
    @Expose
    private String documentShare;
    @SerializedName("document_type")
    @Expose
    private String documentType;
    @SerializedName("document_create_at")
    @Expose
    private String documentCreateAt;

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    public String getDocumentImage() {
        return documentImage;
    }

    public void setDocumentImage(String documentImage) {
        this.documentImage = documentImage;
    }

    public String getDocumentDownload() {
        return documentDownload;
    }

    public void setDocumentDownload(String documentDownload) {
        this.documentDownload = documentDownload;
    }

    public String getDocumentShare() {
        return documentShare;
    }

    public void setDocumentShare(String documentShare) {
        this.documentShare = documentShare;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentCreateAt() {
        return documentCreateAt;
    }

    public void setDocumentCreateAt(String documentCreateAt) {
        this.documentCreateAt = documentCreateAt;
    }
}
