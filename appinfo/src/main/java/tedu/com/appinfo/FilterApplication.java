package tedu.com.appinfo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import permission.PermissionUtil;

public class FilterApplication  extends Activity implements OnClickListener{

	private Button btallapp; // 所有应用程序
	private Button btsystemapp;// 系统程序
	private Button btthirdapp; // 第三方应用程序
	
	private int filter = MainActivity.FILTER_ALL_APP; 
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		PermissionUtil.needPermission(this,1, Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_EXTERNAL_STORAGE);
		// 初始化控件并监听
		btallapp = (Button) findViewById(R.id.btallapp);
		btsystemapp = (Button) findViewById(R.id.btsystemapp);
		btthirdapp = (Button) findViewById(R.id.btthirdapp);

		btallapp.setOnClickListener(this);
		btsystemapp.setOnClickListener(this);
		btthirdapp.setOnClickListener(this);

	}



	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		System.out.println(""+view.getId());
		switch(view.getId()){
		case R.id.btallapp	:
			filter = MainActivity.FILTER_ALL_APP ;
			break ;
		case R.id.btsystemapp:
			filter = MainActivity.FILTER_SYSTEM_APP ;
			break ;
		case R.id.btthirdapp:
			filter = MainActivity.FILTER_THIRD_APP ;
			break ;
		}
		Intent intent = new Intent(getBaseContext(),MainActivity.class) ;
		intent.putExtra("filter", filter) ;
		startActivity(intent);
	}

}
