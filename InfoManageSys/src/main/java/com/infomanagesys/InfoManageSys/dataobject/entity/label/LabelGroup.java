package com.infomanagesys.InfoManageSys.dataobject.entity.label;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="label_group")
public class LabelGroup implements Serializable {
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
    private int priorityLevel;
    @Column(columnDefinition="TEXT")
    private String labels;
    @Column(columnDefinition="TEXT")
    private String fileType;
    @Column
    private String name;

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

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LabelGroup{" +
                "id=" + id +
                ", updateDate=" + updateDate +
                ", createDate=" + createDate +
                ", pid='" + pid + '\'' +
                ", priorityLevel=" + priorityLevel +
                ", labels='" + labels + '\'' +
                ", fileType='" + fileType + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    // ---------- create builder ----------//
    public static LabelGroupBuilder dirClassifyRulesBuilderBuilder(){
        return new LabelGroupBuilder();
    }
    public static class LabelGroupBuilder{
        private Long id;
        private Timestamp updateDate;
        private Timestamp createDate;
        private String pid;
        private int priorityLevel;
        private String labels;
        private String fileType;
        private String name;

        public LabelGroupBuilder withId(Long id){
            this.id=id;
            return this;
        }
        public LabelGroupBuilder withUpdateDate(Timestamp updateDate){
            this.updateDate=updateDate;
            return this;
        }
        public LabelGroupBuilder withCreateDate(Timestamp createDate){
            this.createDate=createDate;
            return this;
        }
        public LabelGroupBuilder withPid(String pid){
            this.pid=pid;
            return this;
        }
        public LabelGroupBuilder withPriorityLevel(int priorityLevel){
            this.priorityLevel=priorityLevel;
            return this;
        }
        public LabelGroupBuilder withLabels(String labels){
            this.labels=labels;
            return this;
        }
        public LabelGroupBuilder withFileType(String fileType){
            this.fileType=fileType;
            return this;
        }
        public LabelGroupBuilder withName(String name){
            this.name = name;
            return this;
        }
        public LabelGroup build(){
            LabelGroup labelGroup = new LabelGroup();
            labelGroup.setId(this.id);
            labelGroup.setPid(this.pid);
            labelGroup.setPriorityLevel(this.priorityLevel);
            labelGroup.setLabels(this.labels);
            labelGroup.setFileType(this.fileType);
            labelGroup.setName(this.name);
            if(this.id==null){
                Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                labelGroup.setCreateDate(currentDate);
            }
            return labelGroup;
        }
    }
}
