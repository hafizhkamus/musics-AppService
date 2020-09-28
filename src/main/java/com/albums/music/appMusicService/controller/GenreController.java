package com.albums.music.appMusicService.controller;


import com.albums.music.appMusicService.dataTables.DataTableRequest;
import com.albums.music.appMusicService.dataTables.DataTableResponse;
import com.albums.music.appMusicService.entity.Artis;
import com.albums.music.appMusicService.entity.Genre;
import com.albums.music.appMusicService.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api/genre")
public class GenreController {

    @Autowired
    private GenreService service;

    @GetMapping(path = "/list-genre")
    public ResponseEntity<List<Genre>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(path = "/genre/{id}")
    public ResponseEntity<Genre> getProvinsiDetails(@PathVariable("id") Integer id){
        Optional<Genre> prov = service.findById(id);
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
    public ResponseEntity<Map<String, Object>> saveProvinsiJson(@RequestBody Genre genre){
        Map<String,Object> status = new HashMap<>();
        service.insertOrUpdate(genre);
        status.put("message", "OK");
        return ResponseEntity.ok().body(status);
    }

    @PostMapping(path = "/datatable")
    public ResponseEntity<DataTableResponse<Genre>> getDataProvinsi(@RequestBody DataTableRequest dataTableRequest){
        return ResponseEntity.ok().body(service.datatables(dataTableRequest));
    }
}
