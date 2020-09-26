package com.albums.music.appMusicService.controller;


import com.albums.music.appMusicService.dataTables.DataTableRequest;
import com.albums.music.appMusicService.dataTables.DataTableResponse;
import com.albums.music.appMusicService.entity.Albums;
import com.albums.music.appMusicService.entity.Artis;
import com.albums.music.appMusicService.service.AlbumsService;
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
@RequestMapping(path = "/api/albums")
public class AlbumsController {

    @Autowired
    private AlbumsService service;

    @GetMapping(path = "/list-albums")
    public ResponseEntity<List<Albums>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(path = "/albums/{id}")
    public ResponseEntity<Albums> getProvinsiDetails(@PathVariable("id") Integer id){
        Optional<Albums> prov = service.findById(id);
        if(prov.isPresent()){
            return ResponseEntity.ok().body(prov.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping(path = "/list-albums/artis/{id}")
    public ResponseEntity<List<Albums>> findByArtis(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(service.findByArtis(id));
    }

    @GetMapping(path = "/list-albums/labels/{idLabel}")
    public ResponseEntity<List<Albums>> findByLabels(@PathVariable("idLabel") Integer idLabel){
        return ResponseEntity.ok().body(service.findByLabels(idLabel));
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
    public ResponseEntity<Map<String, Object>> saveProvinsiJson(@RequestBody Albums albums){
        Map<String,Object> status = new HashMap<>();
        service.insertOrUpdate(albums);
        status.put("message", "OK");
        return ResponseEntity.ok().body(status);
    }

    @PostMapping(path = "/datatable")
    public ResponseEntity<DataTableResponse<Albums>> getDataProvinsi(@RequestBody DataTableRequest dataTableRequest){
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
