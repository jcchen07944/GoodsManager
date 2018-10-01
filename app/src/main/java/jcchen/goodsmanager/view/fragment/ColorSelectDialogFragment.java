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

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.ColorInfo;
import jcchen.goodsmanager.presenter.impl.PurchasePresenterImpl;
import jcchen.goodsmanager.view.adapter.ColorListViewAdapter;
import jcchen.goodsmanager.view.listener.OnColorSelectedListener;

public class ColorSelectDialogFragment extends DialogFragment {

    private SearchView mSearchView;
    private ListView mListView;

    private ColorListViewAdapter adapter;
    private PurchasePresenterImpl presenter;
    private OnColorSelectedListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.color_select_dialog_layout, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        adapter = new ColorListViewAdapter(getActivity(), presenter.getColorList());

        mListView = (ListView) view.findViewById(R.id.color_list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onColorSelected((ColorInfo) mListView.getItemAtPosition(i));
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
}
