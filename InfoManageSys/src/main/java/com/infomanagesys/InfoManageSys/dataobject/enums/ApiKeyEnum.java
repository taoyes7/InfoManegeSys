package com.infomanagesys.InfoManageSys.dataobject.enums;

public enum ApiKeyEnum {
    BAIDUI_NLP_ENUM("11003509","uVzOkw5KkPpmyzrmbkyGXSjN","hAkCrjUKjhWhwTNG4plqPWEsIIEcsWLp","百度自然语言处理apiKey");
    private String appid;
    private String apikey;
    private String secretKey;
    private String description;

    private ApiKeyEnum( String appid, String apikey, String secretKey,String description){
        this.appid =appid;
        this.apikey =apikey;
        this.secretKey =secretKey;
        this.description =description;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
