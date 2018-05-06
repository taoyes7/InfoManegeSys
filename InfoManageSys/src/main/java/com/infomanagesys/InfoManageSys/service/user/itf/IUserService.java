package com.infomanagesys.InfoManageSys.service.user.itf;

import com.infomanagesys.InfoManageSys.dataobject.entity.user.User;
import com.infomanagesys.InfoManageSys.dataobject.entity.user.UserDiary;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LoginResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.user.*;
import org.json.JSONObject;

public interface IUserService {
    public ResponseDTO register(JSONObject userInfo);
    public LoginResponseDTO login(User user);
    public boolean userCheck(String sessionId);
    public String getUserId(String sessionId);
    public UserInfoDTO getUserInfo(String userId);
    public UserInfoDTO changeUserInfoImg(String userId,String photoId);
    public ResponseDTO changeUserInfo(String userId,JSONObject _userInfo);
    public UserLinkDTO CreateUserLink(String userId,JSONObject _userLink);
    public ResponseDTO DeleteUserLink(String userLinkId);
    public UserLinkDTOS GetUserLinkS(String userId);
    public UserDiaryDTO CreateUserDiary(String userId,String title,String content);
    public UserPlanDTO CreateUserPlan(String userId,String title,String content,int level);
    public UserDiaryDTOS GetUserDiaryS(String userId);
    public UserPlanDTOS GetUseruserPlanS(String userId);
    public UserDiaryDTO changeUaerDiary(String diaryId,String title,String content);
    public ResponseDTO EndUseruserPlan(String planId);

}
