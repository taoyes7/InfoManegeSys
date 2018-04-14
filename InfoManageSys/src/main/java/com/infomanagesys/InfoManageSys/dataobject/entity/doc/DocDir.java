package com.infomanagesys.InfoManageSys.dataobject.entity.doc;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "doc_dir")
public class DocDir implements Serializable {
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
    private String parent;
    @Column(columnDefinition="TEXT")
    private String path;
    @Column
    private String status;
    @Column
    private String user;
    @Column(columnDefinition="TEXT")
    private String label;
    @Column
    private int level;

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

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "DocDir{" +
                "id=" + id +
                ", updateDate=" + updateDate +
                ", createDate=" + createDate +
                ", pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", path='" + path + '\'' +
                ", status='" + status + '\'' +
                ", user='" + user + '\'' +
                ", label='" + label + '\'' +
                ", level=" + level +
                '}';
    }

    // ---------- create builder ----------//
    public static DocDirBuilder docDirBuilder() {
        return new DocDirBuilder();
    }
    public static class DocDirBuilder {
        private Long id;
        private String pid;
        private String name;
        private String parent;
        private String path;
        private Timestamp updateDate;
        private Timestamp createDate;
        private String status;
        private String user;
        private String label;
        private int level;
        public DocDirBuilder withId(Long id){
            this.id=id;
            return this;
        }
        public DocDirBuilder withPid(String pid) {
            this.pid = pid;
            return this;
        }
        public DocDirBuilder withName(String name) {
            this.name = name;
            return this;
        }
        public DocDirBuilder withtParent(String parent) {
            this.parent = parent;
            return this;
        }
        public DocDirBuilder withPath(String path) {
            this.path = path;
            return this;
        }
        public DocDirBuilder withUpdateDate(Timestamp updateDate) {
            this.updateDate = updateDate;
            return this;
        }
        public DocDirBuilder withCreateDate(Timestamp createDate) {
            this.createDate = createDate;
            return this;
        }
        public DocDirBuilder withStatus(String status){
            this.status=status;
            return this;
        }
        public DocDirBuilder withUser(String user){
            this.user=user;
            return this;
        }
        public DocDirBuilder withLabel(String label) {
            this.label = label;
            return this;
        }
        public DocDirBuilder withLevel(int level){
            this.level = level;
            return this;
        }

        public DocDir build(){
            DocDir docDir = new DocDir();
            docDir.setId(this.id);
            docDir.setPid(this.pid);
            docDir.setName(this.name);
            docDir.setParent(this.parent);
            docDir.setPath(this.path);
//            docDir.setUpdateDate(currentDate);
            docDir.setStatus(this.status);
            docDir.setUser(this.user);
            docDir.setLabel(this.label);
            docDir.setLevel(this.level);
            if(this.id==null){
                Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                docDir.setCreateDate(currentDate);
            }
            return docDir;
        }
    }
}

