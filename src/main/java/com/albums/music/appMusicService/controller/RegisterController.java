package com.albums.music.appMusicService.controller;


import com.albums.music.appMusicService.dto.AkunAdminDto;
import com.albums.music.appMusicService.service.AkunAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;

@Controller
@RequestMapping(path = "/api/register")
public class RegisterController {

    @Autowired
    private AkunAdminService service;

    @PostMapping
    public ResponseEntity<?> saveAdmin(@RequestBody AkunAdminDto.New newAkun){
        try{
            service.saveAdmin(newAkun);
            return new ResponseEntity<>(newAkun, HttpStatus.CREATED);
        } catch (SQLException e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody AkunAdminDto.New newAkun){
        try{
            service.saveUser(newAkun);
            return new ResponseEntity<>(newAkun, HttpStatus.CREATED);
        } catch (SQLException e){
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
