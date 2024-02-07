package ua.sukhyna.ApiAccessLoadAnalyzer.service.mapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ua.sukhyna.ApiAccessLoadAnalyzer.utils.CsvReader;
import ua.sukhyna.ApiAccessLoadAnalyzer.service.validation.ValidationService;
import ua.sukhyna.ApiAccessLoadAnalyzer.domain.RequestVO;
import ua.sukhyna.ApiAccessLoadAnalyzer.domain.RequestsMapResult;
import ua.sukhyna.ApiAccessLoadAnalyzer.domain.enums.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RequestsMapper {

    private final ValidationService validationService;

    public RequestsMapResult mapRequestsFile(MultipartFile csv) {

        RequestsMapResult requestResult = new RequestsMapResult();

        List<RequestVO> requests = new ArrayList<>();

        if (validationService.isValidFile(csv)) {

            List<String> dataRows = CsvReader.getRowsFromCsv(csv);
            requestResult.setTotalRows(dataRows.size());

            for (String row : dataRows) {
                if (validationService.isValidRow(row)) {
                    RequestVO request = mapRow(row);
                    requests.add(request);
                }
            }
            requestResult.setRequests(requests);

        } else {
            log.error("File is wrong format.");
        }

        return requestResult;
    }

    private RequestVO mapRow(String row) {
        String[] values = row.split(";");
        RequestVO vo = new RequestVO();
        vo.setIp(values[0]);
        vo.setData(values[1]);
        vo.setRequestMethod(RequestMethod.valueOf(values[2]));
        vo.setUri(values[3]);
        vo.setStatus(Integer.parseInt(values[4]));
        return vo;
    }
}
