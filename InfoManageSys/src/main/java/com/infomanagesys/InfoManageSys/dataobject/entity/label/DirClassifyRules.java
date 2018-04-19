package com.infomanagesys.InfoManageSys.dataobject.entity.label;

import com.infomanagesys.InfoManageSys.util.Pid;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name="dir_classify_rules")
public class DirClassifyRules implements Serializable {
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
    @Column(columnDefinition="TEXT")
    private String  labelsGroup;

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

    public String getLabelsGroup() {
        return labelsGroup;
    }

    public void setLabelsGroup(String labelsGroup) {
        this.labelsGroup = labelsGroup;
    }

    @Override
    public String toString() {
        return "DirClassifyRules{" +
                "id=" + id +
                ", updateDate=" + updateDate +
                ", createDate=" + createDate +
                ", pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", labelsGroup='" + labelsGroup + '\'' +
                '}';
    }
    // ---------- create builder ----------//
    public static DirClassifyRulesBuilder dirClassifyRulesBuilderBuilder(){
        return new DirClassifyRulesBuilder();
    }
    public static class DirClassifyRulesBuilder{
        private Long id;
        private Timestamp updateDate;
        private Timestamp createDate;
        private String pid;
        private String name;
        private String  labelsGroup;

        public DirClassifyRulesBuilder withId(Long id){
            this.id=id;
            return this;
        }
        public DirClassifyRulesBuilder withUpdateDate(Timestamp updateDate){
            this.updateDate=updateDate;
            return this;
        }
        public DirClassifyRulesBuilder withCreateDate(Timestamp createDate){
            this.createDate=createDate;
            return this;
        }
        public DirClassifyRulesBuilder withPid(String pid){
            this.pid=pid;
            return this;
        }
        public DirClassifyRulesBuilder withName(String name){
            this.name=name;
            return this;
        }
        public DirClassifyRulesBuilder withLabelsGroup(String labelsGroup){
            this.labelsGroup=labelsGroup;
            return this;
        }
        public DirClassifyRules build(){
            DirClassifyRules dirClassifyRules =new DirClassifyRules();
            dirClassifyRules.setId(this.id);
            dirClassifyRules.setPid(this.pid);
            dirClassifyRules.setName(this.name);
            dirClassifyRules.setLabelsGroup(this.labelsGroup);
            if(this.id==null){
                Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                dirClassifyRules.setCreateDate(currentDate);
            }
            return dirClassifyRules;
        }
    }
}
