package com.infomanagesys.InfoManageSys.service.doc.impl;

import com.baidu.aip.nlp.AipNlp;
import com.infomanagesys.InfoManageSys.dao.repository.doc.LabelRepository;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFile;
import com.infomanagesys.InfoManageSys.dataobject.entity.doc.Label;
import com.infomanagesys.InfoManageSys.dataobject.enums.ApiKeyEnum;
import com.infomanagesys.InfoManageSys.dataobject.enums.LabelEnum;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LabelResponseDTO;
import com.infomanagesys.InfoManageSys.service.doc.itf.INLPService;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@Service
public class NLPServiceImpl implements INLPService {
    private final LabelRepository labelRepository;
    @Autowired
    public NLPServiceImpl(final LabelRepository labelRepository){
        this.labelRepository = labelRepository;
    }
    @Override
    public ArrayList<LabelResponseDTO> GetLabelsByWorldFile(DocFile docFile) {
        ArrayList<LabelResponseDTO> labelResponseDTOArrayList = new ArrayList<LabelResponseDTO>();
        File file = new File(docFile.getPathLocal() +"\\"+ docFile.getName()+"." + docFile.getPostfix().toLowerCase());
        String content = "";
        String title = docFile.getName();
        try {
            FileInputStream fis = new FileInputStream(file);
            switch (docFile.getPostfix()) {
                case "DOC":
                    HWPFDocument doc = new HWPFDocument(fis);
                    content = doc.getDocumentText();
                    break;
                case "DOCX":
                    XWPFDocument xdoc = new XWPFDocument(fis);
                    XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
                    content = extractor.getText();
                    break;
                default:
                    break;
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        AipNlp client = new AipNlp(ApiKeyEnum.BAIDUI_NLP_ENUM.getAppid()
                , ApiKeyEnum.BAIDUI_NLP_ENUM.getApikey()
                , ApiKeyEnum.BAIDUI_NLP_ENUM.getSecretKey());
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<String, Object>();
        JSONObject res = client.keyword(title, content, options);

        try {
            ArrayList<String> arrayList= new ArrayList<String>();
            JSONArray items = res.getJSONArray("items");
            System.out.print(res);
            System.out.print(items);
            for (int i=0;i<items.length();i++) {
                JSONObject item = items.getJSONObject(i);
                if(labelRepository.findFirstByContent(item.getString("tag"))==null){
                    Label label = Label.labelBuilder()
                            .withType("other")
                            .withContent(item.getString("tag"))
                            .withContent(item.getString("tag"))
                            .withStatus("0").build();
                    labelRepository.save(label);
                }else {
                    LabelResponseDTO labelResponseDTO = new LabelResponseDTO();
                    labelResponseDTO.setContent(item.getString("tag"));
                    labelResponseDTO.setScore(item.getDouble("score"));
                    labelResponseDTO.setLevel(getLevelByScore(item.getDouble("score")));
                    labelResponseDTO.setColor(LabelEnum.GetEnumByLevel(labelResponseDTO.getLevel()).getColor());
                    labelResponseDTOArrayList.add(labelResponseDTO);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  labelResponseDTOArrayList;
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
}

