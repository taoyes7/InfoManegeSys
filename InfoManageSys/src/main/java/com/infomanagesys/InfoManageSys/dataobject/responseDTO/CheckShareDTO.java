package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import java.io.Serializable;

public class CheckShareDTO extends ResponseDTO implements Serializable {
    private FileResponseDTO file = new FileResponseDTO();
    private String passWord;
    private String code;

    public FileResponseDTO getFile() {
        return file;
    }

    public void setFile(FileResponseDTO file) {
        this.file = file;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
