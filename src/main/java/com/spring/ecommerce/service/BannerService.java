package com.spring.ecommerce.service;

import com.spring.ecommerce.persistence.model.Banners;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface BannerService {
    public List<Banners> findAll();
    public Optional<Banners> findById(Long id);
    public Banners save(Banners banners);
    public List<String> saveMultil ( List<MultipartFile> files);
    public Banners update(MultipartFile file, Long idBanner) throws IOException;
    public void delete(Long id);
    public Banners updateStatus(Long id);

}