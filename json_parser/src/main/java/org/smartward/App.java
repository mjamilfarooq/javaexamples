package org.smartward;

import org.smartward.queries.WardQuery;

import java.io.FileInputStream;
import java.time.Duration;
import java.time.Instant;


public class App
{


    public static void main( String[] args ) {
        try {
            try (FileInputStream input = new FileInputStream(args[0])) {
                Instant start = Instant.now();
                WardQuery queries = new WardQuery();
                queries.ExecuteWithParallelStreams(input);
                System.out.println("Elapsed Time: " + Duration.between(start, Instant.now()).toMillis());
                queries.printResults();
            }
        } catch(Exception ex) {
            System.err.println(ex.getMessage());
        }
    }
}
