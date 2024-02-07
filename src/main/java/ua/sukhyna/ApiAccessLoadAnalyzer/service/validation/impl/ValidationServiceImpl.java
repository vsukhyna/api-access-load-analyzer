package ua.sukhyna.ApiAccessLoadAnalyzer.service.validation.impl;

import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.InetAddressValidator;
import org.apache.commons.validator.routines.IntegerValidator;
import org.apache.commons.validator.routines.RegexValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.sukhyna.ApiAccessLoadAnalyzer.domain.enums.RequestMethod;
import ua.sukhyna.ApiAccessLoadAnalyzer.service.validation.ValidationService;

import java.util.Arrays;
import java.util.Objects;

@Service
public class ValidationServiceImpl implements ValidationService {

    @Override
    public boolean isValidFile(MultipartFile file) {
        return Objects.equals(file.getContentType(), "text/csv") && !file.isEmpty();
    }

    @Override
    public boolean isValidRow(String row) {
        String[] rowParts = row.split(";");

        if (rowParts.length != 5)
            return false;

        return isValidIp(rowParts[0])
                && isValidDate(rowParts[1])
                && isValidMethod(rowParts[2])
                && isValidUri(rowParts[3])
                && isValidStatus(rowParts[4]);
    }

    private boolean isValidIp(String ip) {
        InetAddressValidator validator = InetAddressValidator.getInstance();
        return validator.isValid(ip);
    }

    private boolean isValidDate(String date) {
        return GenericValidator.isDate(date, "dd/MM/yyyy:HH:mm:ssZ", false);
    }

    private boolean isValidMethod(String method) {
        return Arrays.stream(RequestMethod.values()).map(Objects::toString).toList().contains(method);
    }

    private boolean isValidUri(String uri) {
        RegexValidator uriValidator = new RegexValidator("^[/\\w\\-\\.]+$");
        return uriValidator.isValid(uri);
    }

    private boolean isValidStatus(String status) {
        IntegerValidator integerValidator = IntegerValidator.getInstance();
        Integer statusCode = integerValidator.validate(status);
        if (statusCode == null)
            return false;
        return HttpStatus.resolve(statusCode) != null;
    }
}
