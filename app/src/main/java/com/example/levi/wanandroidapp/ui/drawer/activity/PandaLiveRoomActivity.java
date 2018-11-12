package com.example.levi.wanandroidapp.ui.drawer.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.levi.wanandroidapp.R;
import com.example.levi.wanandroidapp.base.activity.BaseActivity;
import com.example.levi.wanandroidapp.contract.drawer.PandaLiveContract;
import com.example.levi.wanandroidapp.data.drawer.LiveList;
import com.example.levi.wanandroidapp.media.IjkVideoView;
import com.example.levi.wanandroidapp.media.PlayerManager;
import com.example.levi.wanandroidapp.model.constant.Constant;
import com.example.levi.wanandroidapp.presenter.drawer.PandaLivePresenter;
import com.example.levi.wanandroidapp.util.app.LogUtil;
import com.example.levi.wanandroidapp.util.app.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 未能获取到直播流，目前播放的是主播上传的精彩视频片段
 */
public class PandaLiveRoomActivity extends BaseActivity<PandaLivePresenter> implements PandaLiveContract.View {

    private static final String TAG = PandaLiveRoomActivity.class.getSimpleName();
    @BindView(R.id.video_view)
    IjkVideoView ijkVideoView;
    @BindView(R.id.live_loading)
    View viewLoading;
    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.view_portrait_bottom)
    View viewPortraitBottom;
    @BindView(R.id.view_back)
    View viewBack;
    @BindView(R.id.view_play)
    View viewPlay;
    @BindView(R.id.view_full_screen)
    View viewFullScreen;
    @BindView(R.id.tv_title)
    TextView mTitleTv;
    @BindView(R.id.image_play)
    ImageView mImagePlay;
    @BindView(R.id.rl_video)
    View viewContent;
    @BindView(R.id.image_land_play)
    ImageView mImageLandPlay;
    @BindView(R.id.view_land_play)
    RelativeLayout viewLandPlay;
    @BindView(R.id.view_refresh)
    RelativeLayout viewRefresh;
    @BindView(R.id.view_exit_full_screen)
    View viewExitFullScreen;
    @BindView(R.id.view_landscape_bottom)
    View viewLandscapeBottom;
    @BindView(R.id.iv_control)
    ImageView mImageControl;
    @BindView(R.id.tv_control_name)
    TextView mTvControlName;
    @BindView(R.id.tv_control_num)
    TextView mTvControlNum;
    @BindView(R.id.view_control)
    View viewControl;
    @BindView(R.id.view_share)
    RelativeLayout mShare;

    private LiveList.ItemsBeanX mRoomInfo;
    private int mScreenWidth;
    private PlayerManager mPlayerManager;
    private RelativeLayout.LayoutParams params;
    private AudioManager mAudioManager;
    private int mMaxVolume;
    private int mShowVolume;
    private int mShowLightness;
    private GestureDetector mGestureDetector;
    private String mLiveStreamUrl;
    private int rid; //用于请求直播源

    @Override
    protected void initInject() {
        mActivityComponent.inject(this);
    }

    @Override
    protected void initUI() {
        mRoomInfo = (LiveList.ItemsBeanX) getIntent().getSerializableExtra(Constant.ROOM);
        mTitleTv.setText(mRoomInfo.getName());
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mScreenWidth = metrics.widthPixels;
        initPlayer();
        initLayoutParams();
        initVolumeAndLight();
        addTouchListener();
        setGone(mShare, viewRefresh);
    }

    @Override
    protected void initData() {
        viewLoading.setVisibility(View.VISIBLE);
        rid = mRoomInfo.getUserinfo().getRid();
        mPresenter.getLiveUrl(rid);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_room;
    }

    private void initPlayer() {
        mPlayerManager = new PlayerManager(this);
        mPlayerManager.live(true);
        mPlayerManager.setScaleType(PlayerManager.SCALETYPE_16_9);
    }

    /**
     * 初始化视频宽高
     */
    private void initLayoutParams() {
        params = (RelativeLayout.LayoutParams) viewContent.getLayoutParams();
        params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        params.height = mScreenWidth * 9 / 16;
        ijkVideoView.setLayoutParams(params);
    }

    /**
     * 初始化音量和亮度
     */
    private void initVolumeAndLight() {
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        assert mAudioManager != null;
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mShowVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) * 100 / mMaxVolume;
        mShowLightness = getScreenBrightness();
    }

    /**
     * 获取屏幕亮度
     */
    private int getScreenBrightness() {
        int screenBrightness = 255;
        try {
            screenBrightness = Settings.System.getInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i(TAG, e.getMessage());
        }
        return screenBrightness;
    }

    private void addTouchListener() {
        GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (isLandscape()) {
                    float x1 = e1.getX();
                    float absDistanceX = Math.abs(distanceX);
                    float absDistanceY = Math.abs(distanceY);
                    if (absDistanceX < absDistanceY) {
                        setVisible(viewControl);
                        if (distanceY > 0) { //向上滑动
                            if (x1 >= mScreenWidth * 0.65) {
                                changeVolume(1);
                            } else {
                                changeLightness(1);
                            }
                        } else {
                            if (x1 >= mScreenWidth * 0.65) {
                                changeVolume(-1);
                            } else {
                                changeLightness(-1);
                            }
                        }
                    }
                    return false;
                }
                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                if (isLandscape()) {
                    if (viewTop.getVisibility() == View.VISIBLE && viewLandscapeBottom.getVisibility() == View.VISIBLE) {
                        setGone(viewTop, viewLandscapeBottom);
                    } else {
                        setVisible(viewTop, viewLandscapeBottom);
                    }
                } else {
                    if (viewTop.getVisibility() == View.VISIBLE && viewPortraitBottom.getVisibility() == View.VISIBLE) {
                        setGone(viewTop, viewPortraitBottom);
                    } else {
                        setVisible(viewTop, viewPortraitBottom);
                    }
                }
                return true;
            }
        };
        mGestureDetector = new GestureDetector(this, mGestureListener);
    }

    /**
     * 改变屏幕亮度
     */
    private void changeLightness(int variable) {
        mShowLightness += variable;
        if (mShowLightness > 255) {
            mShowLightness = 255;
        } else if (mShowLightness <= 0) {
            mShowLightness = 0;
        }
        mTvControlName.setText("亮度");
        mImageControl.setImageResource(R.mipmap.ic_light);
        mTvControlNum.setText(mShowLightness * 100 / 255 + "%");
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = mShowLightness / 255f;
        getWindow().setAttributes(lp);
        ijkVideoView.postDelayed(() -> setGone(viewControl), 2000);
    }

    /**
     * 改变音量
     */
    private void changeVolume(int variable) {
        mShowVolume += variable;
        if (mShowVolume > 100) {
            mShowVolume = 100;
        } else if (mShowVolume < 0) {
            mShowVolume = 0;
        }
        mTvControlName.setText("音量");
        mImageControl.setImageResource(R.mipmap.ic_volume);
        int valueVolume = mShowVolume * mMaxVolume / 100;
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, valueVolume, 0);
        ijkVideoView.postDelayed(() -> setGone(viewControl), 2000);
    }

    private boolean isLandscape() {
        return getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }


    private void startLive() {
        mPlayerManager.play(mLiveStreamUrl);
        mPlayerManager.setPlayerStateListener(new PlayerManager.PlayerStateListener() {
            @Override
            public void onLoading() {

            }

            @Override
            public void onPlay() {
                setVisible(viewTop, viewPortraitBottom);
                setInVisible(viewLoading);
                if (viewTop.getVisibility() == View.VISIBLE && viewPortraitBottom.getVisibility() == View.VISIBLE) {
                    ijkVideoView.postDelayed(() -> setGone(viewTop, viewPortraitBottom), 2000);
                }
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError() {
                ToastUtil.show(mActivity, getString(R.string.video_play_error));
            }
        });
        ijkVideoView.setOnCompletionListener(iMediaPlayer -> ToastUtil.show(mActivity, getString(R.string.video_net_error)));
    }

    @OnClick({R.id.view_back, R.id.view_play, R.id.view_full_screen, R.id.view_share, R.id.view_refresh, R.id.view_land_play, R.id.view_exit_full_screen})
    void click(View view) {
        switch (view.getId()) {
            case R.id.view_back: {
                if (isLandscape()) {
                    setOrCancelFullScreen();
                } else {
                    onBackPressedSupport();
                }
                break;
            }
            case R.id.view_land_play: {
                if (ijkVideoView != null && ijkVideoView.isPlaying()) {
                    mImageLandPlay.setImageResource(R.mipmap.ic_pause);
                    ijkVideoView.pause();
                } else {
                    mImageLandPlay.setImageResource(R.mipmap.ic_play);
                    ijkVideoView.start();
                }
                break;
            }
            case R.id.view_play: {
                if (ijkVideoView != null && ijkVideoView.isPlaying()) {
                    mImagePlay.setImageResource(R.mipmap.ic_pause);
                    ijkVideoView.pause();
                } else {
                    mImagePlay.setImageResource(R.mipmap.ic_play);
                    ijkVideoView.start();
                }
                break;
            }
            case R.id.view_full_screen: {
                setOrCancelFullScreen();
                break;
            }
            case R.id.view_exit_full_screen: {
                setOrCancelFullScreen();
                break;
            }

        }
    }

    /**
     * 设置或取消全屏
     */
    private void setOrCancelFullScreen() {
        if (isLandscape()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mPlayerManager.setScaleType(PlayerManager.SCALETYPE_16_9);
            params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            params.height = mScreenWidth * 9 / 16;
            ijkVideoView.setLayoutParams(params);
            setVisible(viewTop, viewPortraitBottom);
            setGone(viewLandscapeBottom);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mPlayerManager.setScaleType(PlayerManager.SCALETYPE_FILLPARENT);
            params.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            params.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            ijkVideoView.setLayoutParams(params);
            setVisible(viewTop, viewLandscapeBottom);
            setGone(viewPortraitBottom);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isLandscape()) {
            setVisible(viewTop, viewLandscapeBottom);
            setGone(viewPortraitBottom);
            if (viewTop.getVisibility() == View.VISIBLE && viewLandscapeBottom.getVisibility() == View.VISIBLE) {
                ijkVideoView.postDelayed(() -> setGone(viewTop, viewLandscapeBottom), 2000);
            }
        } else {
            setVisible(viewTop, viewPortraitBottom);
            setGone(viewLandscapeBottom);
            if (viewTop.getVisibility() == View.VISIBLE && viewPortraitBottom.getVisibility() == View.VISIBLE) {
                ijkVideoView.postDelayed(() -> setGone(viewTop, viewPortraitBottom), 2000);
            }
        }
    }

    /**
     * 将activity的触摸事件传递给GestureDetector
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector != null) {
            mGestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ijkVideoView != null && ijkVideoView.isPlaying()) {
            ijkVideoView.stopBackgroundPlay();
            ijkVideoView.stopPlayback();
            ijkVideoView.release(true);
        }
        if (mPlayerManager != null && mPlayerManager.isPlaying()) {
            mPlayerManager.stop();
            mPlayerManager.onDestroy();
        }
    }

    @Override
    public void getLiveUrlSuccess(String url) {
        LogUtil.i(TAG, "url：" + url);
        mLiveStreamUrl = url;
        startLive();
    }

    @Override
    public void getLiveUrlError(String errMsg) {
        ToastUtil.show(mContext, getString(R.string.video_url_null));
        LogUtil.i(TAG, errMsg);
    }
}
