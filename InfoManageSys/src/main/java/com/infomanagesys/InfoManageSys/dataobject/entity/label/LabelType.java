package com.infomanagesys.InfoManageSys.dataobject.entity.label;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="label_type")
public class LabelType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Timestamp updateDate;
    @Column
    private Timestamp createDate;
    @Column
    private String pid;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "LabelType{" +
                "id=" + id +
                ", updateDate=" + updateDate +
                ", createDate=" + createDate +
                ", pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", user='" + user + '\'' +
                '}';
    }

    // ---------- create builder ----------//
    public static LabelTypeBuilder labelTypeBuilder(){
        return new LabelTypeBuilder();
    }
    public static class LabelTypeBuilder{
        private Long id;
        private Timestamp updateDate;
        private Timestamp createDate;
        private String pid;
        private String description;
        private String name;
        private String user;

        public LabelTypeBuilder withId(Long id){
            this.id=id;
            return this;
        }
        public LabelTypeBuilder withUpdateDate(Timestamp updateDate){
            this.updateDate=updateDate;
            return this;
        }
        public LabelTypeBuilder withCreateDate(Timestamp createDate){
            this.createDate=createDate;
            return this;
        }
        public LabelTypeBuilder withPid(String pid){
            this.pid=pid;
            return this;
        }

        public LabelTypeBuilder withDescription(String description){
            this.description=description;
            return this;
        }
        public LabelTypeBuilder withName(String name){
            this.name = name;
            return this;
        }
        public LabelTypeBuilder withUser(String user){
            this.user = user;
            return this;
        }
        public LabelType build(){
            LabelType labelType = new LabelType();
            labelType.setId(this.id);
            labelType.setPid(this.pid);
            labelType.setDescription(this.description);
            labelType.setName(this.name);
            labelType.setUser(this.user);
            if(this.id==null){
                Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                labelType.setCreateDate(currentDate);
            }
            return labelType;
        }
    }
}
