package org.smartward;

import org.junit.Test;

import org.smartward.queries.WardQuery;

import java.io.FileInputStream;
import java.time.Duration;
import java.time.Instant;

import static org.junit.Assert.*;


/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void SmartJsonTester() {
        try {
            try (FileInputStream input = new FileInputStream("smartward.json")) {
                Instant start = Instant.now();
                WardQuery queries = new WardQuery();
                queries.ExecuteWithParallelStreams(input);
                System.out.println("Elapsed Time: " + Duration.between(start, Instant.now()).toMillis());

                //Testing Patient Resultset
                var patients = queries.getAllPatients();
                assertEquals(500, patients.size());

                var doctors = queries.getAllDoctors();
                assertEquals(40, doctors.size());

                var nurseMap = queries.getAllNursesGroupByWardNumber();
                assertEquals(10, nurseMap.keySet().size());

                assertEquals(9, nurseMap.get(1).size());
                assertEquals(11, nurseMap.get(2).size());
                assertEquals(13, nurseMap.get(3).size());
                assertEquals(10, nurseMap.get(4).size());
                assertEquals(4, nurseMap.get(5).size());
                assertEquals(7, nurseMap.get(6).size());
                assertEquals(12, nurseMap.get(7).size());
                assertEquals(12, nurseMap.get(8).size());
                assertEquals(8, nurseMap.get(9).size());
                assertEquals(14, nurseMap.get(10).size());

            }
        } catch(Exception ex) {

        }
    }

    @Test
    public void CaseInsensitivityTest() {
        try {
            try (FileInputStream input = new FileInputStream("allpatients.json")) {
                Instant start = Instant.now();
                WardQuery queries = new WardQuery();
                queries.ExecuteWithParallelStreams(input);
                System.out.println("Elapsed Time: " + Duration.between(start, Instant.now()).toMillis());

                //Testing Patient Resultset
                var patients = queries.getAllPatients();
                assertEquals(100, patients.size());

                queries.printResults();

            }
        } catch(Exception ex) {

        }
    }

    @Test
    public void SmartAnotherTester() {
        try {
            try (FileInputStream input = new FileInputStream("another_test.json")) {
                Instant start = Instant.now();
                WardQuery queries = new WardQuery();
                queries.ExecuteWithParallelStreams(input);
                System.out.println("Elapsed Time: " + Duration.between(start, Instant.now()).toMillis());

                //Testing Patient Resultset
                var patients = queries.getAllPatients();
                assertEquals(37, patients.size());

                var doctors = queries.getAllDoctors();
                assertEquals(31, doctors.size());

                var nurseMap = queries.getAllNursesGroupByWardNumber();
                assertEquals(9, nurseMap.keySet().size());

                assertEquals(4, nurseMap.get(1).size());
                assertEquals(2, nurseMap.get(2).size());
                assertEquals(6, nurseMap.get(3).size());
                assertEquals(2, nurseMap.get(4).size());
                assertEquals(3, nurseMap.get(5).size());
                assertEquals(5, nurseMap.get(6).size());
                assertEquals(4, nurseMap.get(7).size());
                assertEquals(4, nurseMap.get(8).size());
                assertEquals(2, nurseMap.get(10).size());

            }
        } catch(Exception ex) {

        }
    }

}
