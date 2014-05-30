/**
 * @author dingdj
 * Date:2014-5-28下午2:32:17
 *
 */
package com.ddj.launcher2.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.ddj.launcher.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.FileUtils;
import android.util.Log;

/**
 * @author dingdj Date:2014-5-28下午2:32:17
 * 
 */
public class IconCustomizer {

	private static IconCustomizer instance;

	public static IconCustomizer getInstance(Context context) {
		if (instance == null) {
			instance = new IconCustomizer(context);
		}
		return instance;
	}

	private IconCustomizer(Context context) {
		mIconMask = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.icon_mask)).getBitmap();

		if (mIconMask != null) {
			int maskWidth = mIconMask.getWidth();
			int maskHeight = mIconMask.getHeight();
			mMaskPixels = new int[maskWidth * maskHeight];
			mIconMask.getPixels(mMaskPixels, 0, maskWidth, 0, 0, maskWidth, maskHeight);
		}
	}

	private static final String TAG = IconCustomizer.class.getSimpleName();

	private static final Canvas sCanvas = new Canvas();

	private static int sCustomizedIconHeight = 90;

	private static int sCustomizedIconWidth = 90;

	private static int sIconHeight = 90;

	private static int sIconWidth = 90;

	private static final Rect sOldBounds = new Rect();

	private static final String sPathPrefix = "/data/system/customized_icons/";

	private static int sDensity = 0x0;

	private static Resources sSystemResource;

	private static Bitmap mIconBackground;

	private static Bitmap mIconPattern;

	private static Bitmap mIconMask;

	private static Bitmap mIconBorder;

	private static int[] mMaskPixels;

	static {
		sSystemResource = Resources.getSystem();

		sDensity = sSystemResource.getDisplayMetrics().densityDpi;

		sCanvas.setDensity(sDensity);

		sCanvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG, Paint.FILTER_BITMAP_FLAG));

		// sIconWidth = sIconHeight = sSystemResource.getDimensionPixelSize(
		// R.dimen.app_icon_size);

		/*
		 * mIconBackground = ((BitmapDrawable) sSystemResource.getDrawable(
		 * R.drawable.icon_background)).getBitmap();
		 */

		// if (mIconBackground != null) {
		// sCustomizedIconWidth = mIconBackground.getWidth();
		// sCustomizedIconHeight = mIconBackground.getHeight();
		// }
		//
		// if ( sCustomizedIconWidth < 90 )
		// sIconWidth = sIconHeight = 48;
		// else
		// sIconWidth = sIconHeight = 90;

		// Log.i(TAG,
		// String.format("sCustomizedIconWidth = %d, sCustomizedIconHeigh = %d, sIconWidth = %d, sIconHeight = %d",
		// sCustomizedIconWidth, sCustomizedIconHeight, sIconWidth,
		// sIconHeight));

		/*
		 * mIconPattern = ((BitmapDrawable) sSystemResource.getDrawable(
		 * R.drawable.icon_pattern)).getBitmap();
		 * 
		 * mIconBorder = ((BitmapDrawable) sSystemResource.getDrawable(
		 * R.drawable.icon_border)).getBitmap();
		 */

		
	}

	/*
	 * public static BitmapDrawable getBuiltInIconDrawable(String packageName) {
	 * String fileName = getFileName(packageName, null); Bitmap icon =
	 * getBuiltInIcon(fileName); if (icon != null) { return new
	 * BitmapDrawable(sSystemResource, icon); } return null; }
	 * 
	 * private static Bitmap getBuiltInIcon(String fileName) { return
	 * ThemeResource.getPreloadAppIcon(sSystemResource, fileName); }
	 */

	private static Bitmap getBitmapResource(int resId) {
		BitmapDrawable d = (BitmapDrawable) sSystemResource.getDrawable(resId);
		return d.getBitmap();
	}

	public static String getFileName(String packageName, String className) {
		return String.format("%s.png", (className != null) ? className : packageName);
	}

	/*
	 * public static Drawable getCustomizedIconDrawable(String fileName) {
	 * Bitmap icon = getBuiltInIcon(fileName); if (icon == null) { StringBuilder
	 * sb = new StringBuilder(); sb.append(sPathPrefix); sb.append(fileName);
	 * String pathName = sb.toString(); File iconFile = new File(pathName); if
	 * (iconFile.exists()) { icon = BitmapFactory.decodeFile(pathName); if (icon
	 * != null) { icon.setDensity(sDensity); } else { iconFile.delete(); } } }
	 * if (icon != null) { return new BitmapDrawable(sSystemResource, icon); }
	 * return null; }
	 */

	public Bitmap generateIconDrawable(Drawable base) {
		Bitmap icon = drawableToBitmap(base);
		return new BitmapDrawable(sSystemResource, composeIcon(icon)).getBitmap();
	}

	/**
	 * 合成 Icon
	 * 
	 * @param icon
	 * @return
	 */
	private static Bitmap composeIcon(Bitmap base) {
		int baseWidth = base.getWidth();
		int baseHeight = base.getHeight();
		int[] basePixels = new int[baseWidth * baseHeight];
		base.getPixels(basePixels, 0, baseWidth, 0, 0, baseWidth, baseHeight);
		base.recycle();

		int w = baseWidth;
		int h = baseHeight;

		int filterColor = Color.BLACK;

		if (mMaskPixels != null) {
			int[] hueGraph = new int[256];

			for (int i = 0; i < w * h; i++) {
				int src = basePixels[i];
				if ((0xff & (src >> 24)) > 0x80) {
					hueGraph[getHue(src)]++;
				}
				if(mMaskPixels[i] == 0){
					basePixels[i] = src & mMaskPixels[i];
				}
			}

			// 计算颜色直方图中的最大分量
			int hue = -1;
			int count = 0;
			for (int i = 0; i < hueGraph.length; i++) {
				if (count < hueGraph[i]) {
					count = hueGraph[i];
					hue = i;
				}
			}

			// 计算对比色，可以替换不同的算法
			hue = (hue < 128) ? hue + 128 : hue - 128;

			/*hue += 64;
			hue = (hue > 255) ? hue - 255 : hue;

			Log.i(TAG, "HUE = " + hue);*/

			filterColor = hsb(hue, 192, 128);
		}

		filterColor = Color.BLACK;
		
		Bitmap maskedBitmap = Bitmap.createBitmap(basePixels, w, h, Config.ARGB_8888);

		Bitmap icon = Bitmap.createBitmap(w, h, Config.ARGB_8888);

		Canvas canvas = sCanvas;

		synchronized (sCanvas) {
			canvas.setBitmap(icon);
			Paint paint = new Paint();
			paint.setFilterBitmap(true);

			if (mMaskPixels != null) {
				paint.setColorFilter(new PorterDuffColorFilter(filterColor, Mode.MULTIPLY));
			}

			if (mIconBackground != null) {
				canvas.drawBitmap(mIconBackground, 0, 0, paint);
			}

			if (mIconPattern != null) {
				canvas.drawBitmap(mIconPattern, 0, 0, null);
			}

			canvas.drawBitmap(maskedBitmap, 0, 0, null);

			if (mIconBorder != null) {
				canvas.drawBitmap(mIconBorder, 0, 0, null);
			}
		}

		maskedBitmap.recycle();

		return icon;
	}

	private static Bitmap drawableToBitmap(Drawable icon) {
		synchronized (sCanvas) {
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
					bitmapDrawable.setTargetDensity(sDensity);
				}
			}
			// int sourceWidth = icon.getIntrinsicWidth();
			// int sourceHeight = icon.getIntrinsicHeight();

			// if (sourceWidth > 0 && sourceWidth > 0) {
			// // There are intrinsic sizes.
			// if (width < sourceWidth || height < sourceHeight) {
			// // It's too big, scale it down.
			// final float ratio = (float) sourceWidth / sourceHeight;
			// if (sourceWidth > sourceHeight) {
			// height = (int) (width / ratio);
			// } else if (sourceHeight > sourceWidth) {
			// width = (int) (height * ratio);
			// }
			// } else if (sourceWidth < width && sourceHeight < height) {
			// // It's small, use the size they gave us.
			// width = sourceWidth;
			// height = sourceHeight;
			// }
			// }

			// no intrinsic size --> use default size
			int textureWidth = sCustomizedIconWidth;
			int textureHeight = sCustomizedIconHeight;

			final Bitmap bitmap = Bitmap.createBitmap(textureWidth, textureHeight, Bitmap.Config.ARGB_8888);
			final Canvas canvas = sCanvas;
			canvas.setBitmap(bitmap);

			final int left = (textureWidth - width) / 2;
			final int top = (textureHeight - height) / 2;

			sOldBounds.set(icon.getBounds());
			icon.setBounds(left, top, left + width, top + height);
			icon.draw(canvas);
			icon.setBounds(sOldBounds);

			return bitmap;
		}
	}

	public static void clearAllCache() {
		File file = new File(sPathPrefix);
		File[] matchedFiles = file.listFiles();
		for (File f : matchedFiles) {
			f.delete();
		}
	}

	public static void saveCustomizedIconBitmap(String fileName, Bitmap icon) {
		StringBuilder sb = new StringBuilder();
		sb.append(sPathPrefix);
		sb.append(fileName);
		String pathName = sb.toString();
		File file = new File(pathName);

		FileOutputStream outputStream = null;

		try {
			if (file.exists()) {
				outputStream = new FileOutputStream(file);
			}
			if (outputStream == null) {
				File parent = file.getParentFile();
				parent.mkdirs();
				int perm = FileUtils.S_IRUSR | FileUtils.S_IWUSR | FileUtils.S_IXUSR | FileUtils.S_IRWXG | FileUtils.S_IRGRP | FileUtils.S_IWGRP
						| FileUtils.S_IRWXO | FileUtils.S_IROTH | FileUtils.S_IXOTH;
				FileUtils.setPermissions(parent.getPath(), perm, -1, -1);
				outputStream = new FileOutputStream(file);
			}
			int perm = FileUtils.S_IRUSR | FileUtils.S_IWUSR | FileUtils.S_IRGRP | FileUtils.S_IWGRP | FileUtils.S_IROTH;
			FileUtils.setPermissions(pathName, perm, -1, -1);
			icon.compress(CompressFormat.PNG, 100, outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the hue of a 32-bit ARGB color.
	 * 
	 * @param color
	 *            a 32-bit ARGB color.
	 * @return the hue, from 0 to 255.
	 */
	public static int getHue(int color) {
		int v = RGBtoHSB(color);
		return (v >> 16) & 0xff;
	}

	/**
	 * Creates an opaque color.
	 * 
	 * @param h
	 *            the hue, from 0 to 255.
	 * @param s
	 *            the saturation, from 0 to 255.
	 * @param b
	 *            the brightness, from 0 to 255.
	 * @return a packed 32-bit ARGB color with the alpha set to 255 (opaque).
	 */
	public static int hsb(int h, int s, int b) {
		return hsba(h, s, b, 0xff);
	}

	/**
	 * Creates a color with alpha.
	 * 
	 * @param h
	 *            the hue, from 0 to 255.
	 * @param s
	 *            the saturation, from 0 to 255.
	 * @param b
	 *            the brightness, from 0 to 255.
	 * @param alpha
	 *            the alpha component, from 0 (transparent) to 255 (opaque).
	 * @return a packed 32-bit ARGB color.
	 */
	public static int hsba(int h, int s, int b, int alpha) {
		return HSBtoRGB((alpha << 24) | (h << 16) | (s << 8) | b);
	}

	/**
	 * Converts a a packed, 32-bit RGB (red, green, blue) color to HSB (hue,
	 * saturation, brightness). The hue, saturation, and brightness are in the
	 * range 0 - 255. The alpha value, if any, is not modified.
	 * 
	 * <pre>
	 * int hsb = Colors.RGBtoHSB(rgb);
	 * int h = (hsb &gt;&gt; 16) &amp; 0xff;
	 * int s = (hsb &gt;&gt; 8) &amp; 0xff;
	 * int b = hsb &amp; 0xff;
	 * </pre>
	 * 
	 * @param argbColor
	 *            a 32-bit ARGB color.
	 * @return a 32-bit AHSB color.
	 * @see #HSBtoRGB(int)
	 */
	public static int RGBtoHSB(int argbColor) {

		int a = argbColor >>> 24;
		int r = (argbColor >> 16) & 0xff;
		int g = (argbColor >> 8) & 0xff;
		int b = argbColor & 0xff;

		int minRGB = Math.min(Math.min(r, g), b);
		int maxRGB = Math.max(Math.max(r, g), b);

		// Brightness
		int v = maxRGB;

		if (minRGB == maxRGB) {
			// Gray - no hue or saturation
			return (a << 24) | v;
		}

		int diff = maxRGB - minRGB;

		// Saturation
		int s;
		if (diff == maxRGB) {
			s = 255;
		} else {
			s = intDivRound(diff << 8, maxRGB);
		}

		// Hue
		int h;
		if (r == maxRGB) {
			h = (g - b);
		} else if (g == maxRGB) {
			h = (diff << 1) + (b - r);
		} else { // b == maxRGB
			h = (diff << 2) + (r - g);
		}
		h = intDivFloor(h << 8, diff * 6) & 0xff;

		return (a << 24) | (h << 16) | (s << 8) | v;
	}

	/**
	 * Converts a packed, 32-bit HSB (hue, saturation, brightness) color to RGB
	 * (red, green, blue). The alpha value, if any, is not modified.
	 * 
	 * <pre>
	 * int rgb = Colors.HSBtoRGB(hsb);
	 * int r = (rgb &gt;&gt; 16) &amp; 0xff;
	 * int g = (rgb &gt;&gt; 8) &amp; 0xff;
	 * int b = rgb &amp; 0xff;
	 * </pre>
	 * 
	 * @param ahsbColor
	 *            a 32-bit AHSB color.
	 * @return a 32-bit ARGB color.
	 * @see #RGBtoHSB(int)
	 */
	public static int HSBtoRGB(int ahsbColor) {

		int a = ahsbColor >>> 24;
		int h = (ahsbColor >> 16) & 0xff;
		int s = (ahsbColor >> 8) & 0xff;
		int v = ahsbColor & 0xff;

		if (s == 0) {
			// Gray
			return (a << 24) | (v << 16) | (v << 8) | v;
		}

		int h6 = h * 6 + 3;
		int i = h6 >> 8; // 0 .. 5
		int f = h6 - (i << 8);

		int p = s * 255;
		int q = s * f;
		int t = p - q;

		int r = 0;
		int g = 0;
		int b = 0;

		if (i == 0) {
			g = t;
			b = p;
		} else if (i == 1) {
			r = q;
			b = p;
		} else if (i == 2) {
			r = p;
			b = t;
		} else if (i == 3) {
			r = p;
			g = q;
		} else if (i == 4) {
			r = t;
			g = p;
		} else {
			g = p;
			b = q;
		}

		r = ((v << 16) - (v * r)) >> 16;
		g = ((v << 16) - (v * g)) >> 16;
		b = ((v << 16) - (v * b)) >> 16;

		return (a << 24) | (r << 16) | (g << 8) | b;
	}

	/**
	 * Divides the number, n, by the divisor, d, rounding the result to the
	 * nearest integer.
	 */
	public static final int intDivRound(int n, int d) {
		if ((d > 0) ^ (n > 0)) {
			return (n - (d >> 1)) / d;
		} else {
			return (n + (d >> 1)) / d;
		}
	}

	/**
	 * Divides the number, n, by the divisor, d, returning the nearest integer
	 * less than or equal to the result.
	 */
	public static final int intDivFloor(int n, int d) {
		if (d > 0) {
			if (n < 0) {
				return (n - d + 1) / d;
			} else {
				return n / d;
			}
		} else if (d < 0) {
			if (n > 0) {
				return (n - d - 1) / d;
			} else {
				return n / d;
			}
		} else {
			// d == 0 throws ArithmeticException
			return n / d;
		}
	}
	
	
	

}
