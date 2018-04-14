package com.infomanagesys.InfoManageSys.service.user.itf;

import com.infomanagesys.InfoManageSys.dataobject.entity.user.User;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LoginResponseDTO;

public interface IUserService {
    public boolean register(User user);
    public LoginResponseDTO login(User user);
    public boolean userCheck(String sessionId);
    public String getUserId(String sessionId);

}
