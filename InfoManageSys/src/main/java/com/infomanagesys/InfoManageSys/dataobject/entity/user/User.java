package com.infomanagesys.InfoManageSys.dataobject.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String pid;
    @Column
    private Timestamp updateDate;
    @Column
    private Timestamp createDate;
    @Column
    private String passworld;
    @Column
    private String status;
    @Column
    private String accountid;

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

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", pid='" + pid + '\'' +
                ", updateDate=" + updateDate +
                ", createDate=" + createDate +
                ", passworld='" + passworld + '\'' +
                ", status='" + status + '\'' +
                ", accountid='" + accountid + '\'' +
                '}';
    }

    // ---------- create builder ----------//
    public static UserBuilder userBuilder() {
        return new UserBuilder();
    }
    public static class UserBuilder {
        private Long id;
        private String pid;
        private Timestamp updateDate;
        private Timestamp createDate;
        private String passworld;
        private String status;
        private String accountid;
        public UserBuilder withId(Long id){
            this.id=id;
            return this;
        }
        public UserBuilder withPid(String pid) {
            this.pid = pid;
            return this;
        }
        public UserBuilder withUpdateDate(Timestamp updateDate) {
            this.updateDate = updateDate;
            return this;
        }
        public UserBuilder withCreateDate(Timestamp createDate) {
            this.createDate = createDate;
            return this;
        }
        public UserBuilder withPassworld(String passworld){
            this.passworld=passworld;
            return this;
        }
        public UserBuilder withStatus(String status){
            this.status=status;
            return this;
        }
        public UserBuilder withAccountid(String accountid){
            this.accountid=accountid;
            return this;
        }

        public User build(){
//            Timestamp currentDate = new Timestamp(System.currentTimeMillis());
            User user = new User();
            user.setId(this.id);
            user.setPid(this.pid);
//            user.setUpdateDate(currentDate);
//            user.setCreateDate(this.createDate);
            user.setPassworld(this.passworld);
            user.setStatus(this.status);
            user.setAccountid(this.accountid);
            if(this.id==null){
                Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                user.setCreateDate(currentDate);
            }
            return user;
        }
    }
}
