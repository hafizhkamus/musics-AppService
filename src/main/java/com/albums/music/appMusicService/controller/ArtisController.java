package com.albums.music.appMusicService.controller;

import com.albums.music.appMusicService.dataTables.DataTableRequest;
import com.albums.music.appMusicService.dataTables.DataTableResponse;
import com.albums.music.appMusicService.entity.Artis;
import com.albums.music.appMusicService.service.ArtisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api/artis")
public class ArtisController {

    @Autowired
    private ArtisService service;

    @GetMapping(path = "/list-artis")
    public ResponseEntity<List<Artis>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(path = "/artis/{id}")
    public ResponseEntity<Artis> getProvinsiDetails(@PathVariable("id") Integer id){
        Optional<Artis> prov = service.findById(id);
        if(prov.isPresent()){
            return ResponseEntity.ok().body(prov.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        try {
            service.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/save")
    public ResponseEntity<Map<String, Object>> saveProvinsiJson(@RequestBody Artis artis) throws IOException {
        Map<String,Object> status = new HashMap<>();
        service.insertOrUpdate(artis);
        status.put("message", "OK");
        return ResponseEntity.ok().body(status);
    }

    @PostMapping(path = "/datatable")
    public ResponseEntity<DataTableResponse<Artis>> getDataProvinsi(@RequestBody DataTableRequest dataTableRequest){
        return ResponseEntity.ok().body(service.datatables(dataTableRequest));
    }

    @PostMapping("/filesupload")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = null;
        Map<String, Object> pesan = new HashMap<>();
        try {
            String namaFile = service.saveFile(file);
            pesan.put("pesan", "uploaded the file successfully: " + namaFile);
            pesan.put("file", namaFile);
            return ResponseEntity.ok().body(pesan);
        } catch (Exception exception) {
            pesan.put("pesan", "cannot input file");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(pesan);
        }
    }

    @GetMapping(value = "/image/{id}")
    public ResponseEntity<InputStreamResource>getImage(@PathVariable("id") String id){
        try{
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(
                    new InputStreamResource( service.load(id).getInputStream() ));
        }catch(IOException ex){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }

}
