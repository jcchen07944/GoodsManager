package jcchen.goodsmanager.view.fragment;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Vector;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.SizeInfo;
import jcchen.goodsmanager.presenter.impl.PurchasePresenterImpl;
import jcchen.goodsmanager.presenter.impl.SettingPresenterImpl;
import jcchen.goodsmanager.view.adapter.SizeListViewAdapter;
import jcchen.goodsmanager.view.listener.OnSizeSelectedListener;
import jcchen.goodsmanager.view.widget.RoundedImageView;

public class SizeSelectDialogFragment extends DialogFragment {

    public static final String TAG = "SizeSelectDialogFragment";

    private ListView mListView;
    private RoundedImageView mRoundedImageView;
    private TextView mTextView;

    private SettingPresenterImpl mSettingPresenter;
    private OnSizeSelectedListener listener;
    private SizeListViewAdapter adapter;

    private Vector<SizeInfo> sizeSelectList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.size_select_dialog_layout, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mTextView = (TextView) view.findViewById(R.id.size_preview);

        adapter = new SizeListViewAdapter(getActivity(), mSettingPresenter.getSizeList());
        if (sizeSelectList == null)
            sizeSelectList = new Vector<>();
        for (int i = 0; i < sizeSelectList.size(); i++) {
            long id = adapter.getItemId(sizeSelectList.get(i).getName());
            if(id == -1)
                sizeSelectList.remove(i);
            else
                adapter.setSelected((int) id, true);
        }

        mListView = (ListView) view.findViewById(R.id.size_list);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapter.isSelected((int) adapter.getItemId(i))) {
                    adapter.setSelected((int) adapter.getItemId(i), false);
                    for (int j = 0; j < sizeSelectList.size(); j++) {
                        if (sizeSelectList.get(j).getName().equals(((SizeInfo) mListView.getItemAtPosition(i)).getName())) {
                            sizeSelectList.remove(j);
                            break;
                        }
                    }
                } else {
                    adapter.setSelected((int) adapter.getItemId(i), true);
                    sizeSelectList.add((SizeInfo) mListView.getItemAtPosition(i));
                }
                updateTextView();
            }
        });

        mRoundedImageView = (RoundedImageView) view.findViewById(R.id.size_confirm_button);
        mRoundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSizeSelected(sizeSelectList);
                dismiss();
            }
        });

        updateTextView();
    }

    public void setPresenter(SettingPresenterImpl presenter) {
        this.mSettingPresenter = presenter;
    }

    public void setListener(OnSizeSelectedListener listener) {
        this.listener = listener;
    }

    public void loadSavedData(Vector<SizeInfo> sizeSelectList) {
        if (sizeSelectList == null)
            sizeSelectList = new Vector<>();
        this.sizeSelectList = (Vector<SizeInfo>) sizeSelectList.clone();
    }

    private void updateTextView() {
        String text = getResources().getString(R.string.size) + " : ";
        for(int i = 0; i < sizeSelectList.size(); i++) {
            if(i > 0)
                text = text.concat("/");
            text = text.concat(sizeSelectList.get(i).getName());
        }
        if(sizeSelectList.size() == 0)
            text = text.concat("F");
        mTextView.setText(text);
    }
}
