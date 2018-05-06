package com.infomanagesys.InfoManageSys.service.photo.impl;

import com.alibaba.cloudapi.sdk.core.model.ApiResponse;
import com.infomanagesys.InfoManageSys.dao.repository.doc.LabelRepository;
import com.infomanagesys.InfoManageSys.dao.repository.doc.LabelTypeRepository;
import com.infomanagesys.InfoManageSys.dataobject.entity.label.Label;
import com.infomanagesys.InfoManageSys.dataobject.entity.label.LabelType;
import com.infomanagesys.InfoManageSys.dataobject.entity.photo.Photo;
import com.infomanagesys.InfoManageSys.dataobject.enums.ApiKeyEnum;
import com.infomanagesys.InfoManageSys.dataobject.enums.FileStatusEnum;
import com.infomanagesys.InfoManageSys.dataobject.enums.LabelEnum;
import com.infomanagesys.InfoManageSys.dataobject.enums.SeverPathEnum;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LabelResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LabelTypeResponseDTO;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.photo.*;
import com.infomanagesys.InfoManageSys.exception.UserCheckException;
import com.infomanagesys.InfoManageSys.service.photo.itf.IImgRecService;
import com.infomanagesys.InfoManageSys.util.Pid;
import com.infomanagesys.InfoManageSys.util.SyncImgRecClient;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.baidu.aip.imageclassify.AipImageClassify;

@Service
public class ImgRecServiceImpl implements IImgRecService {
    private final LabelRepository labelRepository;
    private final LabelTypeRepository labelTypeRepository;
    @Autowired
    public ImgRecServiceImpl(final LabelRepository labelRepository,
                          final LabelTypeRepository labelTypeRepository){
        this.labelRepository = labelRepository;
        this.labelTypeRepository = labelTypeRepository;
    }

    @Override
    public Object ImgRec(Photo photo, String userId,String imgfile){
        ArrayList<LabelResponseDTO> labelResponseDTOS = new ArrayList<LabelResponseDTO>();
        JSONObject body = new JSONObject();
        SyncImgRecClient syncImgRecClient = SyncImgRecClient.newBuilder()
                .appKey(ApiKeyEnum.ALI_IMGREC_ENUM.getApikey())
                .appSecret(ApiKeyEnum.ALI_IMGREC_ENUM.getSecretKey())
                .build();
        try{
            body.put("type",1);
            body.put("content",getImageDataByUrl(imgfile));
            byte[] _body =  body.toString().getBytes();
            ApiResponse response = syncImgRecClient.ImgRec(_body);
            System.out.print(response);
            return new String(response.getBody(),"utf-8");
        }catch (Exception e){
            e.printStackTrace();
            throw new UserCheckException("参数解析失败");
        }

    }
    private static byte[] getImageDataByUrl(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
    }
        Base64 base64 = new Base64();

