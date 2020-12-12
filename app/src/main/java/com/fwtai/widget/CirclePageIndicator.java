package com.fwtai.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import com.yinlz.cdc.R;

public class CirclePageIndicator extends View implements PageIndicator{
	
	public static final int HORIZONTAL = 0;
	
	public static final int VERTICAL = 1;

	private float mRadius;
	private final Paint mPaintPageFill;
	private final Paint mPaintStroke;
	private final Paint mPaintFill;
	private ViewPager mViewPager;
	private ViewPager.OnPageChangeListener mListener;
	private int mCurrentPage;
	private int mSnapPage;
	private int mCurrentOffset;
	private int mScrollState;
	private int mPageSize;
	private int mOrientation;
	private boolean mCentered;
	private boolean mSnap;

	public CirclePageIndicator(final Context context){
		this(context, null);
	}

	public CirclePageIndicator(final Context context,final AttributeSet attrs){
		this(context, attrs, R.attr.vpiCirclePageIndicatorStyle);
	}

	public CirclePageIndicator(final Context context,final AttributeSet attrs,final int defStyle){
		super(context, attrs, defStyle);
		final Resources res = getResources();
		final int defaultPageColor = ContextCompat.getColor(context,R.color.transparent);
		final int defaultFillColor = ContextCompat.getColor(context,R.color.white);
		final int defaultStrokeColor = ContextCompat.getColor(context,R.color.stroke);
		final int defaultOrientation = res.getInteger(R.integer.default_circle_indicator_orientation);
		final float defaultStrokeWidth = res.getDimension(R.dimen.default_circle_indicator_stroke_width);
		final float defaultRadius = res.getDimension(R.dimen.default_circle_indicator_radius);
		final boolean defaultCentered = res.getBoolean(R.bool.default_circle_indicator_centered);
		final boolean defaultSnap = res.getBoolean(R.bool.default_circle_indicator_snap);
		final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CirclePageIndicator,defStyle,R.style.Widget_CirclePageIndicator);
		mCentered = a.getBoolean(R.styleable.CirclePageIndicator_centered, defaultCentered);
		mOrientation = a.getInt(R.styleable.CirclePageIndicator_orientation, defaultOrientation);
		mPaintPageFill = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintPageFill.setStyle(Style.FILL);
		mPaintPageFill.setColor(a.getColor(R.styleable.CirclePageIndicator_pageColor, defaultPageColor));
		mPaintStroke = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintStroke.setStyle(Style.STROKE);
		mPaintStroke.setColor(a.getColor(R.styleable.CirclePageIndicator_strokeColor, defaultStrokeColor));
		mPaintStroke.setStrokeWidth(a.getDimension(R.styleable.CirclePageIndicator_strokeWidth, defaultStrokeWidth));
		mPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaintFill.setStyle(Style.FILL);
		mPaintFill.setColor(a.getColor(R.styleable.CirclePageIndicator_fillColor,defaultFillColor));
		mRadius = a.getDimension(R.styleable.CirclePageIndicator_radius,defaultRadius);
		mSnap = a.getBoolean(R.styleable.CirclePageIndicator_snap,defaultSnap);
		a.recycle();
	}

	public void setCentered(boolean centered){
		mCentered = centered;
		invalidate();
	}

	public boolean isCentered(){
		return mCentered;
	}

	public void setPageColor(int pageColor){
		mPaintPageFill.setColor(pageColor);
		invalidate();
	}

	public int getPageColor(){
		return mPaintPageFill.getColor();
	}

	public void setFillColor(int fillColor){
		mPaintFill.setColor(fillColor);
		invalidate();
	}

	public int getFillColor(){
		return mPaintFill.getColor();
	}

	public void setOrientation(int orientation){
		switch (orientation){
		case HORIZONTAL:
		case VERTICAL:
			mOrientation = orientation;
			updatePageSize();
			requestLayout();
			break;
		default:
			throw new IllegalArgumentException("Orientation must be either HORIZONTAL or VERTICAL.");
		}
	}

	public int getOrientation(){
		return mOrientation;
	}

	public void setStrokeColor(int strokeColor){
		mPaintStroke.setColor(strokeColor);
		invalidate();
	}

	public int getStrokeColor(){
		return mPaintStroke.getColor();
	}

	public void setStrokeWidth(float strokeWidth){
		mPaintStroke.setStrokeWidth(strokeWidth);
		invalidate();
	}

	public float getStrokeWidth(){
		return mPaintStroke.getStrokeWidth();
	}

	public void setRadius(float radius){
		mRadius = radius;
		invalidate();
	}

	public float getRadius(){
		return mRadius;
	}

	public void setSnap(boolean snap){
		mSnap = snap;
		invalidate();
	}

	public boolean isSnap(){
		return mSnap;
	}

	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);

		if (mViewPager == null){
			return;
		}
		final int count = mViewPager.getAdapter().getCount();
		if (count == 0){
			return;
		}

		if (mCurrentPage >= count){
			setCurrentItem(count - 1);
			return;
		}

		int longSize;
		int longPaddingBefore;
		int longPaddingAfter;
		int shortPaddingBefore;
		if (mOrientation == HORIZONTAL){
			longSize = getWidth();
			longPaddingBefore = getPaddingLeft();
			longPaddingAfter = getPaddingRight();
			shortPaddingBefore = getPaddingTop();
		} else {
			longSize = getHeight();
			longPaddingBefore = getPaddingTop();
			longPaddingAfter = getPaddingBottom();
			shortPaddingBefore = getPaddingLeft();
		}

		final float fourRadius = mRadius * 4;
		final float shortOffset = shortPaddingBefore + mRadius + mPaintStroke.getStrokeWidth();
		float longOffset = longPaddingBefore + mRadius;
		if (mCentered){
			longOffset += ((longSize - longPaddingBefore - longPaddingAfter) / 2.0f)
					- (((count - 1) * fourRadius) / 2.0f) - mRadius;
		}

		float dX;
		float dY;

		float pageFillRadius = mRadius;
		if (mPaintStroke.getStrokeWidth() > 0){
			pageFillRadius -= mPaintStroke.getStrokeWidth() / 2.0f;
		}

		// Draw stroked circles
		for (int iLoop = 0; iLoop < count; iLoop++){
			float drawLong = longOffset + (iLoop * fourRadius);
			if (mOrientation == HORIZONTAL){
				dX = drawLong;
				dY = shortOffset;
			} else {
				dX = shortOffset;
				dY = drawLong;
			}
			if (mPaintPageFill.getAlpha() > 0){
				canvas.drawCircle(dX, dY, pageFillRadius, mPaintPageFill);
			}
			if (pageFillRadius != mRadius){
				canvas.drawCircle(dX, dY, mRadius, mPaintStroke);
			}
		}
		float cx = (mSnap ? mSnapPage : mCurrentPage) * fourRadius;
		if (!mSnap && (mPageSize != 0)){
			cx += (mCurrentOffset * 1.0f / mPageSize) * fourRadius;
		}
		if (mOrientation == HORIZONTAL){
			dX = longOffset + cx;
			dY = shortOffset;
		} else {
			dX = shortOffset;
			dY = longOffset + cx;
		}
		canvas.drawCircle(dX, dY, mRadius, mPaintFill);
	}

	@Override
	public void setViewPager(ViewPager view){
		if (view.getAdapter() == null){
			throw new IllegalStateException("ViewPager尚未实例化");
		}
		mViewPager = view;
		mViewPager.addOnPageChangeListener(this);
		updatePageSize();
		invalidate();
	}

	private void updatePageSize(){
		if (mViewPager != null){
			mPageSize = (mOrientation == HORIZONTAL) ? mViewPager.getWidth() : mViewPager.getHeight();
		}
	}

	@Override
	public void setViewPager(ViewPager view, int initialPosition){
		setViewPager(view);
		setCurrentItem(initialPosition);
	}

	@Override
	public void setCurrentItem(int item){
		if (mViewPager == null){
			throw new IllegalStateException("ViewPager has not been bound.");
		}
		mViewPager.setCurrentItem(item);
		mCurrentPage = item;
		invalidate();
	}

	@Override
	public void notifyDataSetChanged(){
		invalidate();
	}

	@Override
	public void onPageScrollStateChanged(int state){
		mScrollState = state;
		if (mListener != null){
			mListener.onPageScrollStateChanged(state);
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
		mCurrentPage = position;
		mCurrentOffset = positionOffsetPixels;
		updatePageSize();
		invalidate();
		if (mListener != null){
			mListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
		}
	}

	@Override
	public void onPageSelected(int position){
		if (mSnap || mScrollState == ViewPager.SCROLL_STATE_IDLE){
			mCurrentPage = position;
			mSnapPage = position;
			invalidate();
		}
		if (mListener != null){
			mListener.onPageSelected(position);
		}
	}

	@Override
	public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener){
		mListener = listener;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		if (mOrientation == HORIZONTAL){
			setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec));
		} else {
			setMeasuredDimension(measureShort(widthMeasureSpec), measureLong(heightMeasureSpec));
		}
	}

	private int measureLong(int measureSpec){
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if ((specMode == MeasureSpec.EXACTLY) || (mViewPager == null)){
			result = specSize;
		} else {
			final int count = mViewPager.getAdapter().getCount();
			result = (int) (getPaddingLeft() + getPaddingRight() + (count * 2 * mRadius) + (count - 1) * mRadius + 1);
			if (specMode == MeasureSpec.AT_MOST){
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	private int measureShort(int measureSpec){
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY){
			result = specSize;
		} else {
			result = (int) (2 * mRadius + getPaddingTop() + getPaddingBottom() + 2 * mPaintStroke.getStrokeWidth());
			if (specMode == MeasureSpec.AT_MOST){
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	@Override
	public void onRestoreInstanceState(Parcelable state){
		SavedState savedState = (SavedState) state;
		super.onRestoreInstanceState(savedState.getSuperState());
		mCurrentPage = savedState.currentPage;
		mSnapPage = savedState.currentPage;
		requestLayout();
	}

	@Override
	public Parcelable onSaveInstanceState(){
		Parcelable superState = super.onSaveInstanceState();
		SavedState savedState = new SavedState(superState);
		savedState.currentPage = mCurrentPage;
		return savedState;
	}

	static class SavedState extends BaseSavedState {
		int currentPage;

		public SavedState(Parcelable superState){
			super(superState);
		}

		private SavedState(Parcel in){
			super(in);
			currentPage = in.readInt();
		}

		@Override
		public void writeToParcel(Parcel dest, int flags){
			super.writeToParcel(dest, flags);
			dest.writeInt(currentPage);
		}

		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){
			@Override
			public SavedState createFromParcel(Parcel in){
				return new SavedState(in);
			}

			@Override
			public SavedState[] newArray(int size){
				return new SavedState[size];
			}
		};
	}
}