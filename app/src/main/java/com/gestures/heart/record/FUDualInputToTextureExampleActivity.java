package com.gestures.heart.record;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.opengl.EGL14;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.faceunity.wrapper.faceunity;
import com.gestures.heart.R;
import com.gestures.heart.base.utils.Config;
import com.gestures.heart.base.view.CommonPopupWindow;
import com.gestures.heart.base.view.SectionProgressBar;
import com.gestures.heart.base.view.tab.CommonTabLayout;
import com.gestures.heart.base.view.tab.listener.OnTabSelectListener;
import com.gestures.heart.camera.AspectFrameLayout;
import com.gestures.heart.camera.CameraUtils;
import com.gestures.heart.camera.EffectAndFilterSelectAdapter;
import com.gestures.heart.camera.MiscUtil;
import com.gestures.heart.camera.authpack;
import com.gestures.heart.camera.encoder.TextureMovieEncoder;
import com.gestures.heart.camera.gles.CameraClipFrameRect;
import com.gestures.heart.camera.gles.FullFrameRect;
import com.gestures.heart.camera.gles.LandmarksPoints;
import com.gestures.heart.camera.gles.Texture2dProgram;
import com.gestures.heart.util.Constants;
import com.gestures.heart.util.FileUtils;
import com.gestures.heart.util.VideoUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.gestures.heart.camera.encoder.TextureMovieEncoder.IN_RECORDING;
import static com.gestures.heart.camera.encoder.TextureMovieEncoder.START_RECORDING;


/**
 * 这个Activity演示了从Camera取数据,用fuDualInputToTexure处理并预览展示
 * 所谓dual input，指从cpu和gpu同时拿数据，
 * cpu拿到的是nv21的byte数组，gpu拿到的是对应的texture
 * <p>
 * Created by lirui on 2016/12/13.
 */

@SuppressWarnings("deprecation")
public class FUDualInputToTextureExampleActivity extends AppCompatActivity
        implements Camera.PreviewCallback,
        SurfaceTexture.OnFrameAvailableListener, View.OnClickListener {

    final static String TAG = "FUDualInputToTextureEg";

    Camera mCamera;

    GLSurfaceView glSf;
    GLRenderer glRenderer;


    int cameraWidth = 1280;
    int cameraHeight = 720;

    byte[] mCameraNV21Byte;
    byte[] fuImgNV21Bytes;

    int mFrameId = 0;

    static int mFaceBeautyItem = 0; //美颜道具
    static int mEffectItem = 0; //贴纸道具
    static int mGestureItem = 0; //手势道具
    static int[] itemsArray = {mFaceBeautyItem, mEffectItem, mGestureItem};

    long resumeTimeStamp;
    boolean isFirstOnFrameAvailable;
    long frameAvailableTimeStamp;

    boolean VERBOSE_LOG = false;

    float mFaceBeautyColorLevel = 0.2f;
    float mFaceBeautyBlurLevel = 6.0f;
    float mFaceBeautyCheekThin = 1.0f;
    float mFaceBeautyEnlargeEye = 0.5f;
    float mFaceBeautyRedLevel = 0.5f;
    int mFaceShape = 3;
    float mFaceShapeLevel = 0.5f;

    String mFilterName = EffectAndFilterSelectAdapter.FILTERS_NAME[0];

    boolean isNeedEffectItem = true;
    static String mEffectFileName = EffectAndFilterSelectAdapter.EFFECT_ITEM_FILE_NAME[1];

    int currentCameraType = Camera.CameraInfo.CAMERA_FACING_FRONT;

    boolean mUseBeauty = true;

    int cameraDataAlreadyCount = 0;
    final Object prepareCameraDataLock = new Object();
    boolean isNeedSwitchCameraSurfaceTexture = true;

    TextureMovieEncoder mTextureMovieEncoder;

    boolean mUseGesture = false;

    HandlerThread mCreateItemThread;
    Handler mCreateItemHandler;

    Context mContext;

    boolean isBenchmarkFPS = true;
    boolean isBenchmarkTime = false;

    boolean isInPause = false;

    boolean isInAvatarMode = false;

    boolean boostBestCameraFPS = false;


    private RecyclerView mEffectRecyclerView;
    private EffectAndFilterSelectAdapter mEffectRecyclerAdapter;
    private BottomSheetBehavior<View> behavior;
    protected ImageView ivRecord;

    private CameraManager mCameraManager;


    private boolean mLightStatus = false;
    private int mRecordStatus = 0;
    private boolean mBeauty = true;//是否开启美颜
    private View ivToolMenu;
    private MainHandler mMainHandler;
    private RecordData mRecordData;
    private long mStartTime;//单位ms
    private long mStopTime;//单位ms
    private long mCurrentTime;//单位ms
    private long mCurrentTotalDuration;//单位ms
    private String mCurrentVideo;
    private List<String> mVideoList;
    private SectionProgressBar mSectionProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_base);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = 0.7f;
        getWindow().setAttributes(params);

        mContext = this;

        mMainHandler = new MainHandler(this);

        glSf = (GLSurfaceView) findViewById(R.id.glsv);
        glSf.setEGLContextClientVersion(2);
        glRenderer = new GLRenderer();
        glSf.setRenderer(glRenderer);
        glSf.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        mCreateItemThread = new HandlerThread("CreateItemThread");
        mCreateItemThread.start();
        mCreateItemHandler = new CreateItemHandler(mCreateItemThread.getLooper(), mContext);

        initData();
        initView();
    }

    private void initData() {
        mRecordData = new RecordData();
        mVideoList = new ArrayList<>();
    }


    private void initView() {
        //进度
        mSectionProgressBar = (SectionProgressBar) findViewById(R.id.videoProgressView);
        mSectionProgressBar.setFirstPointTime(Config.DEFAULT_MIN_RECORD_DURATION);
        mSectionProgressBar.setTotalTime(this, Config.DEFAULT_MAX_RECORD_DURATION);

        // 删除预选中  回调
        mSectionProgressBar.setDelSelectedBar(new SectionProgressBar.DelSelectedBar() {
            @Override
            public void isSelected(final boolean bool) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        mDeleteBtn.setBackgroundResource(bool ? R.mipmap.btn_del_back_b : R.mipmap.btn_del_back_a);
                    }
                });
            }
        });

        //顶部菜单
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.ivSwitchCamera).setOnClickListener(this);
        findViewById(R.id.tvNext).setOnClickListener(this);


        //贴纸
        mEffectRecyclerView = (RecyclerView) findViewById(R.id.effect_recycle_view);
        mEffectRecyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 4));
        mEffectRecyclerAdapter = new EffectAndFilterSelectAdapter(mEffectRecyclerView, EffectAndFilterSelectAdapter.RECYCLEVIEW_TYPE_EFFECT);
        mEffectRecyclerAdapter.setOnItemSelectedListener(new EffectAndFilterSelectAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int itemPosition) {
                Log.d(TAG, "effect item selected " + itemPosition);
                onEffectItemSelected(EffectAndFilterSelectAdapter.EFFECT_ITEM_FILE_NAME[itemPosition]);
                //showHintText(mEffectRecyclerAdapter.getHintStringByPosition(itemPosition));
            }
        });
        mEffectRecyclerView.setAdapter(mEffectRecyclerAdapter);

        //底部按钮
        findViewById(R.id.iv_face_btn).setOnClickListener(this);
        ivRecord = (ImageView) findViewById(R.id.ivRecord);
        ivRecord.setOnClickListener(this);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);

        findViewById(R.id.ivRecord).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
