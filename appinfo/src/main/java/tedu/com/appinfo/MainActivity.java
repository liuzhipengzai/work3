package tedu.com.appinfo;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import permission.PermissionUtil;

public class MainActivity extends Activity implements View.OnClickListener{

	private String[] requestPermissions = {
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.READ_EXTERNAL_STORAGE,};
	public static final int FILTER_ALL_APP = 0; // 所有应用程序
	public static final int FILTER_SYSTEM_APP = 1; // 系统程序
	public static final int FILTER_THIRD_APP = 2; // 第三方应用程序

	ImageButton ib;
	TextView tv;
	private PackageInfo pi;

	private ListView listview = null;
	private PackageManager pm;
	private int filter = FILTER_ALL_APP;
	private List<AppInfo> mlistAppInfo ;
	private BrowseApplicationInfoAdapter browseAppAdapter = null ;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_app_list);



		listview = (ListView) findViewById(R.id.listviewApp);
		tv =  (TextView) findViewById(R.id.show);
		tv.setOnClickListener(this);
		if(getIntent()!=null){
			filter = getIntent().getIntExtra("filter", 0) ;
		}
		mlistAppInfo = queryFilterAppInfo(filter); // 查询所有应用程序信息
		// 构建适配器，并且注册到listView
		browseAppAdapter = new BrowseApplicationInfoAdapter(this, mlistAppInfo);
		listview.setAdapter(browseAppAdapter);
	}
	// 根据查询条件，查询特定的ApplicationInfo
	private List<AppInfo> queryFilterAppInfo(int filter) {
		pm = this.getPackageManager();
		// 取得所有安装软件信息
		List<PackageInfo> allPackageInfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_ACTIVITIES);
		// 查询所有已经安装的应用程序
		List<ApplicationInfo> listAppcations = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
		Collections.sort(listAppcations, new ApplicationInfo.DisplayNameComparator(pm));// 排序
		List<AppInfo> appInfos = new ArrayList<AppInfo>(); // 保存过滤查到的AppInfo
		// 根据条件来过滤
		switch (filter) {
			case FILTER_ALL_APP: // 所有应用程序
				appInfos.clear();
				for (int i = 0; i < allPackageInfos.size(); i++) {
					// 得到每个软件信息
					pi = allPackageInfos.get(i);
					ApplicationInfo appInfo = pi.applicationInfo;
					appInfos.add(getAppInfo(appInfo,pi));
				}
//				for (ApplicationInfo app : listAppcations) {
//					appInfos.add(getAppInfo(app,pi));
//				}
				return appInfos;
			case FILTER_SYSTEM_APP: // 系统程序
				appInfos.clear();
				for (int i = 0; i < allPackageInfos.size(); i++) {
					pi = allPackageInfos.get(i);
					ApplicationInfo appInfo = pi.applicationInfo;
					if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
						// 得到每个软件信息
						appInfos.add(getAppInfo(appInfo,pi));
					}


				}
				return appInfos;
			case FILTER_THIRD_APP: // 第三方应用程序
				appInfos.clear();
				for (int i = 0; i < allPackageInfos.size(); i++) {
					pi = allPackageInfos.get(i);
					ApplicationInfo appInfo = pi.applicationInfo;
					if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
						// 得到每个软件信息
						appInfos.add(getAppInfo(appInfo,pi));
					}


				}
				break;
			default:
				return null;
		}
		return appInfos;
	}
	// 构造一个AppInfo对象 ，并赋值
	private AppInfo getAppInfo(ApplicationInfo app,PackageInfo pi) {
		AppInfo appInfo = new AppInfo();
		appInfo.setAppLabel((String) app.loadLabel(pm));
		appInfo.setAppIcon(app.loadIcon(pm));
		appInfo.setPkgName(app.packageName);
		appInfo.setVersion(pi.versionName);
		return appInfo;
	}

	StringBuffer x=new StringBuffer();
	@Override
	public void onClick(View view) {

		//ActivityCompat.requestPermissions(this, requestPermissions, 10086);

		for(int i=0;i<mlistAppInfo.size();i++){
			String appname = mlistAppInfo.get(i).getAppLabel();
			String packagename = mlistAppInfo.get(i).getPkgName();
			String version = mlistAppInfo.get(i).getVersion();
			x.append(appname+"\t"+packagename+"\t"+version);
			x.append("\n");
			String y = null;

			y = new String(x);

			try {
				ByteArrayInputStream inputStream = new ByteArrayInputStream(y.getBytes("GBK"));
				//Environment.getExternalStorageDirectory()
				//Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath()=/storage/emulated/0/Music
				//this.getExternalFilesDir("appinfo").toString()存储到Android/data中
				FileOutputStream outputStream = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getPath()+ "/a.xls");

				byte[] buf=new byte[1024];
				int len ;
				while ((len = inputStream.read(buf)) != -1) {
					outputStream.write(buf,0,len);
				}
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
				Log.e("tedu","e="+e);
			}
		}
		Toast.makeText(this,"打印成功，请到手机目录查看",Toast.LENGTH_LONG).show();
	}







}


