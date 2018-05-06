package com.infomanagesys.InfoManageSys.dataobject.responseDTO.user;

import com.infomanagesys.InfoManageSys.dataobject.entity.user.UserPlan;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.ResponseDTO;

import java.io.Serializable;
import java.util.ArrayList;

public class UserPlanDTOS extends ResponseDTO implements Serializable {
    private ArrayList<UserPlan> on_userPlanS = new ArrayList<UserPlan>();
    private ArrayList<UserPlan> end_userPlanS = new ArrayList<UserPlan>();

    public ArrayList<UserPlan> getOn_userPlanS() {
        return on_userPlanS;
    }

    public void setOn_userPlanS(ArrayList<UserPlan> on_userPlanS) {
        this.on_userPlanS = on_userPlanS;
    }

    public ArrayList<UserPlan> getEnd_userPlanS() {
        return end_userPlanS;
    }

    public void setEnd_userPlanS(ArrayList<UserPlan> end_userPlanS) {
        this.end_userPlanS = end_userPlanS;
    }
}
