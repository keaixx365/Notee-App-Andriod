package com.example.noteeapp.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.noteeapp.Dao.NotesDao;
import com.example.noteeapp.Database.NotesDatabase;
import com.example.noteeapp.Modelentity.Notes;

import java.util.List;

public class NotesRepository {

    public NotesDao notesDao;
    public LiveData<List<Notes>>getallNotes;

    //for filter
    public LiveData<List<Notes>>hightolow;
    public LiveData<List<Notes>>lowtohigh;


    public NotesRepository(Application application){
        NotesDatabase database=NotesDatabase.getDatabaseInstance(application);
        notesDao = database.notesDao();
        getallNotes=notesDao.getallNotes();

        //for filter
        hightolow=notesDao.highToLow();
        lowtohigh=notesDao.lowToHigh();

    }
    public void insertNotes(Notes notes){
        notesDao.insertNotes(notes);
    }
    public void deleteNotes(int id){
        notesDao.deleteNotes(id);
    }
    public void updateNotes(Notes notes){
        notesDao.updateNotes(notes);
    }


}
