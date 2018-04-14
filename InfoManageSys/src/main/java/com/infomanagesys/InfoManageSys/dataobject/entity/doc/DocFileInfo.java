package com.infomanagesys.InfoManageSys.dataobject.entity.doc;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "doc_file_info")
public class DocFileInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String fileId;
    @Column
    private String description;
    @Column(columnDefinition="TEXT")
    private String label;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "DocFileInfo{" +
                "id=" + id +
                ", fileId='" + fileId + '\'' +
                ", description='" + description + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
    // ---------- create builder ----------//
    public static DocFileInfoBuilder docFileInfoBuilder() {
        return new DocFileInfoBuilder();
    }
    public static class DocFileInfoBuilder {
        private Long id;
        private String fileId;
        private String description;
        private String label;
        public DocFileInfoBuilder withId(Long id){
            this.id=id;
            return this;
        }
        public DocFileInfoBuilder withFileId(String fileId) {
            this.fileId = fileId;
            return this;
        }
        public DocFileInfoBuilder withtDescription(String description) {
            this.description = description;
            return this;
        }
        public DocFileInfoBuilder withLabel(String label) {
            this.label = label;
            return this;
        }

        public DocFileInfo build(){

            DocFileInfo docFileInfo = new DocFileInfo();
            docFileInfo.setId(this.id);
            docFileInfo.setFileId(this.fileId);
            docFileInfo.setDescription(this.description);
            docFileInfo.setLabel(this.label);
            return docFileInfo;
        }
    }
}
