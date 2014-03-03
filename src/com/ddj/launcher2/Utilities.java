/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ddj.launcher2;

import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.util.DisplayMetrics;
import android.util.Log;

import com.ddj.launcher.R;

/**
 * Various utilities shared amongst the Launcher's classes.
 */
final class Utilities {
    @SuppressWarnings("unused")
    private static final String TAG = "Launcher.Utilities";

    private static int sIconWidth = -1;
    private static int sIconHeight = -1;
    private static int sIconTextureWidth = -1;
    private static int sIconTextureHeight = -1;

    private static final Paint sBlurPaint = new Paint();
    private static final Paint sGlowColorPressedPaint = new Paint();
    private static final Paint sGlowColorFocusedPaint = new Paint();
    private static final Paint sDisabledPaint = new Paint();
    private static final Rect sOldBounds = new Rect();
    private static final Canvas sCanvas = new Canvas();

    static {
        sCanvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG,
                Paint.FILTER_BITMAP_FLAG));
    }
    static int sColors[] = { 0xffff0000, 0xff00ff00, 0xff0000ff };
    static int sColorIndex = 0;

    /**
     * Returns a bitmap suitable for the all apps view. Used to convert pre-ICS
     * icon bitmaps that are stored in the database (which were 74x74 pixels at hdpi size)
     * to the proper size (48dp)
     */
    static Bitmap createIconBitmap(Bitmap icon, Context context) {
        int textureWidth = sIconTextureWidth;
        int textureHeight = sIconTextureHeight;
        int sourceWidth = icon.getWidth();
        int sourceHeight = icon.getHeight();
        if (sourceWidth > textureWidth && sourceHeight > textureHeight) {
            // Icon is bigger than it should be; clip it (solves the GB->ICS migration case)
            return Bitmap.createBitmap(icon,
                    (sourceWidth - textureWidth) / 2,
                    (sourceHeight - textureHeight) / 2,
                    textureWidth, textureHeight);
        } else if (sourceWidth == textureWidth && sourceHeight == textureHeight) {
            // Icon is the right size, no need to change it
            return icon;
        } else {
            // Icon is too small, render to a larger bitmap
            final Resources resources = context.getResources();
            return createIconBitmap(new BitmapDrawable(resources, icon), context);
        }
    }
    
    /**
	 * 修复Bitmap清晰度问题
	 * @param icon
	 * @param context
	 * @return Bitmap
	 */
	public static Bitmap createIconBitmapThumbnail(Drawable icon, Context context) {
		if (null == icon)
			return null;
		synchronized (sCanvas) {
			if (sIconWidth <= 0 || sIconHeight <= 0) {
				final Resources resources = context.getResources();
				sIconWidth = sIconHeight = (int) resources.getDimensionPixelSize(R.dimen.app_icon_size);
				
			}

			int width = sIconWidth;
			int height = sIconHeight;

			if (icon instanceof FastBitmapDrawable) {
				icon = new BitmapDrawable(context.getResources(), ((FastBitmapDrawable) icon).getBitmap());
			}

			if (icon instanceof PaintDrawable) {
				PaintDrawable painter = (PaintDrawable) icon;
				painter.setIntrinsicWidth(width);
				painter.setIntrinsicHeight(height);
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

			if (width < iconWidth || height < iconHeight) {
				final float ratio = (float) iconWidth / iconHeight;

				if (iconWidth > iconHeight) {
					height = (int) (width / ratio);
				} else if (iconHeight > iconWidth) {
					width = (int) (height * ratio);
				}

				final Bitmap.Config c = icon.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
				final Bitmap thumb = Bitmap.createBitmap(sIconWidth, sIconHeight, c);
				final Canvas canvas = sCanvas;
				canvas.setBitmap(thumb);
				sOldBounds.set(icon.getBounds());
				final int x = (sIconWidth - width) / 2;
				final int y = (sIconHeight - height) / 2;
				icon.setBounds(x, y, x + width, y + height);
				icon.draw(canvas);
				icon.setBounds(sOldBounds);
				return thumb;
			} else if (iconWidth < width && iconHeight < height) {
				final Bitmap.Config c = Bitmap.Config.ARGB_8888;
				final Bitmap thumb = Bitmap.createBitmap(sIconWidth, sIconHeight, c);
				final Canvas canvas = sCanvas;
				canvas.setBitmap(thumb);
				sOldBounds.set(icon.getBounds());
				icon.setBounds(0, 0, width, height);
				icon.draw(canvas);
				icon.setBounds(sOldBounds);
				return thumb;
			} else if (icon instanceof BitmapDrawable) {
				// Log.i(TAG, "icon instanceof BitmapDrawable");
				return ((BitmapDrawable) icon).getBitmap();
			} else {
				final Bitmap.Config c = Bitmap.Config.ARGB_8888;
				final Bitmap thumb = Bitmap.createBitmap(sIconWidth, sIconHeight, c);
				final Canvas canvas = sCanvas;
				canvas.setBitmap(thumb);
				sOldBounds.set(icon.getBounds());
				icon.setBounds(0, 0, width, height);
				icon.draw(canvas);
				icon.setBounds(sOldBounds);
				return thumb;
			}
		}
	}
    

    /**
     * Returns a bitmap suitable for the all apps view.
     */
    static Bitmap createIconBitmap(Drawable icon, Context context) {
        synchronized (sCanvas) { // we share the statics :-(
            if (sIconWidth == -1) {
                initStatics(context);
            }

            int width = sIconWidth;
            int height = sIconHeight;

            if (icon instanceof PaintDrawable) {
                PaintDrawable painter = (PaintDrawable) icon;
                painter.setIntrinsicWidth(width);
                painter.setIntrinsicHeight(height);
            } else if (icon instanceof BitmapDrawable) {
                // Ensure the bitmap has a density.
                BitmapDrawable bitmapDrawable = (BitmapDrawable) icon;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (bitmap.getDensity() == Bitmap.DENSITY_NONE) {
                    bitmapDrawable.setTargetDensity(context.getResources().getDisplayMetrics());
                }
            }
            int sourceWidth = icon.getIntrinsicWidth();
            int sourceHeight = icon.getIntrinsicHeight();
            if (sourceWidth > 0 && sourceHeight > 0) {
                // There are intrinsic sizes.
                if (width < sourceWidth || height < sourceHeight) {
                    // It's too big, scale it down.
                    final float ratio = (float) sourceWidth / sourceHeight;
                    if (sourceWidth > sourceHeight) {
                        height = (int) (width / ratio);
                    } else if (sourceHeight > sourceWidth) {
                        width = (int) (height * ratio);
                    }
                } else if (sourceWidth < width && sourceHeight < height) {
                    // Don't scale up the icon
                    width = sourceWidth;
                    height = sourceHeight;
                }
            }

            // no intrinsic size --> use default size
            int textureWidth = sIconTextureWidth;
            int textureHeight = sIconTextureHeight;

            final Bitmap bitmap = Bitmap.createBitmap(textureWidth, textureHeight,
                    Bitmap.Config.ARGB_8888);
            final Canvas canvas = sCanvas;
           /* float scaleRadio = getScaleRadio((BitmapDrawable)icon, sourceWidth, sourceHeight, sIconTextureWidth, textureHeight);
            icon.setBounds(0, 0, sourceWidth, sourceHeight); */
            canvas.setBitmap(bitmap);
           /* canvas.save();
            canvas.translate((sIconTextureWidth - sourceWidth*scaleRadio) / 2.0F, (sIconTextureHeight - sourceHeight*scaleRadio) / 2.0F);
            canvas.scale(scaleRadio, scaleRadio);
            icon.draw(canvas);
            canvas.restore();
            canvas.setBitmap(null);*/

            final int left = (textureWidth-width) / 2;
            final int top = (textureHeight-height) / 2;

            @SuppressWarnings("all") // suppress dead code warning
            final boolean debug = false;
            if (debug) {
                // draw a big box for the icon for debugging
                canvas.drawColor(sColors[sColorIndex]);
                if (++sColorIndex >= sColors.length) sColorIndex = 0;
                Paint debugPaint = new Paint();
                debugPaint.setColor(0xffcccc00);
                canvas.drawRect(left, top, left+width, top+height, debugPaint);
            }

            sOldBounds.set(icon.getBounds());
            icon.setBounds(left, top, left+width, top+height);
            icon.draw(canvas);
            icon.setBounds(sOldBounds);
            canvas.setBitmap(null);

            return bitmap;
        }
    }

    static void drawSelectedAllAppsBitmap(Canvas dest, int destWidth, int destHeight,
            boolean pressed, Bitmap src) {
        synchronized (sCanvas) { // we share the statics :-(
            if (sIconWidth == -1) {
                // We can't have gotten to here without src being initialized, which
                // comes from this file already.  So just assert.
                //initStatics(context);
                throw new RuntimeException("Assertion failed: Utilities not initialized");
            }

            dest.drawColor(0, PorterDuff.Mode.CLEAR);

            int[] xy = new int[2];
            Bitmap mask = src.extractAlpha(sBlurPaint, xy);

            float px = (destWidth - src.getWidth()) / 2;
            float py = (destHeight - src.getHeight()) / 2;
            dest.drawBitmap(mask, px + xy[0], py + xy[1],
                    pressed ? sGlowColorPressedPaint : sGlowColorFocusedPaint);

            mask.recycle();
        }
    }

    /**
     * Returns a Bitmap representing the thumbnail of the specified Bitmap.
     * The size of the thumbnail is defined by the dimension
     * android.R.dimen.launcher_application_icon_size.
     *
     * @param bitmap The bitmap to get a thumbnail of.
     * @param context The application's context.
     *
     * @return A thumbnail for the specified bitmap or the bitmap itself if the
     *         thumbnail could not be created.
     */
    static Bitmap resampleIconBitmap(Bitmap bitmap, Context context) {
        synchronized (sCanvas) { // we share the statics :-(
            if (sIconWidth == -1) {
                initStatics(context);
            }

            if (bitmap.getWidth() == sIconWidth && bitmap.getHeight() == sIconHeight) {
                return bitmap;
            } else {
                final Resources resources = context.getResources();
                return createIconBitmap(new BitmapDrawable(resources, bitmap), context);
            }
        }
    }

    static Bitmap drawDisabledBitmap(Bitmap bitmap, Context context) {
        synchronized (sCanvas) { // we share the statics :-(
            if (sIconWidth == -1) {
                initStatics(context);
            }
            final Bitmap disabled = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                    Bitmap.Config.ARGB_8888);
            final Canvas canvas = sCanvas;
            canvas.setBitmap(disabled);
            
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, sDisabledPaint);

            canvas.setBitmap(null);

            return disabled;
        }
    }

    private static void initStatics(Context context) {
        final Resources resources = context.getResources();
        final DisplayMetrics metrics = resources.getDisplayMetrics();
        final float density = metrics.density;      
        sIconWidth = sIconHeight = (int) resources.getDimension(R.dimen.app_icon_size);
        sIconTextureWidth = sIconTextureHeight = sIconWidth;

        sBlurPaint.setMaskFilter(new BlurMaskFilter(5 * density, BlurMaskFilter.Blur.NORMAL));
        sGlowColorPressedPaint.setColor(0xffffc300);
        sGlowColorFocusedPaint.setColor(0xffff8e00);

        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0.2f);
        sDisabledPaint.setColorFilter(new ColorMatrixColorFilter(cm));
        sDisabledPaint.setAlpha(0x88);
    }

    /** Only works for positive numbers. */
    static int roundToPow2(int n) {
        int orig = n;
        n >>= 1;
        int mask = 0x8000000;
        while (mask != 0 && (n & mask) == 0) {
            mask >>= 1;
        }
        while (mask != 0) {
            n |= mask;
            mask >>= 1;
        }
        n += 1;
        if (n != orig) {
            n <<= 1;
        }
        return n;
    }

    static int generateRandomId() {
        return new Random(System.currentTimeMillis()).nextInt(1 << 24);
    }
    
    private static int getIconSize(int sDensity)
    {
      switch (sDensity)
      {
      default:
        int i = 90 * sDensity / 240;
        return i + i % 2;
      case 480:
        return 192;
      case 320:
      }
      return 136;
    }
    
    private static float getScaleRadio(BitmapDrawable drawable, int sw, int sh, int dw, int dh){
    	float radioW = dw/(sw+0.0F);
    	float radioH = dh/(sh+0.0F);
    	float f3 = getContentRatio(drawable);
    	//printStackForDebug();
        Log.d("IconCustomizer", "Content Ratio = " + f3);
        if ((f3 > 0.0F) && (f3 <= 2.0F))
          return 0.9F * f3;
    	return Math.min(1.0F, Math.min(radioW, radioH));
    }
    
    private static float getContentRatio(Drawable drawable)
    {
        if(drawable instanceof BitmapDrawable)
        {
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            float f = getEdgePosition(bitmap, true, false);
            if(f >= 0.0F)
            {
                float f1 = getEdgePosition(bitmap, true, true);
                if(f1 >= 0.0F)
                {
                    float f2 = getEdgePosition(bitmap, false, false);
                    if(f2 >= 0.0F)
                    {
                        float f3 = getEdgePosition(bitmap, false, true);
                        if(f3 >= 0.0F)
                            return Math.min((float)sIconWidth / (1.0F + (f3 - f2)), (float)sIconHeight / (1.0F + (f1 - f)));
                    }
                }
            }
        }
        return -1F;
    }
    
    public static int getEdgePosition(Bitmap bitmap, boolean flag, boolean flag1){
    	int w = bitmap.getWidth();
    	int h = bitmap.getHeight();
    	if(flag){//计算高度
    		if(flag1){//计算从底部开始
    			return getPosition(bitmap, h-1, w);
    		}else{//计算从顶部开始
    			return getPositionReverse(bitmap, h-1, w);
    		}
    	}else{//计算宽度
    		if(flag1){//计算从右边开始
    			return getPosition(bitmap, w-1, h);
    		}else{//计算从左边开始
    			return getPositionReverse(bitmap, w-1, h);
    		}
    	}
    }
    
    /**
     * outSideMax 外层循环最大值 h-1
     * inSideMax 内层循环最大值
     * @author dingdj
     * Date:2013-12-26下午3:18:58
     * @param outSideMax
     * @return
     */
    private static int getPosition(Bitmap bitmap, int outSideMax, int insideMax){
    	int j = outSideMax;
		 for(; j>outSideMax/2; j--){
			int rtn = 0;
			for(int i=0; i<insideMax; i++){
				if(bitmap.getPixel(i, j) >>>24 > 50){//如果该像素的alpha值小于50
					rtn++;
				}
				if(rtn > 0){//有像素透明度大于50 判断这是边界
					return j;
				}
			}
		 }
		 return -1;
    }
    
    
    
    
    /**
     * outSideMax 外层循环最大值 h-1
     * inSideMax 内层循环最大值
     * @author dingdj
     * Date:2013-12-26下午3:18:58
     * @param outSideMax
     * @return
     */
    private static int getPositionReverse(Bitmap bitmap, int outSideMax, int insideMax){
    	int j = 0;
		 for(; j<outSideMax/2; j++){
			int rtn = 0;
			for(int i=0; i<insideMax; i++){
				if(bitmap.getPixel(i, j) >>>24 > 50){//如果该像素的alpha值大于50
					rtn++;
				}
				if(rtn > 0){//有像素透明度大于50 判断这是边界
					return j;
				}
			}
		 }
		 return -1;
    }
    
    /**
	 * 将drawable转换为bitmap
	 * @param drawable
	 * @return Bitmap
	 */
	public static Bitmap drawable2Bitmap(Drawable drawable) {
		if (null == drawable)
			return null;
		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}
}
