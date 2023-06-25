package dev.dani.fileupload.service;

import dev.dani.fileupload.exception.exceptions.InvalidPathSequenceException;
import dev.dani.fileupload.exception.exceptions.ResourceNotFoundException;
import dev.dani.fileupload.model.entity.Attachment;
import dev.dani.fileupload.model.response.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AttachmentService {

    FileResponse saveAttachment(MultipartFile file) throws InvalidPathSequenceException, IOException;

    Attachment getAttachment(String fileId) throws ResourceNotFoundException;
}
