package com.android_camp.doseit.fragments;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android_camp.doseit.DatabaseHelper;
import com.android_camp.doseit.Medicine;
import com.android_camp.doseit.R;
import com.android_camp.doseit.VoiceActivity;
import com.android_camp.doseit.fragments.adapter.ListAdapter;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.Locale;

public class FragmentSearchbar extends Fragment implements View.OnClickListener, DatabaseHelper.Help {



    public interface MedicineCallBack {
        void onSelectedMedicine(Medicine m);
    }

    private EditText mTextInput;
    private ImageButton mSpeakSearchBtn;
    private ImageButton mTextSearchBtn;
    private ListView mMedicineList;
    private MedicineCallBack mCallback;
    private ListAdapter mListAdapter;
    private ArrayList<Medicine> mList = null;
    private ArrayList<String> mMedName = null;
    private Medicine clickedOn;


    private TextToSpeech textToSpeech;
    private FragmentParameters.Swipe swiper;


    public void start() {
        if(getActivity() instanceof VoiceActivity){
            textToSpeech.speak("Medicine", TextToSpeech.QUEUE_FLUSH, null);
            mSpeakSearchBtn.callOnClick();
        }
    }

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
        Firebase.setAndroidContext(getContext());
        DatabaseHelper database = new DatabaseHelper();
        database.initDataBase(this);

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

        if(getActivity() instanceof VoiceActivity) {
            textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {

                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        textToSpeech.setLanguage(Locale.US);
                    }
                }
            });
            swiper = (FragmentParameters.Swipe) getActivity();
        }

        return view;
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
                    String res = result.get(0);
                    mTextInput.setText(res);
                    textToSpeech.speak(res, TextToSpeech.QUEUE_FLUSH, null);

                    Medicine medicine =  mListAdapter.getMedicine(res);
                    if(medicine == null) {
                        swiper.moveToNext(1);
                    }

                    mCallback.onSelectedMedicine(medicine);
                    swiper.moveToNext(2);

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (MedicineCallBack) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
    @Override
    public void dealWithData(ArrayList<Medicine> l, ArrayList<String> medsName) {
        Log.d("MSG", "dealWithDataWorks");
        mList = l;
        mMedName = medsName;
        mListAdapter.setMedicineList(mList);
        mMedicineList.setAdapter(mListAdapter);
        mMedicineList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId) {
                mCallback.onSelectedMedicine((Medicine)((ListAdapter)listView.getAdapter()).getItem(itemPosition));
            }
        });
    }
}