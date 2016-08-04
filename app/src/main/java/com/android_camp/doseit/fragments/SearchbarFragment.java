package com.android_camp.doseit.fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android_camp.doseit.Parameter;
import com.android_camp.doseit.R;
import com.android_camp.doseit.fragments.adapter.ListAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchbarFragment extends Fragment implements View.OnClickListener {

    private EditText mTextInput;
    private ImageButton mSpeakSearchBtn;
    private ImageButton mTextSearchBtn;
    private ListView mMedicineList;
    private ListAdapter mListAdapter;
    private Parameter mParameter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchbar, container, false);
        mTextInput = (EditText) view.findViewById(R.id.search_input);
        mSpeakSearchBtn = (ImageButton) view.findViewById(R.id.voice_search);
        mTextSearchBtn = (ImageButton) view.findViewById(R.id.text_search);
        mTextSearchBtn.setOnClickListener(this);
        mSpeakSearchBtn.setOnClickListener(this);
        mMedicineList = (ListView) view.findViewById(R.id.list_meds);
        mListAdapter = new ListAdapter(getContext());
        mMedicineList.setAdapter(mListAdapter);

        mTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.length() > 0 && s.subSequence(s.length() - 1, s.length()).toString().equalsIgnoreCase("\n")) {
                    textSearch(s);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }

    private void voiceSearch() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100: {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    mTextInput.setText(result.get(0));
                }
                break;
            }
        }
    }

    private void textSearch(CharSequence s) {
        mListAdapter.getFilter().filter(s);
        mTextInput.setText("");
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.text_search:
                textSearch(mTextInput.getText());
                break;
            case R.id.voice_search:
                voiceSearch();
                break;
            default:
                break;
        }
    }
}
