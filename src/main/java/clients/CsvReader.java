package clients;

import models.FlInsuranceData;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CsvReader {
    private final Pattern pattern;

    public CsvReader(final String separator) {
        this.pattern = Pattern.compile(separator);
    }

    public List<FlInsuranceData> loadCsvContentToList(
            final BufferedReader bufferedReader) throws IOException {
        try {
            return bufferedReader.lines().skip(1).map( line -> {
                final String[] lineArray = pattern.split(line);
                return new FlInsuranceData
                        .Builder()
                        .policyId(Integer.parseInt(lineArray[0]))
                        .stateCode(lineArray[1])
                        .country(lineArray[2])
                        .construction(lineArray[15])
                        .build();
            }).collect(Collectors.toList());
        } finally {
            bufferedReader.close();
        }
    }
}
