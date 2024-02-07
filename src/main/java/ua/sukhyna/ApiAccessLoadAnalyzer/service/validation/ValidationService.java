package ua.sukhyna.ApiAccessLoadAnalyzer.service.validation;

import org.springframework.web.multipart.MultipartFile;

public interface ValidationService {

    boolean isValidFile(MultipartFile file);

    boolean isValidRow(String row);
}
