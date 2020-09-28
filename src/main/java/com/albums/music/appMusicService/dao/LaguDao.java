package com.albums.music.appMusicService.dao;


import com.albums.music.appMusicService.dataTables.DataTableRequest;
import com.albums.music.appMusicService.entity.Albums;
import com.albums.music.appMusicService.entity.Lagu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LaguDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Lagu> getAllLagu(){
        String baseQuery = "select la.id_lagu as idLagu, la.judul, la.durasi, " +
                " al.id_album as idAlbum, ar.id_artis as idArtis, ge.id_genre as idGenre, ar.nama_artis as namaArtis, " +
                "al.nama_albums as namaAlbums, ge.nama_genre as namaGenre " +
                " from lagu la join artis ar on" +
                " la.id_artis = ar.id_artis join genre ge on la.id_genre = ge.id_genre " +
                " join albums al on la.id_album = al.id_album";
        List<Lagu> prop = jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Lagu.class));
        return prop;
    }

    public Optional<Lagu> getLaguById(int id) {
        String baseQuery = "select la.id_lagu as idLagu, la.judul, la.durasi, " +
                " al.id_album as idAlbum, ar.id_artis as idArtis, ge.id_genre as idGenre, ar.nama_artis as namaArtis, " +
                "al.nama_albums as namaAlbums, ge.nama_genre as namaGenre " +
                " from lagu la join artis ar on" +
                " la.id_artis = ar.id_artis join genre ge on la.id_genre = ge.id_genre " +
                " join albums al on la.id_album = al.id_album where la.id_lagu = ?";
        Object param[] = {id};
        try {
            return Optional.of(jdbcTemplate.queryForObject(baseQuery, param, BeanPropertyRowMapper.newInstance(Lagu.class)));

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Lagu> getLaguByArtis(int idArtis) {

        String baseQuery = "select la.id_lagu as idLagu, la.judul, la.durasi, " +
                " al.id_album as idAlbum, ar.id_artis as idArtis, ge.id_genre as idGenre, ar.nama_artis as namaArtis, " +
                "al.nama_albums as namaAlbums, ge.nama_genre as namaGenre " +
                " from lagu la join artis ar on" +
                " la.id_artis = ar.id_artis join genre ge on la.id_genre = ge.id_genre " +
                " join albums al on la.id_album = al.id_album where la.id_artis = ?";

        Object[] param = {idArtis};

        return jdbcTemplate.query(baseQuery, param, (rs, rowNUm) -> {
            Lagu albums = new Lagu();
            albums.setIdLagu(rs.getInt("idLagu"));
            albums.setJudul(rs.getString("judul"));
            albums.setDurasi(rs.getString("durasi"));
            albums.setIdAlbum(rs.getInt("idAlbum"));
            albums.setNamaAlbums(rs.getString("namaAlbums"));
            albums.setIdGenre(rs.getInt("idGenre"));
            albums.setIdArtis(rs.getInt("idArtis"));
            albums.setNamaGenre(rs.getString("namaGenre"));
            albums.setNamaArtis(rs.getString("namaArtis"));
            return albums;
        });
    }

    public List<Lagu> getLaguByGenre(int idGenre) {
        String baseQuery = "select la.id_lagu as idLagu, la.judul, la.durasi, " +
                " al.id_album as idAlbum, ar.id_artis as idArtis, ge.id_genre as idGenre, ar.nama_artis as namaArtis, " +
                "al.nama_albums as namaAlbums, ge.nama_genre as namaGenre " +
                " from lagu la join artis ar on" +
                " la.id_artis = ar.id_artis join genre ge on la.id_genre = ge.id_genre " +
                " join albums al on la.id_album = al.id_album where la.id_genre = ?";

        Object[] param = {idGenre};

            return jdbcTemplate.query(baseQuery, param, (rs, rowNUm) -> {
            Lagu albums = new Lagu();
            albums.setIdLagu(rs.getInt("idLagu"));
            albums.setJudul(rs.getString("judul"));
            albums.setDurasi(rs.getString("durasi"));
            albums.setIdAlbum(rs.getInt("idAlbum"));
            albums.setNamaAlbums(rs.getString("namaAlbums"));
            albums.setIdGenre(rs.getInt("idGenre"));
            albums.setIdArtis(rs.getInt("idArtis"));
            albums.setNamaGenre(rs.getString("namaGenre"));
            albums.setNamaArtis(rs.getString("namaArtis"));
            return albums;
        });
    }

    public List<Lagu> getLaguByAlbums(int idAlbum) {
        String baseQuery = "select la.id_lagu as idLagu, la.judul, la.durasi, " +
                " al.id_album as idAlbum, ar.id_artis as idArtis, ge.id_genre as idGenre, ar.nama_artis as namaArtis, " +
                "al.nama_albums as namaAlbums, ge.nama_genre as namaGenre " +
                " from lagu la join artis ar on" +
                " la.id_artis = ar.id_artis join genre ge on la.id_genre = ge.id_genre " +
                " join albums al on la.id_album = al.id_album where la.id_album = ?";

        Object[] param = {idAlbum};

        return jdbcTemplate.query(baseQuery, param, (rs, rowNUm) -> {
            Lagu albums = new Lagu();
            albums.setIdLagu(rs.getInt("idLagu"));
            albums.setJudul(rs.getString("judul"));
            albums.setDurasi(rs.getString("durasi"));
            albums.setIdAlbum(rs.getInt("idAlbum"));
            albums.setNamaAlbums(rs.getString("namaAlbums"));
            albums.setIdGenre(rs.getInt("idGenre"));
            albums.setIdArtis(rs.getInt("idArtis"));
            albums.setNamaGenre(rs.getString("namaGenre"));
            albums.setNamaArtis(rs.getString("namaArtis"));
            return albums;
        });
    }

    public void insertLagu(Lagu lagu){
        String baseQuery = "insert into lagu (id_lagu, judul, durasi, id_album, id_artis, id_genre) values (?, ?, ?, ?, ?,?)";
        Object parameters[] = { lagu.getIdLagu(),lagu.getJudul(), lagu.getDurasi(), lagu.getIdAlbum(),lagu.getIdArtis(),lagu.getIdGenre()};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void updateLagu(Lagu lagu){
        String baseQuery = "update lagu set judul = ?, durasi = ?, id_album = ?, id_artis = ?, id_genre = ? where id_lagu =?";
        Object parameters[] = {lagu.getJudul(), lagu.getDurasi(), lagu.getIdAlbum(),lagu.getIdArtis(),lagu.getIdGenre(),lagu.getIdLagu() };

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void deleteLagu(Integer id){
        String baseQuery = "delete from lagu where id_lagu =?";
        Object parameters[] = {id};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void insertOrUpdateLagu(Lagu lagu){
        Optional<Lagu> lagu1  = getLaguById(lagu.getIdLagu());
        if (lagu1.isPresent()){
            updateLagu(lagu);
        } else {
            insertLagu(lagu);
        }
    }

    public Integer getBanyakLagu(DataTableRequest req){
        String baseQuery = "select count(id_lagu) as banyak from lagu";
        if(!req.getExtraParam().isEmpty()){
            String namaLagu = (String) req.getExtraParam().get("judul");
            baseQuery = "select count(id_lagu) as banyak from lagu where judul like concat('%', ?, '%')";
            return jdbcTemplate.queryForObject(baseQuery, Integer.class, namaLagu);
        } else {
            return jdbcTemplate.queryForObject(baseQuery, null, Integer.class);
        }
    }

    public List<Lagu> getAllLaguRec(DataTableRequest request){
        String baseQuery = "select la.id_lagu as idLagu, la.judul, la.durasi, " +
                "al.id_album as idAlbum, ar.id_artis as idArtis, ge.id_genre as idGenre, ar.nama_artis as namaArtis, " +
                "al.nama_albums as namaAlbums, ge.nama_genre as namaGenre " +
                "from lagu la join artis ar on " +
                "la.id_artis = ar.id_artis join genre ge on la.id_genre = ge.id_genre " +
                "join albums al on la.id_album = al.id_album " +
                "order by "+(request.getSortCol()+1)+" "+request.getSortDir()+" limit ? offset ? ";
        if(!request.getExtraParam().isEmpty()){
            String namaLagu = (String) request.getExtraParam().get("judul");
            baseQuery = "select la.id_lagu as idLagu, la.judul, la.durasi, " +
                    "al.id_album as idAlbum, ar.id_artis as idArtis, ge.id_genre as idGenre, ar.nama_artis as namaArtis, " +
                    "al.nama_albums as namaAlbums, ge.nama_genre as namaGenre " +
                    "from lagu la join artis ar on " +
                    "la.id_artis = ar.id_artis join genre ge on la.id_genre = ge.id_genre " +
                    "join albums al on la.id_album = al.id_album where judul = ?" +
                    "order by "+(request.getSortCol()+1)+" "+request.getSortDir()+" limit ? offset ? ";
            return jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Lagu.class),namaLagu,
                    request.getLength(), request.getStart());
        } else {
            return jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Lagu.class),
                    request.getLength(), request.getStart());
        }

    }
}
