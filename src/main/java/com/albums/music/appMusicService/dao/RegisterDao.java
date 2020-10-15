package com.albums.music.appMusicService.dao;


import com.albums.music.appMusicService.dto.GroupUserDto;
import com.albums.music.appMusicService.entity.AkunAdmin;
import com.albums.music.appMusicService.entity.Albums;
import com.albums.music.appMusicService.entity.GroupUser;
import com.albums.music.appMusicService.entity.UserAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class RegisterDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate template;

    public void insert(Map<String, Object> param){
        String baseQuery = "insert into group_user(id,id_user, id_group) values (?,?,?)";
        Object parameter[] = {param.get("id"),param.get("idUser"), param.get("idGroup")};
        jdbcTemplate.update(baseQuery, parameter);
    }

    public void deleteById(GroupUserDto.Information value) throws DataAccessException {
        String baseQuery = "delete from group_user where id_user = :idUser and id_group = :idGroup";

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("idUser", value.getIdUser());
        parameterSource.addValue("idGroup", value.getIdGruop());

        template.update(baseQuery, parameterSource);
    }

    public boolean tambahRoleAdmin(AkunAdmin akunAdmin){
        String baseQuery = "select id, username from akun_admin where username = ?";
        boolean isTambah = false;
        try{
            Optional<AkunAdmin> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                AkunAdmin akunAdmin1 = new AkunAdmin();
                akunAdmin1.setId(rs.getString("id"));
                akunAdmin1.setUsername(rs.getString("user_name"));
                return akunAdmin1;
            },akunAdmin.getUsername()));
            if(Objects.equals(akunAdmin.getUsername(),hasil.get().getUsername())){
                Map<String, Object> param = new HashMap<>();
                param.put("id", UUID.randomUUID().toString());
                param.put("idUser", hasil.get().getId());
                param.put("idGroup", 2);
                insert(param);
                isTambah = true;
                return isTambah;
            } else {
                isTambah = false;
            }
        } catch (Exception e){
            e.printStackTrace();
            isTambah = false;
        }
        return isTambah;
    }

    public boolean tambahRoleUser(AkunAdmin akunAdmin){
        String baseQuery = "select id, username from akun_admin where username = ?";
        boolean isTambah = false;
        try{
            Optional<AkunAdmin> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                AkunAdmin akunAdmin1 = new AkunAdmin();
                akunAdmin1.setId(rs.getString("id"));
                akunAdmin1.setUsername(rs.getString("user_name"));
                return akunAdmin1;
            },akunAdmin.getUsername()));
            if(Objects.equals(akunAdmin.getUsername(),hasil.get().getUsername())){
                Map<String, Object> param = new HashMap<>();
                param.put("id", UUID.randomUUID().toString());
                param.put("idUser", hasil.get().getId());
                param.put("idGroup", 3);
                insert(param);
                isTambah = true;
                return isTambah;
            } else {
                isTambah = false;
            }
        } catch (Exception e){
            e.printStackTrace();
            isTambah = false;
        }
        return isTambah;
    }

    public boolean tambahRoleAdminPremium(AkunAdmin akunAdmin){
        String baseQuery = "select id, username from akun_admin where username = ?";
        boolean isTambah = false;
        try{
            Optional<AkunAdmin> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                AkunAdmin akunAdmin1 = new AkunAdmin();
                akunAdmin1.setId(rs.getString("id"));
                akunAdmin1.setUsername(rs.getString("user_name"));
                return akunAdmin1;
            },akunAdmin.getUsername()));
            if(Objects.equals(akunAdmin.getUsername(),hasil.get().getUsername())){
                Map<String, Object> param = new HashMap<>();
                param.put("id", UUID.randomUUID().toString());
                param.put("idUser", hasil.get().getId());
                param.put("idGroup", 4);
                insert(param);
                isTambah = true;
                return isTambah;
            } else {
                isTambah = false;
            }
        } catch (Exception e){
            e.printStackTrace();
            isTambah = false;
        }
        return isTambah;
    }


    public List<GroupUser> findAll() throws EmptyResultDataAccessException {
        String baseQuery= "select g.id as id, a.username as username, r.role_name as roleName " +
                "from group_user g join akun_admin a on (g.id_user = a.id) join roles r on (g.id_group = r.id_role)";

        MapSqlParameterSource params = new MapSqlParameterSource();

        return template.query(baseQuery, params, new RowMapper<GroupUser>() {
            @Override
            public GroupUser mapRow(ResultSet resultSet, int i) throws SQLException {
                GroupUser arsip = new GroupUser();
                arsip.setId(resultSet.getString("id"));
                arsip.setUsername(resultSet.getString("username"));
                arsip.setRoleName(resultSet.getString("roleName"));

                return arsip;
            }
        });
    }

    public GroupUser checkingSuperAdmin(String idUser){
        String baseQuery="select id , id_group as idGroup, id_user as idUser from group_user " +
                "where id_user = ? and id_group = 1";
        GroupUser groupUsers = new GroupUser();
        boolean isCheck = false;
        try{
            Optional<GroupUser> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                GroupUser groupUser = new GroupUser();
                groupUser.setId(rs.getString("id"));
                groupUser.setIdGroup(rs.getInt("idGroup"));
                groupUser.setIdUser(rs.getString("idUser"));
                return groupUser;
            },idUser));
            if (hasil != null){
                isCheck = true;
                groupUsers.setId(hasil.get().getId());
                groupUsers.setIdUser(hasil.get().getIdUser());
                groupUsers.setIsCheck(isCheck);
                return groupUsers;
            } else{
                isCheck = false;
                groupUsers.setIsCheck(isCheck);
            }
        } catch (Exception e){
            e.printStackTrace();
            isCheck = false;
            groupUsers.setIsCheck(isCheck);
        }
        return groupUsers;
    }

    public boolean checkingAdmin(String idUser){
        String baseQuery="select id , id_group as idGroup, id_user as idUser from group_user " +
                "where id_user = ? and id_group = 2";
        boolean isCheck = false;
        try{
            Optional<GroupUser> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                GroupUser groupUser = new GroupUser();
                groupUser.setId(rs.getString("id"));
                groupUser.setIdGroup(rs.getInt("idGroup"));
                groupUser.setIdUser(rs.getString("idUser"));
                return groupUser;
            },idUser));
            if (hasil != null){
                isCheck = true;
                return isCheck;
            } else{
                isCheck = false;
            }
        } catch (Exception e){
            e.printStackTrace();
            isCheck = false;
        }
        return isCheck;
    }

    public boolean checkingUser(String idUser){
        String baseQuery="select id , id_group as idGroup, id_user as idUser from group_user " +
                "where id_user = ? and id_group = 3";
        boolean isCheck = false;
        try{
            Optional<GroupUser> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                GroupUser groupUser = new GroupUser();
                groupUser.setId(rs.getString("id"));
                groupUser.setIdGroup(rs.getInt("idGroup"));
                groupUser.setIdUser(rs.getString("idUser"));
                return groupUser;
            },idUser));
            if (hasil.isPresent()){
                isCheck = true;
                return isCheck;
            } else{
                isCheck = false;
            }
        } catch (Exception e){
            e.printStackTrace();
            isCheck = false;
        }
        return isCheck;
    }

    public boolean checkingUserPremium(String idUser){
        String baseQuery="select id , id_group as idGroup, id_user as idUser from group_user " +
                "where id_user = ? and id_group = 4";
        boolean isCheck = false;
        try{
            Optional<GroupUser> hasil = Optional.of(jdbcTemplate.queryForObject(baseQuery, (rs, rownum) ->{
                GroupUser groupUser = new GroupUser();
                groupUser.setId(rs.getString("id"));
                groupUser.setIdGroup(rs.getInt("idGroup"));
                groupUser.setIdUser(rs.getString("idUser"));
                return groupUser;
            },idUser));
            if (hasil.isPresent()){
                isCheck = true;
                return isCheck;
            } else{
                isCheck = false;
            }
        } catch (Exception e){
            e.printStackTrace();
            isCheck = false;
        }
        return isCheck;
    }


}
