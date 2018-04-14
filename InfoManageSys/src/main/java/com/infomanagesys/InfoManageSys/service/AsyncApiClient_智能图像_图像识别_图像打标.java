package com.infomanagesys.InfoManageSys.service;/*
* Copyright 2017 Alibaba Group
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import com.alibaba.cloudapi.sdk.core.BaseApiClient;
import com.alibaba.cloudapi.sdk.core.BaseApiClientBuilder;
import com.alibaba.cloudapi.sdk.core.annotation.NotThreadSafe;
import com.alibaba.cloudapi.sdk.core.annotation.ThreadSafe;
import com.alibaba.cloudapi.sdk.core.enums.Method;
import com.alibaba.cloudapi.sdk.core.enums.Scheme;
import com.alibaba.cloudapi.sdk.core.enums.ParamPosition;
import com.alibaba.cloudapi.sdk.core.model.ApiCallBack;
import com.alibaba.cloudapi.sdk.core.model.ApiRequest;
import com.alibaba.cloudapi.sdk.core.model.BuilderParams;

@ThreadSafe
public final class AsyncApiClient_智能图像_图像识别_图像打标 extends BaseApiClient {
    public final static String GROUP_HOST = "txsbtxdb.market.alicloudapi.com";

    private AsyncApiClient_智能图像_图像识别_图像打标(BuilderParams builderParams) {
        super(builderParams);
    }

    @NotThreadSafe
    public static class Builder extends BaseApiClientBuilder<Builder, AsyncApiClient_智能图像_图像识别_图像打标>{

        @Override
        protected AsyncApiClient_智能图像_图像识别_图像打标 build(BuilderParams params) {
            return new AsyncApiClient_智能图像_图像识别_图像打标(params);
        }
    }

    public static Builder newBuilder(){
        return new Builder();
    }

    public static AsyncApiClient_智能图像_图像识别_图像打标 getInstance(){
        return getApiClassInstance(AsyncApiClient_智能图像_图像识别_图像打标.class);
    }

    public void 智能图像_图像识别_图像打标(byte[] _body, ApiCallBack _callBack) {
        String _apiPath = "/image/tag";

        ApiRequest _apiRequest = new ApiRequest(Scheme.HTTP, Method.POST_BODY, GROUP_HOST, _apiPath, _body);


        asyncInvoke(_apiRequest, _callBack);
    }

}

