package com.albums.music.appMusicService.dao;


import com.albums.music.appMusicService.dto.AkunAdminDto;
import com.albums.music.appMusicService.entity.AkunAdmin;
import com.albums.music.appMusicService.entity.Albums;
import com.albums.music.appMusicService.entity.Artis;
import com.albums.music.appMusicService.entity.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class AkunAdminDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private JdbcTemplate template;

    public int save(AkunAdminDto.New value) throws SQLException {
        String basequery = "insert into akun_admin (id, username, keyword)" +
                " values (:id,:username, :keyword)";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", value.getId());
        parameterSource.addValue("username", value.getUsername());
        parameterSource.addValue("keyword", value.getKeyword());

        return jdbcTemplate.update(basequery, parameterSource);
    }

    public List<AkunAdmin> findAll() throws EmptyResultDataAccessException {
        String baseQuery = "select id as id, username as username, keyword as keyword from akun_admin";
        List<AkunAdmin> prop = jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(AkunAdmin.class));
        return prop;
    }

    public List<AkunAdmin> getAkunById(String id) throws EmptyResultDataAccessException {
        String baseQuery = "select id as id, username as username from akun_admin where id = ?";
        Object[] param = {id};

        return template.query(baseQuery, param,  (rs, rowNUm) -> {
            AkunAdmin akunAdmin = new AkunAdmin();
            akunAdmin.setUsername(rs.getString("username"));
            akunAdmin.setId(rs.getString("id"));
            return akunAdmin;
        });
    }


    public List<Roles> findAllRoles() throws EmptyResultDataAccessException{
        String baseQuery = "select role_name as roleName from roles";
        List<Roles> prop = jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Roles.class));
        return prop;
    }
}
