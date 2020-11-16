package edu.byu.cs.tweeter.view.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import edu.byu.cs.tweeter.view.LoginFragment;
import edu.byu.cs.tweeter.view.RegisterFragment;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public TabsAdapter(FragmentManager fm, int NoofTabs){
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                LoginFragment login = new LoginFragment();
                return login;
            case 1:
                RegisterFragment register = new RegisterFragment();
                return register;
            default:
                return null;
        }
    }
}
