/**
 * @author dingdj
 * Date:2014-5-30下午3:51:29
 *
 */
package com.ddj.launcher2.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.util.Log;

import com.ddj.launcher.R;

/**
 * @author dingdj Date:2014-5-30下午3:51:29
 * 
 */
public class IconUtil {

	private static IconUtil instance;

	private Bitmap mIconMask;

	private int sDensity = 0;
	public int sCustomizedIconHeight = 0;
	public int sCustomizedIconWidth = 0;
	private Paint sCutPaint = null;
	private Canvas sCanvas;

	public static IconUtil getInstance(Context context) {
		if (instance == null) {
			instance = new IconUtil(context);
		}
		return instance;
	}

	private IconUtil(Context context) {
		mIconMask = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.icon_mask)).getBitmap();
		sDensity = context.getResources().getDisplayMetrics().densityDpi;
		//Log.e("IconUtil", sDensity+"");
		sCustomizedIconHeight = sCustomizedIconWidth = getIconSize();
		//Log.e("IconUtil", sCustomizedIconHeight+"");
		sCanvas = new Canvas();
		sCanvas.setDrawFilter(new PaintFlagsDrawFilter(4, 2));
	}

	/**
	 * 获取图标的外部接口
	 * @author dingdj
	 * Date:2014-5-30下午5:03:18
	 *  @param drawable
	 *  @param mContext
	 *  @return
	 */
	public Bitmap createIconBitmap(Drawable drawable, Context mContext) {
		Bitmap bitmap = drawableToBitmap(drawable, getScaleRadio(drawable), mContext);
		return composeIcon(bitmap, mIconMask, null, null, null);
	}

	/**
	 * 合成icon
	 * 
	 * @author dingdj Date:2014-5-30下午4:27:01
	 * @param sourceBitmap
	 * @param iconMask
	 * @param icon_background
	 * @param icon_pattern
	 * @param icon_border
	 * @return
	 */
	public Bitmap composeIcon(Bitmap sourceBitmap, Bitmap iconMask, Bitmap icon_background, Bitmap icon_pattern, Bitmap icon_border) {
		int width = sourceBitmap.getWidth();
		int height = sourceBitmap.getHeight();
		// 全部有多少个像素点
		int pixelNum = sourceBitmap.getByteCount()/4;
		// 每行有多少像素点
		int rowPixelNum = sourceBitmap.getRowBytes()/4;
		int[] arrayOfInt = new int[pixelNum];
		sourceBitmap.getPixels(arrayOfInt, 0, rowPixelNum, 0, 0, width, height);
		sourceBitmap.recycle();
		Bitmap localBitmap = Bitmap.createBitmap(sCustomizedIconWidth, sCustomizedIconHeight, Bitmap.Config.ARGB_8888);
		Canvas localCanvas = new Canvas(localBitmap);
		localCanvas.drawBitmap(arrayOfInt, 0, rowPixelNum, 0, 0, width, height, true, null);
		try {
			if (iconMask != null) {
				if (sCutPaint == null) {
					sCutPaint = new Paint();
					sCutPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
				}
				localCanvas.drawBitmap(iconMask, 0.0F, 0.0F, sCutPaint);
			}
			localBitmap.getPixels(arrayOfInt, 0, rowPixelNum, 0, 0, width, height);
			localCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
			if (icon_background != null) {
				/*
				 * Paint localPaint = new Paint(); float[] arrayOfFloat1 =
				 * calcBackgroundColor(pixelNum, rowPixelNum, arrayOfInt);
				 * ColorMatrix localColorMatrix = new ColorMatrix(); float[]
				 * arrayOfFloat2 = new float[20]; arrayOfFloat2[0] =
				 * arrayOfFloat1[0]; arrayOfFloat2[1] = 0.0F; arrayOfFloat2[2] =
				 * 0.0F; arrayOfFloat2[3] = 0.0F; arrayOfFloat2[4] = 0.0F;
				 * arrayOfFloat2[5] = 0.0F; arrayOfFloat2[6] = arrayOfFloat1[1];
				 * arrayOfFloat2[7] = 0.0F; arrayOfFloat2[8] = 0.0F;
				 * arrayOfFloat2[9] = 0.0F; arrayOfFloat2[10] = 0.0F;
				 * arrayOfFloat2[11] = 0.0F; arrayOfFloat2[12] =
				 * arrayOfFloat1[2]; arrayOfFloat2[13] = 0.0F; arrayOfFloat2[14]
				 * = 0.0F; arrayOfFloat2[15] = 0.0F; arrayOfFloat2[16] = 0.0F;
				 * arrayOfFloat2[17] = 0.0F; arrayOfFloat2[18] = 1.0F;
				 * arrayOfFloat2[19] = 0.0F;
				 * localColorMatrix.set(arrayOfFloat2);
				 * localPaint.setColorFilter(new
				 * ColorMatrixColorFilter(localColorMatrix));
				 * localCanvas.drawBitmap(icon_background, 0.0F, 0.0F,
				 * localPaint);
				 */
			}
			if (icon_pattern != null)
				localCanvas.drawBitmap(icon_pattern, 0.0F, 0.0F, null);
			localCanvas.drawBitmap(arrayOfInt, 0, rowPixelNum, 0, 0, width, height, true, null);
			
			if (icon_border != null)
				localCanvas.drawBitmap(icon_border, 0.0F, 0.0F, null);
			return localBitmap;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据缩放系数 生成图片
	 * 
	 * @author dingdj Date:2014-5-30下午4:36:20
	 * @param icon
	 * @param scale
	 * @param context
	 * @return
	 */
	private Bitmap drawableToBitmap(Drawable icon, float scale, Context context) {
		synchronized (sCanvas) {
			int width = sCustomizedIconWidth;
			int height = sCustomizedIconHeight;
			if (icon instanceof PaintDrawable) {
				PaintDrawable paintDrawable = (PaintDrawable) icon;
				paintDrawable.setIntrinsicWidth(width);
				paintDrawable.setIntrinsicHeight(height);
			} else if (icon instanceof BitmapDrawable) {
				// Ensure the bitmap has a density.
				BitmapDrawable bitmapDrawable = (BitmapDrawable) icon;
				Bitmap bitmap = bitmapDrawable.getBitmap();
				if (bitmap.getDensity() == Bitmap.DENSITY_NONE) {
					bitmapDrawable.setTargetDensity(context.getResources().getDisplayMetrics());
				}
			}

			int iconWidth = icon.getIntrinsicWidth();
			int iconHeight = icon.getIntrinsicHeight();
			icon.setBounds(0, 0, iconWidth, iconHeight);
			Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			Canvas canvas = sCanvas;
			canvas.setBitmap(bitmap);
			canvas.save();
			canvas.translate((width - scale * iconWidth) / 2.0F, (height - scale * iconHeight) / 2.0F);
			canvas.scale(scale, scale);
			icon.draw(canvas);
			canvas.restore();
			return bitmap;
		}
	}

	private float getScaleRadio(Drawable drawable) {
		if (drawable instanceof PaintDrawable) {
			return 1f;
		}

		int sourceWidth = drawable.getIntrinsicWidth();
		int sourceHeight = drawable.getIntrinsicHeight();

		if (sourceWidth < 0 || sourceHeight < 0) {
			return 1f;
		}

		float ratioW = sCustomizedIconWidth / (sourceWidth + 0.0F);
		float ratioH = sCustomizedIconHeight / (sourceHeight + 0.0F);
		float f3 = getContentRatio(drawable);
		Log.d("IconCustomizer", "Content Ratio = " + f3);
		if ((f3 > 0.0F) && (f3 <= 2.0F))
			return 0.9F * f3;
		return Math.min(1.0F, Math.min(ratioW, ratioH));
	}

	private float getContentRatio(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
			return (float) sCustomizedIconHeight / (1.0F + getEdgePosition(bitmap));
		}
		return -1F;
	}

	public int getEdgePosition(Bitmap bitmap) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		return Math.max(getPositionFromBottomToTop(bitmap, h, w) - getPositionFromTop2Bottom(bitmap, h, w),
				getPositionFromRightToLeft(bitmap, w, h) - getPositionFromLeftToRight(bitmap, w, h));
	}
	
	
	/**
	 * outSideMax 外层循环最大值 h-1 inSideMax 内层循环最大值
	 * 
	 * @author dingdj Date:2013-12-26下午3:18:58
	 * @param width
	 * @return
	 */
	private int getPositionFromRightToLeft(Bitmap bitmap, int width, int height) {
		int w_j = width-1;
		for (; w_j > width / 2; w_j--) {
			int rtn = 0;
			for (int h_i = 0; h_i < height; h_i++) {
				if (bitmap.getPixel(w_j, h_i) >>> 24 > 0) {// 如果该像素的alpha值小于50
					rtn++;
				}
				if (rtn > 0) {// 有像素透明度大于50 判断这是边界
					return w_j;
				}
			}
		}
		return -1;
	}
	
	
	/**
	 * outSideMax 外层循环最大值 h-1 inSideMax 内层循环最大值
	 * 
	 * @author dingdj Date:2013-12-26下午3:18:58
	 * @param width
	 * @return
	 */
	private int getPositionFromLeftToRight(Bitmap bitmap, int width, int height) {
		int w_j = 0;
		for (; w_j < width; w_j++) {
			int rtn = 0;
			for (int h_i = 0; h_i < height; h_i++) {
				if (bitmap.getPixel(w_j, h_i) >>> 24 > 0) {// 如果该像素的alpha值小于50
					rtn++;
				}
				if (rtn > 0) {// 有像素透明度大于50 判断这是边界
					return w_j;
				}
			}
		}
		return -1;
	}

	/**
	 * outSideMax 外层循环最大值 h-1 inSideMax 内层循环最大值
	 * 
	 * @author dingdj Date:2013-12-26下午3:18:58
	 * @param height
	 * @return
	 */
	private int getPositionFromBottomToTop(Bitmap bitmap, int height, int width) {
		int h_j = height-1;
		for (; h_j > height / 2; h_j--) {
			int rtn = 0;
			for (int w_i = 0; w_i < width; w_i++) {
				if (bitmap.getPixel(w_i, h_j) >>> 24 > 0) {// 如果该像素的alpha值小于50
					rtn++;
				}
				if (rtn > 0) {// 有像素透明度大于50 判断这是边界
					return h_j;
				}
			}
		}
		return -1;
	}

	/**
	 * outSideMax 外层循环最大值 h-1 inSideMax 内层循环最大值
	 * 
	 * @author dingdj Date:2013-12-26下午3:18:58
	 * @param height
	 * @return
	 */
	private int getPositionFromTop2Bottom(Bitmap bitmap, int height, int width) {
		int h_j = 0;
		for (; h_j < height / 2; h_j++) {
			int rtn = 0;
			for (int w_i = 0; w_i < width; w_i++) {
				if (bitmap.getPixel(w_i, h_j) >>> 24 > 0) {// 如果该像素的alpha值大于50
					rtn++;
				}
				if (rtn > 0) {// 有像素透明度大于50 判断这是边界
					return h_j;
				}
			}
		}
		return -1;
	}

	/**
	 * 获取iconsize
	 * 
	 * @author dingdj Date:2014-5-30下午4:36:57
	 * @return
	 */
	private int getIconSize() {
		switch (sDensity) {
		default:
			int i = 90 * sDensity / 240;
			return i + i % 2;
		case 480:
			return 192;
		case 320:
		}
		return 136;
	}
}
