package com.my.myvideoplayer.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.my.myvideoplayer.GlobleVar.GlobleVar;
import com.my.myvideoplayer.R;

/**
 * Created by Administrator on 2016/12/8 0008.
 */

public class ConfigDialog extends DialogFragment {
    private EditText mET_configIP;
    private EditText mET_configPort;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_config, null);
        mET_configIP = (EditText) view.findViewById(R.id.et_config_IP);
        mET_configPort = (EditText) view.findViewById(R.id.et_config_port);

        mET_configIP.setText(GlobleVar.IP);
        mET_configPort.setText(GlobleVar.PORT);

        builder.setTitle("环境配置");
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String configIP = mET_configIP.getText().toString();
                String configPort = mET_configPort.getText().toString();

                GlobleVar.IP = configIP;
                GlobleVar.PORT = configPort;


            }
        }).setNegativeButton("取消", null);

        return builder.create();
    }
}
