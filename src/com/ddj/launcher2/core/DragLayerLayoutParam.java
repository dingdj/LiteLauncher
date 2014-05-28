/**
 * @author dingdj
 * Date:2014-3-18上午11:21:00
 *
 */
package com.ddj.launcher2.core;

import android.widget.FrameLayout;

/**
 * @author dingdj
 * Date:2014-3-18上午11:21:00
 *
 */
public class DragLayerLayoutParam extends FrameLayout.LayoutParams{

	  public int x, y;
      public boolean customPosition = false;

      /**
       * {@inheritDoc}
       */
      public DragLayerLayoutParam(int width, int height) {
          super(width, height);
      }

      public void setWidth(int width) {
          this.width = width;
      }

      public int getWidth() {
          return width;
      }

      public void setHeight(int height) {
          this.height = height;
      }

      public int getHeight() {
          return height;
      }

      public void setX(int x) {
          this.x = x;
      }

      public int getX() {
          return x;
      }

      public void setY(int y) {
          this.y = y;
      }

      public int getY() {
          return y;
      }

}
