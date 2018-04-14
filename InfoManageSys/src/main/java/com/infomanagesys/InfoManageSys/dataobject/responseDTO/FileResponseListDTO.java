package com.infomanagesys.InfoManageSys.dataobject.responseDTO;

import java.util.ArrayList;

public class FileResponseListDTO extends ResponseDTO{
    private ArrayList<FileResponseDTO> fileResponseDTOArrayList = new ArrayList<FileResponseDTO>();

    public ArrayList<FileResponseDTO> getFileResponseDTOArrayList() {
        return fileResponseDTOArrayList;
    }

    public void setFileResponseDTOArrayList(ArrayList<FileResponseDTO> fileResponseDTOArrayList) {
        this.fileResponseDTOArrayList = fileResponseDTOArrayList;
    }
}
