package com.my.myvideoplayer;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.my.myvideoplayer.Adapter.ItemAdapter;
import com.my.myvideoplayer.Fragment.ConfigDialog;
import com.my.myvideoplayer.GlobleVar.GlobleVar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.HttpManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private List<String> videolist = new ArrayList<String>();

    BaseAdapter stringArrayAdapter;

    private ListView mLV_mainVideoList;
    private SwipeRefreshLayout mSRL_mainRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLV_mainVideoList = (ListView) findViewById(R.id.lv_main_videoList);
        mSRL_mainRefresh = (SwipeRefreshLayout) findViewById(R.id.srl_main_refresh);
        mSRL_mainRefresh.setOnRefreshListener(this);

        onRefresh();

        stringArrayAdapter = new ItemAdapter(this, videolist);


//        VideoView videoView = (VideoView) this.findViewById(R.id.video_view);
//        videoView.setMediaController(new MediaController(this));
//        videoView.setVideoURI(uri);
//        videoView.start();
//        videoView.requestFocus();


    }

    @Override
    public void onRefresh() {
        getDirectory();
    }

    private void getDirectory() {
        HttpManager httpManager = x.http();
        RequestParams requestParams = new RequestParams("http://" + GlobleVar.IP + ":" + GlobleVar.PORT + "/VideoList/queryList.do");
        httpManager.get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                videolist.clear();
                Log.d("MyLog", result);

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("videoList");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.d("MyLog", (String) jsonArray.get(i));
                        videolist.add((String) jsonArray.get(i));

                    }

                    mLV_mainVideoList.setAdapter(stringArrayAdapter);
                    mLV_mainVideoList.setOnItemClickListener(MainActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                Log.d("MyLog", "error");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                mSRL_mainRefresh.setRefreshing(false);

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String str = videolist.get(i);

        Uri uri = Uri.parse("http://" + GlobleVar.IP + ":" + GlobleVar.PORT + "/VideoList/videoes/" + str);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Log.v("URI:::::::::", uri.toString());
        intent.setDataAndType(uri, "video/mp4");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_userinfo) {

            ConfigDialog configDialog = new ConfigDialog();
            configDialog.show(getFragmentManager(), "config");

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {

                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
