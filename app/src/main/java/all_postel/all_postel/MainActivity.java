package all_postel.all_postel;

        import android.annotation.TargetApi;
        import android.app.Activity;
        import android.app.Application;
        import android.content.Intent;
        import android.content.pm.PackageInfo;
        import android.content.pm.PackageManager;
        import android.content.pm.Signature;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.util.Base64;
        import android.util.Log;
        import android.view.Window;
        import android.view.WindowManager;
        import android.webkit.CookieSyncManager;
        import android.webkit.URLUtil;
        //import android.webkit.WebResourceRequest;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.Toast;
        import android.view.KeyEvent;

        import android.webkit.JavascriptInterface;
        import android.content.Context;

        import com.facebook.CallbackManager;
        import com.facebook.FacebookSdk;
        import com.google.firebase.iid.FirebaseInstanceId;

        import java.security.MessageDigest;
        import java.security.NoSuchAlgorithmException;



public class MainActivity extends Activity {

    private WebView mWebview;
    private CallbackManager callbackManager;

//    public final String TAG = this.getClass().getSimpleName();
//    private void printKeyHash() {
//        try {
//            PackageInfo info = getPackageManager()
//                    .getPackageInfo("all_postel.all_postel",
//                            PackageManager.GET_SIGNATURES);
//            for (Signature signature:info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d(TAG, "KeyHash: " + Base64.encodeToString(md.digest(),Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        this.setTitle("ALL-POSTEL");
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        super.onCreate(savedInstanceState);


        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().startSync();

        mWebview = new WebView(this);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setDomStorageEnabled(true);

        mWebview.addJavascriptInterface(new WebAppInterface(this), "Android");

//        if (Build.VERSION.SDK_INT >= 19) {
//            mWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        }

        final Activity activity = this;




//        startService(new Intent(this, MyFirebaseInstanceIDService.class));

        //String token = FirebaseInstanceId.getInstance().getToken();

        mWebview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }

        });

        mWebview.loadUrl("https://mobile.all-postel.ru");


        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if( URLUtil.isNetworkUrl(url) ) {
                    return false;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity( intent );
                return true;
            }

        });



        setContentView(mWebview);



//        printKeyHash();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        mWebview.loadUrl("javascript: appBack();");
    }



    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void appExit() {
            finish();
            return;
        }
    }




}
