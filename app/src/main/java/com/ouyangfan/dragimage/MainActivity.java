package com.ouyangfan.dragimage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;

import java.io.File;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pickImage();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("TAG", " onActivityResult = " + requestCode + " , resultCode = "+ resultCode);
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    private void pickImage() {
        Crop.pickImage(this);
        Log.i("TAG", "pick image");
    }

    private void beginCrop(Uri source) {
        Log.i("TAG", "begin crop uri = "+ source.toString());
        Uri destination = Uri.fromFile(new File(getCacheDir(),
                "cropped_" + System.currentTimeMillis() + ".jpg"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            Uri uri = Crop.getOutput(intent);
            String imagePath = uri.getPath();
            // TODO
            Log.i(" imagePath = ", imagePath);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(intent).getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
