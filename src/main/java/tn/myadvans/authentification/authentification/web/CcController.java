package tn.myadvans.authentification.authentification.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.myadvans.authentification.authentification.entities.Cc;
import tn.myadvans.authentification.authentification.service.CcService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


@CrossOrigin("*")
@RestController
public class CcController {

    private final CcService ccService;

    public CcController(CcService ccService) {
        this.ccService = ccService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/cc/{id}")
    public ResponseEntity<Cc> getCc(@PathVariable("id") Long id) {
        return new ResponseEntity<>(ccService.getCc(id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/cc/getAll/ccs")
    public ResponseEntity<List<Cc>> getCc() {
        return new ResponseEntity<>(ccService.getAllCcs(),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_SUPPORT')")
    @GetMapping(value = "/cc/picture/{pictureName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    public ResponseEntity<byte[]> getImageWithMediaType(@PathVariable("pictureName") String pictureName) throws IOException {
        String imagePath = "pictures/"+pictureName;

        File imageFile = new File(imagePath);
        if (!imageFile.exists() || imageFile.isDirectory()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/cc/modify")
    public ResponseEntity<Cc> getCc(@RequestBody Cc cc) {
        return new ResponseEntity<>(ccService.modifyCc(cc),HttpStatus.OK);
    }

}
