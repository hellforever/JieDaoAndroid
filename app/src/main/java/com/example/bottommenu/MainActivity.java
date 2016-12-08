package com.example.bottommenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener{

	PopMenus popupWindow_custommenu;
	LinearLayout layout_custommenu, layout_customemenu;

	FragmentManager fm;
	FragmentTransaction ft;

	/**
	 * JSON DATA
	 */
	String jsonStr = "{\"customemenu\": [{\"title\":\"借道\",\"sub\":[{\"title\":\"找商品\"},{\"title\":\"我要发布\"}]},{\"title\":\"我的\",\"sub\":[{\"title\":\"我的信息\"},{\"title\":\"我的发布\"}]},{\"title\":\"服务\",\"sub\":[{\"title\":\"意见反馈\"},{\"title\":\"新手教程\"},{\"title\":\"关于借道\"}]}]}";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		layout_customemenu = (LinearLayout) findViewById(R.id.layout_customemenu);
		layout_custommenu = (LinearLayout) findViewById(R.id.layout_custommenu);
        // textView = (TextView) findViewById(R.id.main_text_view);

		fm = getFragmentManager();
		ft = fm.beginTransaction();
		ft.replace(R.id.fragment_content, new DefaultFragment());
		ft.commit();
		try {
			setCustomMenu(new JSONObject(jsonStr));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	private void setCustomMenu(JSONObject jsonObject) throws JSONException {
		JSONArray jsonCustomMenu = jsonObject.getJSONArray("customemenu");
		if (jsonCustomMenu != null && jsonCustomMenu.length() > 0) {
			layout_customemenu.setVisibility(View.VISIBLE);
			layout_custommenu.removeAllViews();
			JSONArray btnJson = jsonCustomMenu;
			for (int i = 0; i < btnJson.length(); i++) {

				final JSONObject ob = btnJson.getJSONObject(i);
				LinearLayout layout = (LinearLayout) ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_custommenu, null);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
				layout.setLayoutParams(lp);
				TextView tv_custommenu_name = (TextView) layout.findViewById(R.id.tv_custommenu_name);
				tv_custommenu_name.setText(ob.getString("title"));
				if (ob.getJSONArray("sub").length() > 0) // 显示三角
				{
					tv_custommenu_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_black, 0);
				} else // 隐藏三角
				{
					tv_custommenu_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				}
				layout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try {
							if (ob.getJSONArray("sub").length() == 0) {
								Toast.makeText(getApplicationContext(), "Main menu clicked", Toast.LENGTH_SHORT).show();
							} else {
								popupWindow_custommenu = new PopMenus(getApplicationContext(), MainActivity.this, ob.getJSONArray("sub"), v.getWidth() + 10, 0);
								popupWindow_custommenu.showAtLocation(v);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				layout_custommenu.addView(layout);
			}
		} else {
			layout_customemenu.setVisibility(View.GONE);
		}
	}
	
	public void btnShowExchange_Click(View v) {
		// Toast.makeText(getApplicationContext(), "菜单点击事件", Toast.LENGTH_SHORT).show();
        // TextView tv = (TextView)findViewById(R.id.main_text_view);
        // tv.setText("Hello World!");
		ft = fm.beginTransaction();
		ft.replace(R.id.fragment_content, new DefaultFragment());
		ft.commit();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View view) {

	}
}
