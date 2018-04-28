package com.infomanagesys.InfoManageSys.dataobject.entity.doc;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "file_share")
public class FileShare implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String pid;
    @Column
    private String type;
    @Column
    private Timestamp createDate;
    @Column(columnDefinition="TEXT")
    private String content;
    @Column
    private String passworld;
    @Column
    private String status;
    @Column
    private Timestamp updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPassworld() {
        return passworld;
    }

    public void setPassworld(String passworld) {
        this.passworld = passworld;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "FileShare{" +
                "id=" + id +
                ", pid='" + pid + '\'' +
                ", type=" + type +
                ", createDate=" + createDate +
                ", content='" + content + '\'' +
                ", passworld='" + passworld + '\'' +
                ", status='" + status + '\'' +
                ", updateDate=" + updateDate +
                '}';
    }

    public static FileShareBuilder fileShareBuilder() {
        return new FileShareBuilder();
    }
    public static class FileShareBuilder {
        private Long id;
        private String pid;
        private String type;
        private Timestamp createDate;
        private String content;
        private String passworld;
        private String status;
        private Timestamp updateDate;
        public FileShareBuilder withId(Long id){
            this.id=id;
            return this;
        }
        public FileShareBuilder withpid(String pid) {
            this.pid = pid;
            return this;
        }
        public FileShareBuilder withType(String type) {
            this.type = type;
            return this;
        }
        public FileShareBuilder withCreateDate(Timestamp createDate) {
            this.createDate = createDate;
            return this;
        }
        public FileShareBuilder withContent(String content) {
            this.content = content;
            return this;
        }
        public FileShareBuilder withPassworld(String passworld){
            this.passworld=passworld;
            return this;
        }
        public FileShareBuilder withStatus(String status){
            this.status=status;
            return this;
        }
        public FileShareBuilder withUpdateDate(Timestamp updateDate){
            this.updateDate = updateDate;
            return this;
        }

        public FileShare build(){
//            Timestamp currentDate = new Timestamp(System.currentTimeMillis());
            FileShare fileShare = new FileShare();
            fileShare.setId(this.id);
            fileShare.setPid(this.pid);
            fileShare.setType(this.type);
//            fileShare.setCreateDate(currentDate);
            fileShare.setContent(this.content);
            fileShare.setPassworld(this.passworld);
            fileShare.setStatus(this.status);
            if(this.id==null){
                Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                fileShare.setCreateDate(currentDate);
            }
            return fileShare;
        }
    }
}
