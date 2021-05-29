package cn.henu.cs.note.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import cn.henu.cs.note.R;
import cn.henu.cs.note.utils.CRUD;

public class my_Fragment extends Fragment {

    private ListView listView1, listView2, listView3, listView4;

    private long favoritesNum = 0;
    private long allNotesNum = 0;
    private TextView sumPieces, storePieces, remainNum;
    private EditText mySignature;
    private Context context;

    public my_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my, container, false);
        context = getActivity();
        sumPieces = v.findViewById(R.id.my_fragment_pieces);
        storePieces = v.findViewById(R.id.my_fragment_storePieces);
        remainNum = v.findViewById(R.id.my_fragment_remainNum);
        mySignature = v.findViewById(R.id.my_fragment_mySignature);
        upData();
        mySignature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                remainNum.setText("("+(30-mySignature.getText().length())+")");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return v;
    }

    private void upData() {
        CRUD op = new CRUD(context);
        op.open();
        allNotesNum = op.getAllNoteNum();
        favoritesNum = op.getAllFavoritesNoteNum();
        op.close();
        sumPieces.setText(""+allNotesNum);
        storePieces.setText(""+favoritesNum);
        remainNum.setText("("+(30-mySignature.getText().length())+")");
    }

    @Override
    public void onResume() {
        super.onResume();
        upData();
    }
}