package com.infomanagesys.InfoManageSys.service.doc.itf;

import com.infomanagesys.InfoManageSys.dataobject.entity.doc.DocFile;
import com.infomanagesys.InfoManageSys.dataobject.responseDTO.LabelResponseDTO;

import java.util.ArrayList;

public interface INLPService {
    public ArrayList<LabelResponseDTO> GetLabelsByWorldFile(DocFile docFile);
}
