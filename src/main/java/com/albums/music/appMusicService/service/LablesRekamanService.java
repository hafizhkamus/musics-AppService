package com.albums.music.appMusicService.service;


import com.albums.music.appMusicService.dao.LablesRekamanDao;
import com.albums.music.appMusicService.dataTables.DataTableRequest;
import com.albums.music.appMusicService.dataTables.DataTableResponse;
import com.albums.music.appMusicService.entity.Artis;
import com.albums.music.appMusicService.entity.LablesRekaman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LablesRekamanService {

    @Autowired
    private LablesRekamanDao dao;

    public DataTableResponse<LablesRekaman> datatables(DataTableRequest res){
        DataTableResponse<LablesRekaman> tables = new DataTableResponse<>();
        tables.setData(dao.getAllLabelsRec( res));
        Integer total = dao.getBanyakLabel( res);
        tables.setRecordsTotal(total);
        tables.setRecordFiltered(total);
        tables.setDraw(res.getDraw());
        return tables;
    }

    public List<LablesRekaman> findAll() throws EmptyResultDataAccessException {
        return dao.getAllLabels();
    }

    public Optional<LablesRekaman> findById(Integer id) throws EmptyResultDataAccessException {
        return dao.getLabelsById(id);
    }

    public void insertOrUpdate(LablesRekaman lablesRekaman) {
        dao.insertOrUpdateLabels(lablesRekaman);
    }

    public void deleteById(Integer id) throws DataAccessException {
        dao.deleteLabels(id);
    }
}
