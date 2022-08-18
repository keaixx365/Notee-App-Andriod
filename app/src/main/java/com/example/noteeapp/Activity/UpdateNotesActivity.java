package com.example.noteeapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noteeapp.Modelentity.Notes;
import com.example.noteeapp.R;
import com.example.noteeapp.ViewModel.NotesViewModel;
import com.example.noteeapp.databinding.ActivityUpdateNotesBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Date;

public class UpdateNotesActivity extends AppCompatActivity {

    //binding
    ActivityUpdateNotesBinding binding;
    String priority="1";
    //jo data intent m note adpdater m set kiya hai ab usko get krna
    String stitle,ssubtitle, snotes,spriority;
    int iid;

    NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //2
        binding=ActivityUpdateNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel= ViewModelProviders.of(this).get(NotesViewModel.class); //8

        //get ki process
        iid=getIntent().getIntExtra("id",0);
        stitle=getIntent().getStringExtra("title"); //wha pr yeh key di hai aur yeh sam hona chahie adapter jese
        ssubtitle=getIntent().getStringExtra("subtitle");
        spriority=getIntent().getStringExtra("priority");
        snotes=getIntent().getStringExtra("note");

        //set krna hai get k baad
        binding.upTitle.setText(stitle);
        binding.upSubtitle.setText(ssubtitle);
        binding.upNotes.setText(snotes);


         //chng k baad bhi greeb pr tick thi//user n jo phele se dali ho whi page par dikhe uska code hai
        if(spriority.equals("1")){
            binding.greenPriority.setImageResource((R.drawable.ic_baseline_done_24));
        }
        else if(spriority.equals("2")){
            binding.yellowPriority.setImageResource((R.drawable.ic_baseline_done_24));
        }
        else if(spriority.equals("3")){
            binding.redPriority.setImageResource((R.drawable.ic_baseline_done_24));
        }
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
        //button for updarte click event lga diya but sbse pheli chiz yeha hume notes milegi kaise kuki wo toh adpater m hai
        binding.updateNotesBtn.setOnClickListener(v -> {
            //update button k work k liye
            String title=binding.upTitle.getText().toString();
            String subtitle=binding.upSubtitle.getText().toString();

            String notes=binding.upNotes.getText().toString();
            UpdateNotes(title, subtitle, notes);

        });


    }

    private void UpdateNotes(String title, String subtitle, String notes) {
        Date date = new Date();//11
        CharSequence sequence = DateFormat.format("MMMM d,yyyy", date.getTime());
        //hume viewmodel bhi chihiye hai

        Notes updateNotes=new Notes();//add krna hai sbkp
        updateNotes.id=iid;
        updateNotes.notesTitle = title;
        updateNotes.notesSubtitle = subtitle;
        updateNotes.notes = notes;//9
        updateNotes.notesPriority = priority;
        updateNotes.notesDate = sequence.toString();

        notesViewModel.updateNote(updateNotes);

        Toast.makeText(this, "Notes Updated Successfully", Toast.LENGTH_SHORT).show();//10
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menuinflater le kr menu ko yeha inflate kiya
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //menu item milte hai
        if(item.getItemId()==R.id.ic_delete){
            //hume ek layout desgin krna hai
            BottomSheetDialog sheetDialog=new BottomSheetDialog(UpdateNotesActivity.this);
            View view= LayoutInflater.from(UpdateNotesActivity.this).inflate(R.layout.delete_bottom_sheet,
                    (LinearLayout) findViewById(R.id.bottomSheet));
            sheetDialog.setContentView(view);

            //lick hone par chnge ho
            TextView yes,no;
            yes=view.findViewById(R.id.delete_yes);
            no=view.findViewById(R.id.delete_no);

            yes.setOnClickListener(v ->{
                //notes view model m delete krne k liye ek method bnya tha usme id pass krni thi aur wo notes dao m jyega
                notesViewModel.deleteNote(iid);
                finish();
            });
            no.setOnClickListener(v->{
                 sheetDialog.dismiss();
            });
            sheetDialog.show();

        }
        return true;
    }
}