//                    startProgressView();
                } else if (action == MotionEvent.ACTION_UP) {
//                    stopProgressView();
                }
                return false;
            }
        });


        final CommonTabLayout topTabLayout = (CommonTabLayout) findViewById(R.id.layout_record_tab);
        topTabLayout.setTabData(Config.getRecordTabData());
        topTabLayout.setCurrentTab(0);

        topTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
//                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });


        initPopupWindow();

    }

    // 删除 视频端 (回删)
    private boolean isSelectedLast = false;

    public void onClickDelete(View v) {
        isSelectedLast = !isSelectedLast;
        if (isSelectedLast) {  // 预选中
            mSectionProgressBar.setSelectedLast(isSelectedLast);
        } else {
            if (true) {  // true ： 删除 本段视频失败   删除成功不提示
//                Log.d(this, "回删视频段失败");
            } else {
                isSelectedLast = false;
            }
        }
    }

    /**
     // 录制第1段 log
     VideoRecordActivity: onRecordStarted :  1512643573116
     VideoRecordActivity: onSectionIncreased  1968 totalDuration: 1968 sectionCount: 1
     VideoRecordActivity: onRecordStopped 1512643575075
     // 录制第2段  log
     VideoRecordActivity: onRecordStarted :  1512643578236
     VideoRecordActivity: onSectionIncreased  1468 totalDuration: 3436 sectionCount: 2
     VideoRecordActivity: onRecordStopped 1512643579696
     // 录制第3段  log
     VideoRecordActivity: onRecordStarted :  1512643582129
     VideoRecordActivity: onSectionIncreased  1965 totalDuration: 5401 sectionCount: 3
     VideoRecordActivity: onRecordStopped 1512643584085
     // 录制第4段  log
    VideoRecordActivity: onRecordStarted :  1512643587590
    VideoRecordActivity: onSectionIncreased  879 totalDuration: 6280 sectionCount: 4
     VideoRecordActivity: onRecordStopped 1512643588464
     // 回删 log
     VideoRecordActivity: onSectionDecreased 879 totalDuration: 5401 sectionCount: 3
     * */
    /**
     *  开始录 进度  UI 端调用
     * */
    public void onRecordStarted() {
        Log.i(TAG, "onRecordStarted : " + System.currentTimeMillis());
        mSectionProgressBar.setSelectedLast(isSelectedLast = false);
        mSectionProgressBar.setCurrentState(SectionProgressBar.State.START);
    }

    /***
     *  停止刷新 进度  UI端调用
     * */
    public void onRecordStopped() {
        Log.i(TAG, "onRecordStopped ：  " + System.currentTimeMillis());
        mSectionProgressBar.setCurrentState(SectionProgressBar.State.PAUSE);
    }

    /***
     *   ####  录制端调用
     *   增加 视频段 调用
     *   incDuration : 本段视频 结束时间 - 开始时间
     *   totalDuration ： 所有小段视频  总时间
     *   sectionCount ： 段数
     * */
    public void onSectionIncreased(long incDuration, long totalDuration, int sectionCount) {
        Log.i(TAG, "onSectionIncreased    incDuration: " + incDuration + " totalDuration: " + totalDuration + " sectionCount: " + sectionCount);
        onSectionCountChanged(sectionCount, totalDuration);
        mSectionProgressBar.addBreakPointTime(totalDuration);
    }

    /***
     *
     * ####  录制端调用
     *  回删视频段 调用
     *  decDuration  ： 回删段 毫秒数
     *  totalDuration ： 剩余所有段 的 总毫秒数
     *  sectionCount ： 剩余段数
     * */
    public void onSectionDecreased(long decDuration, long totalDuration, int sectionCount) {
        Log.i(TAG, "onSectionDecreased    decDuration: " + decDuration + " totalDuration: " + totalDuration + " sectionCount: " + sectionCount);
        onSectionCountChanged(sectionCount, totalDuration);
        mSectionProgressBar.setSelectedLast(false);
        mSectionProgressBar.removeLastBreakPoint();
    }

    // 刷新 页面
    private void onSectionCountChanged(final int count, final long totalTime) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                mDeleteBtn.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
