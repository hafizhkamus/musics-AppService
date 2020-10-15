package com.albums.music.appMusicService.service;


import com.albums.music.appMusicService.dao.RegisterDao;
import com.albums.music.appMusicService.dto.GroupUserDto;
import com.albums.music.appMusicService.entity.GroupUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupUserService {

    @Autowired
    private RegisterDao dao;

    public List<GroupUser> findAll() throws EmptyResultDataAccessException{
        return dao.findAll();
    }

    public void deleteById(GroupUserDto.Information value) throws DataAccessException {
        dao.deleteById(value);
    }
}
