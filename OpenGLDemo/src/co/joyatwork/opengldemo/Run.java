package co.joyatwork.opengldemo;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class Run extends Activity {

    private static final String TAG = Run.class.getSimpleName();
	private GLSurfaceView glSurfaceView; //This is our OpenGL view provided by android.

    /** Called when the activity is first created. */
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.d(TAG, "onCreate");
        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Initiate the Open GL view and
        // create an instance with this activity
        glSurfaceView = new GLSurfaceView(this);

        // set our renderer to be the main renderer with
        // the current activity context
        glSurfaceView.setRenderer(new GLRenderer(this));
        setContentView(glSurfaceView);
    }

	/** Remember to resume the glSurface  */
	@Override
	protected void onResume() {
		super.onResume();
        Log.d(TAG, "onResume");
        glSurfaceView.onResume();
	}

	/** Also pause the glSurface  */
	@Override
	protected void onPause() {
		super.onPause();
        Log.d(TAG, "onPause");
		glSurfaceView.onPause();
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.run, menu);
        return true;
    }
    
}
