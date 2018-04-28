package com.infomanagesys.InfoManageSys.dataobject.entity.photo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "photo")
public class Photo implements Serializable {

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
    private String type;
    @Column
    private String name;
    @Column(columnDefinition="TEXT")
    private String labels;
    @Column
    private String groupId;
    @Column
    private String user;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", pid='" + pid + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", labels='" + labels + '\'' +
                ", groupId='" + groupId + '\'' +
                ", user='" + user + '\'' +
                '}';
    }

    public static PhotoBuilder photoBuilder() {
        return new PhotoBuilder();
    }
    public static class PhotoBuilder {
        private Long id;
        private String pid;
        private String type;
        private Timestamp createDate;
        private Timestamp updateDate;
        private String description;
        private String status;
        private String user;
        private String name;
        private String labels;
        private String groupId;
        public PhotoBuilder withId(Long id){
            this.id=id;
            return this;
        }
        public PhotoBuilder withPid(String pid){
            this.pid=pid;
            return this;
        }
        public PhotoBuilder withType(String type) {
            this.type = type;
            return this;
        }
        public PhotoBuilder withCreateDate(Timestamp createDate) {
            this.createDate = createDate;
            return this;
        }
        public PhotoBuilder withUpdateDate(Timestamp updateDate) {
            this.updateDate = updateDate;
            return this;
        }
        public PhotoBuilder withDescription(String description){
            this.description=description;
            return this;
        }
        public PhotoBuilder withStatus(String status){
            this.status=status;
            return this;
        }
        public PhotoBuilder withUser(String user){
            this.user=user;
            return this;
        }
        public PhotoBuilder withName(String name){
            this.name=name;
            return  this;
        }
        public PhotoBuilder withLabels(String labels){
            this.labels=labels;
            return this;
        }
        public PhotoBuilder withGroupId(String groupId){
            this.groupId =groupId;
            return this;
        }

        public Photo build(){
            Photo photo = new Photo();
            photo.setId(this.id);
            photo.setPid(this.pid);
            photo.setType(this.type);
            photo.setName(this.name);
            photo.setLabels(this.labels);
            photo.setGroupId(this.groupId);
            photo.setDescription(this.description);
            photo.setStatus(this.status);
            photo.setUser(this.user);
            if(this.id==null){
                Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                photo.setCreateDate(currentDate);
            }
            return photo;
        }
    }
}
