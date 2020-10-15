package com.albums.music.appMusicService.service;


import com.albums.music.appMusicService.dao.AkunAdminDao;
import com.albums.music.appMusicService.dao.RegisterDao;
import com.albums.music.appMusicService.dto.AkunAdminDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AkunAdminService {

    @Autowired
    private AkunAdminDao dao;

    @Autowired
    private RegisterDao register;

    public int saveAdmin(AkunAdminDto.New newAkun) throws SQLException{
        String randomId = UUID.randomUUID().toString();
        newAkun.setId(randomId);
        Map<String, Object> paramRegAcc = new HashMap<>();
        paramRegAcc.put("id", UUID.randomUUID().toString());
        paramRegAcc.put("idUser", newAkun.getId());
        paramRegAcc.put("idGroup", 2);
        register.insert(paramRegAcc);
        return dao.save(newAkun);
    }

    public int saveUser(AkunAdminDto.New newAkun) throws SQLException{
        String randomId = UUID.randomUUID().toString();
        newAkun.setId(randomId);
        Map<String, Object> paramRegAcc = new HashMap<>();
        paramRegAcc.put("id", UUID.randomUUID().toString());
        paramRegAcc.put("idUser", newAkun.getId());
        paramRegAcc.put("idGroup", 3);
        register.insert(paramRegAcc);
        return dao.save(newAkun);
    }

    public int saveUserPremium(AkunAdminDto.New newAkun) throws SQLException{
        String randomId = UUID.randomUUID().toString();
        newAkun.setId(randomId);
        Map<String, Object> paramRegAcc = new HashMap<>();
        paramRegAcc.put("id", UUID.randomUUID().toString());
        paramRegAcc.put("idUser", newAkun.getId());
        paramRegAcc.put("idGroup", 4);
        register.insert(paramRegAcc);
        return dao.save(newAkun);
    }

}
