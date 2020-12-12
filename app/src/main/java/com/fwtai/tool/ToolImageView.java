package com.fwtai.tool;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.yinlz.cdc.R;

import java.io.File;

public final class ToolImageView{

	private static Activity activity;
	
	private static ToolImageView instance = null;
	
	private ToolImageView(final Activity activity){
		ToolImageView.activity = activity;
	}
	
	public static final ToolImageView getInstance(){
		if (instance == null){
			instance = new ToolImageView(activity);
		}
		return instance;
	}
	
	public final void loadImage(final String image_url,final ImageView imageView){
		Picasso.with(activity).load(image_url).placeholder(R.drawable.ic_launcher).error(R.drawable.temp_icon_19).into(imageView);
	}
	
	public final void loadImage(final File file,final ImageView imageView){
		Picasso.with(activity).load(file).into(imageView);
	}
	
	public final void loadImage(final int resourceId,final ImageView imageView){
		Picasso.with(activity).load(resourceId).into(imageView);
	}
	
	public final void loadImageSize(final String image_url, int width, int height,final ImageView imageView){
        Picasso.with(activity).load(image_url).resize(width,height).centerCrop().into(imageView);
    }
	
	public final void loadImageCrop(String image_url, ImageView imageView){
        Picasso.with(activity).load(image_url).transform(new CropImage()).into(imageView);
    }
	
	/** 实现对图片的自定义裁剪 */
    public final class CropImage implements Transformation {
        @Override
        public Bitmap transform(Bitmap source){
            int size = Math.min(source.getWidth(),source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            final Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result!=null){
                source.recycle();;
            }
            return result;
        }
        @Override
        public String key(){
            return "square()";
        }
    }
}