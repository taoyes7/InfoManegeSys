package com.infomanagesys.InfoManageSys.service.doc.itf;

import com.infomanagesys.InfoManageSys.dataobject.responseDTO.AddLabelRequestDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.AllLabelResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.FileResponseDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface IDocService {
    public FileResponseDTO uploadFile(String userId, MultipartFile file);
    public FileResponseDTO createDir(String userId, String dirName);
    public FileResponseDTO createRootDir(String userId);
    public ArrayList<FileResponseDTO> openNewDir(String userId, String dirId);
    public ArrayList<FileResponseDTO> openRootDir(String userId);
    public FileResponseDTO getCurrentDir(String userId);
    public ArrayList<FileResponseDTO> getChildFile(String parentId);
    public FileResponseDTO getParentFile(String childId);
    public AllLabelResponseDTO getAllLabels(String userId);
    public FileResponseDTO addLabelToDir(String userId,JSONObject file, JSONArray labels);
    public boolean checkLabel(String userId, String content);

}
