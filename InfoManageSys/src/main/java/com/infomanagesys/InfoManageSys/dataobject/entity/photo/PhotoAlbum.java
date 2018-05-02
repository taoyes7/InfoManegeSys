package com.infomanagesys.InfoManageSys.dataobject.entity.photo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "photo_album")
public class PhotoAlbum implements Serializable {

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
    private String description;
    @Column
    private String name;
    @Column(columnDefinition="TEXT")
    private String labels;
    @Column
    private String parentId;
    @Column
    private String user;
    @Column
    private int level;
    @Column
    private String img;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "PhotoAlbum{" +
                "id=" + id +
                ", pid='" + pid + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", labels='" + labels + '\'' +
                ", parentId='" + parentId + '\'' +
                ", user='" + user + '\'' +
                ", level=" + level +
                ", img='" + img + '\'' +
                '}';
    }

    public static PhotoAlbumBuilder photoAlbumBuilder() {
        return new PhotoAlbumBuilder();
    }
    public static class PhotoAlbumBuilder {
        private Long id;
        private String pid;
        private Timestamp createDate;
        private Timestamp updateDate;
        private String description;
        private String status;
        private String user;
        private String name;
        private String labels;
        private String parentId;
        private int level;
        private String img;
        public PhotoAlbumBuilder withId(Long id){
            this.id=id;
            return this;
        }
        public PhotoAlbumBuilder withPid(String pid){
            this.pid=pid;
            return this;
        }
        public PhotoAlbumBuilder withCreateDate(Timestamp createDate) {
            this.createDate = createDate;
            return this;
        }
        public PhotoAlbumBuilder withUpdateDate(Timestamp updateDate) {
            this.updateDate = updateDate;
            return this;
        }
        public PhotoAlbumBuilder withDescription(String description){
            this.description=description;
            return this;
        }
        public PhotoAlbumBuilder withStatus(String status){
            this.status=status;
            return this;
        }
        public PhotoAlbumBuilder withUser(String user){
            this.user=user;
            return this;
        }
        public PhotoAlbumBuilder withName(String name){
            this.name=name;
            return  this;
        }
        public PhotoAlbumBuilder withLabels(String labels){
            this.labels=labels;
            return this;
        }
        public PhotoAlbumBuilder withParentId(String parentId){
            this.parentId =parentId;
            return this;
        }
        public PhotoAlbumBuilder withLevel(int level){
            this.level=level;
            return this;
        }

        public PhotoAlbumBuilder withImg(String img){
            this.img=img;
            return this;
        }
        public PhotoAlbum build(){
            PhotoAlbum photoAlbum = new PhotoAlbum();
            photoAlbum.setId(this.id);
            photoAlbum.setPid(this.pid);
            photoAlbum.setName(this.name);
            photoAlbum.setLabels(this.labels);
            photoAlbum.setParentId(this.parentId);
            photoAlbum.setDescription(this.description);
            photoAlbum.setStatus(this.status);
            photoAlbum.setUser(this.user);
            photoAlbum.setLevel(this.level);
            photoAlbum.setImg(this.img);
            if(this.id==null){
                Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                photoAlbum.setCreateDate(currentDate);
            }
            return photoAlbum;
        }
    }
}
