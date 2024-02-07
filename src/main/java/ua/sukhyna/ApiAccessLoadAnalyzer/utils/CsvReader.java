package ua.sukhyna.ApiAccessLoadAnalyzer.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    public static List<String> getRowsFromCsv(MultipartFile csv) {

        List<String> rows = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csv.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                rows.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return rows;
    }

}
