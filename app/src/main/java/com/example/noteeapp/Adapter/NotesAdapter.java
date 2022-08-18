package com.example.noteeapp.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteeapp.Activity.UpdateNotesActivity;
import com.example.noteeapp.MainActivity;
import com.example.noteeapp.Modelentity.Notes;
import com.example.noteeapp.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter  extends RecyclerView.Adapter<NotesAdapter.notesViewholder> {

    MainActivity mainActivity;
    List<Notes> notes;
    //for search
    List<Notes> allNotesitem;//ek lis bnya jisme sarre notes dal diye

    public NotesAdapter(MainActivity mainActivity, List<Notes> notes) {//constructor

        this.mainActivity=mainActivity;
        this.notes=notes;

        //search
        allNotesitem=new ArrayList<>(notes);
    }
    //search iss search ho kr saare notes aayege
    public void searchNotes(List<Notes> filterredName){
        this.notes=filterredName;
        notifyDataSetChanged();//iska matalb yeh hai ki unko pta chal jyega ki uske andr kuch data chnge huye hai
                                 //iske baad main activity m jna hai

    }

    @Override
    public notesViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new notesViewholder(LayoutInflater.from(mainActivity).inflate(R.layout.item_notes, parent, false));
    }

    @Override
    public void onBindViewHolder( notesViewholder holder, int position) {
        Notes note=notes.get(position);
        if(note.notesPriority.equals("1")){
            holder.notesPriority.setBackgroundResource(R.drawable.green_shape);
        }
        else if(note.notesPriority.equals("2")){
            holder.notesPriority.setBackgroundResource(R.drawable.yellow_shape);
        }
        else if(note.notesPriority.equals("3")){
            holder.notesPriority.setBackgroundResource(R.drawable.red_shape);
        }


            holder.title.setText(note.notesTitle);
           holder.subtitle.setText(note.notesSubtitle);
            holder.notesDate.setText(note.notesDate);


            //yeha hai notes aur hume chahiye UpdateNotesActivity mm
        holder.itemView.setOnClickListener(v->{
            //yeh click listernr hai jb humare notes pr click ho yeh update k liye

            Intent intent=new Intent(mainActivity, UpdateNotesActivity.class); //ese krke saare data ko snd krna hai toh humne intent k andar add kr liya
            intent.putExtra("id",note.id); //yeh not dao se mil rha hai aur wo integer type ka hai                              //ab get k liye update note activite m jyege
            intent.putExtra("title",note.notesTitle);
            intent.putExtra("subtitle",note.notesSubtitle);
            intent.putExtra("note",note.notes);
            intent.putExtra("priority",note.notesPriority);

            mainActivity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {

        return notes.size();
    }

    static class notesViewholder extends RecyclerView.ViewHolder {
        TextView title,subtitle,notesDate;
        View notesPriority;

        public notesViewholder( View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.notesTitle);
            subtitle=itemView.findViewById(R.id.notesSubtitle);
            notesDate=itemView.findViewById(R.id.notesDate);
            notesPriority=itemView.findViewById(R.id.notesPriority);
        }
    }

}
