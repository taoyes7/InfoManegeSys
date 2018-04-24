package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class ClassfiyedFileResponseDTO extends ResponseDTO implements Serializable {
    private String dirId;
    private ArrayList<RulesAndFileLIstResponseDTO> rulesAndFileLIstResponseDTOArrayList = new ArrayList<RulesAndFileLIstResponseDTO>();

    public String getDirId() {
        return dirId;
    }

    public void setDirId(String dirId) {
        this.dirId = dirId;
    }

    public ArrayList<RulesAndFileLIstResponseDTO> getRulesAndFileLIstResponseDTOArrayList() {
        return rulesAndFileLIstResponseDTOArrayList;
    }

    public void setRulesAndFileLIstResponseDTOArrayList(ArrayList<RulesAndFileLIstResponseDTO> rulesAndFileLIstResponseDTOArrayList) {
        this.rulesAndFileLIstResponseDTOArrayList = rulesAndFileLIstResponseDTOArrayList;
    }
}
