package com.albums.music.appMusicService.dao;


import com.albums.music.appMusicService.dto.AkunAdminDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class AkunAdminDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public int save(AkunAdminDto.New value) throws SQLException {
        String basequery = "insert into akun_admin (username, keyword, group_id, tgl_register)" +
                " values (:username, :keyword, :group_id, :tglRegister)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("username", value.getUsername());
        parameterSource.addValue("keyword", value.getKeyword());
        parameterSource.addValue("group_id", value.getGroupId());
        parameterSource.addValue("tglRegister", value.getTglRegister());

        return jdbcTemplate.update(basequery, parameterSource);
    }
}
