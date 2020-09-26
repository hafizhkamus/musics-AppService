package com.albums.music.appMusicService.dao;


import com.albums.music.appMusicService.dataTables.DataTableRequest;
import com.albums.music.appMusicService.entity.Albums;
import com.albums.music.appMusicService.entity.Artis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AlbumsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Albums> getAllAlbums(){
        String baseQuery = "select al.id_album as idAlbum, al.nama_albums as namaAlbums," +
                " al.id_labels as idLabel, al.id_artis as idArtis, al.keterangan, ar.nama_artis as namaArtis, la.nama_labels as namaLabels from Albums al join Artis ar on " +
                "al.id_artis = ar.id_artis join lables_rekaman la on al.id_labels = la.id_label ";
        List<Albums> prop = jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Albums.class));
        return prop;
    }

    public Optional<Albums> getAlbumsById(int id) {
        String baseQuery = "select al.id_album as idAlbum, al.nama_albums as namaAlbums," +
                " al.id_labels as idLabel, al.id_artis as idArtis, al.keterangan, ar.nama_artis as namaArtis from Albums al join Artis ar on " +
                "al.id_artis = ar.id_artis " +
                "where al.id_albums = ?";
        Object param[] = {id};
        try {
            return Optional.of(jdbcTemplate.queryForObject(baseQuery, param, BeanPropertyRowMapper.newInstance(Albums.class)));

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Albums> getAlbumsByArtist(int id) {

        String baseQuery = "select al.id_album as idAlbum, al.nama_albums as namaAlbums," +
                " al.id_labels as idLabel, al.id_artis as idArtis, al.keterangan, ar.nama_artis as namaArtis from Albums al join Artis ar on " +
                "al.id_artis = ar.id_artis where al.id_artis = ? ";

        Object[] param = {id};

        return jdbcTemplate.query(baseQuery, param, (rs, rowNUm) -> {
            Albums albums = new Albums();
            albums.setIdAlbum(rs.getInt("idAlbum"));
            albums.setNamaAlbums(rs.getString("namaAlbums"));
            albums.setIdlabel(rs.getInt("idLabel"));
            albums.setIdArtis(rs.getInt("idArtis"));
            albums.setKeterangan(rs.getString("keterangan"));
            albums.setNamaArtis(rs.getString("namaArtis"));
            return albums;
        });
    }

    public List<Albums> getAlbumsByLabels(int idLabel) {

        String baseQuery = "select al.id_album as idAlbum, al.nama_albums as namaAlbums," +
                " al.id_labels as idLabel, al.id_artis as idArtis, al.keterangan, ar.nama_labels as namaLabels from Albums al join lables_rekaman ar on " +
                "al.id_labels = ar.id_label where al.id_labels = ? ";

        Object[] param = {idLabel};

        return jdbcTemplate.query(baseQuery, param, (rs, rowNUm) -> {
            Albums albums = new Albums();
            albums.setIdAlbum(rs.getInt("idAlbum"));
            albums.setNamaAlbums(rs.getString("namaAlbums"));
            albums.setIdlabel(rs.getInt("idLabel"));
            albums.setIdArtis(rs.getInt("idArtis"));
            albums.setKeterangan(rs.getString("keterangan"));
            albums.setNamaLabels(rs.getString("namaLabels"));
            return albums;
        });
    }

    public void insertAlbums(Albums albums){
        String baseQuery = "insert into albums (id_album, nama_albums, id_labels, id_artis, keterangan) values (?, ?, ?, ?, ?)";
        Object parameters[] = { albums.getIdAlbum(),albums.getNamaAlbums(),albums.getIdAlbum(),albums.getIdArtis(), albums.getKeterangan()};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void updateAlbums(Albums albums){
        String baseQuery = "update albums set nama_albums = ?, id_labels = ?, id_artis = ?, keterangan = ? where id_album =?";
        Object parameters[] = {albums.getNamaAlbums(),albums.getIdAlbum(),albums.getIdArtis(), albums.getKeterangan(), albums.getIdAlbum() };

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void deleteAlbums(Integer id){
        String baseQuery = "delete from albums where id_album =?";
        Object parameters[] = {id};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void insertOrUpdateAlbums(Albums albums){
        Optional<Albums> albums1  = getAlbumsById(albums.getIdAlbum());
        if (albums1.isPresent()){
            updateAlbums(albums);
        } else {
            insertAlbums(albums);
        }
    }

    public Integer getBanyakAlbums(DataTableRequest req){
        String baseQuery = "select count(id_album) as banyak from albums";
        if(!req.getExtraParam().isEmpty()){
            String namaAlbums = (String) req.getExtraParam().get("nama_albums");
            baseQuery = "select count(id_album) as banyak from albums where nama_albums like concat('%', ?, '%')";
            return jdbcTemplate.queryForObject(baseQuery, Integer.class, namaAlbums);
        } else {
            return jdbcTemplate.queryForObject(baseQuery, null, Integer.class);
        }
    }

    public List<Albums> getAllAlbumsRec(DataTableRequest request){
        String baseQuery = "select al.id_album as idAlbum, al.nama_albums as namaAlbums, " +
                "al.id_labels as idLabel, al.id_artis as idArtis, al.keterangan, ar.nama_artis as namaArtis from Albums al join Artis ar on " +
                "al.id_artis = ar.id_artis " +
                "order by "+(request.getSortCol()+1)+" "+request.getSortDir()+" limit ? offset ? ";
        if(!request.getExtraParam().isEmpty()){
            String namaAlbums = (String) request.getExtraParam().get("nama_albums");
            baseQuery = "select al.id_album as idAlbum, al.nama_albums as namaAlbums, " +
                    "al.id_labels as idLabel, al.id_artis as idArtis, al.keterangan, ar.nama_artis as namaArtis from Albums al join Artis ar on " +
                    "al.id_artis = ar.id_artis where al.nama_albums like concat('%', ?, '%')" +
                    "order by "+(request.getSortCol()+1)+" "+request.getSortDir()+" limit ? offset ? ";;
            return jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Albums.class),namaAlbums,
                    request.getLength(), request.getStart());
        } else {
            return jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(Albums.class),
                    request.getLength(), request.getStart());
        }

    }

}
