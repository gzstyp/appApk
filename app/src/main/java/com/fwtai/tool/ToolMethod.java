package com.fwtai.tool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.fwtai.config.LocalConfig;
import com.fwtai.config.Variables;
import com.fwtai.widget.AlertDialog;
import com.yinlz.cdc.R;

import java.util.HashMap;

public final class ToolMethod{

    private static ToolMethod instance = null;

    private ToolMethod(){}

    /**懒汉式单例模式*/
    public final static ToolMethod getInstance(){
        if (instance == null)
            instance = new ToolMethod();
        return instance;
    }

    /**实名认证*/
    public final void auth(final Activity activity){
        new AlertDialog(activity,"你还未实名认证,不能操作,要认证吗?",false,new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //final Intent intent = new Intent(activity,AuthActivity.class);
                final Intent intent = new Intent();
                activity.startActivityForResult(intent,200);
            }
        },null);
    }

    public final HashMap<String,String> requestParams(final Context context){
        final HashMap<String,String> params = new HashMap<>();
        final LocalConfig localConfig = LocalConfig.getInstance();
        params.put("token",localConfig.getValue(context,Variables.key_token));
        params.put("userId",localConfig.getValue(context,Variables.key_userId));
        return params;
    }

    /**
     * 倒计时,默认是60秒
     * @param process --> 秒后重新获取
     * @param finish --> 获取验证码
     * @作者 田应平
     * @QQ 444141300
     * @用法 ToolMethod.getInstance().viewTimer(textView,"秒后重新获取","获取验证码",60);
     * @创建时间 2019/12/11 15:53
    */
    public final void viewTimer(final TextView textView,final String process,final String finish){
        new CountDownTimer(60000,1000){
            public void onTick(final long millisUntilFinished){
                textView.setClickable(false);
                textView.setText(millisUntilFinished / 1000 + process);
            }
            public void onFinish(){
                textView.setClickable(true);
                textView.setText(finish);
            }
        }.start();
    }

    /**
     * 倒计时
     * @param process --> 秒后重新获取
     * @param finish --> 获取验证码
     * @param sec --> 秒数
     * @作者 田应平
     * @QQ 444141300
     * @用法 ToolMethod.getInstance().viewTimer(textView,"秒后重新获取","获取验证码",60);
     * @创建时间 2019年12月11日 15:55:19
    */
    public final void viewTimer(final TextView textView,final String process,final String finish,final long sec){
        new CountDownTimer(1000 * sec,1000){
            public void onTick(final long millisUntilFinished){
                textView.setClickable(false);
                textView.setText(millisUntilFinished / 1000 + process);
            }
            public void onFinish(){
                textView.setClickable(true);
                textView.setText(finish);
            }
        }.start();
    }

    /**
     * 倒计时,默认是60秒
     * @param process --> 秒后重新获取
     * @param finish --> 获取验证码
     * @作者 田应平
     * @QQ 444141300
     * @用法 ToolMethod.getInstance().viewTimer(button,"秒后重新获取","获取验证码",60);
     * @创建时间 2019年12月11日 15:56:34
    */
    public final void viewTimer(final Button button,final String process,final String finish){
        new CountDownTimer(60000,1000){
            public void onTick(final long millisUntilFinished){
                button.setClickable(false);
                button.setText(millisUntilFinished / 1000 + process);
            }
            public void onFinish(){
                button.setClickable(true);
                button.setText(finish);
            }
        }.start();
    }

    /**
     * 倒计时
     * @param process --> 秒后重新获取
     * @param finish --> 获取验证码
     * @param sec --> 秒数
     * @作者 田应平
     * @QQ 444141300
     * @用法 ToolMethod.getInstance().viewTimer(button,"秒后重新获取","获取验证码",60);
     * @创建时间 2019年12月11日 15:56:46
    */
    public final void viewTimer(final Button button,final String process,final String finish,final long sec){
        new CountDownTimer(1000 * sec,1000){
            public void onTick(final long millisUntilFinished){
                button.setClickable(false);
                button.setText(millisUntilFinished / 1000 + process);
            }
            public void onFinish(){
                button.setClickable(true);
                button.setText(finish);
            }
        }.start();
    }

    /**从底部往顶部滑动并结束当前的Activity,用法:ToolMethod.getInstance().fromBottomToTop(this);*/
    public final void fromBottomToTop(final Activity activity){
        activity.overridePendingTransition(R.anim.from_bottom_top_in,R.anim.from_bottom_top_out);
    }

    /**从底部往顶部滑动并结束当前的Activity,用法:ToolMethod.getInstance().fromBottomToTopFinish(this);*/
    public final void fromBottomToTopFinish(final Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.from_bottom_top_in,R.anim.from_bottom_top_out);
    }

    /**左边的进来,右边退出(下一步或下一个页面),用法:ToolMethod.getInstance().fromLeftToRight(this);*/
    public final void fromLeftToRight(final Activity activity){
        activity.overridePendingTransition(R.anim.from_left_right_in,R.anim.from_left_righ_out);
    }

    /**左边的进来,右边退出(下一步或下一个页面)并结束当前的Activity,用法:ToolMethod.getInstance().fromLeftToRightFinish(this);*/
    public final void fromLeftToRightFinish(final Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.from_left_right_in,R.anim.from_left_righ_out);
    }

    /**从右边进,左边退出(上一步或返回页面),用法:ToolMethod.getInstance().fromRightToLeft(this);*/
    public final void fromRightToLeft(final Activity activity){
        activity.overridePendingTransition(R.anim.from_right_left_in,R.anim.from_right_left_out);
    }

    /**从右边进,左边退出(上一步或返回页面)并结束当前的Activity,用法:ToolMethod.getInstance().fromRightToLeftFinish(this);*/
    public final void fromRightToLeftFinish(final Activity activity){
        activity.finish();
        activity.overridePendingTransition(R.anim.from_right_left_in,R.anim.from_right_left_out);
    }
}