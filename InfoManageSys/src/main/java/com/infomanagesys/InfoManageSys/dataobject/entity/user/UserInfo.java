package com.infomanagesys.InfoManageSys.dataobject.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Data
@Entity
@Table(name = "user_info")
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String userId;
    @Column
    private String head_uerl;
    @Column
    private String moren_ablum;
    @Column(columnDefinition="TEXT")
    private String usual_files;
    @Column
    private String name;
    @Column
    private String job;
    @Column
    private String motto;
    @Column
    private String phone;
    @Column
    private String qq;
    @Column
    private String e_mail;
    @Column
    private String address;
    @Column
    private String jobDate;
    @Column
    private Timestamp updateDate;
    @Column
    private Timestamp createDate;

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

    public String getHead_uerl() {
        return head_uerl;
    }

    public void setHead_uerl(String head_uerl) {
        this.head_uerl = head_uerl;
    }

    public String getMoren_ablum() {
        return moren_ablum;
    }

    public void setMoren_ablum(String moren_ablum) {
        this.moren_ablum = moren_ablum;
    }

    public String getUsual_files() {
        return usual_files;
    }

    public void setUsual_files(String usual_files) {
        this.usual_files = usual_files;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJobDate() {
        return jobDate;
    }

    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
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

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", head_uerl='" + head_uerl + '\'' +
                ", moren_ablum='" + moren_ablum + '\'' +
                ", usual_files='" + usual_files + '\'' +
                ", name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", motto='" + motto + '\'' +
                ", phone='" + phone + '\'' +
                ", qq='" + qq + '\'' +
                ", e_mail='" + e_mail + '\'' +
                ", address='" + address + '\'' +
                ", jobDate=" + jobDate +
                ", updateDate=" + updateDate +
                ", createDate=" + createDate +
                '}';
    }
}
