package ljusas.com.movies.activities;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import ljusas.com.movies.R;


public class AboutDialog extends AlertDialog.Builder{

	public AboutDialog(Context context) {
		super(context);
		
		setTitle(R.string.about_aplication);
	    setMessage(R.string.text);
	    setCancelable(false);
	    
	    setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});
	    
	    setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
	}
	
	
	public AlertDialog prepareDialog(){
		AlertDialog dialog = create();
		dialog.setCanceledOnTouchOutside(false);
		
		return dialog;
	}
	
}
