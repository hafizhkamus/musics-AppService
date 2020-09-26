package com.albums.music.appMusicService.dao;


import com.albums.music.appMusicService.dataTables.DataTableRequest;
import com.albums.music.appMusicService.entity.Artis;
import com.albums.music.appMusicService.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Genre> getAllGenre(){
        String baseQuery = "select id_genre as idGenre, nama_genre as namaGenre from genre";
        List<Genre> prop = jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Genre.class));
        return prop;
    }

    public Optional<Genre> getGenreById(int id) {
        String baseQuery = "select id_genre as idGenre, nama_genre as namaGenre from genre where " +
                "id_genre = ?";
        Object param[] = {id};
        try {
            return Optional.of(jdbcTemplate.queryForObject(baseQuery, param, BeanPropertyRowMapper.newInstance(Genre.class)));

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void insertGenre(Genre genre){
        String baseQuery = "insert into genre(id_genre, nama_genre) values (?,?);";
        Object parameters[] = {genre.getIdGenre(),genre.getNamaGenre()};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void updateGenre(Genre genre){
        String baseQuery = "update genre set nama_genre = ? where id_genre =?";
        Object parameters[] = {genre.getNamaGenre(),genre.getIdGenre()};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void deleteGenre(Integer id){
        String baseQuery = "delete from genre where id_genre =?";
        Object parameters[] = {id};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void insertOrUpdateGenre(Genre genre){
        Optional<Genre> genre1  = getGenreById(genre.getIdGenre());
        if (genre1.isPresent()){
            updateGenre(genre);
        } else {
            insertGenre(genre);
        }
    }

    public Integer getBanyakGenre(DataTableRequest req){
        String baseQuery = "select count(id_genre) as banyak from genre";
        if(!req.getExtraParam().isEmpty()){
            String namaGenre = (String) req.getExtraParam().get("nama_genre");
            baseQuery = "select count(id_genre) as banyak from genre where nama_genre like concat('%', ?, '%')";
            return jdbcTemplate.queryForObject(baseQuery, Integer.class, namaGenre);
        } else {
            return jdbcTemplate.queryForObject(baseQuery, null, Integer.class);
        }
    }

    public List<Artis> getAllGenreJson(DataTableRequest request){
        String baseQuery = "select id_genre as idGenre, nama_genre as namaGenre keterangan from genre "
                + "order by "+(request.getSortCol()+1)+" "+request.getSortDir()+" limit ? offset ? ";
        if(!request.getExtraParam().isEmpty()){
            String namaGenre = (String) request.getExtraParam().get("nama_genre");
            baseQuery = "select id_genre as idGenre, nama_genre as namaGenre from genre where nama_genre like concat('%', ?, '%') "
                    + "order by "+(request.getSortCol()+1)+" "+request.getSortDir()+" limit ? offset ? ";
            return jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Artis.class),namaGenre,
                    request.getLength(), request.getStart());
        } else {
            return jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Artis.class),
                    request.getLength(), request.getStart());
        }

    }


}
