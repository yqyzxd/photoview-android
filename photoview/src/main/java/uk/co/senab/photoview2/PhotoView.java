/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package uk.co.senab.photoview2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.widget.ImageView;

import uk.co.senab.photoview.R;

public class PhotoView extends ImageView implements IPhotoView {

    private PhotoViewAttacher mAttacher;

    private ScaleType mPendingScaleType;

    private boolean mAttached;
    public PhotoView(Context context) {
        this(context, null);
    }

    public PhotoView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public PhotoView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        super.setScaleType(ScaleType.MATRIX);

        if (attr != null) {
            TypedArray array = context.obtainStyledAttributes(attr, R.styleable.PhotoView);
            mAttached = array.getBoolean(R.styleable.PhotoView_attached, true);
            array.recycle();
        }
        init();
    }

    protected void init() {

        if (mAttached && (null == mAttacher || null == mAttacher.getImageView())) {
            mAttacher = new PhotoViewAttacher(this);
        }

        if (null != mPendingScaleType) {
            setScaleType(mPendingScaleType);
            mPendingScaleType = null;
        }
    }

    @Override
    public void setRotationTo(float rotationDegree) {
        if (mAttacher!=null){
            mAttacher.setRotationTo(rotationDegree);
        }

    }

    @Override
    public void setRotationBy(float rotationDegree) {
        if (mAttacher!=null) {
            mAttacher.setRotationBy(rotationDegree);
        }
    }

    @Override
    public boolean canZoom() {
        if (mAttacher!=null) {
            return mAttacher.canZoom();
        }
        return false;
    }

    @Override
    public RectF getDisplayRect() {
        if (mAttacher!=null) {
            return mAttacher.getDisplayRect();
        }
        return new RectF();
    }

    @Override
    public void getDisplayMatrix(Matrix matrix) {
        if (mAttacher!=null) {
            mAttacher.getDisplayMatrix(matrix);
        }
    }

    @Override
    public boolean setDisplayMatrix(Matrix finalRectangle) {
        if (mAttacher!=null) {
            return mAttacher.setDisplayMatrix(finalRectangle);
        }
        return false;
    }

    @Override
    public float getMinimumScale() {
        if (mAttacher!=null) {
            return mAttacher.getMinimumScale();
        }
        return 1f;
    }

    @Override
    public float getMediumScale() {
        if (mAttacher!=null) {
            return mAttacher.getMediumScale();
        }
        return 1f;
    }

    @Override
    public float getMaximumScale() {
        if (mAttacher!=null) {
            return mAttacher.getMaximumScale();
        }
        return 1f;
    }

    @Override
    public float getScale() {
        if (mAttacher!=null) {
            return mAttacher.getScale();
        }
        return (float) Math.sqrt((float) Math.pow(getScaleX(), 2) + (float) Math.pow(getScaleY(), 2));

    }

    @Override
    public ScaleType getScaleType() {
        if (mAttacher!=null) {
            return mAttacher.getScaleType();
        }
        return super.getScaleType();
    }

    @Override
    public Matrix getImageMatrix() {
        if (mAttacher!=null) {
            return mAttacher.getImageMatrix();
        }
        return super.getImageMatrix();
    }

    @Override
    public void setAllowParentInterceptOnEdge(boolean allow) {
        if (mAttacher!=null) {
            mAttacher.setAllowParentInterceptOnEdge(allow);
        }
    }

    @Override
    public void setMinimumScale(float minimumScale) {
        if (mAttacher!=null) {
            mAttacher.setMinimumScale(minimumScale);
        }
    }

    @Override
    public void setMediumScale(float mediumScale) {
        if (mAttacher!=null) {
            mAttacher.setMediumScale(mediumScale);
        }
    }

    @Override
    public void setMaximumScale(float maximumScale) {
        if (mAttacher!=null) {
            mAttacher.setMaximumScale(maximumScale);
        }
    }

    @Override
    public void setScaleLevels(float minimumScale, float mediumScale, float maximumScale) {
        if (mAttacher!=null) {
            mAttacher.setScaleLevels(minimumScale, mediumScale, maximumScale);
        }
    }

    @Override
    // setImageBitmap calls through to this method
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (null != mAttacher) {
            mAttacher.update();
        }
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        if (null != mAttacher) {
            mAttacher.update();
        }
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        if (null != mAttacher) {
            mAttacher.update();
        }
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        boolean changed = super.setFrame(l, t, r, b);
        if (null != mAttacher) {
            mAttacher.update();
        }
        return changed;
    }

    @Override
    public void setOnMatrixChangeListener(PhotoViewAttacher.OnMatrixChangedListener listener) {
        if (mAttacher!=null) {
            mAttacher.setOnMatrixChangeListener(listener);
        }
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        if (mAttacher!=null) {
            mAttacher.setOnLongClickListener(l);
        }
    }

    @Override
    public void setOnPhotoTapListener(PhotoViewAttacher.OnPhotoTapListener listener) {
        if (mAttacher!=null) {
            mAttacher.setOnPhotoTapListener(listener);
        }
    }

    @Override
    public void setOnViewTapListener(PhotoViewAttacher.OnViewTapListener listener) {
        if (mAttacher!=null) {
            mAttacher.setOnViewTapListener(listener);
        }
    }

    @Override
    public void setScale(float scale) {
        if (mAttacher!=null) {
            mAttacher.setScale(scale);
        }
    }

    @Override
    public void setScale(float scale, boolean animate) {
        if (mAttacher!=null) {
            mAttacher.setScale(scale, animate);
        }
    }

    @Override
    public void setScale(float scale, float focalX, float focalY, boolean animate) {
        if (mAttacher!=null) {
            mAttacher.setScale(scale, focalX, focalY, animate);
        }
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (null != mAttacher) {
            mAttacher.setScaleType(scaleType);
        } else {
            mPendingScaleType = scaleType;
            super.setScaleType(scaleType);
        }
    }

    @Override
    public void setZoomable(boolean zoomable) {
        if (mAttacher!=null) {
            mAttacher.setZoomable(zoomable);
        }
    }

    @Override
    public Bitmap getVisibleRectangleBitmap() {
        if (mAttacher!=null) {
            return mAttacher.getVisibleRectangleBitmap();
        }
        return getDrawingCache();
    }

    @Override
    public void setZoomTransitionDuration(int milliseconds) {
        if (mAttacher!=null) {
            mAttacher.setZoomTransitionDuration(milliseconds);
        }
    }

    @Override
    public IPhotoView getIPhotoViewImplementation() {
        return mAttacher;
    }

    @Override
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener newOnDoubleTapListener) {
        if (mAttacher!=null) {
            mAttacher.setOnDoubleTapListener(newOnDoubleTapListener);
        }
    }

    @Override
    public void setOnScaleChangeListener(PhotoViewAttacher.OnScaleChangeListener onScaleChangeListener) {
        if (mAttacher!=null) {
            mAttacher.setOnScaleChangeListener(onScaleChangeListener);
        }
    }

    @Override
    public void setOnSingleFlingListener(PhotoViewAttacher.OnSingleFlingListener onSingleFlingListener) {
        if (mAttacher!=null) {
            mAttacher.setOnSingleFlingListener(onSingleFlingListener);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mAttacher!=null) {
            mAttacher.cleanup();
            mAttacher = null;
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        init();
        super.onAttachedToWindow();
    }
}
