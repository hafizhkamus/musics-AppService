package com.albums.music.appMusicService.dao;


import com.albums.music.appMusicService.dto.AkunAdminDto;
import com.albums.music.appMusicService.dto.UserAdminDto;
import com.albums.music.appMusicService.entity.*;
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

    public List<AkunAdminDto.Information> getAllUser(){
        String baseQuery= "select username , keyword, group_id as groupId from akun_admin a join  roles r join (a.group_id = r.role_id) " +
                "where a.username like concat('%', ?, '%')";
        return jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(AkunAdminDto.Information.class));
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

    public List<String> getRolesByUserName(String userName){
        String query = "select r.role_name from group_user g join roles r on (g.id_group = r.id_role) " +
                " join akun_admin a on (g.id_user = a.id) where a.username = ?";

        Object param[] = {userName};

        List<String> prop = jdbcTemplate.query(query, (rs, rownum) ->{
            return rs.getString("role_name");
        }, param);

        return prop;
    }

    public boolean cekLoginValid(UserAdmin user){
        String baseQuery = "select u.user_name from user_admin u join akun_admin a on (u.user_name = a.username) where tokenkey = ?";
        StatusLogin state = new StatusLogin();
        try{
            boolean isValid = false;
            Optional<UserAdmin> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                UserAdmin use = new UserAdmin();
                use.setUserName(rs.getString("user_name"));
                return use;
            },user.getTokenKey()));
            if (hasil.isPresent()){
                if (Objects.equals(user.getUserName(), hasil.get().getTokenKey())){
                    List<String> roleName = getRolesByUserName(hasil.get().getUserName());
                    if (roleName != null){
                        state.setIsValid(true);
                        state.setRole(roleName);
                        state.setToken(user.getTokenKey());
                        return state.getIsValid();
                    }
                } else{
                    state.setIsValid(false);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            state.setIsValid(false);
        }
        return state.getIsValid();
    }

    public void insertUserLogin(Map<String, Object>param){
        String SQL = "insert into user_admin (user_name, tokenkey) values (?,?)";
        Object parameter[] = {param.get("username"), param.get("token")};
        jdbcTemplate.update(SQL, parameter);
    }

}
