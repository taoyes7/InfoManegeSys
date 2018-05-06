package com.infomanagesys.InfoManageSys.service.user.impl;

import com.infomanagesys.InfoManageSys.dao.repository.TempTableRepository;
import com.infomanagesys.InfoManageSys.dao.repository.photo.PhotoAlbumRepository;
import com.infomanagesys.InfoManageSys.dao.repository.photo.PhotoRepository;
import com.infomanagesys.InfoManageSys.dao.repository.user.*;
import com.infomanagesys.InfoManageSys.dataobject.entity.TempTable;
import com.infomanagesys.InfoManageSys.dataobject.entity.photo.Photo;
import com.infomanagesys.InfoManageSys.dataobject.entity.photo.PhotoAlbum;
import com.infomanagesys.InfoManageSys.dataobject.entity.user.*;
import com.infomanagesys.InfoManageSys.dataobject.enums.FileStatusEnum;
import com.infomanagesys.InfoManageSys.dataobject.enums.SeverPathEnum;
import com.infomanagesys.InfoManageSys.dataobject.enums.TempTypeEnum;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LoginResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.user.*;
import com.infomanagesys.InfoManageSys.exception.FileExistException;
import com.infomanagesys.InfoManageSys.exception.UserCheckException;
import com.infomanagesys.InfoManageSys.service.doc.impl.DocServiceImpl;
import com.infomanagesys.InfoManageSys.service.photo.impl.PhotoServiceImpl;
import com.infomanagesys.InfoManageSys.service.user.itf.IUserService;
import com.infomanagesys.InfoManageSys.util.Pid;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final TempTableRepository tempTableRepository;
    private final UserInfoRepository userInfoRepository;
    private final DocServiceImpl docService;
    private final PhotoServiceImpl photoService;
    private final PhotoAlbumRepository photoAlbumRepository;
    private final PhotoRepository photoRepository;
    private final UserLinkRepository userLinkRepository;
    private final UserDiaryRepository userDiaryRepository;
    private final UserPlanRepository userPlanRepository;
    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final TempTableRepository tempTableRepository,
                           final UserInfoRepository userInfoRepository,
                           final DocServiceImpl docService,
                           final PhotoServiceImpl photoService,
                           final PhotoAlbumRepository photoAlbumRepository,
                           final PhotoRepository photoRepository,
                           final UserLinkRepository userLinkRepository,
                           final UserDiaryRepository userDiaryRepository,
                           final UserPlanRepository userPlanRepository){
        this.userRepository=userRepository;
        this.tempTableRepository=tempTableRepository;
        this.userInfoRepository=userInfoRepository;
        this.docService = docService;
        this.photoService =photoService;
        this.photoAlbumRepository = photoAlbumRepository;
        this.photoRepository = photoRepository;
        this.userLinkRepository = userLinkRepository;
        this.userDiaryRepository =userDiaryRepository;
        this.userPlanRepository = userPlanRepository;
    }

    @Override
    public ResponseDTO register(JSONObject _userInfo){
        try {
            User user = userRepository.findFirstByAccountid(_userInfo.getString("phone"));
            if(user!=null){
                throw new FileExistException("该手机号已被注册，请直接登录");
            }else {
               user= userRepository.save(
                        User.userBuilder()
                                .withAccountid(_userInfo.getString("phone"))
                                .withPassworld(_userInfo.getString("password"))
                                .withPid(Pid.getPid())
                                .withStatus("0")
                                .build());
            }
            UserInfo userInfo = new UserInfo();
            userInfo.setCreateDate(new Timestamp(System.currentTimeMillis()));
            userInfo.setUserId(user.getPid());
            userInfo.setHead_uerl("http://localhost:8080/icon/user_head.jpg");
            userInfo.setName(_userInfo.getString("name"));
            userInfo.setJob(_userInfo.getString("job"));
            userInfo.setJobDate(_userInfo.getString("job_date"));
            userInfo.setMotto(_userInfo.getString("motto"));
            userInfo.setPhone(_userInfo.getString("phone"));
            userInfo.setQq(_userInfo.getString("qq"));
            userInfo.setE_mail(_userInfo.getString("e_mail"));
            userInfo.setAddress(_userInfo.getString("address"));
            userInfoRepository.save(userInfo);
            File UserRootFile = new File(SeverPathEnum.FILE_PATH.getPath()+"//"+user.getPid());
            if(UserRootFile.mkdir()){
                System.out.println("用户根目录创建成功");
                File file = new File(SeverPathEnum.FILE_PATH.getPath()+"//"+user.getPid()+"//file");
                File photo = new File(SeverPathEnum.FILE_PATH.getPath()+"//"+user.getPid()+"//photo");
                File minPhoto = new File(SeverPathEnum.FILE_PATH.getPath()+"//"+user.getPid()+"//minPhoto");
                File tempFile = new File(SeverPathEnum.FILE_PATH.getPath()+"//"+user.getPid()+"//tempFile");
                if(file.mkdir()){
                    System.out.println("用户文件目录创建成功");
                    if(photo.mkdir()){
                        System.out.println("用户图片文件目录创建成功");
                        if(minPhoto.mkdir()){
                            System.out.println("用户缩略图图片文件目录创建成功");
                            if(tempFile.mkdir()){
                                System.out.println("用户临时文件目录创建成功");
                            }
                        }
                    }
                }
                docService.createRootDir(user.getPid());
                docService.CreateLabelType(user.getPid(),"未分组标签","未分组的标签");
                docService.CreateLabelType(user.getPid(),"废弃标签","废弃的标签");
//                docService.
//                photoService.CreateAlbum(user.getPid(),"未分类","未分类",new JSONArray());
        photoAlbumRepository.save(
                        PhotoAlbum.photoAlbumBuilder()
                                .withLabels(new JSONArray().toString())
                                .withName("未分类")
                                .withDescription("未分类")
                                .withParentId("WEI_FEN_LEI")
                                .withUser(user.getPid())
                                .withImg(SeverPathEnum.FILE_SEVER_PATH.getPath()+"/icon/DEFAULT_ABLUM.jpg")
                                .withStatus(FileStatusEnum.STATUS_AVAILABLE.getState())
                                .withPid(Pid.getPid())
                                .withLevel(-1)
                                .build()
                );
                photoAlbumRepository.save(
                        PhotoAlbum.photoAlbumBuilder()
                                .withLabels(new JSONArray().toString())
                                .withName("root")
                                .withDescription("相册根目录")
                                .withParentId("root")
                                .withUser(user.getPid())
                                .withImg(SeverPathEnum.FILE_SEVER_PATH.getPath()+"/icon/DEFAULT_ABLUM.jpg")
                                .withStatus(FileStatusEnum.STATUS_AVAILABLE.getState())
                                .withPid(Pid.getPid())
                                .withLevel(-1)
                                .build()
                );

            }else {
                throw new UserCheckException("创建用户根目录失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new UserCheckException("参数解析失败");
        }
        return new ResponseDTO();
    }
    @Override
    public LoginResponseDTO login(User user){
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        User _user = userRepository.findFirstByAccountidAndPassworld(user.getAccountid(),user.getPassworld());
        if(_user != null){
            UUID uuid = UUID.randomUUID();
            String sessionId = uuid.toString().replaceAll("-","");
            TempTable tempTable = TempTable.tempTableBuilder()
                    .withTempKey(sessionId)
                    .withTempValue(_user.getPid())
                    .withTempUser(_user.getPid())
                    .withTempType(TempTypeEnum.TEMP_TYPE_SESSIONID.getKey())
                    .build();
            tempTableRepository.save(tempTable);
            loginResponseDTO.setState(true);
            loginResponseDTO.setSessionId(uuid.toString().replaceAll("-",""));
        }else {
            loginResponseDTO.setState(false);
        }
        return loginResponseDTO;
    }
    @Override
    public boolean userCheck(String sessionId){
        if(tempTableRepository.findFirstByTempKey(sessionId)!=null){
            return true;
        }else {
            return false;
        }
    }
    @Override
    public String getUserId(String sessionId){
        return tempTableRepository.findFirstByTempKey(sessionId).getTempValue();
    }
    @Override
    public UserInfoDTO getUserInfo(String userId){
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserInfo(userInfoRepository.findFirstByUserId(userId));
        return userInfoDTO;
    }
    @Override
    public UserInfoDTO changeUserInfoImg(String userId,String photoId){
        UserInfo userInfo = userInfoRepository.findFirstByUserId(userId);
        Photo photo = photoRepository.findFirstByPidAndStatus(photoId,FileStatusEnum.STATUS_AVAILABLE.getState());
        userInfo.setHead_uerl(SeverPathEnum.FILE_SEVER_PATH.getPath()+"/userData/"+userId+"/photo/"+photoId+"."+photo.getType());
        userInfoRepository.save(userInfo);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserInfo(userInfo);
        return userInfoDTO;
    }
    @Override
    public ResponseDTO changeUserInfo(String userId,JSONObject _userInfo){
        UserInfo userInfo = userInfoRepository.findFirstByUserId(userId);
        try {
            userInfo.setName(_userInfo.getString("name"));
            userInfo.setJob(_userInfo.getString("job"));
            userInfo.setJobDate(_userInfo.getString("job_date"));
            userInfo.setMotto(_userInfo.getString("motto"));
            userInfo.setQq(_userInfo.getString("qq"));
            userInfo.setE_mail(_userInfo.getString("e_mail"));
            userInfo.setAddress(_userInfo.getString("address"));
            userInfoRepository.save(userInfo);
            return new ResponseDTO();
        }catch (Exception e){
            e.printStackTrace();
            throw new UserCheckException("参数解析失败");
        }

    }
    @Override
    public UserLinkDTO CreateUserLink(String userId, JSONObject _userLink){

        UserLink userLink = new UserLink();
        try {
            userLink.setAddress(_userLink.getString("address"));
            userLink.setCreateDate(new Timestamp(System.currentTimeMillis()));
            userLink.setDescription(_userLink.getString("description"));
            userLink.setE_mail(_userLink.getString("e_mail"));
            userLink.setJob(_userLink.getString("job"));
            userLink.setName(_userLink.getString("name"));
            userLink.setPhone(_userLink.getString("phone"));
            userLink.setPid(Pid.getPid());
            userLink.setUserId(userId);
            userLink.setStatus(FileStatusEnum.STATUS_AVAILABLE.getState());
        }catch (Exception e){
            e.printStackTrace();
            throw new UserCheckException("参数解析出错");
        }
        userLink= userLinkRepository.save(userLink);
        UserLinkDTO userLinkDTO = new UserLinkDTO();
        userLinkDTO.setUserLink(userLink);
        return userLinkDTO;
    }
    @Override
    public ResponseDTO DeleteUserLink(String userLinkId){
        UserLink userLink = userLinkRepository.findFirstByPidAndStatus(userLinkId,FileStatusEnum.STATUS_AVAILABLE.getState());
        userLink.setStatus(FileStatusEnum.STATUS_DELETED.getState());
        userLinkRepository.save(userLink);
        return new ResponseDTO();
    }
    @Override
    public UserLinkDTOS GetUserLinkS(String userId){
        ArrayList<UserLink> userLinks = userLinkRepository.findByUserIdAndStatus(userId,FileStatusEnum.STATUS_AVAILABLE.getState());
        UserLinkDTOS userLinkDTOS=new UserLinkDTOS();
        userLinkDTOS.setUserLinkS(userLinks);
        return  userLinkDTOS;
    }
    @Override
    public UserDiaryDTO CreateUserDiary(String userId, String title, String content){
        UserDiary userDiary = new UserDiary();
        userDiary.setCreateDate(new Timestamp(System.currentTimeMillis()));
        userDiary.setPid(Pid.getPid());
        userDiary.setUserId(userId);
        userDiary.setStatus(FileStatusEnum.STATUS_AVAILABLE.getState());
        userDiary.setTitle(title);
        userDiary.setContent(content);
        userDiary = userDiaryRepository.save(userDiary);
        UserDiaryDTO userDiaryDTO = new UserDiaryDTO();
        userDiaryDTO.setUserDiary(userDiary);
        return userDiaryDTO;
    }
    @Override
    public UserPlanDTO CreateUserPlan(String userId, String title, String content, int level){

        UserPlan userPlan = new UserPlan();
        userPlan.setCreateDate(new Timestamp(System.currentTimeMillis()));
        userPlan.setPid(Pid.getPid());
        userPlan.setUserId(userId);
        userPlan.setStatus(FileStatusEnum.STATUS_AVAILABLE.getState());
        userPlan.setTitle(title);
        userPlan.setContent(content);
        userPlan.setLevel(level);
        userPlan.setIsEnd("false");
        userPlan = userPlanRepository.save(userPlan);
        UserPlanDTO userPlanDTO =new UserPlanDTO();
        userPlanDTO.setUserPlan(userPlan);
        return userPlanDTO;
    }
    @Override
    public UserDiaryDTOS GetUserDiaryS(String userId){
        ArrayList<UserDiary> userDiaries = userDiaryRepository.findByUserIdAndStatus(userId,FileStatusEnum.STATUS_AVAILABLE.getState());
        int pages = userDiaries.size()/7;
        int items = userDiaries.size()%7;
        if(items>0){
            pages++;
        }
        UserDiaryDTOS  userDiaryDTOS = new UserDiaryDTOS();
        userDiaryDTOS.setPages(pages);
        userDiaryDTOS.setItems(items);
        userDiaryDTOS.setUserDiarieS(userDiaries);
        return userDiaryDTOS;
    }
    @Override
    public UserPlanDTOS GetUseruserPlanS(String userId){
        ArrayList<UserPlan> on_userPlans = userPlanRepository.findByUserIdAndStatusAndIsEndOrderByLevelAsc(userId,FileStatusEnum.STATUS_AVAILABLE.getState(),"false");
        ArrayList<UserPlan> end_userPlans=userPlanRepository.findByUserIdAndStatusAndIsEndOrderByCreateDateDesc(userId,FileStatusEnum.STATUS_AVAILABLE.getState(),"true");
        UserPlanDTOS userPlanDTOS = new UserPlanDTOS();

        userPlanDTOS.setOn_userPlanS(on_userPlans);
        userPlanDTOS.setEnd_userPlanS(end_userPlans);
        return userPlanDTOS;
    }
    @Override
    public  UserDiaryDTO changeUaerDiary(String diaryId,String title,String content){
        UserDiary userDiary = userDiaryRepository.findFirstByPidAndStatus(diaryId,FileStatusEnum.STATUS_AVAILABLE.getState());
        userDiary.setContent(content);
        userDiary.setTitle(title);
        userDiary=userDiaryRepository.save(userDiary);
        UserDiaryDTO userDiaryDTO = new UserDiaryDTO();
        userDiaryDTO.setUserDiary(userDiary);
        return userDiaryDTO;
    }
    @Override
    public ResponseDTO EndUseruserPlan(String planId){
        UserPlan userPlan = userPlanRepository.findFirstByPidAndStatus(planId,FileStatusEnum.STATUS_AVAILABLE.getState());
        userPlan.setIsEnd("true");
        userPlanRepository.save(userPlan);
        return new ResponseDTO();
    }

}
