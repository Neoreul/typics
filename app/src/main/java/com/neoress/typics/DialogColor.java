package com.neoress.typics;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import static com.neoress.typics.EditPicActivity.cur_edt_id;
import static com.neoress.typics.EditPicActivity.txt_addText;

/**
 * Created by neoreul on 3/28/17.
 */

public class DialogColor extends Dialog implements View.OnClickListener {
    public Activity c;
    public Dialog d;
    public Button done, cancel;
    public static int a = 255, r = 255, g = 255, b = 255;
    private int rCur, gCur, bCur, aCur;
    TextView textView;
    TextView textR, textG, textB, textA;

    public DialogColor(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_color);
        done = findViewById(R.id.btnColorDone);
        cancel = findViewById(R.id.btnColorCancel);
        textView = findViewById(R.id.textColorView);
        done.setOnClickListener(this);
        cancel.setOnClickListener(this);

        textView.setText(txt_addText[cur_edt_id].getText().toString());
        textView.setTextColor(txt_addText[cur_edt_id].getTextColors());

        rCur = r;
        gCur = g;
        bCur = b;
        aCur = a;

        SeekBar seekR = findViewById(R.id.seekBarR_Color);
        textR = findViewById(R.id.txt_R_Color);
        textR.setText(Integer.toString(r));
        seekR.setProgress(r);
        seekR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                r = progress;
                textR.setText(Integer.toString(progress));
                textView.setTextColor(Color.argb(a, r, g, b));
            }
        });
        SeekBar seekG = findViewById(R.id.seekBarG_Color);
        textG = findViewById(R.id.txt_G_Color);
        textG.setText(Integer.toString(g));
        seekG.setProgress(g);
        seekG.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                g = progress;
                textG.setText(Integer.toString(progress));
                textView.setTextColor(Color.argb(a, r, g, b));
            }
        });
        SeekBar seekB = findViewById(R.id.seekBarB_Color);
        textB = findViewById(R.id.txt_B_Color);
        textB.setText(Integer.toString(b));
        seekB.setProgress(b);
        seekB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                b = progress;
                textB.setText(Integer.toString(progress));
                textView.setTextColor(Color.argb(a, r, g, b));
            }
        });
        SeekBar seekAlpha = findViewById(R.id.seekBarA_Color);
        textA = findViewById(R.id.txt_A_Color);
        textA.setText(Integer.toString(a));
        seekAlpha.setProgress(a);
        seekAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub
                a = progress;
                textA.setText(Integer.toString(progress));
                textView.setTextColor(Color.argb(a, r, g, b));
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnColorDone:
                txt_addText[cur_edt_id].setTextColor(Color.argb(a, r, g, b));
                break;
            case R.id.btnColorCancel:
                r = rCur;
                g = gCur;
                b = bCur;
                a = aCur;
                break;
            default:
                break;
        }
        dismiss();
    }
}
