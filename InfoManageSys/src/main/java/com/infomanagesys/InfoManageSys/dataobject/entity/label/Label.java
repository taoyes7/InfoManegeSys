package com.infomanagesys.InfoManageSys.dataobject.entity.label;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "label")
public class Label implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String pid;
    @Column
    private String type;
    @Column
    private Timestamp createDate;
    @Column
    private Timestamp updateDate;
    @Column
    private String content;
    @Column
    private String description;
    @Column
    private String status;
    @Column
    private String user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", pid='" + pid + '\'' +
                ", type='" + type + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", content='" + content + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", user='" + user + '\'' +
                '}';
    }

    public static LabelBuilder labelBuilder() {
        return new LabelBuilder();
    }
    public static class LabelBuilder {
        private Long id;
        private String pid;
        private String type;
        private Timestamp createDate;
        private Timestamp updateDate;
        private String content;
        private String description;
        private String status;
        private String user;
        public LabelBuilder withId(Long id){
            this.id=id;
            return this;
        }
        public LabelBuilder withPid(String pid){
            this.pid=pid;
            return this;
        }
        public LabelBuilder withType(String type) {
            this.type = type;
            return this;
        }
        public LabelBuilder withCreateDate(Timestamp createDate) {
            this.createDate = createDate;
            return this;
        }
        public LabelBuilder withUpdateDate(Timestamp updateDate) {
            this.updateDate = updateDate;
            return this;
        }
        public LabelBuilder withContent(String content) {
            this.content = content;
            return this;
        }
        public LabelBuilder withDescription(String description){
            this.description=description;
            return this;
        }
        public LabelBuilder withStatus(String status){
            this.status=status;
            return this;
        }
        public LabelBuilder withUser(String user){
            this.user=user;
            return this;
        }

        public Label build(){
//            Timestamp currentDate = new Timestamp(System.currentTimeMillis());
            Label label = new Label();
            label.setId(this.id);
            label.setPid(this.pid);
            label.setType(this.type);
//            label.setCreateDate(currentDate);
//            label.setUpdateDate(this.updateDate);
            label.setContent(this.content);
            label.setDescription(this.description);
            label.setStatus(this.status);
            label.setUser(this.user);
            if(this.id==null){
                Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                label.setCreateDate(currentDate);
            }
            return label;
        }
    }
}
