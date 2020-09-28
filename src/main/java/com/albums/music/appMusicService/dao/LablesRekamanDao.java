package com.albums.music.appMusicService.dao;


import com.albums.music.appMusicService.dataTables.DataTableRequest;
import com.albums.music.appMusicService.entity.LablesRekaman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LablesRekamanDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<LablesRekaman> getAllLabels(){
        String baseQuery = "SELECT id_label as idLabel, nama_labels  as namaLabels, alamat, no_telp as noTelp, contact_person as contactPerson, url_website as url FROM lables_rekaman";
        List<LablesRekaman> prop = jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(LablesRekaman.class));
        return prop;
    }

    public Optional<LablesRekaman> getLabelsById(int id) {
        String baseQuery = "SELECT id_label as idLabel, nama_labels  as namaLabels, alamat, no_telp as noTelp, contact_person as contactPerson, url_website as url FROM lables_rekaman where id_label = ?";
        Object param[] = {id};
        try {
            return Optional.of(jdbcTemplate.queryForObject(baseQuery, param, BeanPropertyRowMapper.newInstance(LablesRekaman.class)));

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void insertLabel(LablesRekaman lablesRekaman){
        String baseQuery = "insert into lables_rekaman (id_label, nama_labels, alamat, no_telp, contact_person, url_website) values (?, ?, ?, ?, ?, ?)";
        Object parameters[] = {lablesRekaman.getIdLabel(),lablesRekaman.getNamaLabels(),lablesRekaman.getAlamat(),lablesRekaman.getNoTelp(),lablesRekaman.getContactPerson(),lablesRekaman.getUrl()};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void updateLabel(LablesRekaman lablesRekaman){
        String baseQuery = "update lables_rekaman set nama_labels = ?, alamat = ?, no_telp = ?, contact_person = ?, url_website = ?  where id_artis =?";
        Object parameters[] = {lablesRekaman.getNamaLabels(),lablesRekaman.getAlamat(),lablesRekaman.getNoTelp(),lablesRekaman.getContactPerson(),lablesRekaman.getUrl(),lablesRekaman.getIdLabel() };

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void deleteLabels(Integer id){
        String baseQuery = "delete from lables_rekaman where id_label =?";
        Object parameters[] = {id};

        jdbcTemplate.update(baseQuery,parameters);
    }

    public void insertOrUpdateLabels(LablesRekaman lablesRekaman){
        Optional<LablesRekaman> lablesRekaman1  = getLabelsById(lablesRekaman.getIdLabel());
        if (lablesRekaman1.isPresent()){
            updateLabel(lablesRekaman);
        } else {
            insertLabel(lablesRekaman);
        }
    }

    public Integer getBanyakLabel(DataTableRequest req){
        String baseQuery = "select count(id_label) as banyak from lables_rekaman";
        if(!req.getExtraParam().isEmpty()){
            String namaLabels = (String) req.getExtraParam().get("namaLabels");
            baseQuery = "select count(id_label) as banyak from lables_rekaman where nama_labels like concat('%', ?, '%')";
            return jdbcTemplate.queryForObject(baseQuery, Integer.class, namaLabels);
        } else {
            return jdbcTemplate.queryForObject(baseQuery, null, Integer.class);
        }
    }

    public List<LablesRekaman> getAllLabelsRec(DataTableRequest request){
        String baseQuery = "SELECT id_label as idLabel, nama_labels  as namaLabels, alamat, no_telp as noTelp, contact_person as contactPerson, url_website as url FROM lables_rekaman "
                + "order by "+(request.getSortCol()+1)+" "+request.getSortDir()+" limit ? offset ? ";
        if(!request.getExtraParam().isEmpty()){
            String namaLabels = (String) request.getExtraParam().get("namaLabels");
            baseQuery = "SELECT id_label as idLabel, nama_labels  as namaLabels, alamat, no_telp as noTelp, contact_person as contactPerson, url_website as url FROM lables_rekaman where nama_labels like concat('%', ?, '%') "
                    + "order by "+(request.getSortCol()+1)+" "+request.getSortDir()+" limit ? offset ? ";
            return jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(LablesRekaman.class),namaLabels,
                    request.getLength(), request.getStart());
        } else {
            return jdbcTemplate.query(baseQuery, BeanPropertyRowMapper.newInstance(LablesRekaman.class),
                    request.getLength(), request.getStart());
        }

    }
}
