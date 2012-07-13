package tynstep.android.viewer;

import tynstep.android.viewer.imagezoom.ImageViewTouch;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class MyGallery extends EcoGallery implements OnGestureListener { //, OnScaleGestureListener {

	private static final String TAG = "MyGallery";
//	ZoomableImageView imageView;
//	PinchImageView imageView;
	ImageViewTouch imageView;
	
//	ImageView imageView;
	
//	Scroller mScroller;
	
//	static final int NONE = 0;
//	static final int DRAG = 1;  
//	int mode = NONE;
		
	private Context c;
//	private final LayoutInflater mInflater;
//	
//	private ScaleGestureDetector mScaleDetector;
	private GestureDetector mDetector;
//	private float mScaleFactor = 1.f;
	
//	float new_distance_touch, old_distance_touch, init_x, init_y;
	
//	Matrix matrix = new Matrix();
//	Matrix savedMatrix = new Matrix();
	
//	PointF mid = new PointF();
//	PointF start = new PointF();
	
//	float MAX_ZOOM = 5.0f;
//	float MIN_ZOOM = 0.9f;
	
//	ImageView imgPicture;
	
	public MyGallery(Context context) {
		super(context);
	}
	
	public MyGallery(Context context, AttributeSet attrSet) {
	    super(context, attrSet);
//	    mInflater = LayoutInflater.from(context);
	    c = context;
	    mDetector = new GestureDetector(c,this);
//	    mScaleDetector = new ScaleGestureDetector(c, this);
	}

//	public boolean isDraggable() {
//		if ( mScaleFactor > 1.0f ) {
//			return true;
//		}
//		else {
//			return false;
//		}
//	}
	
	
	@Override
	public boolean onInterceptTouchEvent( MotionEvent event ) {
		
//		imageView = (ZoomableImageView) super.getSelectedView();
//		imageView = (PinchImageView) super.getSelectedView();
		
//		Log.d("event",""+event.getAction());
		
		imageView = (ImageViewTouch) super.getSelectedView();
		
//		imageView= (ImageView) super.getSelectedView();
		
//		float mScale = imageView.getScale();
//		Log.d(TAG, "Scale=" + imageView.currentScale);
		
//		// For ZoomableImageView
//		if ( !(imageView.normScale > 1.0f) ) {
//	    	onTouchEvent( event );
//	    }

//		// For PinchImageView
//		if ( !(imageView.getScale() > 1.0f) ) {
//	    	onTouchEvent( event );
//	    }
		
		// For ImageViewTouch
	    if ( !(imageView.getScale() > 1.0f) ) {
//	    	requestDisallowInterceptTouchEvent(true);
	    	onTouchEvent( event );
	    }
	    
	    return super.onInterceptTouchEvent( event );
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) { //gesture detector for the movements
//
//	    imgPicture = (ImageView) super.getSelectedView();
//	
//	    if (mDetector.onTouchEvent(event)) {
//	        Log.d("onTouchEvent", "--[ MOVEMENT ]--");
//	        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//	        case MotionEvent.ACTION_DOWN:
//	            init_x = event.getX();
//	            init_y = event.getY();
//	            midPoint(mid, event);
//	            savedMatrix.set(matrix);
//	            start.set(event.getX(), event.getY());
//	            Log.d(TAG, "mode=DRAG" );
//	            mode = DRAG;
//	            break;
//	            
//	        case MotionEvent.ACTION_MOVE:
//	            if (mode == DRAG) {
//	                matrix.set(savedMatrix);
//	                matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
//	            }
//	            break;
//	        }
//	
//	        imgPicture = (ImageView) super.getSelectedView();
//	        imgPicture.setImageMatrix(matrix);
//	
//	        return true;
//	    }
//	    else if(mScaleDetector.onTouchEvent(event)) { // scale detector for zoom
//	        Log.d("onTouchEvent", "--[ SCALE ]--");
//	        return true;
//	    }
//	    else 
//	        return super.onTouchEvent(event);
	        
//	    if (!(imageView.getScale() > 1.0f)) {
//	    	return false;
//	    }
//	    else {
//	    	return super.onTouchEvent(event);
//	    }
		return super.onTouchEvent(event);
	}
//	
//		
//	
//	public boolean onScale(ScaleGestureDetector detector) {
//	
//	    mScaleFactor *= detector.getScaleFactor();
//	    mScaleFactor = Math.max(MIN_ZOOM, Math.min(mScaleFactor, MAX_ZOOM));
//	
//	    if (new_distance_touch > 10f) {
//	        matrix.set(savedMatrix);
//	        matrix.postScale(mScaleFactor, mScaleFactor, mid.x, mid.y);
//	        Log.d("ZOOMMING",matrix.toShortString());
//	    }
//	    else {
//	        matrix.set(savedMatrix);
//	        matrix.postTranslate(init_x - start.x, init_y - start.y);
//	        Log.d("PANNING",matrix.toShortString());
//	    }
//	
//	    imgPicture.setImageMatrix(matrix);
//	
//	    imgPicture.invalidate();
//	
//	    Log.d("MATRIX", matrix.toString());
//	    return true;
//	}
//	
//	//@Override
//	public boolean onScaleBegin(ScaleGestureDetector detector) {
//	    Log.d(TAG, "-- onScaleBegin --");
//	    matrix = imgPicture.getImageMatrix();
//	    savedMatrix.set(matrix);
//	    start.set(init_x, init_y);
//	    return true;
//	}
//	
//	//@Override
//	public void onScaleEnd(ScaleGestureDetector detector) {
//	    Log.d(TAG, "-- onScaleEnd --");
//	    old_distance_touch = detector.getPreviousSpan();
//	    new_distance_touch = detector.getCurrentSpan();
//	
//	}
	
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//	    Log.d(TAG, "-- onFling --");
	
	    float velMax = 2500f;
	    float velMin = 1000f;
	    float velX = Math.abs(velocityX);
	    if (velX > velMax) {
	      velX = velMax;
	    } 
	    else if (velX < velMin) {
	      velX = velMin;
	    }
	    velX -= 600;
	    int k = 500000;
	    int speed = (int) Math.floor(1f / velX * k);
	    setAnimationDuration(speed);
	
	    int kEvent;
	    if (isScrollingLeft(e1, e2)) {
	      kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
	    } else {
	      kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
	    }
	    onKeyDown(kEvent, null);
	    
	    return true;
	}

	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2){
	    return e2.getX() > e1.getX();
	}
	
