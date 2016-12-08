package com.example.bottommenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "ResourceAsColor", "ShowToast" })
public class PopMenus {
	private JSONArray jsonArray;
	private Context context;
	private PopupWindow popupWindow;
	private LinearLayout listView;
	private int width, height;
	private View containerView;
    private MainActivity mainActivity;

	public PopMenus(Context context, MainActivity mainActivity, JSONArray _jsonArray, int _width, int _height) {
		this.context = context;
        this.mainActivity = mainActivity;
		this.jsonArray = _jsonArray;
		this.width = _width;
		this.height = _height;
		containerView = LayoutInflater.from(context).inflate(R.layout.popmenus, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
		containerView.setLayoutParams(lp);
		// 设置 listview
		listView = (LinearLayout) containerView.findViewById(R.id.layout_subcustommenu);
		try {
			setSubMenu();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listView.setBackgroundColor(ContextCompat.getColor(context, R.color.black));
		listView.setFocusableInTouchMode(true);
		listView.setFocusable(true);

		popupWindow = new PopupWindow(containerView, width == 0 ? LayoutParams.WRAP_CONTENT : width, height == 0 ? LayoutParams.WRAP_CONTENT : height);
	}

	// 下拉式 弹出 pop菜单 parent 右下角
	public void showAsDropDown(View parent) {
		popupWindow.setBackgroundDrawable(new ColorDrawable());
		popupWindow.showAsDropDown(parent);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 使其聚集
		popupWindow.setFocusable(true);
		// 刷新状态
		popupWindow.update();

		popupWindow.setOnDismissListener(new OnDismissListener() {

			// 在dismiss中恢复透明度
			@Override
			public void onDismiss() {
			}
		});
	}

	public void showAtLocation(View parent) {
		popupWindow.setBackgroundDrawable(new ColorDrawable());
		containerView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		int[] location = new int[2];
		parent.getLocationOnScreen(location);
		int x = location[0] - 5;
		int y = parent.getHeight() - (parent.getHeight() / 3);
		// Utils.toast(context, y +""); //location[1] - popupHeight -
		// parent.getHeight()
		popupWindow.showAtLocation(parent, Gravity.LEFT | Gravity.BOTTOM, x, y);

		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 使其聚集
		popupWindow.setFocusable(true);
		// 刷新
		popupWindow.update();

		popupWindow.setOnDismissListener(new OnDismissListener() {

			// 在dismiss中恢复透明度
			@Override
			public void onDismiss() {
			}
		});
	}

	// 隐藏菜单
	public void dismiss() {
		popupWindow.dismiss();
	}

	void setSubMenu() throws JSONException {
		listView.removeAllViews();
		for (int i = 0; i < jsonArray.length(); i++) {
			final JSONObject ob = jsonArray.getJSONObject(i);
			final LinearLayout layoutItem = (LinearLayout) ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.pomenu_menuitem, null);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
			containerView.setLayoutParams(lp);
			layoutItem.setFocusable(true);
			TextView tv_funbtntitle = (TextView) layoutItem.findViewById(R.id.pop_item_textView);
			View pop_item_line = layoutItem.findViewById(R.id.pop_item_line);
			if ((i + 1) == jsonArray.length()) {
				pop_item_line.setVisibility(View.GONE);
			}
			tv_funbtntitle.setText(ob.getString("title"));
			layoutItem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
                    TextView textView = (TextView)v.findViewById(R.id.pop_item_textView);
                    // mainActivity.textView.setText(textView.getText());
					mainActivity.fm = mainActivity.getFragmentManager();
					mainActivity.ft = mainActivity.fm.beginTransaction();
					if ("找商品".equals(textView.getText())) {
						mainActivity.ft.replace(R.id.fragment_content, new GoodsFragment());
					}else if ("关于借道".equals(textView.getText())){
						mainActivity.ft.replace(R.id.fragment_content, new AboutFragment());
					}else {
						Toast.makeText(context, "这里我什么都没做哟，只是一个简单地提示" +
								context.getClass() + " | " + textView.getText(),
								Toast.LENGTH_SHORT).show();
					}
					// 不要忘记提交
					mainActivity.ft.commit();
					dismiss();
				}
			});
			listView.addView(layoutItem);
		}
		listView.setVisibility(View.VISIBLE);
	}

}
