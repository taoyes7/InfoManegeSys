package com.infomanagesys.InfoManageSys.dataobject.entity.photo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "photo_relation")
public class PhotoRelation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String pid;
    @Column
    private Timestamp createDate;
    @Column
    private Timestamp updateDate;
    @Column
    private String status;
    @Column
    private String user;
    @Column
    private String photoId;
    @Column
    private String ablumId;

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

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getAblumId() {
        return ablumId;
    }

    public void setAblumId(String ablumId) {
        this.ablumId = ablumId;
    }

    @Override
    public String toString() {
        return "PhotoRelation{" +
                "id=" + id +
                ", pid='" + pid + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", status='" + status + '\'' +
                ", user='" + user + '\'' +
                ", photoId='" + photoId + '\'' +
                ", ablumId='" + ablumId + '\'' +
                '}';
    }

    public static PhotoRelationBuilder photoRelationBuilder() {
        return new PhotoRelationBuilder();
    }
    public static class PhotoRelationBuilder {
        private Long id;
        private String pid;
        private Timestamp createDate;
        private Timestamp updateDate;
        private String description;
        private String status;
        private String user;
        private String photoId;
        private String ablumId;
        public PhotoRelationBuilder withId(Long id){
            this.id=id;
            return this;
        }
        public PhotoRelationBuilder withPid(String pid){
            this.pid=pid;
            return this;
        }
        public PhotoRelationBuilder withCreateDate(Timestamp createDate) {
            this.createDate = createDate;
            return this;
        }
        public PhotoRelationBuilder withUpdateDate(Timestamp updateDate) {
            this.updateDate = updateDate;
            return this;
        }
        public PhotoRelationBuilder withDescription(String description){
            this.description=description;
            return this;
        }
        public PhotoRelationBuilder withStatus(String status){
            this.status=status;
            return this;
        }
        public PhotoRelationBuilder withUser(String user){
            this.user=user;
            return this;
        }
        public PhotoRelationBuilder withPhotoId(String photoId){
            this.photoId=photoId;
            return this;
        }
        public PhotoRelationBuilder withAblumId(String ablumId){
            this.ablumId=ablumId;
            return this;
        }


        public PhotoRelation build(){
            PhotoRelation photoRelation = new PhotoRelation();
            photoRelation.setId(this.id);
            photoRelation.setPid(this.pid);
            photoRelation.setStatus(this.status);
            photoRelation.setUser(this.user);
            photoRelation.setPhotoId(this.photoId);
            photoRelation.setAblumId(this.ablumId);
            if(this.id==null){
                Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                photoRelation.setCreateDate(currentDate);
            }
            return photoRelation;
        }
    }
}
