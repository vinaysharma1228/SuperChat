package com.rbhagat.superchat.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.rbhagat.superchat.Fragments.callsFragments;
import com.rbhagat.superchat.Fragments.chatsFragments;
import com.rbhagat.superchat.Fragments.statusFragments;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

//    public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
//        super(fm, behavior);
//    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0: return new chatsFragments();
            case 1: return new statusFragments();
            case 2: return new callsFragments();
            default: return new chatsFragments();
        }

    }

    @Override
    public int getCount() {

        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title=null;

        if (position==0)
        {
            title="CHATS";
        }
        if (position==1)
        {
            title="SuperChatGPT";
        }
        if (position==2)
        {
            title="CALLS";
        }
        return title;
    }
}
