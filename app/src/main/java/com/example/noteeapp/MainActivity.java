package com.example.noteeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.noteeapp.Activity.InsertNotesActivity;
import com.example.noteeapp.Adapter.NotesAdapter;
import com.example.noteeapp.Modelentity.Notes;
import com.example.noteeapp.ViewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    NotesViewModel notesViewModel;//badme krege 6

    FloatingActionButton newNotesBtn;  //1
    RecyclerView notesRecycler;
    NotesAdapter adapter;


    //forfilter
    TextView nofilter, hightolow, lowtohigh;

    //search
    List<Notes> filternotesalllist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newNotesBtn=findViewById(R.id.newNotesBtn); //2
        notesRecycler=findViewById(R.id.notesRecycler);

        //forfilrt
        nofilter=findViewById(R.id.nofilter);
        hightolow=findViewById(R.id.hightolow);
        lowtohigh=findViewById(R.id.lowtohigh);

        //jb user pheli baar aayega toh usko by default no filter m bhejrgr
        nofilter.setBackgroundResource(R.drawable.filter_selected_shape);

        //ab teeno k click event bnega
        nofilter.setOnClickListener(v->{
            loadData(0);
            nofilter.setBackgroundResource(R.drawable.filter_selected_shape);
           hightolow.setBackgroundResource(R.drawable.filter_un_shape);
           lowtohigh.setBackgroundResource(R.drawable.filter_un_shape);
        });
        hightolow.setOnClickListener(v->{
            loadData(1);
            nofilter.setBackgroundResource(R.drawable.filter_un_shape);
            hightolow.setBackgroundResource(R.drawable.filter_selected_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_un_shape);
        });
        lowtohigh.setOnClickListener(v->{
            loadData(2);
            nofilter.setBackgroundResource(R.drawable.filter_un_shape);
            hightolow.setBackgroundResource(R.drawable.filter_un_shape);
            lowtohigh.setBackgroundResource(R.drawable.filter_selected_shape);
        });


        notesViewModel= ViewModelProviders.of(this).get(NotesViewModel.class);

        newNotesBtn.setOnClickListener(v ->{ //3
            startActivity(new Intent(MainActivity.this, InsertNotesActivity.class));
        });

     notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
         @Override
         public void onChanged(List<Notes> notes) {
             setAdapter(notes);
             filternotesalllist=notes;

         }
        });


    }
    //for filter
    private  void loadData(int i){
        if(i==0){
            notesViewModel.getAllNotes.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesalllist=notes;

                }
            });
        }
        else if(i==1){
            notesViewModel.hightolow.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesalllist=notes;

                }
            });

        }
        else if(i==2){
            notesViewModel.lowtohigh.observe(this, new Observer<List<Notes>>() {
                @Override
                public void onChanged(List<Notes> notes) {
                    setAdapter(notes);
                    filternotesalllist=notes;

                }
            });

        }
    }
    //to grt dataaccordingto filter

    public void setAdapter(List<Notes> notes) {

            notesRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));//frontpage
            adapter=new NotesAdapter(MainActivity.this,notes); //iske liye uper set kiya adpter
            notesRecycler.setAdapter(adapter);
    }
    //for search

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_notes, menu);

        MenuItem menuItem=menu.findItem(R.id.app_bar_search);

        SearchView searchView=(SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Notes here...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                NotesFilter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void NotesFilter(String s) {
        ArrayList<Notes> FilterName = new ArrayList<>();
        //yeh list isliye bnya hai kuki jo user search kre toh unse related saare new list m daal dege aur uss list ko adpter m set kr dege
        for (Notes notes: this.filternotesalllist)  {
            if(notes.notesTitle.contains(s) || notes.notesTitle.contains(s)){
                FilterName.add(notes);
            }
        }
        this.adapter.searchNotes(FilterName);
    }
}
