package ua.sukhyna.ApiAccessLoadAnalyzer.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ua.sukhyna.ApiAccessLoadAnalyzer.domain.enums.RequestMethod;

@Data
@RequiredArgsConstructor
public class RequestVO {
    private String ip;
    private String data;
    private RequestMethod requestMethod;
    private String uri;
    private int status;
}
