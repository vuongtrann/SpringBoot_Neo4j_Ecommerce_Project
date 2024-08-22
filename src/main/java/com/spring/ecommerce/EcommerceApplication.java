package com.spring.ecommerce;

import com.opencsv.exceptions.CsvException;
import com.spring.ecommerce.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
@EnableNeo4jRepositories

public class EcommerceApplication  {
    @Autowired
    private  ReaderService readerService;

    @Value("${csv.file.path}")
    private String filePath;
    @Value("${csv.file.path}")
    private String filePath2;

//    @Value("${file.path}")
//    private String filePath;


    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);

    }
//    @Bean
//    public CommandLineRunner run() {
//        return args -> {
//
//            try {
//                if (Files.exists(Paths.get(filePath))) {
//                    readerService.csvReader(filePath);
//                    System.out.println("Successfully read CSV file");
//                }else {
//                    System.out.println("File not found");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        };
//    }

}
