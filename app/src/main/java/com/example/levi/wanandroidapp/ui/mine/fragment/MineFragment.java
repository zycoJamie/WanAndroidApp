package com.example.levi.wanandroidapp.ui.mine.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.fragment.BaseFragment;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.ui.login.LoginActivity;
import com.example.levi.wanandroidapp.util.app.SharedPreferenceUtil;
import com.example.levi.wanandroidapp.util.app.SkipUtil;
import com.example.levi.wanandroidapp.util.glide.GlideUtil;
import com.example.levi.wanandroidapp.widget.CommonDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: Levi
 * CreateDate: 2018/10/17 9:57
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.iv_avatar)
    ImageView mAvatarIv;
    @BindView(R.id.tv_name)
    TextView mNameTv;
    @BindView(R.id.tv_logout)
    TextView mLogoutTv;

    private boolean isLogin;
    private CommonDialog mDialog;


    public static MineFragment getInstance() {
        return new MineFragment();
    }

    @Override
    protected void initData() {
        isLogin= (boolean) SharedPreferenceUtil.get(mContext, Constant.ISLOGIN,false);
        String username= (String) SharedPreferenceUtil.get(mContext,Constant.USERNAME,Constant.DEFAULT);
        mLogoutTv.setVisibility(isLogin? View.VISIBLE:View.GONE);
        mNameTv.setText(isLogin?username:getString(R.string.mine_tv_username));
        mAvatarIv.setEnabled(!isLogin);
    }

    @Override
    protected void initUI() {
        GlideUtil.loadCircleImage(mContext,R.mipmap.user_avatar,mAvatarIv);
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_mine;
    }

    @OnClick({R.id.iv_avatar,R.id.tv_mine_collection,R.id.tv_about_me,R.id.tv_logout})
    void click(View view){
        switch(view.getId()){
            case R.id.iv_avatar:{
                SkipUtil.overlay(mContext, LoginActivity.class);
                break;
            }
            case R.id.tv_mine_collection:{

                break;
            }
        }
    }
}
