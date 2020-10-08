package com.albums.music.appMusicService.service;


import com.albums.music.appMusicService.dao.AkunAdminDao;
import com.albums.music.appMusicService.dto.AkunAdminDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class AkunAdminService {

    @Autowired
    private AkunAdminDao dao;

    public int saveAdmin(AkunAdminDto.New newAkun) throws SQLException{
        newAkun.setTglRegister(Timestamp.valueOf(LocalDateTime.now()));
        newAkun.setGroupId(1);
        return dao.save(newAkun);
    }

    public int saveUser(AkunAdminDto.New newAkun) throws SQLException{
        newAkun.setTglRegister(Timestamp.valueOf(LocalDateTime.now()));
        newAkun.setGroupId(2);
        return dao.save(newAkun);
    }

}
