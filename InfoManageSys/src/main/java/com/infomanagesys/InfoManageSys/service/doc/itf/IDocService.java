package com.infomanagesys.InfoManageSys.service.doc.itf;

import com.infomanagesys.InfoManageSys.dataobject.responseDTO.FileResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface IDocService {
    public FileResponseDTO uploadFile(String userId, MultipartFile file);
    public FileResponseDTO createDir(String userId, String dirName);
    public Boolean createRootDir(String userId);
    public ArrayList<FileResponseDTO> openNewDir(String userId, String dirId);
    public ArrayList<FileResponseDTO> openRootDir(String userId);
    public FileResponseDTO getCurrentDir(String userId);
    public ArrayList<FileResponseDTO> getChildFile(String parentId);
}
