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

import com.alibaba.cloudapi.sdk.core.model.ApiRequest;
import com.alibaba.cloudapi.sdk.core.model.ApiResponse;
import com.alibaba.cloudapi.sdk.core.model.ApiCallBack;

public class AsyncDemo_智能图像_图像识别_图像打标 {

    private AsyncApiClient_智能图像_图像识别_图像打标 asyncClient = null;

    public AsyncDemo_智能图像_图像识别_图像打标() {
        this.asyncClient = AsyncApiClient_智能图像_图像识别_图像打标.newBuilder()
                .appKey("24830743")
                .appSecret("6523fa9cfee5b8251d345d2ded48f724")
                .build();
    }

    public void 智能图像_图像识别_图像打标Demo(byte[] body) {
        asyncClient.智能图像_图像识别_图像打标(body, new ApiCallBack() {
            @Override
            public void onFailure(ApiRequest request, Exception e) {
                System.out.println("failure");
                e.printStackTrace();
            }

            @Override
            public void onResponse(ApiRequest request, ApiResponse response) {
                System.out.println("success");
                printResponse(response);
            }
        });
    }

    private static void printResponse(ApiResponse response) {
        try {
            System.out.println("response code = " + response.getStatusCode());
            System.out.println("response content = " + new String(response.getBody(), "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

