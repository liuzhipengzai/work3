package tedu.com.appinfo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Adapter;

/**
 * Created by Administrator on 2017/7/2.
 */

public class DialogWithYesOrNoUtils {

    private static DialogWithYesOrNoUtils instance = null;

    public static DialogWithYesOrNoUtils getInstance() {
        if (instance == null) {
            instance = new DialogWithYesOrNoUtils();
        }
        return instance;
    }

    private DialogWithYesOrNoUtils(){}

    public void showDialog(Context context, String titleInfo, final DialogWithYesOrNoUtils.DialogCallBack callBack) {
        AlertDialog.Builder alterDialog = new AlertDialog.Builder(context);
        alterDialog.setMessage(titleInfo);
        alterDialog.setCancelable(true);

        alterDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callBack.exectEvent();
            }
        });
        alterDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alterDialog.show();
    }

    public interface DialogCallBack {
        void exectEvent();
    }



    private int filter = MainActivity.FILTER_ALL_APP;
    public int dialog2(final Context context, final BrowseApplicationInfoAdapter adapter) {
        final String items[]={"所有应用","系统应用","第三方应用"};
        //dialog参数设置
        AlertDialog.Builder builder=new AlertDialog.Builder(context);  //先得到构造器
        builder.setCancelable(false);
        builder.setTitle("选择应用界面"); //设置标题
        //builder.setMessage("是否确认退出?"); //设置内容
        builder.setIcon(R.mipmap.ic_action_list_2);//设置图标，图片id即可
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    filter = MainActivity.FILTER_ALL_APP ;
                }else if(which==1){
                    filter = MainActivity.FILTER_SYSTEM_APP ;
                }else{
                    filter = MainActivity.FILTER_THIRD_APP ;
                }
                Log.e("tedu","filter1="+filter);

//                dialog.dismiss();
//                Toast.makeText(context, items[which], Toast.LENGTH_SHORT).show();

            }
        });
//        builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                Toast.makeText(context, "确定", Toast.LENGTH_SHORT).show();
//            }
//        });
        builder.create().show();
        return filter;
    }
}
