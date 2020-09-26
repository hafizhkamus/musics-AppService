package com.albums.music.appMusicService.dao;


import com.albums.music.appMusicService.dataTables.DataTableRequest;
import com.albums.music.appMusicService.entity.Artis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArtisDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Artis> getAllArtis(){
        String baseQuery = "select id_artis as idArtis, nama_artis as namaArtis, foto, url_website as url, keterangan from artis";
        List<Artis> prop = jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Artis.class));
        return prop;
    }

    public Optional<Artis> getArtisByID(int id) {
        String baseQuery = "select id_artis as idArtis, nama_artis as namaArtis, foto, url_website as url, keterangan from artis where " +
                "id_artis = ?";
        Object param[] = {id};
        try {
            return Optional.of(jdbcTemplate.queryForObject(baseQuery, param, BeanPropertyRowMapper.newInstance(Artis.class)));

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void insertArtis(Artis artis){
        String baseQuery = "insert into artis (id_artis, nama_artis, foto, url_website, keterangan) values (?, ?, ?, ?, ?)";
        Object parameters[] = {artis.getIdArtis(),artis.getNamaArtis(),artis.getFoto(),artis.getUrl(),artis.getKeterangan()};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void updateArtis(Artis artis){
        String baseQuery = "update artis set nama_artis = ?, keterangan = ?, url_website = ?  where id_artis =?";
        Object parameters[] = {artis.getNamaArtis(),artis.getKeterangan(),artis.getUrl(),artis.getIdArtis() };

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void deleteArtis(Integer id){
        String baseQuery = "delete from artis where id_artis =?";
        Object parameters[] = {id};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void insertOrUpdateArtis(Artis artis){
        Optional<Artis> artis1  = getArtisByID(artis.getIdArtis());
        if (artis1.isPresent()){
            updateArtis(artis);
        } else {
            insertArtis(artis);
        }
    }

    public Integer getBanyakArtis(DataTableRequest req){
        String baseQuery = "select count(id_artis) as banyak from artis";
        if(!req.getExtraParam().isEmpty()){
            String namaArtis = (String) req.getExtraParam().get("nama_artis");
            baseQuery = "select count(id_artis) as banyak from artis where nama_artis like concat('%', ?, '%')";
            return jdbcTemplate.queryForObject(baseQuery, Integer.class, namaArtis);
        } else {
            return jdbcTemplate.queryForObject(baseQuery, null, Integer.class);
        }
    }

    public List<Artis> getAllArtisJson(DataTableRequest request){
        String baseQuery = "select id_artis as idArtis, nama_artis as namaArtis, url_website as url, keterangan from artis "
                + "order by "+(request.getSortCol()+1)+" "+request.getSortDir()+" limit ? offset ? ";
        if(!request.getExtraParam().isEmpty()){
            String namaArtis = (String) request.getExtraParam().get("nama_artis");
            baseQuery = "select id_artis as idArtis, nama_artis as namaArtis, url_website as url, keterangan from artis where nama_artis like concat('%', ?, '%') "
                    + "order by "+(request.getSortCol()+1)+" "+request.getSortDir()+" limit ? offset ? ";
            return jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Artis.class),namaArtis,
                    request.getLength(), request.getStart());
        } else {
            return jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Artis.class),
                    request.getLength(), request.getStart());
        }

    }


}
