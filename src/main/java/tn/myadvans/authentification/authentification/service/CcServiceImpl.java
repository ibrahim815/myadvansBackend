package tn.myadvans.authentification.authentification.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tn.myadvans.authentification.authentification.entities.Cc;
import tn.myadvans.authentification.authentification.repository.CcRepository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CcServiceImpl implements CcService {

    private final CcRepository ccRepository;

    public CcServiceImpl(CcRepository ccRepository) {
        this.ccRepository = ccRepository;
    }

    @Override
    public Cc getCc(Long id) {
        return ccRepository.findById(id).orElse(null);
    }

    @Override
    public String addPicture(Long idUser, MultipartFile file) {
        Cc cc = ccRepository.findById(idUser).orElse(null);
        if (cc == null) {
            return "Client not found";
        }
        String fileName= cc.getUsername()+"."+file.getContentType().replaceAll("image/","");
        Path uploadPath = Paths.get("pictures", fileName);
        File uploadDir = new File(uploadPath.toString()).getParentFile();
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if (!file.isEmpty()) {
            try {
                File outputFile = uploadPath.toFile();
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
                outputStream.write(file.getBytes());
                outputStream.flush();
                outputStream.close();
                cc.setPicture(fileName);
                ccRepository.save(cc);
                return fileName;
            } catch (IOException e) {
                // Handle the exception
                return "Error while saving the picture";
            }
        } else {
            return "No picture found in the request";
        }
    }

    @Transactional
    @Override
    public Cc modifyCc(Cc cc) {
        Cc originalCc = ccRepository.findById(cc.getId()).orElse(null);
        assert originalCc != null;
        originalCc.setEmail(cc.getEmail());
        originalCc.setName(cc.getName());
        originalCc.setLastname(cc.getLastname());
        originalCc.setPhone(cc.getPhone());

        return ccRepository.save(originalCc);
    }

    @Override
    public List<Cc> getAllCcs() {
        return ccRepository.findAll();
    }


}
