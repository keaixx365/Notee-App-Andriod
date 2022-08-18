package com.example.noteeapp.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.noteeapp.Modelentity.Notes;

import java.util.List;

@androidx.room.Dao
public interface NotesDao {//data acess object

    @Query("SELECT * FROM Notes_Database") //CNTRL+SPACE SE AAYA TABKE NAME
    LiveData<List<Notes>> getallNotes();
    // List<Notes> getallNotes(); //get krke de dega live data k form m


    //for filter
    @Query("SELECT * FROM Notes_Database ORDER BY notes_priority DESC")
    LiveData<List<Notes>> highToLow();

    @Query("SELECT * FROM Notes_Database ORDER BY notes_priority ASC")
    LiveData<List<Notes>> lowToHigh();

    @Insert
    void insertNotes(Notes... notes);

    @Query("DELETE FROM Notes_Database WHERE id=:id")
    void deleteNotes(int id);

    @Update
    void updateNotes(Notes notes);
}
