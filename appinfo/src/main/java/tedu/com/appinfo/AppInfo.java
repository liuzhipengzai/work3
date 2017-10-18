package tedu.com.appinfo;

import android.content.Intent;
import android.graphics.drawable.Drawable;

//Model类 ，用来存储应用程序信息
public class AppInfo {

	private String appLabel;    //应用程序标签
	private Drawable appIcon ;  //应用程序图像
	private Intent intent ;     //启动应用程序的Intent ，一般是Action为Main和Category为Lancher的Activity
	private String pkgName ;    //应用程序所对应的包名
	private String version;		//应用程序的版本号
	public void setVersion(String version) {
		this.version = version;
	}
	public String getVersion() {
		return version;
	}


	public AppInfo(){}
	
	public String getAppLabel() {
		return appLabel;
	}
	public void setAppLabel(String appName) {
		this.appLabel = appName;
	}
	public Drawable getAppIcon() {
		return appIcon;
	}
	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}
	public Intent getIntent() {
		return intent;
	}
	public void setIntent(Intent intent) {
		this.intent = intent;
	}
	public String getPkgName(){
		return pkgName ;
	}
	public void setPkgName(String pkgName){
		this.pkgName=pkgName ;
	}
}
