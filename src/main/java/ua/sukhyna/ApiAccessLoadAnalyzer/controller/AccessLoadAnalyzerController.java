package ua.sukhyna.ApiAccessLoadAnalyzer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ua.sukhyna.ApiAccessLoadAnalyzer.service.AnalyzeService;

@RestController
@RequestMapping("/api/v1/requests")
public class AccessLoadAnalyzerController {

    private final AnalyzeService requestService;

    public AccessLoadAnalyzerController(AnalyzeService requestService) {
        this.requestService = requestService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<?> parseRequests(@RequestParam("requests") MultipartFile requests,
                                           @RequestParam(name = "count", required = false, defaultValue = "100") int count) {
        var result = requestService.processAnalyze(requests, count);
        return ResponseEntity.ok(requestService.processAnalyze(requests, count));
    }
}
