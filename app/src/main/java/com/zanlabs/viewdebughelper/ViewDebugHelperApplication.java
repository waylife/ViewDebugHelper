package com.zanlabs.viewdebughelper;

import android.app.Application;

import java.util.ArrayList;

public class ViewDebugHelperApplication extends Application {

	private static ViewDebugHelperApplication mInstance = null;

	private  String mLastTopActivityName = "";

    private String mCurrentPackName="";

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance=this;
        mCurrentPackName=getPackageName();
	}

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static ViewDebugHelperApplication getInstance() {
		return mInstance;
	}


    public String getLastTopActivityName() {
        return mLastTopActivityName;
    }

    public void setLastTopActivityName(String lastTopActivityName) {
        this.mLastTopActivityName = lastTopActivityName;
    }

    public String getCurrentPackName() {
        return mCurrentPackName;
    }


    private String mXposeCurrentActivity;
    private ArrayList<String> mXposeFragments=new ArrayList<String>();

    public String getXposeCurrentActivity() {
        return mXposeCurrentActivity;
    }

    public void setXposeCurrentActivity(String xposeCurrentActivity) {
        this.mXposeCurrentActivity = xposeCurrentActivity;
    }

    public ArrayList<String> getXposeFragments() {
        return mXposeFragments;
    }

    public void addXposeFragment(String xposeFrament) {
        this.mXposeFragments.add(xposeFrament);
    }

    public void removeAllXposeFragments() {
        this.mXposeFragments.clear();
    }

    public String getCurrentAllFragmentNames(){
        if(this.mXposeFragments.size()==0) {
            return "";
        }else{
            StringBuilder stringBuilder=new StringBuilder();
            int size=this.mXposeFragments.size();
            for (int i=0;i<size;i++){
                stringBuilder.append(mXposeFragments.get(i));
                if(i!=size-1) {
                    stringBuilder.append("\n");
                }
            }
            return stringBuilder.toString();
        }
    }
}
