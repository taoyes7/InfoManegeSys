package com.infomanagesys.InfoManageSys.controller;

import com.infomanagesys.InfoManageSys.dataobject.responseDTO.*;
import com.infomanagesys.InfoManageSys.exception.UserCheckException;
import com.infomanagesys.InfoManageSys.service.doc.impl.DocServiceImpl;
import com.infomanagesys.InfoManageSys.service.user.impl.UserServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/doc/")
public class DocController {
    @Autowired
    private DocServiceImpl docService;
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/upLoadSingleFile", method = RequestMethod.POST)
    public RuleAndFileResponseDTO upLoadSingleFile(@RequestParam("sessionId") String sessionId, HttpServletRequest req,
                                            MultipartHttpServletRequest multiReq) {
        if(userService.userCheck(sessionId)){
            MultipartFile file = multiReq.getFile("file1");
            return  docService.uploadFile(userService.getUserId(sessionId),file);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/createdir", method = RequestMethod.POST)
    public FileResponseDTO createDir(@RequestParam("sessionId") String sessionId, @RequestParam("name") String name) {
        if(userService.userCheck(sessionId)){
//            docService.createRootDir(userService.getUserId(sessionId));
                return docService.createDir(userService.getUserId(sessionId),name);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/create/rootdir", method = RequestMethod.POST)
    public FileResponseDTO createRootDir(@RequestParam("sessionId") String sessionId) {
        if(userService.userCheck(sessionId)){
            return docService.createRootDir(userService.getUserId(sessionId));
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/open/rootdir",method = RequestMethod.POST)
    public ClassfiyedFileResponseDTO openRootDir(@RequestParam("sessionId") String sessionId){
        if(userService.userCheck(sessionId)){
            return docService.openRootDir(userService.getUserId(sessionId));

        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/open/dir", method = RequestMethod.POST)
    public ClassfiyedFileResponseDTO openDir(@RequestParam("sessionId") String sessionId, @RequestParam("dirId") String dirId){
        if(userService.userCheck(sessionId)){
            return docService.openNewDir(userService.getUserId(sessionId),dirId);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/get/currentdir", method = RequestMethod.POST)
    public FileResponseDTO getCurrentDir(@RequestParam("sessionId") String sessionId){
        if(userService.userCheck(sessionId)){
            return docService.getCurrentDir(userService.getUserId(sessionId));
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }

    @RequestMapping(value="/back/parent", method = RequestMethod.POST)
    public ClassfiyedFileResponseDTO backToParent(@RequestParam("sessionId") String sessionId) {
        if (userService.userCheck(sessionId)) {
            String dirId = docService.getCurrentDir(userService.getUserId(sessionId)).getPid();
            dirId = docService.getParentFile(dirId).getPid();
            return docService.openNewDir(userService.getUserId(sessionId), dirId);
        } else {
            throw new UserCheckException("用户校验失败");
        }

    }
    @RequestMapping(value="/add/file/label",method = RequestMethod.POST)
    public ResponseDTO addLabelToFile(@RequestParam("file") String file,
                                   @RequestParam("label") String label,
                                    @RequestParam("sessionId") String sessionId
    ){
        if (userService.userCheck(sessionId)) {
            try{
                JSONObject _file = new JSONObject(file);
                JSONObject _label= new JSONObject(label);
                return docService.addLabelToFile(_file,_label);
            }catch (Exception e){
                e.printStackTrace();
                throw new UserCheckException("参数解析出错");
            }

        }else {
            throw new UserCheckException("用户校验失败");
        }

    }
    @RequestMapping(value="/delete/file/label",method = RequestMethod.POST)
    public ResponseDTO deleteLabelToFile(@RequestParam("file") String file,
                                      @RequestParam("labelId") String labelId,
                                      @RequestParam("sessionId") String sessionId
    ){
        if (userService.userCheck(sessionId)) {
            try{
                JSONObject _file = new JSONObject(file);
                return docService.deleteLabelToFile(_file,labelId);
            }catch (Exception e){
                e.printStackTrace();
                throw new UserCheckException("参数解析出错");
            }

        }else {
            throw new UserCheckException("用户校验失败");
        }

    }
    @RequestMapping(value="/add/label",method = RequestMethod.POST)
    public FileResponseDTO addLabel(@RequestParam("file") String file,
                                    @RequestParam("label") String label,
                                    @RequestParam("sessionId") String sessionId
    ){
        if (userService.userCheck(sessionId)) {
            try{
                JSONObject _file = new JSONObject(file);
                JSONObject _label= new JSONObject(label);
                JSONArray labels=new JSONArray(_label.getString("labels"));
                return docService.addLabelToDir(userService.getUserId(sessionId),_file,labels);
            }catch (Exception e){
                e.printStackTrace();
                throw new UserCheckException("参数解析出错");
            }

        }else {
            throw new UserCheckException("用户校验失败");
        }

    }
    @RequestMapping(value = "/get/alllabels", method = RequestMethod.POST)
    public AllLabelResponseDTO getALlLabel(@RequestParam("sessionId") String sessionId){
        if (userService.userCheck(sessionId)) {
            return docService.getAllLabels(userService.getUserId(sessionId));
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/get/alllabels/group", method = RequestMethod.POST)
    public LabelGroupDTOS getALlLabelByGroup(@RequestParam("sessionId") String sessionId){
        if (userService.userCheck(sessionId)) {
            return docService.getAllLabelsByGroup(userService.getUserId(sessionId));
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value = "/check/label", method = RequestMethod.POST)
    public LabelCheckResponseDTO checkLabel(@RequestParam("sessionId") String sessionId,@RequestParam("label") String label){
        if (userService.userCheck(sessionId)) {
            LabelCheckResponseDTO response = new LabelCheckResponseDTO();

                response.setData(docService.checkLabel(userService.getUserId(sessionId),label));
                return response;

        } else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/add/rules",method = RequestMethod.POST)
    public FileResponseDTO addRule(@RequestParam("dir") String dir,
                                    @RequestParam("label") String label,
                                    @RequestParam("ruleName") String ruleName,
                                    @RequestParam("sessionId") String sessionId,
                                   @RequestParam("fileType") String fileType

    ){
        if (userService.userCheck(sessionId)) {
            try{
                JSONObject _dir = new JSONObject(dir);
                JSONObject _label= new JSONObject(label);
                JSONArray labels=new JSONArray(_label.getString("labels"));
                JSONObject _fileType= new JSONObject(fileType);
                JSONArray fileTypes=new JSONArray(_fileType.getString("fileTypes"));
                return docService.addRuleToDir(userService.getUserId(sessionId),_dir,labels,ruleName,fileTypes);
            }catch (Exception e){
                e.printStackTrace();
                throw new UserCheckException("参数解析出错");
            }

        }else {
            throw new UserCheckException("用户校验失败");
        }

    }
    @RequestMapping(value="/get/classfiyrules",method = RequestMethod.POST)
    public ClassfiyRuleResponseDTO getClassfiyRule(@RequestParam("sessionId") String sessionId,@RequestParam("dirId") String dirId){
        if (userService.userCheck(sessionId)) {
        return docService.getClassfiyRule(dirId);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/delete/classfiyrules",method = RequestMethod.POST)
    public ResponseDTO deleteClassfiyRule(@RequestParam("sessionId") String sessionId,@RequestParam("ruleId") String ruleId, @RequestParam("classfiyId") String classfiyId ){
        if (userService.userCheck(sessionId)) {
            return docService.deleteClassfiyRule(ruleId,classfiyId);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/delete/label",method = RequestMethod.POST)
    public ResponseDTO deleteLabel(@RequestParam("sessionId") String sessionId,@RequestParam("labelId") String labelId, @RequestParam("labelGroupId") String labelGroupId ){
        if (userService.userCheck(sessionId)) {
            return docService.deleteLabel(labelId,labelGroupId);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/delete/filetype",method = RequestMethod.POST)
    public ResponseDTO deleteFileType(@RequestParam("sessionId") String sessionId,@RequestParam("fileType") String fileType, @RequestParam("labelGroupId") String labelGroupId ){
        if (userService.userCheck(sessionId)) {
            return docService.deleteFileType(fileType,labelGroupId);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/add/singlelabel",method = RequestMethod.POST)
    public ResponseDTO addSingleLabel(@RequestParam("sessionId") String sessionId,@RequestParam("label") String label, @RequestParam("labelGroupId") String labelGroupId ){
        if (userService.userCheck(sessionId)) {
            try{
                JSONObject _label= new JSONObject(label);
                return docService.addSingleLabel(_label,labelGroupId);
            }catch (Exception e){
                e.printStackTrace();
                throw new UserCheckException("参数解析出错");
            }
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/add/singlefiletype",method = RequestMethod.POST)
    public ResponseDTO addSingleFileType(@RequestParam("sessionId") String sessionId,@RequestParam("fileType") String fileType, @RequestParam("labelGroupId") String labelGroupId ){
        if (userService.userCheck(sessionId)) {
            try{
                return docService.addSingleFileType(fileType,labelGroupId);
            }catch (Exception e){
                e.printStackTrace();
                throw new UserCheckException("参数解析出错");
            }
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/exchange/level",method = RequestMethod.POST)
    public ResponseDTO exchangeLevel(@RequestParam("sessionId") String sessionId,@RequestParam("curPid") String curPid, @RequestParam("nextPid") String nextPid,@RequestParam("classfiyRuleId") String classfiyRuleId ){
        if (userService.userCheck(sessionId)) {
                return docService.exchangeLevel(curPid,nextPid,classfiyRuleId);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/get/classfiyed/file",method = RequestMethod.POST)
    public ClassfiyedFileResponseDTO getClassfiyedFile(@RequestParam("sessionId") String sessionId,@RequestParam("dirId") String dirId){
        if (userService.userCheck(sessionId)) {
            return docService.getClassfiyedFile(dirId);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
        @RequestMapping(value="/get/curdir/labelandtype",method = RequestMethod.POST)
    public LabelGroupResponseDTO getCurDirLabelAndType(@RequestParam("sessionId") String sessionId,@RequestParam("dirId") String dirId){
        if (userService.userCheck(sessionId)) {
            return docService.getCurDirLabelAndType(dirId);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/select/files",method = RequestMethod.POST)
    public RulesAndFileLIstResponseDTO selectFiles(@RequestParam("sessionId") String sessionId,@RequestParam("dirId") String dirId,@RequestParam("label") String label,@RequestParam("fileType") String fileType){
        if (userService.userCheck(sessionId)) {
            try {
                JSONObject labels = new JSONObject(label);
                JSONObject fileTypes = new JSONObject(fileType);
                JSONArray _labels = new JSONArray(labels.getString("labels"));
                JSONArray _fileTypes = new JSONArray(fileTypes.getString("fileTypes"));
                return docService.selectFiles(dirId,_labels,_fileTypes);
            }catch (Exception e){
                e.printStackTrace();
                throw new UserCheckException("参数解析出错");
            }

        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/create/label/type",method = RequestMethod.POST)
    public LabelTypeResponseDTO CreateLabelType(@RequestParam("sessionId") String sessionId,@RequestParam("name") String name,@RequestParam("description") String description){
        if (userService.userCheck(sessionId)) {

                return docService.CreateLabelType(userService.getUserId(sessionId),name,description);

        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/get/all/labeltypes",method = RequestMethod.POST)
    public LabelTypeDTOS GetAllLabelTypes(@RequestParam("sessionId") String sessionId){
        if (userService.userCheck(sessionId)) {
            return docService.GetAllLabelTypes(userService.getUserId(sessionId));

        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/change/labeltype",method = RequestMethod.POST)
    public ResponseDTO ChangeLabelTypes(@RequestParam("sessionId") String sessionId,@RequestParam("type") String type,@RequestParam("label") String label){
        if (userService.userCheck(sessionId)) {
            try {
                JSONObject _type = new JSONObject(type);
                JSONObject _label = new JSONObject(label);
                return docService.ChangeLabelTypes(_type,_label);
            }catch (Exception e){
                throw new UserCheckException("参数解析失败");
            }


        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/delete/label/label",method = RequestMethod.POST)
    public ResponseDTO DeleteLabelFromLabel(@RequestParam("sessionId") String sessionId,@RequestParam("label") String label){
        if (userService.userCheck(sessionId)) {
            try {
                JSONObject _label = new JSONObject(label);
                return docService.DeleteLabelFromLabel(userService.getUserId(sessionId),_label);
            }catch (Exception e){
                throw new UserCheckException("参数解析失败");
            }


        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/new/label",method = RequestMethod.POST)
    public LabelResponseDTO newLabel(@RequestParam("sessionId") String sessionId,@RequestParam("name") String name,@RequestParam("describe") String describe,@RequestParam("labelType") String labelType){
        if (userService.userCheck(sessionId)) {
            JSONObject _labelType;
            try {
                _labelType = new JSONObject(labelType);
            }catch (Exception e){
                throw new UserCheckException("参数解析失败");
            }
            return docService.newLabel(userService.getUserId(sessionId),name,describe,_labelType);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }
    @RequestMapping(value="/delete/labeltype",method = RequestMethod.POST)
        public LabelGroupDTO deleteLabelType(@RequestParam("sessionId") String sessionId,@RequestParam("labelTypeId") String labelTypeId){
        if (userService.userCheck(sessionId)) {

            return docService.deleteLabelType(userService.getUserId(sessionId),labelTypeId);
        }else {
            throw new UserCheckException("用户校验失败");
        }
    }

    @RequestMapping(value="/download/file", method = RequestMethod.GET)
    public void DownLoadFile(@RequestParam("sessionId") String sessionId,@RequestParam("fileId") String fileId,HttpServletResponse res){
        if (userService.userCheck(sessionId)) {
             docService.DownLoadFile(fileId,res);
        }else {
            throw new UserCheckException("用户校验失败");
        }

    }
    @RequestMapping(value="/share/download/file", method = RequestMethod.GET)
    public void DownLoadShareFile(@RequestParam("fileId") String fileId,HttpServletResponse res){

        docService.DownLoadFile(fileId,res);
    }
    @RequestMapping(value="/delete/file", method = RequestMethod.POST)
    public ResponseDTO DeleteFile(@RequestParam("sessionId") String sessionId,@RequestParam("fileId") String fileId){
        if (userService.userCheck(sessionId)) {
           return  docService.DeleteFile(fileId);
        }else {
            throw new UserCheckException("用户校验失败");
        }

    }
    @RequestMapping(value="/share/file", method = RequestMethod.POST)
    public ShareDTO ShareFile(@RequestParam("sessionId") String sessionId,@RequestParam("fileId") String fileId,@RequestParam("isPrivate") String isPrivate,@RequestParam("shareTypeCode") String shareTypeCode){
        if (userService.userCheck(sessionId)) {
            return  docService.ShareFile(userService.getUserId(sessionId),fileId,isPrivate,shareTypeCode);
        }else {
            throw new UserCheckException("用户校验失败");
        }

    }
    @RequestMapping(value="/check/share", method = RequestMethod.POST)
    public CheckShareDTO GetShareFile(@RequestParam("shareId") String shareId){
            return  docService.GetShareFile(shareId);
    }
    @RequestMapping(value="/get/share/file", method = RequestMethod.POST)
    public CheckShareDTO GetShareFileByPassword(@RequestParam("shareId") String shareId,@RequestParam("password") String password){
        return  docService.GetShareFileByPassword(shareId,password);
    }



}

