package jcchen.goodsmanager.view.fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Vector;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.ColorInfo;
import jcchen.goodsmanager.presenter.impl.PurchasePresenterImpl;
import jcchen.goodsmanager.view.adapter.ColorListViewAdapter;
import jcchen.goodsmanager.view.listener.OnColorSelectedListener;
import jcchen.goodsmanager.view.widget.RoundedImageView;

public class ColorSelectDialogFragment extends DialogFragment {

    private SearchView mSearchView;
    private ListView mListView;
    private RoundedImageView mRoundedImageView;
    private TextView mTextView;

    private ColorListViewAdapter adapter;
    private PurchasePresenterImpl presenter;
    private OnColorSelectedListener listener;

    private Vector<ColorInfo> selectedColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.color_select_dialog_layout, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mTextView = (TextView) view.findViewById(R.id.color_preview);
        updateTextView();

        mListView = (ListView) view.findViewById(R.id.color_list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapter.isSelected((int) adapter.getItemId(i))) {
                    adapter.setSelected((int) adapter.getItemId(i), false);
                    for (int j = 0; j < selectedColor.size(); j++) {
                        if (selectedColor.get(j).getName().equals(((ColorInfo) mListView.getItemAtPosition(i)).getName())) {
                            selectedColor.remove(j);
                            break;
                        }
                    }
                } else {
                    adapter.setSelected((int) adapter.getItemId(i), true);
                    selectedColor.add((ColorInfo) mListView.getItemAtPosition(i));
                }
                updateTextView();
            }
        });

        mRoundedImageView = (RoundedImageView) view.findViewById(R.id.color_confirm_button);
        mRoundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onColorSelected(selectedColor);
                dismiss();
            }
        });

        mSearchView = (SearchView) view.findViewById(R.id.color_search);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQueryHint(getResources().getString(R.string.search));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                // Hide input window.
                if(imm != null)
                    imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
                mSearchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        // Custom SearchView.
        EditText searchEditText = (EditText) mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextOnSecondary));
        searchEditText.setHintTextColor(ContextCompat.getColor(getActivity(), R.color.colorTextHint));
        searchEditText.setGravity(Gravity.CENTER);
    }

    public void setPresenter(PurchasePresenterImpl presenter) {
        this.presenter = presenter;
    }

    public void setListener(OnColorSelectedListener listener) {
        this.listener = listener;
    }

    public void init(Context context) {
        adapter = new ColorListViewAdapter(context, presenter.getColorList());
        selectedColor = new Vector<>();
    }

    private void updateTextView() {
        String text = getResources().getString(R.string.color) + " : ";
        for(int i = 0; i < selectedColor.size(); i++) {
            if(i > 0)
                text = text.concat("/");
            text = text.concat(selectedColor.get(i).getName());
        }
        if(selectedColor.size() == 0)
            text = text.concat("(" + getResources().getString(R.string.none_select) + ")");
        mTextView.setText(text);
    }
}
