package com.albums.music.appMusicService.controller;


import com.albums.music.appMusicService.dataTables.DataTableRequest;
import com.albums.music.appMusicService.dataTables.DataTableResponse;
import com.albums.music.appMusicService.entity.Lagu;
import com.albums.music.appMusicService.service.LaguService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api/lagu")
public class LaguController {

    @Autowired
    private LaguService service;

    @GetMapping(path = "/list-lagu")
    public ResponseEntity<List<Lagu>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(path = "/lagu/{id}")
    public ResponseEntity<Lagu> getProvinsiDetails(@PathVariable("id") Integer id){
        Optional<Lagu> prov = service.findById(id);
        if(prov.isPresent()){
            return ResponseEntity.ok().body(prov.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(path = "/list-lagu/artis/{id}")
    public ResponseEntity<List<Lagu>> findByArtis(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(service.findByArtis(id));
    }

    @GetMapping(path = "/list-lagu/genre/{idGenre}")
    public ResponseEntity<List<Lagu>> findByGenre(@PathVariable("idGenre") Integer idGenre){
        return ResponseEntity.ok().body(service.findByGenre(idGenre));
    }

    @GetMapping(path = "/list-lagu/albums/{idAlbum}")
    public ResponseEntity<List<Lagu>> findByAlbums(@PathVariable("idAlbum") Integer idAlbum){
        return ResponseEntity.ok().body(service.findByAlbums(idAlbum));
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
    public ResponseEntity<Map<String, Object>> saveProvinsiJson(@RequestBody Lagu lagu){
        Map<String,Object> status = new HashMap<>();
        service.insertOrUpdate(lagu);
        status.put("message", "OK");
        return ResponseEntity.ok().body(status);
    }

    @PostMapping(path = "/datatable")
    public ResponseEntity<DataTableResponse<Lagu>> getDataProvinsi(@RequestBody DataTableRequest dataTableRequest){
        return ResponseEntity.ok().body(service.datatables(dataTableRequest));
    }

    @PostMapping("/filesupload/albums")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = null;
        Map<String, Object> pesan = new HashMap<>();
        try {
            String namaFile = service.saveFile(file);
            pesan.put("pesan", "uploaded the file successfully: " + namaFile);
            return ResponseEntity.ok().body(pesan);
        } catch (Exception exception) {
            pesan.put("pesan", "cannot input file");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(pesan);
        }
    }
}
