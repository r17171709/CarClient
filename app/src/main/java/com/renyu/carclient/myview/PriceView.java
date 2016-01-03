package com.renyu.carclient.myview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.renyu.carclient.R;

/**
 * Created by renyu on 16/1/2.
 */
public class PriceView extends LinearLayout {

    Context context;

    TextView price_min;
    EditText price_input;
    TextView price_add;

    int maxNum=-1;

    TextChangeListener listener;

    public PriceView(Context context) {
        this(context, null);
    }

    public PriceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context=context;

        setOrientation(HORIZONTAL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        for (int i=0;i<getChildCount();i++) {
            View view=getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        price_min= (TextView) findViewById(R.id.price_min);
        price_min.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int num=Integer.parseInt(price_input.getText().toString());
                if (num==0) {
                    return;
                }
                price_input.setText(""+(num-1));
            }
        });
        price_add= (TextView) findViewById(R.id.price_add);
        price_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int num=Integer.parseInt(price_input.getText().toString());
                if (num==maxNum) {
                    return;
                }
                price_input.setText(""+(num+1));
            }
        });
        price_input= (EditText) findViewById(R.id.price_input);
        price_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    price_input.setText("0");
                    price_input.setSelection(1);
                }
                if (listener!=null && price_input.getText().toString().equals(s.toString())) {
                    listener.onTextChange(Integer.parseInt(price_input.getText().toString()));
                }
            }
        });
    }

    /**
     * 获取当前商品数量
     * @return
     */
    public int getPriceNum() {
        return Integer.parseInt(price_input.getText().toString());
    }

    /**
     * 设置最大商品数量
     * @param maxNum
     */
    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    /**
     * 设置当前商品数量
     * @param currentNum
     */
    public void setCurrentNum(int currentNum) {
        price_input.setText(""+currentNum);
    }

    public void setOnTextChangeListener(TextChangeListener listener) {
        this.listener = listener;
    }

    public interface TextChangeListener {
        void onTextChange(int num);
    }
}
