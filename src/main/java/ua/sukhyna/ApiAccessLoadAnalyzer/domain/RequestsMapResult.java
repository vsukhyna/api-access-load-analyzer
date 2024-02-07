package ua.sukhyna.ApiAccessLoadAnalyzer.domain;

import lombok.Data;

import java.util.List;

@Data
public class RequestsMapResult {
    private List<RequestVO> requests;
    private int totalRows;
}
