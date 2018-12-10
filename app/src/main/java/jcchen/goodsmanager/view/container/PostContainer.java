package jcchen.goodsmanager.view.container;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.entity.PostBlock;
import jcchen.goodsmanager.entity.PurchaseInfo;
import jcchen.goodsmanager.presenter.impl.SettingPresenterImpl;
import jcchen.goodsmanager.view.widget.BottomSheet.BottomSheetFL;

public class PostContainer extends BottomSheetFL implements Container {

    private Context context;

    private LinearLayout copyLayout;
    private View view;
    private EditText postText;
    private ScrollView verticalScroll;

    private SettingPresenterImpl mSettingPresenter;
    private ArrayList<PostBlock> postList;

    public PostContainer(Context context) {
        super(context);
        this.context = context;
        mSettingPresenter = new SettingPresenterImpl(context);

        init();
    }

    public PostContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        mSettingPresenter = new SettingPresenterImpl(context);

        init();
    }

    @Override
    public void contentShow() {
        // Copy layout animation.
        ObjectAnimator translationY = ObjectAnimator.ofFloat(copyLayout, "translationY", 300, 0);
        translationY.setDuration(250);
        translationY.setInterpolator(new OvershootInterpolator(2f));
        ObjectAnimator alphaIn = ObjectAnimator.ofFloat(copyLayout, "alpha", 0, 1);
        alphaIn.setDuration(100);
        AnimatorSet mAnimationSet = new AnimatorSet();
        mAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                copyLayout.setAlpha(0f);
            }
        });
        mAnimationSet.playTogether(translationY, alphaIn);
        mAnimationSet.start();

        // Text animation.
        alphaIn = ObjectAnimator.ofFloat(verticalScroll, "alpha", 0, 1);
        alphaIn.setDuration(200);
        mAnimationSet = new AnimatorSet();
        mAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                verticalScroll.setAlpha(0f);
            }
        });
        mAnimationSet.play(alphaIn);
        mAnimationSet.setStartDelay(200);
        mAnimationSet.start();

        alphaIn = ObjectAnimator.ofFloat(postText, "alpha", 0, 1);
        alphaIn.setDuration(200);
        mAnimationSet = new AnimatorSet();
        mAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                postText.setAlpha(0f);
            }
        });
        mAnimationSet.play(alphaIn);
        mAnimationSet.setStartDelay(380);
        mAnimationSet.start();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void showItem(Object object) {
        if (object == null)
            return;

        PurchaseInfo purchaseInfo = (PurchaseInfo) object;
        String text = "";
        for (int i = 0; i < postList.size(); i++) {
            if (postList.get(i).isDefault()) {
                // Numbers & Item name.
                text += purchaseInfo.getNumbers() + purchaseInfo.getName() + '\n';

                // Actual price.
                text += "連線價：" + convertToFullwidth(purchaseInfo.getActualPrice() + "") + '\n';

                // Size.
                text += "尺寸：";
                for (int j = 0; j < purchaseInfo.getSizeList().size(); j++) {
                    if(j > 0)
                        text += "/";
                    text += purchaseInfo.getSizeList().get(j).getName();
                }
                if (purchaseInfo.getSizeList().size() == 0)
                    text += "F";
                text += "　★平舖平量 (單位：公分)" + '\n';

                // Size detail.
                ArrayList<String> typeColumns = purchaseInfo.getTypeInfo().getColumn();
                for (int k = 0; k < purchaseInfo.getSizeStructList().size(); k++) {
                    boolean empty = true;
                    if (!purchaseInfo.getSizeStructList().get(k).getSizeName().equals("")) {
                        text += purchaseInfo.getSizeStructList().get(k).getSizeName() + " ";
                        empty = false;
                    }
                    for (int j = 0; j < typeColumns.size(); j++) {
                        if (!typeColumns.get(j).equals("") &&
                                !purchaseInfo.getSizeStructList().get(k).getColumn(j).equals("")) {
                            text += typeColumns.get(j) + "：";
                            text += purchaseInfo.getSizeStructList().get(k).getColumn(j) + " ";
                            empty = false;
                        }
                    }
                    if (!empty)
                        text += '\n';
                }

                // Color.
                if (purchaseInfo.getColorList().size() != 0) {
                    text += "顏色：";
                    for (int j = 0; j < purchaseInfo.getColorList().size(); j++) {
                        if (j > 0)
                            text += "/";
                        text += purchaseInfo.getColorList().get(j).getName();
                    }
                    text += '\n';
                }

                // Material.
                if (!purchaseInfo.getMaterial().equals("")) {
                    text += "材質：" + purchaseInfo.getMaterial();
                    text += '\n';
                }

                // Flexible.
                if (!purchaseInfo.getFlexible().equals("")) {
                    text += "彈性：" + purchaseInfo.getFlexible();
                    text += '\n';
                }

                // End.
                text += '\n';
            }
            else {
                // Other's.
                text += postList.get(i).getContent();
                if (i  < postList.size() - 1)
                    text += "\n\n";
            }
        }
        postText.setText(text);
    }

    @Override
    public void init() {
        view = LayoutInflater.from(context).inflate(R.layout.post_dialog_content, null);
        copyLayout = (LinearLayout) view.findViewById(R.id.post_copy_layout);
        copyLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, context.getText(R.string.copy), Toast.LENGTH_SHORT).show();
                Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    v.vibrate(30);
                }
            }
        });
        verticalScroll = (ScrollView) view.findViewById(R.id.post_scroll_vertical);

        postList = mSettingPresenter.getPostList();
        postText = (EditText) view.findViewById(R.id.post_text);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                setPeekHeight((int) (300 * context.getResources().getDisplayMetrics().density));
                addView(view);
                show();
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    public void postResult() {

    }

    private String convertToFullwidth(String str){
        for(char c:str.toCharArray()){
            str = str.replaceAll("　", " ");
            if((int)c >= 65281 && (int)c <= 65374){
                str = str.replace(c, (char)(((int)c)-65248));
            }
        }
        return str;
    }
}
