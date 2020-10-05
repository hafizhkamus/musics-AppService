package com.albums.music.appMusicService.dao;


import com.albums.music.appMusicService.dto.UserAdminDto;
import com.albums.music.appMusicService.entity.AkunAdmin;
import com.albums.music.appMusicService.entity.Lagu;
import com.albums.music.appMusicService.entity.UserAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserAdminDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<UserAdmin> getAllUser(){
        String baseQuery= " select user_name as userName, user_password as userPassword from user_admin";
        return jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(UserAdmin.class));
    }

    public List<UserAdminDto.Username> getUserName(){
        String baseQuery= " select user_name as userName from user_admin";
        return jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(UserAdminDto.Username.class));
    }

    public List<UserAdminDto.Password> getPassword(){
        String baseQuery= " select user_password as userPassword from user_admin";
        return jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(UserAdminDto.Password.class));
    }

    public Optional<AkunAdmin> getUserAdminById(String userAdmin) {
        String SQL = "select username, keyword from akun_admin where username = ? ";
        try {
            return Optional.of(jdbcTemplate.queryForObject(SQL, (rs, rownum) -> {
                AkunAdmin kab = new AkunAdmin();
                kab.setUsername(rs.getString("username"));
                kab.setKeyword(rs.getString("keyword"));
                return kab;
            }, userAdmin));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public boolean cekLoginValid(UserAdmin user){
        String baseQuery = "select user_name from user_admin where tokenkey = ?";

        try{
            Optional<UserAdmin> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                UserAdmin use = new UserAdmin();
                use.setUserName(rs.getString("user_name"));
                return use;
            },user.getTokenKey()));
                if (hasil.isPresent()){
                    if (Objects.equals(user.getUserName(), hasil.get().getTokenKey())){
                        return true;
                    } else{
                        return false;
                    }
                }
            }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void insertUserLogin(Map<String, Object>param){
        String SQL = "insert into user_admin (user_name, tokenkey) values (?,?)";
        Object parameter[] = {param.get("username"), param.get("token")};
        jdbcTemplate.update(SQL, parameter);
    }

}
