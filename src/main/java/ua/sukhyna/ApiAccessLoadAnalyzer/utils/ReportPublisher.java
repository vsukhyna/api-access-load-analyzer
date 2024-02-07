package ua.sukhyna.ApiAccessLoadAnalyzer.utils;

import ua.sukhyna.ApiAccessLoadAnalyzer.domain.RequestVO;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ReportPublisher {

    public static String publishReport(List<RequestVO> requests,
                                       Map<String, Long> analyzeLoadRequests,
                                       Map<String, Long> statisticsPerSecond,
                                       int totalRows,
                                       Instant startTime,
                                       int requestsCount) {

        StringBuilder result = new StringBuilder();

        // Вывод статистики по количеству запросов
        result.append("Results:\n");
        analyzeLoadRequests.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(requestsCount)
                .forEach(entry -> result.append(entry.getKey())
                        .append(" - ").append(entry.getValue()).append(" times\n"));

        // Вывод статистики запросов в секунду
        result.append("\n---- Statistics ----\n");
        result.append("Requests per seconds:\n");
        statisticsPerSecond.forEach((key, value) -> result.append(key)
                .append(" - ").append(value).append(" requests\n"));

        // Вывод дополнительных счетчиков
        result.append("------------------\n")
                .append("\n---- Counters ----\n")
                .append("Total rows - ").append(totalRows).append("\n")
                .append("Valid rows - ").append(requests.size()).append("\n")
                .append("Processed total time - ")
                .append(Duration.between(startTime, Instant.now()).getSeconds())
                .append(" sec")
                .append("\n------------------");
        return result.toString();
    }
}
