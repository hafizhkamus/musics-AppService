package com.albums.music.appMusicService.controller;


import com.albums.music.appMusicService.dataTables.DataTableRequest;
import com.albums.music.appMusicService.dataTables.DataTableResponse;
import com.albums.music.appMusicService.entity.LablesRekaman;
import com.albums.music.appMusicService.service.LablesRekamanService;
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
@RequestMapping(path = "/api/labels-rekaman")
public class LablesRekamanController {

    @Autowired
    private LablesRekamanService service;

    @GetMapping(path = "/list-labels")
    public ResponseEntity<List<LablesRekaman>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping(path = "/label/{id}")
    public ResponseEntity<LablesRekaman> getProvinsiDetails(@PathVariable("id") Integer id){
        Optional<LablesRekaman> prov = service.findById(id);
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
    public ResponseEntity<Map<String, Object>> saveProvinsiJson(@RequestBody LablesRekaman lablesRekaman){
        Map<String,Object> status = new HashMap<>();
        service.insertOrUpdate(lablesRekaman);
        status.put("message", "OK");
        return ResponseEntity.ok().body(status);
    }

    @PostMapping(path = "/datatable")
    public ResponseEntity<DataTableResponse<LablesRekaman>> getDataProvinsi(@RequestBody DataTableRequest dataTableRequest){
        return ResponseEntity.ok().body(service.datatables(dataTableRequest));
    }
}
