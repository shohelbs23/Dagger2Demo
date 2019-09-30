package com.dagger2demo.callback;


import androidx.fragment.app.Fragment;

public interface CommunicatorFragmentInterface {

    void setContentFragment(Fragment fragment, boolean addToBackStack);

    void addContentFragment(Fragment fragment, boolean addToBackStack);
}