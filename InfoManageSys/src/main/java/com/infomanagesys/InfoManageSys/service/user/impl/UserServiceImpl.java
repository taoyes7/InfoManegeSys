package com.infomanagesys.InfoManageSys.service.user.impl;

import com.infomanagesys.InfoManageSys.dao.repository.TempTableRepository;
import com.infomanagesys.InfoManageSys.dao.repository.user.UserRepository;
import com.infomanagesys.InfoManageSys.dataobject.entity.TempTable;
import com.infomanagesys.InfoManageSys.dataobject.entity.user.User;
import com.infomanagesys.InfoManageSys.dataobject.enums.TempTypeEnum;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LoginResponseDTO;
import com.infomanagesys.InfoManageSys.service.user.itf.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final TempTableRepository tempTableRepository;
    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final TempTableRepository tempTableRepository){
        this.userRepository=userRepository;
        this.tempTableRepository=tempTableRepository;
    }

    @Override
    public boolean register(User user){
        UUID uuid = UUID.randomUUID();

       userRepository.save(
               User.userBuilder()
                       .withAccountid(user.getAccountid())
                       .withPassworld(user.getPassworld())
                       .withPid(uuid.toString().replaceAll("-",""))
                       .withStatus("0")
                       .build());
        return true;
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
}
