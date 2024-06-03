package tn.myadvans.authentification.authentification.service;

import org.springframework.web.multipart.MultipartFile;
import tn.myadvans.authentification.authentification.entities.Cc;

import java.io.IOException;
import java.util.List;

public interface CcService {

    Cc getCc(Long id);

    String addPicture(Long idUser, MultipartFile file) throws IOException;

    Cc modifyCc(Cc cc);

    List<Cc> getAllCcs();

}