//	@Override
//	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//	    Log.d(TAG, "-- onScroll --");
//	    return super.onScroll(e1, e2, distanceX, distanceY);
//	}
	
//	@Override
//	public boolean onDoubleTap( MotionEvent e ) {
//		return false;
//	}
	
//	private float spacing(MotionEvent event) {
//		float x = event.getX(0) - event.getX(1);
//		float y = event.getY(0) - event.getY(1);
//		return FloatMath.sqrt(x * x + y * y);
//		}
//	
	
	
//	private void midPoint(PointF point, MotionEvent event) {
//	    float x = event.getX(0) + event.getX(1);
//	    float y = event.getY(0) + event.getY(1);
//	    point.set(x / 2, y / 2);
//	}
	
//	private class ScaleListener extends
//		ScaleGestureDetector.SimpleOnScaleGestureListener {
//
//		@Override
//		public boolean onScale(ScaleGestureDetector detector) {
//		    mScaleFactor = detector.getScaleFactor();
//		    mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
//		    Log.d(TAG, "" + mScaleFactor);
//		    scale(mScaleFactor, detector.getFocusX(), detector.getFocusY());
//		    return true;
//		}
//	}
	
//	public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            if (matrix == null)
//                matrix = new Matrix();
//            matrix.set(getSelectedImageView().getImageMatrix());
//        } else if (event.getAction() == MotionEvent.ACTION_UP
//                && event.getPointerCount() == 0) {
//            scrolling = 0;
//        }
//
//        mScaleDetector.onTouchEvent(event);
//        mGestureDetector.onTouchEvent(event);
//        return super.onTouchEvent(event);
//    }
	
}