package io.agora.scene.base.component;

import android.app.Activity;
import android.content.res.Configuration;

import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.elvishew.xlog.XLog;

import io.agora.scene.base.BuildConfig;
import io.agora.scene.base.data.provider.DataRepository;
import io.agora.scene.base.provider.DataRepositoryImpl2;
import io.agora.scene.base.utils.KTVUtil;
import io.reactivex.plugins.RxJavaPlugins;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.onAdaptListener;
import me.jessyan.autosize.utils.ScreenUtils;

public class AgoraApplication extends MultiDexApplication {
    private static AgoraApplication sInstance;


    public static AgoraApplication the() {
        return sInstance;
    }

    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initARouter();
        initAutoSize();
        RxJavaPlugins.setErrorHandler(KTVUtil::logE);
        XLog.init();

        DataRepository.Instance().setDataRepositoryImpl(new DataRepositoryImpl2());
    }

    private void initARouter() {
        if(BuildConfig.DEBUG){
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }

    private void initAutoSize() {
        AutoSizeConfig.getInstance().setOnAdaptListener(new onAdaptListener() {
            @Override
            public void onAdaptBefore(Object o, Activity activity) {
                AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(activity)[0]);
                AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(activity)[1]);
                if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    AutoSizeConfig.getInstance()
                            .setDesignWidthInDp(812)
                            .setDesignHeightInDp(375);
                } else {
                    AutoSizeConfig.getInstance()
                            .setDesignWidthInDp(375)
                            .setDesignHeightInDp(812);
                }
//                if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                    AutoSizeConfig.getInstance()
//                            .setDesignWidthInDp((int) (812 * 1.2))
//                            .setDesignHeightInDp((int) (375 *  1.2));
//                } else {
//                    AutoSizeConfig.getInstance()
//                            .setDesignWidthInDp((int) (375 *  1.2))
//                            .setDesignHeightInDp((int) (812 *  1.2));
//                }
            }

            @Override
            public void onAdaptAfter(Object o, Activity activity) {

            }
        });
    }
}