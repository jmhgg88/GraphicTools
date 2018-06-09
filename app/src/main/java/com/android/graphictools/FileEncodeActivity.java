package com.android.graphictools;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class FileEncodeActivity extends Activity {
    private static final String TAG = "GraphicTools::FileEncodeActivity";
    String mSrcFilePath = null;
    String mDstFilePath = null;
    int mSampleSize = 1;
    boolean mShowBitmap = false;
    Bitmap.CompressFormat mOutFormat = Bitmap.CompressFormat.JPEG;

    GraphicUtils mGU;
    EditText mEditTextSrcFileName;
    EditText mEditTextQuality;
    int mQuality;
    TextView mTextViewDstFilePath;
    TextView mTextViewPerformance;
    RadioGroup mRadioGroupSampleSize;
    RadioGroup mRadioGroupOutFormat;
    RadioGroup mRadioGroupShowBitmap;

    private void showDstFilePath() {
        mTextViewDstFilePath.setText(mDstFilePath);
    }
    private void showDecodePerformance() {
        String message = String.valueOf(mGU.getCostTime(false));
        mTextViewPerformance.setText(message);
    }

    private boolean getUserSettings() {
        mTextViewDstFilePath = (TextView) findViewById(R.id.textViewDstFilePath);
        mTextViewPerformance = (TextView) findViewById(R.id.textViewPerformance);

        mEditTextSrcFileName = (EditText) findViewById(R.id.editTextSrcFileName);
        mSrcFilePath = mGU.getSrcFilePath(mEditTextSrcFileName.getText().toString());
        if (mSrcFilePath == null) {
            String message = "Invalid source file name "
                    + mEditTextSrcFileName.getText().toString();
            Toast.makeText(FileEncodeActivity.this, message, Toast.LENGTH_SHORT).show();
            return false;
        }

        mEditTextQuality = (EditText) findViewById(R.id.editTextQuality);
        mQuality = Integer.valueOf(mEditTextQuality.getText().toString());

        RadioButton radioButton;
        mRadioGroupSampleSize = (RadioGroup) findViewById(R.id.radioGroupSampleSize);
        radioButton = (RadioButton) findViewById(mRadioGroupSampleSize.getCheckedRadioButtonId());
        mSampleSize = mGU.getSampleSize(radioButton.getText().toString());

        mRadioGroupShowBitmap = (RadioGroup) findViewById(R.id.radioGroupShowBitmap);
        radioButton = (RadioButton) findViewById(mRadioGroupShowBitmap.getCheckedRadioButtonId());
        mShowBitmap = mGU.getShowBitmap(radioButton.getText().toString());

        mRadioGroupOutFormat = (RadioGroup) findViewById(R.id.radioGroupOutFormat);
        radioButton = (RadioButton) findViewById(mRadioGroupOutFormat.getCheckedRadioButtonId());
        mOutFormat = mGU.getOutFormat(radioButton.getText().toString());

        mDstFilePath = mGU.getDstFilePath(mGU.getCompressDstFileName(mOutFormat));

        Log.d(TAG,"mSrcFilePath:" + mSrcFilePath);
        Log.d(TAG, "mSampleSize:" + mSampleSize);
        Log.d(TAG, "mOutFormat:" + mOutFormat);
        Log.d(TAG, "mQuality:" + mQuality);
        Log.d(TAG, "mDstFilePath:" + mDstFilePath);
        Log.d(TAG, "mShowBitmap:" + mShowBitmap);
        return true;
    }

    private void decodeAndCompressThenSave() {
        String message = null;
        Bitmap bmp = mGU.decodeFile(mSrcFilePath, mSampleSize, Bitmap.Config.ARGB_8888, false);
        if (null == bmp) {
            message = "Can't decode " + mSrcFilePath;
            Toast.makeText(FileEncodeActivity.this, message, Toast.LENGTH_SHORT).show();
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        boolean ret = mGU.compressBitmap(bmp, mOutFormat, mQuality, baos);
        if (false == ret) {
            message = "Compress Bitmap fail!";
            Toast.makeText(FileEncodeActivity.this, message, Toast.LENGTH_SHORT).show();
            return ;
        }
        ret = mGU.writeByteArrayOutputStreamToFile(mDstFilePath, baos);
        message = "Save compress file " + mDstFilePath + (ret? " success!":" fail!");
        Toast.makeText(FileEncodeActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void startEncodeButton() {
        Button btn = (Button) findViewById(R.id.buttonStartEncode);
        btn .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (false == getUserSettings()) return;
                decodeAndCompressThenSave();
                showDstFilePath();
                showDecodePerformance();
            }
        });
    }

    private void startClearButton() {
        Button btn = (Button) findViewById(R.id.buttonClearCacheDir);
        btn .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String dstPath = mGU.deleteDirectory();
                String message;
                if (null == dstPath) {
                    message = "Dst dir already not exist!";
                } else {
                    message = "Delete " + dstPath + " success!";
                }
                Log.d(TAG, message);
                Toast.makeText(FileEncodeActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_encode);

        mGU = new GraphicUtils();
        startEncodeButton();
        startClearButton();
    }
}
