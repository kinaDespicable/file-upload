package dev.dani.fileupload.service.implementation;

import dev.dani.fileupload.exception.exceptions.InvalidPathSequenceException;
import dev.dani.fileupload.exception.exceptions.ResourceNotFoundException;
import dev.dani.fileupload.model.entity.Attachment;
import dev.dani.fileupload.model.response.FileResponse;
import dev.dani.fileupload.repository.AttachmentRepository;
import dev.dani.fileupload.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private static final String DOUBLE_PERIOD = "..";

    private final AttachmentRepository attachmentRepository;


    @Override
    public FileResponse saveAttachment(MultipartFile file) throws InvalidPathSequenceException, IOException {

        String fileName = getFileName(file);

        Attachment attachment = null;

        try {
            attachment = Attachment.builder()
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .data(file.getBytes())
                    .build();

            attachmentRepository.save(attachment);

        } catch (IOException exception) {
            throw new FileUploadException("Could not upload the file");
        }

        return constructFileResponse(attachment, file);
    }

    @Override
    public Attachment getAttachment(String fileId) throws ResourceNotFoundException {
        return attachmentRepository.findById(UUID.fromString(fileId))
                .orElseThrow(() -> new ResourceNotFoundException("Resource with id: " + fileId + "  not found."));
    }

    private String getFileName(MultipartFile file) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (fileName.contains(DOUBLE_PERIOD)) {
            throw new InvalidPathSequenceException("Filename contains invalid path sequence: " + fileName);
        }

        return fileName;
    }

    private FileResponse constructFileResponse(Attachment attachment, MultipartFile file) {

        return FileResponse.builder()
                .fileName(attachment.getFileName())
                .fileType(attachment.getFileType())
                .fileSize(file.getSize())
                .downloadURL(getDownloadUrl(attachment))
                .build();
    }

    private String getDownloadUrl(Attachment attachment) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(String.valueOf(attachment.getId()))
                .toUriString();
    }

}
