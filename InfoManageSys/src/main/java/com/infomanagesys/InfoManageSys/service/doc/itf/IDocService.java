package com.infomanagesys.InfoManageSys.service.doc.itf;

import com.infomanagesys.InfoManageSys.dataobject.responseDTO.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface IDocService {
    public RuleAndFileResponseDTO uploadFile(String userId, MultipartFile file);
    public FileResponseDTO createDir(String userId, String dirName);
    public FileResponseDTO createRootDir(String userId);
    public ClassfiyedFileResponseDTO openNewDir(String userId, String dirId);
    public ClassfiyedFileResponseDTO openRootDir(String userId);
    public FileResponseDTO getCurrentDir(String userId);
    public ArrayList<FileResponseDTO> getChildFile(String parentId);
    public FileResponseDTO getParentFile(String childId);
    public AllLabelResponseDTO getAllLabels(String userId);
    public FileResponseDTO addLabelToDir(String userId,JSONObject file, JSONArray labels);
    public boolean checkLabel(String userId, String content);
    public FileResponseDTO addRuleToDir(String userId,JSONObject dir,JSONArray labels,String ruleName,JSONArray fileTypes);
    public ClassfiyRuleResponseDTO getClassfiyRule( String dirId);
    public ResponseDTO deleteClassfiyRule(String ruleId,String classfiyId);
    public ResponseDTO deleteLabel(String labelId,String labelGroupId);
    public ResponseDTO deleteFileType(String fileType,String labelGroupId);
    public ResponseDTO addSingleLabel(JSONObject label,String labelGroupId);
    public ResponseDTO addSingleFileType(String fileType,String labelGroupId);
    public ResponseDTO exchangeLevel(String curPid,String nextPid,String classfiyRuleId);
    public ClassfiyedFileResponseDTO getClassfiyedFile(String dirId);
    public LabelGroupResponseDTO getLabelGroupByFile(String dirId, FileResponseDTO file);
}
