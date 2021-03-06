package org.andengine.extension.augmentedreality;

import java.io.IOException;

import org.andengine.util.debug.Debug;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * (c) 2010 Nicolas Gramlich (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 21:38:32 - 24.05.2010
 * 
 * @author Lounge Katt
 * 
 */

@SuppressWarnings("deprecation")
class CameraPreviewSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final SurfaceHolder mSurfaceHolder;
	private Camera mCamera;

	// ===========================================================
	// Constructors
	// ===========================================================

	public CameraPreviewSurfaceView(final Context pContext) {
		super(pContext);

		this.mSurfaceHolder = this.getHolder();
		this.mSurfaceHolder.addCallback(this);
		this.mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
		this.mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	public void surfaceCreated(final SurfaceHolder pSurfaceHolder) {
		this.mCamera = Camera.open();
		try {
			this.mCamera.setPreviewDisplay(pSurfaceHolder);
			this.mCamera.startPreview();
		} catch (IOException e) {
			Debug.e("Error in Camera.setPreviewDisplay", e);
		}
	}

	public void surfaceDestroyed(final SurfaceHolder pSurfaceHolder) {
		this.mCamera.stopPreview();
		this.mCamera.release();
		this.mCamera = null;
	}
	
	public void surfaceChanged(SurfaceHolder pSurfaceHolder, int pPixelFormat, int pWidth,
			int pHeight) {
		if (this.mSurfaceHolder.getSurface() == null) {
			return;
		}
		try {
			this.mCamera.stopPreview();
		} catch (Exception e) {
			Debug.e("Camera preview not stopped", e);
		}
		try {
			this.mCamera.setPreviewDisplay(mSurfaceHolder);
			this.mCamera.startPreview();
		} catch (Exception e) {
			Debug.e("Camera preview not started", e);
		}
	}
	
	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}