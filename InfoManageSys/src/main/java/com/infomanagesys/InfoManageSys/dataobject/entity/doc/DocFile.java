package com.infomanagesys.InfoManageSys.dataobject.entity.doc;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "doc_file")
public class DocFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String pid;
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private String parent;
    @Column
    private String path;
    @Column
    private String pathLocal;
    @Column
    private Timestamp updateDate;
    @Column
    private Timestamp createDate;
    @Column
    private String postfix;
    @Column
    private String classify;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getPathLocal() {
        return pathLocal;
    }

    public void setPathLocal(String pathLocal) {
        this.pathLocal = pathLocal;
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

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
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

    @Override
    public String toString() {
        return "DocFile{" +
                "id=" + id +
                ", pid='" + pid + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", parent='" + parent + '\'' +
                ", path='" + path + '\'' +
                ", pathLocal='" + pathLocal + '\'' +
                ", updateDate=" + updateDate +
                ", createDate=" + createDate +
                ", postfix='" + postfix + '\'' +
                ", classify='" + classify + '\'' +
                ", status='" + status + '\'' +
                ", User='" + user + '\'' +
                '}';
    }

    // ---------- create builder ----------//
    public static DocFileBuilder docFileBuilder() {
        return new DocFileBuilder();
    }
    public static class DocFileBuilder {
        private Long id;
        private String pid;
        private String name;
        private String type;
        private String parent;
        private String path;
        private String pathLocal;
        private Timestamp updateDate;
        private Timestamp createDate;
        private String postfix;
        private String classify;
        private String status;
        private String user;
        public DocFileBuilder withId(Long id){
            this.id=id;
            return this;
        }
        public DocFileBuilder withPid(String pid) {
            this.pid = pid;
            return this;
        }
        public DocFileBuilder withName(String name) {
            this.name = name;
            return this;
        }
        public DocFileBuilder withType(String type) {
            this.type = type;
            return this;
        }
        public DocFileBuilder withParent(String parent) {
            this.parent = parent;
            return this;
        }
        public DocFileBuilder withPath(String path) {
            this.path = path;
            return this;
        }
        public DocFileBuilder withPathLocal(String pathLocal) {
            this.pathLocal = pathLocal;
            return this;
        }
        public DocFileBuilder withUpdateDate(Timestamp updateDate) {
            this.updateDate = updateDate;
            return this;
        }
        public DocFileBuilder withCreateDate(Timestamp createDate) {
            this.createDate = createDate;
            return this;
        }
        public DocFileBuilder withPostfix(String postfix){
            this.postfix=postfix;
            return this;
        }
        public DocFileBuilder withClassify(String classify){
            this.classify=classify;
            return this;
        }
        public DocFileBuilder withStatus(String status){
            this.status=status;
            return this;
        }
        public DocFileBuilder withUser(String user){
            this.user=user;
            return this;
        }

        public DocFile build(){

//            Timestamp currentDate = new Timestamp(System.currentTimeMillis());
            DocFile docFile = new DocFile();
            docFile.setId(this.id);
            docFile.setPid(this.pid);
            docFile.setName(this.name);
            docFile.setType(this.type);
            docFile.setParent(this.parent);
            docFile.setPath(this.path);
            docFile.setPathLocal(this.pathLocal);
//            docFile.setUpdateDate(currentDate);
//            docFile.setCreateDate(currentDate);
            docFile.setPostfix(this.postfix);
            docFile.setClassify(this.classify);
            docFile.setStatus(this.status);
            docFile.setUser(this.user);
            if(this.id==null){
                Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                docFile.setCreateDate(currentDate);
            }
            return docFile;
        }
    }
}
