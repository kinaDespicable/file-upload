package dev.dani.fileupload.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileResponse {

    private String fileName;
    private String downloadURL;
    private String fileType;
    private long fileSize;

}