        byte[] codeData= base64.encode(data);
        return codeData;
    }
    @Override
    public ArrayList<LabelResponseDTO> BaiDuImgRec(Photo photo, String userId,double imgSize){
        ArrayList<LabelResponseDTO> labelResponseDTOArrayList = new ArrayList<LabelResponseDTO>();
        AipImageClassify client = new AipImageClassify(ApiKeyEnum.BAIDUI_NLP_ENUM.getAppid()
                , ApiKeyEnum.BAIDUI_NLP_ENUM.getApikey()
                , ApiKeyEnum.BAIDUI_NLP_ENUM.getSecretKey());
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 调用接口
        String srcPath = SeverPathEnum.FILE_PATH.getPath()+"/"+userId+"/photo/"+photo.getPid()+"."+photo.getType();
        String desPath = SeverPathEnum.FILE_PATH.getPath() + "/" + userId + "/minPhoto/" + photo.getPid() + "." + photo.getType();

        try {
//                Thumbnails.of(srcPath).scale(2.0/imgSize - 0.001).toFile(desPath);
            Thumbnails.of(srcPath).size(200,300).toFile(desPath);
            srcPath =desPath;
        }catch (Exception e){
            e.printStackTrace();
            throw new UserCheckException("图片压缩失败");
        }
        JSONObject res = client.advancedGeneral(srcPath, new HashMap<String, String>());

        System.out.println(res.toString());
        try {
            JSONArray labels = res.getJSONArray("result");
            for (int i = 0; i < labels.length(); i++) {
                JSONObject label = labels.getJSONObject(i);
                Label _label = labelRepository.findFirstByUserAndContent(userId,label.getString("keyword"));

                if(_label==null){
                    LabelType labelType = labelTypeRepository.findFirstByUserAndName(userId,"未分组标签");
                    JSONObject type = new JSONObject();
                    type.put("name",labelType.getName());
                    type.put("pid",labelType.getPid());
                    _label = Label.labelBuilder()
                            .withType(type.toString())
                            .withPid(Pid.getPid())
                            .withContent(label.getString("keyword"))
                            .withUser(userId)
                            .withDescription(label.getString("root"))
                            .withStatus(FileStatusEnum.STATUS_AVAILABLE.getState()).build();

                    _label = labelRepository.save(_label);
                }
                LabelTypeResponseDTO labelTypeResponseDTO =new LabelTypeResponseDTO();
                labelTypeResponseDTO.setName(new JSONObject(_label.getType()).getString("name"));
                labelTypeResponseDTO.setPid(new JSONObject(_label.getType()).getString("pid"));

                LabelResponseDTO labelResponseDTO = new LabelResponseDTO();
                labelResponseDTO.setScore(label.getDouble("score"));
                labelResponseDTO.setLevel(getLevelByScore(labelResponseDTO.getScore()));
                labelResponseDTO.setColor(LabelEnum.GetEnumByLevel(labelResponseDTO.getLevel()).getColor());
                labelResponseDTO.setContent(label.getString("keyword"));
                labelResponseDTO.setDiscription(label.getString("root"));
                labelResponseDTO.setPid(_label.getPid());
                labelResponseDTO.setType(labelTypeResponseDTO);
                labelResponseDTOArrayList.add(labelResponseDTO);
            }
            return labelResponseDTOArrayList;
        }catch (Exception e){
            e.printStackTrace();
            throw new UserCheckException("参数解析出错");
        }

    }
    private int getLevelByScore(double score){
        if(score>=0.9){
            return 1;
        }else if(score>=0.7){
            return 2;
        }else if(score>=0.5){
            return 3;
        }else {
            return 4;
        }
    }
    public ImgApiDTO BaiDUImgResIMG(Photo photo, String userId, String typeCode){
        ImgApiDTO imgApiDTO = new ImgApiDTO();
        AipImageClassify client = new AipImageClassify(ApiKeyEnum.BAIDUI_NLP_ENUM.getAppid()
                , ApiKeyEnum.BAIDUI_NLP_ENUM.getApikey()
                , ApiKeyEnum.BAIDUI_NLP_ENUM.getSecretKey());
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 调用接口
        String path = SeverPathEnum.FILE_PATH.getPath()+"/"+userId+"/tempFile/"+photo.getPid()+"."+photo.getType();
//        String desPath = SeverPathEnum.FILE_PATH.getPath() + "/" + userId + "/minPhoto/" + photo.getPid() + "." + photo.getType();

        try {
//                Thumbnails.of(srcPath).scale(2.0/imgSize - 0.001).toFile(desPath);
            Thumbnails.of(path).size(200,300).toFile(path);
//            srcPath =desPath;
        }catch (Exception e){
            e.printStackTrace();
            throw new UserCheckException("图片压缩失败");
        }
        JSONObject res=null;
        try {
            switch (typeCode) {
                case "0":
                    res = client.plantDetect(path, new HashMap<String, String>());
                    JSONArray labels = res.getJSONArray("result");
                    ArrayList<PlantDTO> plantDTOS = new ArrayList<PlantDTO>();
                    for(int i=0;i<labels.length();i++){
                        JSONObject label = labels.getJSONObject(i);
                        PlantDTO plantDTO = new PlantDTO();
                        plantDTO.setName(label.getString("name"));
                        plantDTO.setScore(label.getDouble("score"));
                        plantDTOS.add(plantDTO);
                    }
                    imgApiDTO.setPlantS(plantDTOS);
                    imgApiDTO.setTypeCode(typeCode);
                    break;
                case "1":
                    res = client.animalDetect(path, new HashMap<String, String>());
                    JSONArray labels_1 = res.getJSONArray("result");
                    ArrayList<AnimalDTO> animalDTOS = new ArrayList<AnimalDTO>();
                    for(int i=0;i<labels_1.length();i++){
                        JSONObject label = labels_1.getJSONObject(i);
                        AnimalDTO animalDTO = new AnimalDTO();
                        animalDTO.setName(label.getString("name"));
                        animalDTO.setScore(label.getDouble("score"));
                        animalDTOS.add(animalDTO);
                    }
                    imgApiDTO.setAnimalS(animalDTOS);
                    imgApiDTO.setTypeCode(typeCode);
                    break;
                case "2":
                    res = client.dishDetect(path, new HashMap<String, String>());
                    JSONArray labels_2 = res.getJSONArray("result");
                    ArrayList<FoodDTO> foodDTOS = new ArrayList<FoodDTO>();
                    for(int i=0;i<labels_2.length();i++){
                        JSONObject label = labels_2.getJSONObject(i);
                        FoodDTO foodDTO = new FoodDTO();
                        foodDTO.setName(label.getString("name"));
                        foodDTO.setHas_calorie(label.getBoolean("has_calorie"));
                        foodDTO.setCalorie(label.getDouble("calorie"));
                        foodDTO.setProbability(label.getDouble("probability"));
                        foodDTOS.add(foodDTO);
                    }
                    imgApiDTO.setFoodS(foodDTOS);
                    imgApiDTO.setTypeCode(typeCode);
                    break;
                case "3":
                    res = client.carDetect(path, new HashMap<String, String>());
                    JSONArray labels_3 = res.getJSONArray("result");
                    ArrayList<CarDTO> carDTOS = new ArrayList<CarDTO>();
                    for(int i=0;i<labels_3.length();i++){
                        JSONObject label = labels_3.getJSONObject(i);
                        CarDTO carDTO = new CarDTO();
                        carDTO.setName(label.getString("name"));
                        carDTO.setScore(label.getDouble("score"));
                        carDTO.setYear(label.getString("year"));
                        carDTOS.add(carDTO);
                    }
                    imgApiDTO.setCarS(carDTOS);
                    imgApiDTO.setTypeCode(typeCode);
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            System.out.println(res);
            e.printStackTrace();
            throw  new UserCheckException("图像识别失败");
        }
        System.out.println(res);
        imgApiDTO.setPhoto_src(SeverPathEnum.FILE_SEVER_PATH.getPath()+"/userData/"+userId+"/tempFile/"+photo.getPid()+"."+photo.getType());
        return imgApiDTO;
    }
}
