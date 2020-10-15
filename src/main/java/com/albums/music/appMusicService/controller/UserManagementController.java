package com.albums.music.appMusicService.controller;

import com.albums.music.appMusicService.dao.AkunAdminDao;
import com.albums.music.appMusicService.dao.RegisterDao;
import com.albums.music.appMusicService.dto.GroupUserDto;
import com.albums.music.appMusicService.entity.AkunAdmin;
import com.albums.music.appMusicService.entity.Albums;
import com.albums.music.appMusicService.entity.GroupUser;
import com.albums.music.appMusicService.service.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api/user-info")
public class UserManagementController {

    @Autowired
    private GroupUserService service;

    @Autowired
    private RegisterDao dao;

    @Autowired
    private AkunAdminDao accDao;


    // findAll group users
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        try {
            List<GroupUser> groupUsers = service.findAll();
            return new ResponseEntity<>(groupUsers, HttpStatus.OK);

        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/findById/{id}")
    public ResponseEntity<List<AkunAdmin>> findByLabels(@PathVariable("id") String id){
        return ResponseEntity.ok().body(accDao.getAkunById(id));
    }



    // tambah role

    @PostMapping("/tambah-role-admin")
    public ResponseEntity<Boolean> tambahRoleAdmin(@RequestBody AkunAdmin akunAdmin){
        return ResponseEntity.ok().body(dao.tambahRoleAdmin(akunAdmin));
    }

    @PostMapping("/tambah-role-user")
    public ResponseEntity<Boolean> tambahRoleUser(@RequestBody AkunAdmin akunAdmin){
        return ResponseEntity.ok().body(dao.tambahRoleUser(akunAdmin));
    }

    @PostMapping("/tambah-role-premium")
    public ResponseEntity<Boolean> tambahRolePremium(@RequestBody AkunAdmin akunAdmin){
        return ResponseEntity.ok().body(dao.tambahRoleAdminPremium(akunAdmin));
    }





    //delete role
    @DeleteMapping("/delete-role")
    public ResponseEntity<?> delete(@RequestBody GroupUserDto.Information value){
        try{
            service.deleteById(value);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }






    //checking role
    @PostMapping(path = "/checking-sa")
    public ResponseEntity<GroupUser> checkingSuperAdmin(@RequestBody String idUser){
        return ResponseEntity.ok().body(dao.checkingSuperAdmin(idUser));
    }

    @PostMapping(path = "/checking-admin")
    public ResponseEntity<Boolean> checkAdmin(@RequestBody String idUser){
        return ResponseEntity.ok().body(dao.checkingAdmin(idUser));
    }

    @PostMapping(path = "/checking-user")
    public ResponseEntity<Boolean> checkUser(@RequestBody String idUser){
        return ResponseEntity.ok().body(dao.checkingUser(idUser));
    }

    @PostMapping(path = "/checking-up")
    public ResponseEntity<Boolean> checkUserPremium(@RequestBody String idUser){
        return ResponseEntity.ok().body(dao.checkingUserPremium(idUser));
    }


}