//                mVideoPlayBtn.setVisibility(totalTime >= RecordSettings.DEFAULT_MIN_RECORD_DURATION ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void initPopupWindow() {
        final CommonPopupWindow popWin = new CommonPopupWindow(this, R.layout.layout_record_tools_win, 520, ViewGroup.LayoutParams.WRAP_CONTENT) {
            private ImageView iv_flash_btn, iv_delay_btn, iv_beauty_btn;
            private TextView tv_flash_text, tv_delay_text, tv_beauty_text;

            @Override
            protected void initView() {
                View view = getContentView();

                iv_flash_btn = view.findViewById(R.id.iv_flash_btn);
                iv_delay_btn = view.findViewById(R.id.iv_delay_btn);
                iv_beauty_btn = view.findViewById(R.id.iv_beauty_btn);
                tv_flash_text = view.findViewById(R.id.tv_flash_text);
                tv_delay_text = view.findViewById(R.id.tv_delay_text);
                tv_beauty_text = view.findViewById(R.id.tv_beauty_text);
            }

            @Override
            protected void initEvent() {
                iv_flash_btn.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        // 闪光灯开关
                        lightSwitch(mLightStatus);
                        mLightStatus = !mLightStatus;
                    }
                });
                iv_delay_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //延时拍摄
                        new CountDownTimer(3000, 1000) {

                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                if (mRecordStatus == 0) {
                                    onStartRecording(mRecordData);
                                    mRecordStatus ^= 1;
                                } else {
                                    Toast.makeText(FUDualInputToTextureExampleActivity.this, "录制中", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }.start();
                    }
                });

                iv_beauty_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mBeauty = !mBeauty;
                    }
                });
            }
        };

        final CommonPopupWindow.LayoutGravity layoutGravity = new CommonPopupWindow.LayoutGravity(
                CommonPopupWindow.LayoutGravity.ALIGN_LEFT
                        | CommonPopupWindow.LayoutGravity.TO_BOTTOM);
        layoutGravity.setHoriGravity(CommonPopupWindow.LayoutGravity.CENTER_HORI);

        ivToolMenu = findViewById(R.id.iv_tool_menu);
        ivToolMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWin.showBashOfAnchor(ivToolMenu, layoutGravity, 0, 0);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.ivSwitchCamera:
                onCameraChange();
                break;
            case R.id.tvNext:
                VideoUtils.merge(mVideoList, VideoUtils.createOutputFile4Video(Constants.OUTPUT_PATH));
                break;
            case R.id.iv_face_btn:
                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                break;
            case R.id.ivRecord:
