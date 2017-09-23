package clients;

import com.google.common.collect.ImmutableList;
import junit.framework.Assert;
import models.FlInsuranceData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.BufferedReader;
import java.util.stream.Stream;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

public class CsvReaderTest {

    @Mock
    private BufferedReader bufferedReader;

    private CsvReader csvReader;

    private String headers = "policyID,"
            + "statecode,"
            + "county,"
            + "eq_site_limit,"
            + "hu_site_limit,"
            + "fl_site_limit,"
            + "fr_site_limit,"
            + "tiv_2011,"
            + "tiv_2012,"
            + "eq_site_deductible,"
            + "hu_site_deductible,"
            + "fl_site_deductible,"
            + "fr_site_deductible,"
            + "point_latitude,"
            + "point_longitude,"
            + "line,"
            + "construction,"
            + "point_granularity";
    private String csvValues = "119736,"
            + "FL,"
            + "CLAY COUNTY,"
            + "498960,"
            + "498960,"
            + "498960,"
            + "498960,"
            + "498960,"
            + "792148.9,"
            + "0,"
            + "9979.2,"
            + "0,"
            + "0,"
            + "30.102261,"
            + "-81.711777,"
            + "Residential,"
            + "Masonry,"
            + "1";

    private final String[] sampleCsv = {headers,csvValues};

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        csvReader = new CsvReader(",");
        when(bufferedReader.lines()).thenReturn(Stream.of(sampleCsv));
    }

    @Test
    public void testLoadCsvContentToList() throws Exception {
        ImmutableList<FlInsuranceData> list =
                ImmutableList.copyOf(csvReader.loadCsvContentToList(bufferedReader));
        assertTrue(1 == list.size());
        assertEquals(119736, list.get(0).getPolicyId());
    }

}