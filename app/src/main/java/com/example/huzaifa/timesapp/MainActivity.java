package com.example.huzaifa.timesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    final Context context = this;
    String filename="location";
    //Context context = null;
    TextView tv;

    String data;
    StringBuffer fileContent = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File file = new File(getFilesDir(),filename);
        if (file.exists()) { // check of the file exists
            addToTextView();
        } else {
            file = new File(getFilesDir(), filename);
            Toast.makeText(getBaseContext(), "File Created", Toast.LENGTH_SHORT).show();

            LayoutInflater li = LayoutInflater.from(context);
            final View promptsView = li.inflate(R.layout.first_time_prompt,null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setView(promptsView);
            final Button b2=(Button)promptsView.findViewById(R.id.button2);
            // create alert dialog
            final AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final EditText ed1=(EditText)promptsView.findViewById(R.id.editTextDialogUserInput);
                    data=ed1.getText().toString();
                    try {
                        FileOutputStream fOut = openFileOutput(filename,MODE_WORLD_READABLE);
                        fOut.write(data.getBytes());
                        fOut.close();
                        Toast.makeText(getBaseContext(), "Saved to File", Toast.LENGTH_SHORT).show();
                    }

                    catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    addToTextView();
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();

        }

    }

    public final void addToTextView () { //reads the text stored in the file and adds it to the screen
        int n;
        try {
            FileInputStream fis;
            fis = openFileInput(filename); //file handler
            fileContent = new StringBuffer("");

            byte[] buffer = new byte[1024];

            while ((n = fis.read(buffer)) != -1)
            {
                fileContent.append(new String(buffer, 0, n));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        tv=(TextView)findViewById(R.id.textView);
        if (fileContent == null && fileContent.toString().equals("")) {
            tv.setText("No String");
        } else {
            tv.setText(fileContent.toString());
        }
        //tv.setText("HELLO WORLD");
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
}
