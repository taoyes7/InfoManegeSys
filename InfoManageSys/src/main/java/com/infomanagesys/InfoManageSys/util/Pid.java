package com.infomanagesys.InfoManageSys.util;

import java.util.UUID;

public class Pid {
    public static UUID getUUId(){
        return UUID.randomUUID();
    }
    public static String getPid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
