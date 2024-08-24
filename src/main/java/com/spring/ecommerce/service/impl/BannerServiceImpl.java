
package com.spring.ecommerce.service.impl;

import com.spring.ecommerce.persistence.model.Banners;
import com.spring.ecommerce.persistence.repository.BannerRepository;
import com.spring.ecommerce.service.BannerService;
import com.spring.ecommerce.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    S3Service s3Service;


    @Override
    public List<String> findAll() {
        return bannerRepository.findAll().stream().map(banners -> {
            return banners.getUrl();
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<Banners> findById(Long id) {
        Optional<Banners> banners = bannerRepository.findById(id);
        if (banners.isPresent()) {
            return banners;
        }
        return null;
    }

    @Override
    public Banners save(Banners banners) {

        return null;
    }

    @Override
    public Banners saveMultil(List<MultipartFile> files) {
        files.stream().map(file -> {
            try {
                String url = s3Service.uploadBanner(file);
                Banners banners = new Banners();
                banners.setUrl(url);
                banners.setStartDate(LocalDate.now());
                banners.setEndDate(LocalDate.now().plusDays(10));
                bannerRepository.save(banners);
                return url;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        return null;
    }

    @Override
    public Banners update(MultipartFile file, Long id) throws IOException {
        try {
            Banners banners = bannerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
            String url = s3Service.uploadBanner(file);
            s3Service.remove(banners.getUrl());
            banners.setUrl(url);
            bannerRepository.save(banners);

            return banners;
        }catch (RuntimeException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        try{
            String url = bannerRepository.findById(id).get().getUrl();
            s3Service.remove(url);
            bannerRepository.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
