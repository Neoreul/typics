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

public class DialogShadow extends Dialog implements View.OnClickListener {
    public Activity c;
    public Dialog d;
    public Button done, cancel;
    public static int[] shadow = new int[7];
    private int[] shadowCur = new int[7];
    TextView textView;
    TextView[] textShadow = new TextView[7];

    public DialogShadow(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_shadow);

        done = findViewById(R.id.btnShadowDone);
        cancel = findViewById(R.id.btnShadowCancel);
        textView = findViewById(R.id.textShadowView);
        done.setOnClickListener(this);
        cancel.setOnClickListener(this);

        textView.setText(txt_addText[cur_edt_id].getText().toString());
        textView.setTextColor(txt_addText[cur_edt_id].getTextColors());

        for(int i = 0; i < 7; i++)
            shadowCur[i] = shadow[i];

        SeekBar seekR = findViewById(R.id.seekBarRShadow);
        textShadow[0] = findViewById(R.id.txtRShadow);
        textShadow[0].setText(Integer.toString(shadow[0]));
        seekR.setProgress(shadow[0]);
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
                shadow[0] = progress;
                textShadow[0].setText(Integer.toString(progress));
                textView.setShadowLayer(shadow[4], shadow[5], shadow[6], Color.argb(shadow[3], shadow[0], shadow[1], shadow[2]));
            }
        });
        SeekBar seekG = findViewById(R.id.seekBarGShadow);
        textShadow[1] = findViewById(R.id.txtGShadow);
        textShadow[1].setText(Integer.toString(shadow[1]));
        seekG.setProgress(shadow[1]);
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
                shadow[1] = progress;
                textShadow[1].setText(Integer.toString(progress));
                textView.setShadowLayer(shadow[4], shadow[5], shadow[6], Color.argb(shadow[3], shadow[0], shadow[1], shadow[2]));
            }
        });
        SeekBar seekB = findViewById(R.id.seekBarBShadow);
        textShadow[2] = findViewById(R.id.txtBShadow);
        textShadow[2].setText(Integer.toString(shadow[2]));
        seekB.setProgress(shadow[2]);
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
                shadow[2] = progress;
                textShadow[2].setText(Integer.toString(progress));
                textView.setShadowLayer(shadow[4], shadow[5], shadow[6], Color.argb(shadow[3], shadow[0], shadow[1], shadow[2]));
            }
        });
        SeekBar seekAlpha = findViewById(R.id.seekBarAlphaShadow);
        textShadow[3] = findViewById(R.id.txtAlphaShadow);
        textShadow[3].setText(Integer.toString(shadow[3]));
        seekAlpha.setProgress(shadow[3]);
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
                shadow[3] = progress;
                textShadow[3].setText(Integer.toString(progress));
                textView.setShadowLayer(shadow[4], shadow[5], shadow[6], Color.argb(shadow[3], shadow[0], shadow[1], shadow[2]));
            }
        });
        //------------------------------------------
        SeekBar seekRadius = findViewById(R.id.seekBarRadiusShadow);
        textShadow[4] = findViewById(R.id.txtRadiusShadow);
        textShadow[4].setText(Integer.toString(shadow[4]));
        seekRadius.setProgress(shadow[4]);
        seekRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                shadow[4] = progress;
                textShadow[4].setText(Integer.toString(progress));
                textView.setShadowLayer(shadow[4], shadow[5], shadow[6], Color.argb(shadow[3], shadow[0], shadow[1], shadow[2]));
            }
        });
        SeekBar seekX = findViewById(R.id.seekBarXShadow);
        textShadow[5] = findViewById(R.id.txtXShadow);
        textShadow[5].setText(Integer.toString(shadow[5]));
        seekX.setProgress(shadow[5]);
        seekX.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                shadow[5] = progress;
                textShadow[5].setText(Integer.toString(progress));
                textView.setShadowLayer(shadow[4], shadow[5], shadow[6], Color.argb(shadow[3], shadow[0], shadow[1], shadow[2]));
            }
        });
        SeekBar seekY = findViewById(R.id.seekBarYShadow);
        textShadow[6] = findViewById(R.id.txtYShadow);
        textShadow[6].setText(Integer.toString(shadow[6]));
        seekY.setProgress(shadow[6]);
        seekY.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                shadow[6] = progress;
                textShadow[6].setText(Integer.toString(progress));
                textView.setShadowLayer(shadow[4], shadow[5], shadow[6], Color.argb(shadow[3], shadow[0], shadow[1], shadow[2]));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShadowDone:
                txt_addText[cur_edt_id].setShadowLayer(shadow[4], shadow[5], shadow[6], Color.argb(shadow[3], shadow[0], shadow[1], shadow[2]));
                break;
            case R.id.btnShadowCancel:
                for (int i = 0; i < 7; i++) {
                    shadow[i] = shadowCur[i];
                }
                break;
            default:
                break;
        }
        dismiss();
    }
}
