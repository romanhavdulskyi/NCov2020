package com.demo.app.ncov2020

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.navigation.Navigation
import com.demo.app.basics.mvvm.BaseActivity
import com.demo.app.ncov2020.common.GameNavigatorImpl
import com.demo.app.ncov2020.common.GameNavigatorImpl.Companion.instance

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        //getSupportFragmentManager().beginTransaction().replace(R.id.container, new LoginFragment()).commit();
        instance.setNavController(Navigation.findNavController(this, R.id.nav_host_fragment))
    }

    override fun onDestroy() {
        instance.releaseNavController()
        super.onDestroy()
    } //    @Override
//    public void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mapView.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        mapView.onStop();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
////        if (offlineManager != null) {
////            offlineManager.listOfflineRegions(new OfflineManager.ListOfflineRegionsCallback() {
////                @Override
////                public void onList(OfflineRegion[] offlineRegions) {
////                    if (offlineRegions.length > 0) {
////// delete the last item in the offlineRegions list which will be yosemite offline map
////                        offlineRegions[(offlineRegions.length - 1)].delete(new OfflineRegion.OfflineRegionDeleteCallback() {
////                            @Override
////                            public void onDelete() {
////                                Toast.makeText(
////                                        MainActivity.this,
////                                        "Deleted toasts",
////                                        Toast.LENGTH_LONG
////                                ).show();
////                            }
////
////                            @Override
////                            public void onError(String error) {
////                                Timber.e("On delete error: %s", error);
////                            }
////                        });
////                    }
////                }
////
////                @Override
////                public void onError(String error) {
////                    Timber.e("onListError: %s", error);
////                }
////            });
////        }
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
//    }
//
//    // Progress bar methods
//    private void startProgress() {
//
//// Start and show the progress bar
//        isEndNotified = false;
//        progressBar.setIndeterminate(true);
//        progressBar.setVisibility(View.VISIBLE);
//    }
//
//    private void setPercentage(final int percentage) {
//        progressBar.setIndeterminate(false);
//        progressBar.setProgress(percentage);
//    }
//
//    private void endProgress(final String message) {
//// Don't notify more than once
//        if (isEndNotified) {
//            return;
//        }
//
//// Stop and hide the progress bar
//        isEndNotified = true;
//        progressBar.setIndeterminate(false);
//        progressBar.setVisibility(View.GONE);
//
//// Show a toast
//        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
//    }
}