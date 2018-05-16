package com.gestures.heart.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.gestures.heart.R;
import com.gestures.heart.home.fragment.CasinoFragment;
import com.gestures.heart.home.fragment.MeFragment;
import com.gestures.heart.home.fragment.MsgFragment;
import com.gestures.heart.home.fragment.ShortVideoFragment;

import java.util.ArrayList;
import java.util.List;

public class FactoryManage {

    private Fragment shortVideoFragment, casinoFragment, msgFragment, meFragment = null;
    private static FactoryManage factory = null;

    private List<Fragment> fragmentList = new ArrayList<>(4);
    private FactoryManage() {}

    public static FactoryManage getFactory() {
        if (factory == null) {
            synchronized (FactoryManage.class) {
                if (factory == null) {
                    factory = new FactoryManage();
                }
            }
        }
        return factory;
    }

    public void defaultSelected(FragmentActivity mActiviy){
        switchFragmentIndex(mActiviy, 0);
    }

    public void switchFragmentIndex(FragmentActivity mActiviy, int i) {
        FragmentManager manager = mActiviy.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideAllFragment(manager);
        switch (i) {
            case 0:
                if (shortVideoFragment == null) {
                    fragmentList.add(shortVideoFragment = ShortVideoFragment.newInstance());
                    transaction.add(R.id.fl_content, shortVideoFragment, "shortVideoFragment");
                } else {
                    transaction.show(shortVideoFragment);
                }
                break;
            case 1:
                if (casinoFragment == null) {
                    fragmentList.add(casinoFragment = CasinoFragment.newInstance());
                    transaction.add(R.id.fl_content, casinoFragment, "casinoFragment");
                } else {
                    transaction.show(casinoFragment);
                }
                break;
            case 2:
                if (msgFragment == null) {
                    fragmentList.add(msgFragment = MsgFragment.newInstance());
                    transaction.add(R.id.fl_content, msgFragment, "msgFragment");
                } else {
                    transaction.show(msgFragment);
                }
                break;
            case 3:
                if (meFragment == null) {
                    fragmentList.add(meFragment = MeFragment.newInstance());
                    transaction.add(R.id.fl_content, meFragment, "meFragment");
                } else {
                    transaction.show(meFragment);
                }
                break;

            default:
                break;
        }
        transaction.commit();
    }

    private void hideAllFragment(FragmentManager manager) {

        if (fragmentList == null || fragmentList.size() == 0) {
            return;
        }

        FragmentTransaction transaction = manager.beginTransaction();
        for (Fragment fragmentItem : fragmentList) {
            Fragment fragment = manager.findFragmentByTag(fragmentItem.getTag());
            if (fragment != null && !fragment.isHidden()) {
                transaction.hide(fragment);
            }
        }
        transaction.commit();
    }
}
