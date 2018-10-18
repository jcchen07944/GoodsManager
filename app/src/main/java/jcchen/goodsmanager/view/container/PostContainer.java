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

import jcchen.goodsmanager.R;
import jcchen.goodsmanager.view.widget.BottomSheet.BottomSheetFL;

public class PostContainer extends BottomSheetFL implements Container {

    private Context context;

    private LinearLayout copyLayout;
    private View view;
    private EditText postText;
    private ScrollView verticalScroll;

    public PostContainer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PostContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
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
        postText = (EditText) view.findViewById(R.id.post_text);
        postText.setText(R.string.post_text);
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
}
