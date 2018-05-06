package com.infomanagesys.InfoManageSys.dataobject.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_diary")
public class UserDiary implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String userId;
    @Column
    private String pid;
    @Column
    private Timestamp updateDate;
    @Column
    private Timestamp createDate;
    @Column
    private String status;
    @Column
    private String title;
    @Column(columnDefinition="TEXT")
    private String content;
    @Column(columnDefinition="TEXT")
    private String labels;
    @Transient
    private String updateDate_s;
    @Transient
    private String createDate_s;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    @Transient
    public String getUpdateDate_s() {
        if(this.updateDate!=null) {
            return this.updateDate.toString();
        }else {
            return this.updateDate_s;
        }
    }
    @Transient
    public void setUpdateDate_s(String updateDate_s) {
        if(this.updateDate!=null){
            this.updateDate_s = this.updateDate.toString();
        }

    }
    @Transient
    public String getCreateDate_s() {
        if(this.createDate!=null) {
            return this.createDate.toString();
        }else {
            return this.createDate_s;
        }
    }
    @Transient
    public void setCreateDate_s(String createDate_s) {
        if(this.createDate!=null) {
            this.createDate_s = this.createDate.toString();
        }
    }
}
