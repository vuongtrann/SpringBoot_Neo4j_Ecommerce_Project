package com.spring.ecommerce.service.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import com.spring.ecommerce.persistence.model.Category;
import com.spring.ecommerce.persistence.model.Product;
import com.spring.ecommerce.persistence.repository.CategoryRepository;
import com.spring.ecommerce.persistence.repository.ProductReprository;
import com.spring.ecommerce.service.CategoryService;
import com.spring.ecommerce.service.ProductService;
import com.spring.ecommerce.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReaderServiceImpl implements ReaderService {
    @Autowired
    private ProductReprository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

//    @Value("${csv.file.path}")
//    private String csvFilePath;



    @Override
    public void csvReader(String csvFilePath) throws IOException, CsvException {
        String[][] data = readCSV(csvFilePath);

        try {
            for (String[] row : data) {
                String categoryName = row[0];
                Long parentId = Long.valueOf(row[1]);
                String productName = row[2];
                String imageUrl = row[3];
                String description = row[4];
                Double price = Double.valueOf(row[5]);
                Double rate = Double.valueOf(row[6]);
                String evalua = row[7];
                Long catId = Long.valueOf(row[8]);

                Category category = new Category(categoryName,parentId);
                Category category1 = new Category(catId);
                Product product = new Product(productName,imageUrl,description,price,category1);

                categoryService.saveWithParentID(category);
                productService.save(product);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String[][] readCSV(String filePath) throws IOException, CsvValidationException {
        List<String[]> data = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                data.add(nextLine);
            }
        }
        return data.toArray(new String[0][]);
    }
}
