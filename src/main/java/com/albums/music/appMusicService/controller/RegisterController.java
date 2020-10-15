package com.albums.music.appMusicService.controller;


import com.albums.music.appMusicService.dao.AkunAdminDao;
import com.albums.music.appMusicService.dto.AkunAdminDto;
import com.albums.music.appMusicService.entity.AkunAdmin;
import com.albums.music.appMusicService.entity.Roles;
import com.albums.music.appMusicService.service.AkunAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/api/register")
public class RegisterController {

    @Autowired
    private AkunAdminService service;

    @Autowired
    private AkunAdminDao dao;

    @PostMapping(path = "/saveAdmin")
    public ResponseEntity<?> saveAdmin(@RequestBody AkunAdminDto.New newAkun){
        try{
            service.saveAdmin(newAkun);
            return new ResponseEntity<>(newAkun, HttpStatus.CREATED);
        } catch (SQLException e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<AkunAdmin>> findAll(){
        return ResponseEntity.ok().body(dao.findAll());
    }

    @GetMapping(path = "/allRole")
    public ResponseEntity<List<Roles>> findAllRoles(){
        return ResponseEntity.ok().body(dao.findAllRoles());
    }

    @PostMapping(path = "/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody AkunAdminDto.New newAkun){
        try{
            service.saveUser(newAkun);
            return new ResponseEntity<>(newAkun, HttpStatus.CREATED);
        } catch (SQLException e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/saveUserPremium")
    public ResponseEntity<?> saveUserPremium(@RequestBody AkunAdminDto.New newAkun){
        try{
            service.saveUserPremium(newAkun);
            return new ResponseEntity<>(newAkun, HttpStatus.CREATED);
        } catch (SQLException e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
