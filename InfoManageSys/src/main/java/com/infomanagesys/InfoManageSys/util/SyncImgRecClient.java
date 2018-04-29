package com.infomanagesys.InfoManageSys.util;

import com.alibaba.cloudapi.sdk.core.BaseApiClient;
import com.alibaba.cloudapi.sdk.core.BaseApiClientBuilder;
import com.alibaba.cloudapi.sdk.core.annotation.NotThreadSafe;
import com.alibaba.cloudapi.sdk.core.annotation.ThreadSafe;
import com.alibaba.cloudapi.sdk.core.enums.Method;
import com.alibaba.cloudapi.sdk.core.enums.Scheme;
import com.alibaba.cloudapi.sdk.core.model.ApiRequest;
import com.alibaba.cloudapi.sdk.core.model.ApiResponse;
import com.alibaba.cloudapi.sdk.core.model.BuilderParams;

@ThreadSafe
public class SyncImgRecClient extends BaseApiClient {

    public final static String GROUP_HOST = "txsbtxdb.market.alicloudapi.com";

    private SyncImgRecClient(BuilderParams builderParams) {
        super(builderParams);
    }

    @NotThreadSafe
    public static class Builder extends BaseApiClientBuilder<SyncImgRecClient.Builder, SyncImgRecClient> {

        @Override
        protected SyncImgRecClient build(BuilderParams params) {
            return new SyncImgRecClient(params);
        }
    }

    public static SyncImgRecClient.Builder newBuilder(){
        return new SyncImgRecClient.Builder();
    }

    public static SyncImgRecClient getInstance(){
        return getApiClassInstance(SyncImgRecClient.class);
    }

    public ApiResponse ImgRec(byte[] _body) {
        String _apiPath = "/image/tag";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.POST_BODY, GROUP_HOST, _apiPath, _body);

        return syncInvoke(_apiRequest);
    }
}
