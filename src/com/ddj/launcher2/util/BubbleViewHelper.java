package com.ddj.launcher2.util;

import com.ddj.launcher.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;

/**
 * @author dingdj
 * Date:2014-2-28上午9:57:53
 *
 */
public class BubbleViewHelper {
	public static final int SHADOW_SMALL_COLOUR = 0xCC000000;
	
	private Paint iconPaint;
	private TextPaint textPaint;
	private Rect iconRect = new Rect();
	
	/**
	 * 图片和文字的间距
	 */
    private int text2IconPadding;
	/**
     * 图标上下的间隙
     */
    private int gap;
	
    /**
     * 图片大小
     */
    private int iconSize;
    
    private int folderIconHeight;
    
    /**
     * 文字占的高度
     */
    private int textHeight;
    
    private int iconTop;
    
    private int iconCenterX;
    
    private float scaleSize = -1;
	
	private static BubbleViewHelper instance;
	
	private BubbleViewHelper(Context context){
		init(context);
	}
	
	private void init(Context context){
		textPaint = new TextPaint();
		configTextPaint(context, textPaint);
		textHeight = textPaint.getFontMetricsInt(null);
		iconPaint = new Paint();
		iconPaint.setDither(true);
		iconPaint.setAntiAlias(true);
		gap = 1;
		text2IconPadding = 3;
		iconSize = context.getResources().getDimensionPixelSize(R.dimen.app_icon_size);
		folderIconHeight = context.getResources().getDimensionPixelSize(R.dimen.folder_preview_size);
	}
	
	public static BubbleViewHelper getInstance(Context context){
		if(instance == null){
			instance = new BubbleViewHelper(context);
		}
		return instance;
	}

	public Paint getIconPaint() {
		return iconPaint;
	}

	public Paint getTextPaint() {
		return textPaint;
	}

	public int getTextHeight() {
		return textHeight;
	}
	
	/**
     * 根据宽、高来计算绘画各参数
     * @author dingdj
     * Date:2014-2-27上午11:31:20
     *  @param realWidth
     *  @param realHeight
     */
    public void calcDrawParams(int realWidth, int realHeight, boolean isHasText) {
    	//总高度=topGap + 图标大小+ bottomGap+图文间距+文字高度
		int originalHeight = (isHasText) ? gap+ iconSize + gap + text2IconPadding + textHeight : gap+ iconSize + gap;
		int originalWidth = gap + iconSize + gap;
		int iconLeft = -1;
		float scaleW = 1, scaleH = 1;
		
		if (realWidth < originalWidth) {//需要进行缩放
			scaleW = realWidth * 1.0f / originalWidth;
			iconLeft = gap;
		} else {
			iconLeft = (realWidth - iconSize) / 2;
		}
		
		if (realHeight < originalHeight) {
			scaleH = realHeight * 1.0f / originalHeight;
			iconTop = gap;
		} else {
			iconTop = (realHeight - originalHeight) / 2;
		}
		iconCenterX = realWidth / 2;
		if (scaleW != scaleH) {
			scaleSize = scaleW < scaleH ? scaleW : scaleH;
		}
		iconRect.top = iconTop;
		iconRect.left = iconLeft;
		iconRect.bottom = iconTop + iconSize;
		iconRect.right = iconLeft + iconSize;
	}

    /**
     * 缩放比例
     * @author dingdj
     * Date:2014-2-28上午10:15:05
     *  @return
     */
	public float getScaleSize() {
		return scaleSize;
	}

	public int getIconCenterX() {
		return iconCenterX;
	}

	public Rect getIconRect() {
		return iconRect;
	}
	
	public int getTextTop() {
		return iconTop + iconSize + text2IconPadding + textHeight;
	}
	
	public int getTextPaddingTop() {
		return text2IconPadding - ((folderIconHeight-iconSize)/2)-1;
	}
	
	/**
	 * 配置文字的Paint属性
	 * @author dingdj
	 * Date:2014-2-28上午11:33:10
	 *  @param textPaint
	 */
	public static void configTextPaint(Context context, TextPaint textPaint){
		textPaint.setDither(true);
		textPaint.setAntiAlias(true);
		textPaint.setColor(Color.WHITE);
		textPaint.setShadowLayer(1, 1, 1, SHADOW_SMALL_COLOUR);
		textPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.app_icon_text_size));
	}

	public int getIconSize() {
		return iconSize;
	}
	
	
}
