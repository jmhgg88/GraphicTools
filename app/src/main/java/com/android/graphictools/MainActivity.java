package com.android.graphictools;

import android.app.Activity;
import android.os.Bundle;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends Activity {
    ListView mListViewMainActivity;
    String[] listView = {
            "解码调试",
            "编码调试",
            "HWC Dump Layer（海思平台）",
            "DumpSys To Log"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListViewMainActivity = (ListView) findViewById(R.id.ListViewMainActivity);

        ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        listView
                );
        mListViewMainActivity.setAdapter(adapter);
        mListViewMainActivity.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case 0:
                        //Toast.makeText(MainActivity.this,listView[position],Toast.LENGTH_SHORT).show();
                        intent = new Intent(MainActivity.this,FileDecodeActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        //Toast.makeText(MainActivity.this,listView[position],Toast.LENGTH_SHORT).show();
                        intent = new Intent(MainActivity.this,FileEncodeActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        //Toast.makeText(MainActivity.this,listView[position],Toast.LENGTH_SHORT).show();
                        intent = new Intent(MainActivity.this,HwcDumpLayerActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
