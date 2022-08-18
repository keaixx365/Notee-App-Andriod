package com.example.noteeapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Toast;

import com.example.noteeapp.Modelentity.Notes;
import com.example.noteeapp.R;
import com.example.noteeapp.ViewModel.NotesViewModel;
import com.example.noteeapp.databinding.ActivityInsertNotesBinding;

import java.util.Date;


public class InsertNotesActivity extends AppCompatActivity {
    ActivityInsertNotesBinding binding;//use krne ka wat//1
    String title,subtitle,notes; //5 data ko get krne k liye
    NotesViewModel notesViewModel;//7.activity sief view model k sth interact kregi...yeh sb m bnega
    String priority="1";//one islie hai kuki agar koi priority nhi choose krta hai toh direct greeen m chala jyefga

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityInsertNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());//2.binfing provess finish uska fayada teg hai ki jitni bhi id hofi wo milegi

        notesViewModel= ViewModelProviders.of(this).get(NotesViewModel.class); //8

        //for priority //13
        binding.greenPriority.setOnClickListener(v ->{
           binding.greenPriority.setImageResource(R.drawable.ic_baseline_done_24);
           binding.yellowPriority.setImageResource(0);
           binding.redPriority.setImageResource(0);
            priority="1";

        });
        binding.yellowPriority.setOnClickListener(v ->{
            binding.greenPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(R.drawable.ic_baseline_done_24);
            binding.redPriority.setImageResource(0);
            priority="2";

        });
        binding.redPriority.setOnClickListener(v ->{
            binding.greenPriority.setImageResource(0);
            binding.yellowPriority.setImageResource(0);
            binding.redPriority.setImageResource(R.drawable.ic_baseline_done_24);
            priority="3";

        });

        binding.doneNotesBtn.setOnClickListener(v -> {  //3
            // 6 to get data
                title=binding.notesTitle.getText().toString();
                subtitle=binding.notesSubtitle.getText().toString();

                notes=binding.notesData.getText().toString();
                CreateNotes(title, subtitle, notes); //9 notes ko create krna hai


        });
    }
    public void CreateNotes(String title, String subtitle, String notes) {

            Date date = new Date();//11
            CharSequence sequence = DateFormat.format("MMMM d,yyyy", date.getTime());


            Notes notes1 = new Notes();//9
            notes1.notesTitle = title;
            notes1.notesSubtitle = subtitle;
            notes1.notes = notes;//9
            notes1.notesPriority = priority;
            notes1.notesDate = sequence.toString();//12
            notesViewModel.insertNote(notes1);

            Toast.makeText(this, "Notes Created Successfully", Toast.LENGTH_SHORT).show();//10
            finish();


    }
}