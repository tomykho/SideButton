package tk.claudkho.sample_sidebutton;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class SideButton extends Button implements Animator.AnimatorListener {
    private ObjectAnimator showAnimator, hideAnimator;
    private boolean isShowing;

    public SideButton(Context context) {
        super(context);
    }

    public SideButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(int width) {
        Drawable drawableLeft = getCompoundDrawables()[0];
        if (drawableLeft == null)
            throw new RuntimeException("Drawable Left must be specified");
        float sideTransX = width - drawableLeft.getIntrinsicWidth() - 8;
        setTranslationX(sideTransX);
        showAnimator = ObjectAnimator.ofFloat(this, "translationX", sideTransX, 0);
        showAnimator.addListener(this);
        hideAnimator = ObjectAnimator.ofFloat(this, "translationX", 0, sideTransX);
        showAnimator.addListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init(w);
    }

    @Override
    public void setOnClickListener(final OnClickListener listener) {
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isShowing)
                    show();
                else {
                    hide();
                    if (listener != null)
                        listener.onClick(view);
                }
            }
        });
    }

    private void show() {
        isShowing = true;
        showAnimator.start();
    }

    private void hide() {
        isShowing = false;
        hideAnimator.start();
    }

    @Override
    public void onAnimationStart(Animator animator) {
        setClickable(false);
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        setClickable(true);
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
