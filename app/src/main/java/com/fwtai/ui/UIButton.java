package com.fwtai.ui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.fwtai.activity.BaseActivity;
import com.fwtai.http.ToolHttp;
import com.fwtai.interfaces.IRequest;
import com.fwtai.interfaces.IViewTimer;
import com.fwtai.interfaces.TitleOnClickListener;
import com.fwtai.upgrade.Upgrade;
import com.fwtai.widget.AlertDialog;
import com.fwtai.widget.BaseDialog;
import com.fwtai.widget.HintDialog;
import com.fwtai.widget.SheetDialog;
import com.fwtai.widget.SheetDialog.OnSheetItemClickListener;
import com.fwtai.widget.SheetDialog.SheetItemColor;
import com.fwtai.widget.TitleBar;
import com.fwtai.widget.ViewEvent;
import com.yinlz.cdc.R;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 按钮及dialog的应用
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年3月21日 17:05:39
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public class UIButton extends BaseActivity{

	private Activity activity = this;
	private TitleBar titleBar;
	private final HintDialog hintDialog = HintDialog.getInstance();
	private Dialog loading;
	
	@Override
	protected void onCreate(final Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.layout_button);
		TitleBar.initSystemBar(this);
		initView();
	}

	private void initView(){
		titleBar = (TitleBar) findViewById(R.id.titlebar_button);
		titleBar.setTitle("按钮及dialog的应用");
		titleBar.setLeftClickListener(new TitleOnClickListener(){
			@Override
			public void onClick(View view){
				finish();
			}
		});
		titleBar.setRightText("下载");
		titleBar.setRightClickListener(new TitleOnClickListener(){
			@Override
			public void onClick(final View view){
                nextActivityAnim(UIDownload.class);
			}
		});
		new ViewEvent(findViewById(R.id.button_btn_error),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				hintDialog.error(getApplicationContext(),"操作失败");
			}
		});
		new ViewEvent(findViewById(R.id.button_btn_ok),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				hintDialog.ok(activity,"设计对二叉树和有向");
			}
		});
		new ViewEvent(findViewById(R.id.button_btn_theme),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				hintDialog.theme(activity,"主题颜色");
			}
		});
		new ViewEvent(findViewById(R.id.button_btn_normal),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				hintDialog.normal(activity,"正常颜色");
			}
		});
		/**对话框-单个按钮-没标题*/
		new ViewEvent(findViewById(R.id.button_btn_alert),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				new AlertDialog(activity,"对话框单个按钮没标题","好的",true,null);
			}
		});
		/**对话框-单个按钮-带标题*/
		new ViewEvent(findViewById(R.id.button_btn_alert_title),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				new AlertDialog(activity,"对话框单个按钮带标题有事件","系统提示","朕知道了",false,new OnClickListener(){
					@Override
					public void onClick(View view){
						hintDialog.normal(activity,"嗯,朕知道了");
					}
				});
			}
		});
		/**下拉列表*/
		new ViewEvent(findViewById(R.id.button_btn_sheet),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				final SheetDialog dialog = new SheetDialog(activity).builder();
				dialog.setTitle("请选择性别");
				dialog.setCancelable(false);
				dialog.setCanceledOnTouchOutside(false);
				final ArrayList<String> sex = new ArrayList<String>();
				final String boy = "男生";
				final String girl = "女生";
				sex.add(boy);
				sex.add(girl);
				for (int i = 0; i < sex.size(); i++){
					final String item = sex.get(i);
					dialog.addSheetItem(item, SheetItemColor.Blue, new OnSheetItemClickListener(){
						@Override
						public void onClick(int which){
							if (item.equals(boy)){
								hintDialog.ok(activity,item);
							}else if (item.equals(girl)){
								hintDialog.ok(activity,"你选择的是女生");
							}
						}
					});
				}
				dialog.show();
			}
		});
		/**对话框-两个按钮*/
		new ViewEvent(findViewById(R.id.button_btns),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				new AlertDialog().builder(activity).setMsg("此对话框有两个按钮是吗?").setPositiveButton("确认",new OnClickListener(){
					@Override
					public void onClick(View v){
						hintDialog.ok(activity,"嗯,确定");
					}
				}).setNegativeButton("取消",null).show();
			}
		});
		/**对话框-两个按钮-带标题*/
		new ViewEvent(findViewById(R.id.button_btns_title),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				new AlertDialog().builder(activity).setCancelable(false).setTitle("系统提示").setMsg("此对话框有两个按钮且有标题是吗?").setPositiveButton("确认", new OnClickListener(){
					@Override
					public void onClick(View v){
						hintDialog.ok(activity,"带标题");
					}
				}).setNegativeButton("取消", new OnClickListener(){
					@Override
					public void onClick(View v){
						hintDialog.error(activity,"嗯,不带标题");
					}
				}).show();
			}
		});
		/**版本更新*/
		new ViewEvent(findViewById(R.id.button_btn_upgrade),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				final Upgrade manager = new Upgrade(activity,2);
				if (manager.compareVersion()){
					new AlertDialog(activity,"发现新版本,请及时更新","立即下载","下次提醒",false,new OnClickListener(){
						@Override
						public void onClick(View v){
							manager.downloadApk("http://issuecdn.baidupcs.com/issue/netdisk/apk/BaiduNetdisk_7.16.2.apk");
						}
					},null);
				}else{
					hintDialog.ok(activity,"当前已是最新版了");
				}
			}
		});
		new ViewEvent(findViewById(R.id.button_btns_api1),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				new AlertDialog(activity,"两按钮对话框且标题固定为系统提示",true,new OnClickListener(){
					@Override
					public void onClick(View v){
						hintDialog.ok(activity,"朕知道了");
					}
				},null);
			}
		});
		new ViewEvent(findViewById(R.id.button_btns_api2),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				new AlertDialog(activity,"自定义对话框,含按钮文字、按钮事件、是否允许消失","挑逗戏弄","残忍拒绝",false,new OnClickListener(){
					@Override
					public void onClick(View v){
						hintDialog.ok(activity,"弄她搞她了啊");
					}
				},new OnClickListener(){
					@Override
					public void onClick(View v){
						hintDialog.error(activity,"朕残忍拒绝了");
					}
				});
			}
		});
		new ViewEvent(findViewById(R.id.button_btn3),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				ToolHttp.getInstance().requestGet("http://www.yinlz.com/news/indexShow",new IRequest(){
					@Override
					public void start(){
						loading = hintDialog.createLoading(activity,"正在读取……");
						loading.show();
					}
					@Override
					public void onFailure(IOException exception){
						loading.dismiss();
						hintDialog.error(activity,exception);
					}
					@Override
					public void onSuccess(String data){
						loading.dismiss();
						hintDialog.ok(activity,data);
					}
				});
			}
		});
		new ViewEvent(findViewById(R.id.button_btn_dialog1),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				final BaseDialog viewDialog = BaseDialog.create(activity,R.layout.dialog_ask,0.95f,false);
				new ViewEvent(viewDialog.findViewById(R.id.ask_iv_closed),1,new IViewTimer(){
					@Override
					public void viewClick(View view){
						viewDialog.dismiss();
						hintDialog.ok(activity,"已关闭");
					}
				});
			}
		});
		new ViewEvent(findViewById(R.id.button_btn_dialog2),1,new IViewTimer(){
			@Override
			public void viewClick(View view){
				final BaseDialog viewDialog = BaseDialog.create(activity,R.layout.dialog_ask,0.6f,false);
				new ViewEvent(viewDialog.getCustomView(),1,new IViewTimer(){
					@Override
					public void viewClick(View view){
						viewDialog.dismiss();
						hintDialog.ok(activity,"已关闭了");
					}
				});
			}
		});
	}
}