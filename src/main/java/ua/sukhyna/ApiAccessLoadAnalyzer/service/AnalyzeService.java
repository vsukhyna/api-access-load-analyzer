package ua.sukhyna.ApiAccessLoadAnalyzer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.sukhyna.ApiAccessLoadAnalyzer.domain.RequestVO;
import ua.sukhyna.ApiAccessLoadAnalyzer.domain.RequestsMapResult;
import ua.sukhyna.ApiAccessLoadAnalyzer.service.mapper.RequestsMapper;
import ua.sukhyna.ApiAccessLoadAnalyzer.utils.ReportPublisher;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyzeService {

    private final RequestsMapper requestsMapper;

    public String processAnalyze(MultipartFile csv, int requestsCount) {

        Instant startTime = Instant.now();

        RequestsMapResult requestsVo = requestsMapper.mapRequestsFile(csv);

        Map<String, Long> analyzeLoadRequests = analyzeLoadRequests(requestsVo.getRequests());
        Map<String, Long> statisticsPerSecond = analyzeRequestsPerSecond(requestsVo.getRequests());

        String result = ReportPublisher.publishReport(requestsVo.getRequests(),
                analyzeLoadRequests, statisticsPerSecond, requestsVo.getTotalRows(), startTime, requestsCount);

        return result;
    }


    private Map<String, Long> analyzeLoadRequests(List<RequestVO> requests) {

        Map<String, Long> statisticsRequests = requests.stream()
                .collect(Collectors.groupingBy(
                        request -> request.getUri() + " - " + request.getRequestMethod(),
                        Collectors.counting()));

        return statisticsRequests;
    }

    private Map<String, Long> analyzeRequestsPerSecond(List<RequestVO> requests) {

        Map<String, Long> statisticsPerSecond = requests.stream()
                .collect(Collectors.groupingBy(
                        RequestVO::getData,
                        Collectors.counting()));
        return statisticsPerSecond;
    }

}
