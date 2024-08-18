package com.spring.ecommerce.service;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;

public interface ReaderService {
    public void csvReader (String csvFilePath) throws IOException, CsvException;
}
