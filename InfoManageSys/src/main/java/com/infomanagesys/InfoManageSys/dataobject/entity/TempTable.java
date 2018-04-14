package com.infomanagesys.InfoManageSys.dataobject.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "temp_table")
public class TempTable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String tempKey;
    @Column
    private String tempValue;
    @Column
    private String tempUser;
    @Column
    private Timestamp createDate;
    @Column
    private Timestamp updateDate;
    @Column
    private String tempType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTempKey() {
        return tempKey;
    }

    public void setTempKey(String tempKey) {
        this.tempKey = tempKey;
    }

    public String getTempValue() {
        return tempValue;
    }

    public void setTempValue(String tempValue) {
        this.tempValue = tempValue;
    }

    public String getTempUser() {
        return tempUser;
    }

    public void setTempUser(String tempUser) {
        this.tempUser = tempUser;
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

    public String getTempType() {
        return tempType;
    }

    public void setTempType(String tempType) {
        this.tempType = tempType;
    }

    @Override
    public String toString() {
        return "TempTable{" +
                "id=" + id +
                ", tempKey='" + tempKey + '\'' +
                ", tempValue='" + tempValue + '\'' +
                ", tempUser='" + tempUser + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", tempType='" + tempType + '\'' +
                '}';
    }

    // ---------- create builder ----------//
    public static TempTableBuilder tempTableBuilder() {
        return new TempTableBuilder();
    }
    public static class TempTableBuilder {
        private Long id;
        private String tempKey;
        private String tempValue;
        private String tempUser;
        private String tempType;
        private Timestamp createDate;
        private Timestamp updateDate;
        public TempTableBuilder withId(Long id){
            this.id=id;
            return this;
        }
        public TempTableBuilder withTempKey(String tempKey) {
            this.tempKey = tempKey;
            return this;
        }
        public TempTableBuilder withTempValue(String tempValue){
            this.tempValue=tempValue;
            return this;
        }
        public TempTableBuilder withTempUser(String tempUser){
            this.tempUser=tempUser;
            return this;
        }
        public TempTableBuilder withCreateDate(Timestamp createDate){
            this.createDate = createDate;
            return this;
        }
        public TempTableBuilder withUpdateDate(Timestamp updateDate){
            this.updateDate = updateDate;
            return this;
        }
        public TempTableBuilder withTempType(String tempType){
            this.tempType=tempType;
            return this;
        }

        public TempTable build(){

            TempTable tempTable = new TempTable();
            tempTable.setId(this.id);
            tempTable.setTempKey(this.tempKey);
            tempTable.setTempValue(this.tempValue);
            tempTable.setTempUser(this.tempUser);
            tempTable.setTempType(this.tempType);
            if(this.id==null){
                Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                tempTable.setCreateDate(currentDate);
            }

            return tempTable;
        }
    }
}