//                if (mRecordStatus == 0) {
//                    onStartRecording(mRecordData);
//                    mRecordStatus ^= 1;
//
//                } else {
//                    onStopRecording(mRecordData);
//                    mRecordStatus ^= 1;
//                }
                break;

        }
    }

    final int PREVIEW_BUFFER_COUNT = 3;
    byte[][] previewCallbackBuffer;

    @Override
    protected void onResume() {
        Log.e(TAG, "onResume");

        resumeTimeStamp = System.nanoTime();
        isFirstOnFrameAvailable = true;

        isInPause = false;

        super.onResume();

        openCamera(currentCameraType, cameraWidth, cameraHeight);

        /**
         * 请注意这个地方, camera返回的图像并不一定是设置的大小（因为可能并不支持）
         */
        Camera.Size size = mCamera.getParameters().getPreviewSize();
        cameraWidth = size.width;
        cameraHeight = size.height;
        Log.e(TAG, "open camera size width : " + size.width + " height : " + size.height);

        AspectFrameLayout aspectFrameLayout = (AspectFrameLayout) findViewById(R.id.afl);
        aspectFrameLayout.setAspectRatio(1.0f * cameraHeight / cameraWidth);

        glSf.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause");

        isInPause = true;

        super.onPause();

        mCreateItemHandler.removeMessages(CreateItemHandler.HANDLE_CREATE_ITEM);

        releaseCamera();

        glSf.queueEvent(new Runnable() {
            @Override
            public void run() {
                glRenderer.notifyPause();
                glRenderer.destroySurfaceTexture();

                itemsArray[1] = mEffectItem = 0;
                itemsArray[0] = mFaceBeautyItem = 0;
                //Note: 切忌使用一个已经destroy的item
                faceunity.fuDestroyAllItems();
                //faceunity.fuDestroyItem(mEffectItem);
                //faceunity.fuDestroyItem(mFaceBeautyItem);
                isNeedEffectItem = true;
                faceunity.fuOnDeviceLost();
                // faceunity.fuClearReadbackRelated();
                mFrameId = 0;
            }
        });

        glSf.onPause();

        lastOneHundredFrameTimeStamp = 0;
        oneHundredFrameFUTime = 0;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if (VERBOSE_LOG) {
            Log.e(TAG, "onPreviewFrame len " + data.length);
            Log.e(TAG, "onPreviewThread " + Thread.currentThread());
        }
        mCameraNV21Byte = isInPause ? null : data;
        mCamera.addCallbackBuffer(data);
        synchronized (prepareCameraDataLock) {
            cameraDataAlreadyCount++;
            prepareCameraDataLock.notify();
        }
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        if (isFirstOnFrameAvailable) {
            frameAvailableTimeStamp = System.nanoTime();
            isFirstOnFrameAvailable = false;
            Log.e(TAG, "first frame available time cost " +
                    (frameAvailableTimeStamp - resumeTimeStamp) / MiscUtil.NANO_IN_ONE_MILLI_SECOND);
        }
        if (VERBOSE_LOG) {
            Log.e(TAG, "onFrameAvailable");
        }
        synchronized (prepareCameraDataLock) {
            cameraDataAlreadyCount++;
            prepareCameraDataLock.notify();
        }
    }

    /**
     * set preview and start preview after the surface created
     */
    private void handleCameraStartPreview(SurfaceTexture surfaceTexture) {
        Log.e(TAG, "handleCameraStartPreview");

        if (previewCallbackBuffer == null) {
            Log.e(TAG, "allocate preview callback buffer");
            previewCallbackBuffer = new byte[PREVIEW_BUFFER_COUNT][cameraWidth * cameraHeight * 3 / 2];
        }
        mCamera.setPreviewCallbackWithBuffer(this);
        for (int i = 0; i < PREVIEW_BUFFER_COUNT; i++)
            mCamera.addCallbackBuffer(previewCallbackBuffer[i]);
        try {
            mCamera.setPreviewTexture(surfaceTexture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        surfaceTexture.setOnFrameAvailableListener(this);
        mCamera.startPreview();
    }

    long lastOneHundredFrameTimeStamp = 0;
    int currentFrameCnt = 0;
    long oneHundredFrameFUTime = 0;

    class GLRenderer implements GLSurfaceView.Renderer {

        FullFrameRect mFullScreenFUDisplay;
        FullFrameRect mFullScreenCamera;

        int mCameraTextureId;
        SurfaceTexture mCameraSurfaceTexture;

        boolean isFirstCameraOnDrawFrame;

        int faceTrackingStatus = 0;
        int systemErrorStatus = 0;//success number

        CameraClipFrameRect cameraClipFrameRect;

        LandmarksPoints landmarksPoints;
        float[] landmarksData = new float[150];

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            Log.e(TAG, "onSurfaceCreated fu version " + faceunity.fuGetVersion());

            mFullScreenFUDisplay = new FullFrameRect(new Texture2dProgram(
                    Texture2dProgram.ProgramType.TEXTURE_2D));
            mFullScreenCamera = new FullFrameRect(new Texture2dProgram(
                    Texture2dProgram.ProgramType.TEXTURE_EXT));
            mCameraTextureId = mFullScreenCamera.createTextureObject();

            cameraClipFrameRect = new CameraClipFrameRect(0.4f, 0.4f * 0.8f); //clip 20% vertical
            landmarksPoints = new LandmarksPoints();//如果有证书权限可以获取到的话，绘制人脸特征点

            switchCameraSurfaceTexture();

            try {
                InputStream is = getAssets().open("v3.mp3");
                byte[] v3data = new byte[is.available()];
                int len = is.read(v3data);
                is.close();
                faceunity.fuSetup(v3data, null, authpack.A());
                //faceunity.fuSetMaxFaces(1);//设置最大识别人脸数目
                Log.e(TAG, "fuSetup v3 len " + len);

                if (mUseBeauty) {
                    is = getAssets().open("face_beautification.mp3");
                    byte[] itemData = new byte[is.available()];
                    len = is.read(itemData);
                    Log.e(TAG, "beautification len " + len);
                    is.close();
                    mFaceBeautyItem = faceunity.fuCreateItemFromPackage(itemData);
                    itemsArray[0] = mFaceBeautyItem;
                }

                if (mUseGesture) {
                    is = getAssets().open("heart.mp3");
                    byte[] itemData = new byte[is.available()];
                    len = is.read(itemData);
                    Log.e(TAG, "heart len " + len);
                    is.close();
                    mGestureItem = faceunity.fuCreateItemFromPackage(itemData);
                    itemsArray[2] = mGestureItem;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            isFirstCameraOnDrawFrame = true;
        }

        public void switchCameraSurfaceTexture() {
            Log.e(TAG, "switchCameraSurfaceTexture");
            isNeedSwitchCameraSurfaceTexture = false;
            if (mCameraSurfaceTexture != null) {
                faceunity.fuOnCameraChange();
                destroySurfaceTexture();
            }
            mCameraSurfaceTexture = new SurfaceTexture(mCameraTextureId);
            Log.e(TAG, "send start camera message");
            mMainHandler.sendMessage(mMainHandler.obtainMessage(
                    MainHandler.HANDLE_CAMERA_START_PREVIEW,
                    mCameraSurfaceTexture));
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            Log.e(TAG, "onSurfaceChanged " + width + " " + height);
            GLES20.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            if (VERBOSE_LOG) {
                Log.e(TAG, "onDrawFrame");
            }

            if (isInPause) {
                //glSf.requestRender();
                return;
            }

            if (isNeedSwitchCameraSurfaceTexture) {
                switchCameraSurfaceTexture();
            }

            if (VERBOSE_LOG) {
                Log.e(TAG, "after switchCameraSurfaceTexture");
            }

            /**
             * If camera texture data not ready there will be low possibility in meizu note3 which maybe causing black screen.
             */
            while (cameraDataAlreadyCount < 2) {
                if (VERBOSE_LOG) {
                    Log.e(TAG, "while cameraDataAlreadyCount < 2");
                }
                if (isFirstCameraOnDrawFrame) {
                    glSf.requestRender();
                    return;
                }
                synchronized (prepareCameraDataLock) {
                    //block until new camera frame comes.
                    try {
                        prepareCameraDataLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            isFirstCameraOnDrawFrame = false;

            if (++currentFrameCnt == 100) {
                currentFrameCnt = 0;
                long tmp = System.nanoTime();
                if (isBenchmarkFPS)
                    Log.e(TAG, "dualInput FPS : " + (1000.0f * MiscUtil.NANO_IN_ONE_MILLI_SECOND / ((tmp - lastOneHundredFrameTimeStamp) / 100.0f)));
                lastOneHundredFrameTimeStamp = tmp;
                if (isBenchmarkTime)
                    Log.e(TAG, "dualInput cost time avg : " + oneHundredFrameFUTime / 100.f / MiscUtil.NANO_IN_ONE_MILLI_SECOND);
                oneHundredFrameFUTime = 0;
            }

            /**
             * 获取camera数据, 更新到texture
             */
            float[] mtx = new float[16];
            if (mCameraSurfaceTexture != null) {
                try {
                    mCameraSurfaceTexture.updateTexImage();
                    mCameraSurfaceTexture.getTransformMatrix(mtx);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else
                throw new RuntimeException("HOW COULD IT HAPPEN!!! mCameraSurfaceTexture is null!!!");

            final int isTracking = faceunity.fuIsTracking();

            //TODO comment
//            if (isTracking != faceTrackingStatus) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (isTracking == 0) {
//                            mFaceTrackingStatusImageView.setVisibility(View.VISIBLE);
//                            Arrays.fill(landmarksData, 0);
//                        } else {
//                            mFaceTrackingStatusImageView.setVisibility(View.INVISIBLE);
//                        }
//                    }
//                });
//                faceTrackingStatus = isTracking;
//            }
            if (VERBOSE_LOG) {
                Log.e(TAG, "isTracking " + isTracking);
            }

            final int systemError = faceunity.fuGetSystemError();
            if (systemError != systemErrorStatus) {
                systemErrorStatus = systemError;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "system error " + systemError + " " + faceunity.fuGetSystemErrorString(systemError));
                        //TODO comment
//                        tvSystemError.setText(faceunity.fuGetSystemErrorString(systemError));
                    }
                });
            }

            if (isNeedEffectItem) {
                isNeedEffectItem = false;
                mCreateItemHandler.sendEmptyMessage(CreateItemHandler.HANDLE_CREATE_ITEM);
            }

            if (mBeauty) {
                faceunity.fuItemSetParam(mFaceBeautyItem, "color_level", mFaceBeautyColorLevel);
                faceunity.fuItemSetParam(mFaceBeautyItem, "blur_level", mFaceBeautyBlurLevel);
                faceunity.fuItemSetParam(mFaceBeautyItem, "filter_name", mFilterName);
                faceunity.fuItemSetParam(mFaceBeautyItem, "cheek_thinning", mFaceBeautyCheekThin);
                faceunity.fuItemSetParam(mFaceBeautyItem, "eye_enlarging", mFaceBeautyEnlargeEye);
                faceunity.fuItemSetParam(mFaceBeautyItem, "face_shape", mFaceShape);
                faceunity.fuItemSetParam(mFaceBeautyItem, "face_shape_level", mFaceShapeLevel);
                faceunity.fuItemSetParam(mFaceBeautyItem, "red_level", mFaceBeautyRedLevel);
            } else {
                faceunity.fuItemSetParam(mFaceBeautyItem, "color_level", 0);
                faceunity.fuItemSetParam(mFaceBeautyItem, "blur_level", 0);
                faceunity.fuItemSetParam(mFaceBeautyItem, "filter_name", 0);
                faceunity.fuItemSetParam(mFaceBeautyItem, "cheek_thinning", 0);
                faceunity.fuItemSetParam(mFaceBeautyItem, "eye_enlarging", 0);
                faceunity.fuItemSetParam(mFaceBeautyItem, "face_shape", 0);
                faceunity.fuItemSetParam(mFaceBeautyItem, "face_shape_level", 0);
                faceunity.fuItemSetParam(mFaceBeautyItem, "red_level", 0);
            }

            //faceunity.fuItemSetParam(mFaceBeautyItem, "use_old_blur", 1);

            if (mCameraNV21Byte == null || mCameraNV21Byte.length == 0) {
                Log.e(TAG, "camera nv21 bytes null");
                glSf.requestRender();
                glSf.requestRender();
                return;
            }

            boolean isOESTexture = true; //Tip: camera texture类型是默认的是OES的，和texture 2D不同
            int flags = isOESTexture ? faceunity.FU_ADM_FLAG_EXTERNAL_OES_TEXTURE : 0;
            boolean isNeedReadBack = false; //是否需要写回，如果是，则入参的byte[]会被修改为带有fu特效的；支持写回自定义大小的内存数组中，即readback custom img
            flags = isNeedReadBack ? flags | faceunity.FU_ADM_FLAG_ENABLE_READBACK : flags;
            if (isNeedReadBack) {
                if (fuImgNV21Bytes == null) {
                    fuImgNV21Bytes = new byte[mCameraNV21Byte.length];
                }
                System.arraycopy(mCameraNV21Byte, 0, fuImgNV21Bytes, 0, mCameraNV21Byte.length);
            } else {
                fuImgNV21Bytes = mCameraNV21Byte;
            }
            flags |= currentCameraType == Camera.CameraInfo.CAMERA_FACING_FRONT ? 0 : faceunity.FU_ADM_FLAG_FLIP_X;

            if (isInAvatarMode)
                faceunity.fuItemSetParam(mEffectItem, "default_rotation_mode", (currentCameraType == Camera.CameraInfo.CAMERA_FACING_FRONT) ? 1 : 3);

            long fuStartTime = System.nanoTime();
            /*
             * 这里拿到fu处理过后的texture，可以对这个texture做后续操作，如硬编、预览。
             */
            int fuTex = faceunity.fuDualInputToTexture(fuImgNV21Bytes, mCameraTextureId, flags,
                    cameraWidth, cameraHeight, mFrameId++, itemsArray);
            long fuEndTime = System.nanoTime();
            oneHundredFrameFUTime += fuEndTime - fuStartTime;

            //int fuTex = faceunity.fuBeautifyImage(mCameraTextureId, flags,
            //            cameraWidth, cameraHeight, mFrameId++, new int[] {mEffectItem, mFaceBeautyItem});
            //mFullScreenCamera.drawFrame(mCameraTextureId, mtx);
            if (mFullScreenFUDisplay != null) mFullScreenFUDisplay.drawFrame(fuTex, mtx);
            else
                throw new RuntimeException("HOW COULD IT HAPPEN!!! mFullScreenFUDisplay is null!!!");

            /**
             * 绘制Avatar模式下的镜头内容以及landmarks
             **/
            if (isInAvatarMode) {
                cameraClipFrameRect.drawFrame(mCameraTextureId, mtx);
                faceunity.fuGetFaceInfo(0, "landmarks", landmarksData);
                landmarksPoints.refresh(landmarksData, cameraWidth, cameraHeight, 0.1f, 0.8f, currentCameraType != Camera.CameraInfo.CAMERA_FACING_FRONT);
                landmarksPoints.draw();
            }

            if (mTextureMovieEncoder != null && mTextureMovieEncoder.checkRecordingStatus(START_RECORDING)) {

                mCurrentVideo = VideoUtils.createTempOutputFile4Video(Constants.OUTPUT_PATH_TEMP, mRecordData.currentVideoIndex);
                File outFile = new File(mCurrentVideo);
                mTextureMovieEncoder.startRecording(new TextureMovieEncoder.EncoderConfig(
                        outFile, cameraHeight, cameraWidth,
                        3000000, EGL14.eglGetCurrentContext(), mCameraSurfaceTexture.getTimestamp()
                ));

                //forbid click until start or stop success
                mTextureMovieEncoder.setOnEncoderStatusUpdateListener(new TextureMovieEncoder.OnEncoderStatusUpdateListener() {
                    @Override
                    public void onStartSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e(TAG, "start encoder success");
                                //TODO comment
//                                ivRecord.setVisibility(View.VISIBLE);
                            }
                        });
                    }

                    @Override
                    public void onStopSuccess() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e(TAG, "stop encoder success");
                                //TODO comment
                                ivRecord.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(FUDualInputToTextureExampleActivity.this, "video file saved to "
                                + mCurrentVideo, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            if (mTextureMovieEncoder != null && mTextureMovieEncoder.checkRecordingStatus(IN_RECORDING)) {
                mTextureMovieEncoder.setTextureId(mFullScreenFUDisplay, fuTex, mtx);
                mTextureMovieEncoder.frameAvailable(mCameraSurfaceTexture);
            }

            if (!isInPause) glSf.requestRender();

            mCurrentTime = System.currentTimeMillis();
            mCurrentTotalDuration = mRecordData.totalVideoDuration + mCurrentTime - mStartTime;

//            videoProgressView.setProgressTime(mCurrentTotalDuration);
            if (mCurrentTotalDuration >= Config.DEFAULT_MAX_RECORD_DURATION) {//录制了15s
                onStopRecording(mRecordData);
            }
        }

        public void notifyPause() {
            faceTrackingStatus = 0;

            if (mTextureMovieEncoder != null && mTextureMovieEncoder.checkRecordingStatus(IN_RECORDING)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivRecord.performClick();
                    }
                });
            }

            if (mFullScreenFUDisplay != null) {
                mFullScreenFUDisplay.release(false);
                mFullScreenFUDisplay = null;
            }

            if (mFullScreenCamera != null) {
                mFullScreenCamera.release(false);
                mFullScreenCamera = null;
            }
        }

        public void destroySurfaceTexture() {
            if (mCameraSurfaceTexture != null) {
                mCameraSurfaceTexture.release();
                mCameraSurfaceTexture = null;
            }
        }
    }

    public int getCurrentCameraType() {
        return currentCameraType;
    }

    static class MainHandler extends Handler {

        static final int HANDLE_CAMERA_START_PREVIEW = 1;

        private WeakReference<FUDualInputToTextureExampleActivity> mActivityWeakReference;

        MainHandler(FUDualInputToTextureExampleActivity activity) {
            mActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            FUDualInputToTextureExampleActivity activity = mActivityWeakReference.get();
            switch (msg.what) {
                case HANDLE_CAMERA_START_PREVIEW:
                    Log.e(TAG, "HANDLE_CAMERA_START_PREVIEW");
                    activity.handleCameraStartPreview((SurfaceTexture) msg.obj);
                    break;
            }
        }
    }

    static class CreateItemHandler extends Handler {

        static final int HANDLE_CREATE_ITEM = 1;

        WeakReference<Context> mContext;

        CreateItemHandler(Looper looper, Context context) {
            super(looper);
            mContext = new WeakReference<Context>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            Context context = mContext.get();
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLE_CREATE_ITEM:
                    try {
                        if (mEffectFileName.equals("none")) {
                            itemsArray[1] = mEffectItem = 0;
                        } else {
                            InputStream is = context.getAssets().open(mEffectFileName);
                            byte[] itemData = new byte[is.available()];
                            int len = is.read(itemData);
                            Log.e("FU", "effect len " + len);
                            is.close();
                            final int tmp = itemsArray[1];
                            itemsArray[1] = mEffectItem = faceunity.fuCreateItemFromPackage(itemData);
                            faceunity.fuItemSetParam(mEffectItem, "isAndroid", 1.0);
                            faceunity.fuItemSetParam(mEffectItem, "rotationAngle",
                                    ((FUDualInputToTextureExampleActivity) mContext.get()).getCurrentCameraType()
                                            == Camera.CameraInfo.CAMERA_FACING_FRONT ? 90 : 270);
                            if (tmp != 0) {
                                faceunity.fuDestroyItem(tmp);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void openCamera(int cameraType, int desiredWidth, int desiredHeight) {
        Log.e(TAG, "openCamera");

        cameraDataAlreadyCount = 0;

        if (mCamera != null) {
            throw new RuntimeException("camera already initialized");
        }

        Camera.CameraInfo info = new Camera.CameraInfo();
        int cameraId = 0;
        int numCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numCameras; i++) {
            Camera.getCameraInfo(i, info);
            if (info.facing == cameraType) {
                cameraId = i;
                mCamera = Camera.open(i);
                currentCameraType = cameraType;
                break;
            }
        }
        if (mCamera == null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(FUDualInputToTextureExampleActivity.this,
                            "Open Camera Failed! Make sure it is not locked!", Toast.LENGTH_SHORT)
                            .show();
                }
            });
            throw new RuntimeException("unable to open camera");
        }

        CameraUtils.setCameraDisplayOrientation(this, cameraId, mCamera);

        Camera.Parameters parameters = mCamera.getParameters();

        /**
         * 设置对焦，会影响camera吞吐速率
         */
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO))
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);

        /**
         * 设置fps
         * */
        if (boostBestCameraFPS) {
            int[] closetFramerate = CameraUtils.closetFramerate(parameters, 30);
            Log.e(TAG, "closet framerate min " + closetFramerate[0] + " max " + closetFramerate[1]);
            parameters.setPreviewFpsRange(closetFramerate[0], closetFramerate[1]);
        }

        CameraUtils.choosePreviewSize(parameters, desiredWidth, desiredHeight);
        mCamera.setParameters(parameters);
    }

    private void releaseCamera() {
        Log.e(TAG, "release camera");
        if (mCamera != null) {
            try {
                mCamera.stopPreview();
                mCamera.setPreviewTexture(null);
                mCamera.setPreviewCallbackWithBuffer(null);
                mCamera.release();
                mCamera = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    protected void onEffectItemSelected(String effectItemName) {
        if (effectItemName.equals(mEffectFileName)) {
            return;
        }
        isInAvatarMode = effectItemName.equals("lixiaolong.bundle");
        mCreateItemHandler.removeMessages(CreateItemHandler.HANDLE_CREATE_ITEM);
        mEffectFileName = effectItemName;
        isNeedEffectItem = true;
    }


    protected void onCameraChange() {
        Log.e(TAG, "onCameraChange");
        synchronized (prepareCameraDataLock) {

            isNeedSwitchCameraSurfaceTexture = true;

            cameraDataAlreadyCount = 0;

            releaseCamera();

            mCameraNV21Byte = null;
            mFrameId = 0;

            if (currentCameraType == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                openCamera(Camera.CameraInfo.CAMERA_FACING_BACK, cameraWidth, cameraHeight);
            } else {
                openCamera(Camera.CameraInfo.CAMERA_FACING_FRONT, cameraWidth, cameraHeight);
            }
        }
    }

    protected void onStartRecording(RecordData recordData) {
        if (mRecordData.totalVideoDuration < Config.DEFAULT_MAX_RECORD_DURATION) {
            MiscUtil.Logger(TAG, "start recording", false);
            mStartTime = System.currentTimeMillis();
            mRecordData.currentVideoIndex += 1;
//            videoProgressView.setCurrentState(VideoProgressView.State.START);
            mTextureMovieEncoder = new TextureMovieEncoder();
        } else {
            Toast.makeText(FUDualInputToTextureExampleActivity.this, "最大录制15秒", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onStopRecording(RecordData recordData) {
        if (mTextureMovieEncoder != null && mTextureMovieEncoder.checkRecordingStatus(IN_RECORDING)) {
            MiscUtil.Logger(TAG, "stop recording", false);
            mStopTime = System.currentTimeMillis();
            recordData.currentVideoDuration = calcCurrentVideoDuration(mStartTime, mStopTime);
            recordData.totalVideoDuration += recordData.currentVideoDuration;
            recordData.currentVideo = mCurrentVideo;
            mVideoList.add(recordData.currentVideo);
//            videoProgressView.setCurrentState(VideoProgressView.State.PAUSE);
            mTextureMovieEncoder.stopRecording();


            if (recordData.totalVideoDuration >= Config.DEFAULT_MAX_RECORD_DURATION) {
                VideoUtils.merge(mVideoList, VideoUtils.createOutputFile4Video(Constants.OUTPUT_PATH));
                FileUtils.deleteAllFiles(Constants.OUTPUT_PATH_TEMP);
            }

        }
    }

    /**
     * 计算当前video录制的时长
     *
     * @param startTime
     * @param stopTime
     * @return
     */
    private int calcCurrentVideoDuration(long startTime, long stopTime) {
        return (int) (stopTime - startTime);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
        mEffectFileName = EffectAndFilterSelectAdapter.EFFECT_ITEM_FILE_NAME[1];

        mCreateItemThread.quit();
        mCreateItemThread = null;
        mCreateItemHandler = null;
    }

    /**
     * 手电筒控制方法
     *
     * @param lightStatus
     * @return
     */
    private void lightSwitch(final boolean lightStatus) {
        if (mCameraManager == null) {
            mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        }
        if (lightStatus) { // 关闭手电筒
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    mCameraManager.setTorchMode("0", false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (mCamera != null) {
                    mCamera.stopPreview();
                    mCamera.release();
                    mCamera = null;
                }
            }
        } else { // 打开手电筒
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    mCameraManager.setTorchMode("0", true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                final PackageManager pm = getPackageManager();
                final FeatureInfo[] features = pm.getSystemAvailableFeatures();
                for (final FeatureInfo f : features) {
                    if (PackageManager.FEATURE_CAMERA_FLASH.equals(f.name)) { // 判断设备是否支持闪光灯
                        if (null == mCamera) {
                            mCamera = Camera.open();
                        }
                        final Camera.Parameters parameters = mCamera.getParameters();
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        mCamera.setParameters(parameters);
                        mCamera.startPreview();
                    }
                }
            }
        }
    }


    static class RecordData {
        public int currentVideoIndex;
        public int currentVideoDuration;// 单位ms
        public String currentVideo;
        public int totalVideoDuration;//单位ms

        public void reset() {
            currentVideoIndex = 0;
            currentVideoDuration = 0;
            currentVideo = null;
            totalVideoDuration = 0;
        }
    }

}
