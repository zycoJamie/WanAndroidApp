package com.example.levi.wanandroidapp.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.levi.wanandroidapp.R;

/**
 * Author: Levi
 * CreateDate: 2018/10/17 15:32
 */
public class CommonDialog extends Dialog {

    private Context mContext;
    private TextView mTitleTv;
    private TextView mContentTv;
    private TextView mPositiveBtn;
    private TextView mNegativeBtn;
    private View.OnClickListener onDefaultClickListener = v -> cancel();
    private View.OnClickListener onPositiveListener = onDefaultClickListener;
    private View.OnClickListener onNegativeListener = onDefaultClickListener;
    private String title;
    private String message;
    private String positiveText;
    private String negativeText;


    public CommonDialog(Context context) {
        super(context, R.style.Dialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_logout);
        mTitleTv = findViewById(R.id.dialog_title);
        mContentTv = findViewById(R.id.dialog_content);
        mNegativeBtn = findViewById(R.id.dialog_cancel);
        mPositiveBtn = findViewById(R.id.dialog_sure);
    }

    /**
     * 显示dialog
     */
    @Override
    public void show() {
        super.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindData();
    }

    private void bindData() {
        if (!TextUtils.isEmpty(title)) {
            mTitleTv.setText(title);
        }
        if (!TextUtils.isEmpty(message)) {
            mContentTv.setText(message);
            mContentTv.setVisibility(View.VISIBLE);
        }
        mPositiveBtn.setOnClickListener(onPositiveListener);
        if (!TextUtils.isEmpty(positiveText)) {
            mPositiveBtn.setText(positiveText);
        }
        mNegativeBtn.setOnClickListener(onNegativeListener);
        if (!TextUtils.isEmpty(negativeText)) {
            mNegativeBtn.setText(negativeText);
        }
    }

    public static class Builder {
        private CommonDialog mDialog;

        public Builder(Context context) {
            mDialog = new CommonDialog(context);
        }

        /**
         * 设置dialog的标题
         */
        public Builder setTitle(String title) {
            mDialog.title = title;
            return this;
        }

        /**
         * 设置dialog的内容
         */
        public Builder setContent(String content) {
            mDialog.message = content;
            return this;
        }

        /**
         * 设置positive button的点击事件
         */
        public Builder setPositiveClickListener(View.OnClickListener onClickListener) {
            mDialog.onPositiveListener = onClickListener;
            return this;
        }

        /**
         * 设置negative button的点击事件
         */
        public Builder setNegativeClickListener(View.OnClickListener onClickListener) {
            mDialog.onPositiveListener = onClickListener;
            return this;
        }

        /**
         * 设置是否dialog能取消
         */
        public Builder setCancelable(boolean cancelable) {
            mDialog.setCancelable(cancelable);
            return this;
        }

        /**
         * Builder构造完属性后，返回dialog对象
         */
        public CommonDialog create() {
            return mDialog;
        }
    }


}
