package com.starbucks.id.controller.extension;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.starbucks.id.R;
import com.starbucks.id.helper.StarbucksID;
import com.starbucks.id.helper.UserDefault;
import com.starbucks.id.rest.ApiInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class CustomCamera2 extends Activity implements SurfaceHolder.Callback,
        View.OnClickListener {

    private SurfaceView surfaceView;
    private ImageView imageView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private Button flipCamera;
    private Button flashCameraButton;
    private Button captureImage;
    private Button sendImage;
    private int cameraId;
    private boolean flashmode = false;
    private int rotation;
    View view1,view2,view3,view4;
    private UserDefault userDefault;
    private StarbucksID app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_camera_2);

        userDefault = UserDefault.getInstance(getApplicationContext());
        app = StarbucksID.Companion.getInstance();
        // camera surface view created
        view1 = (View)findViewById(R.id.view1);
        view1.bringToFront();
        view2 = (View)findViewById(R.id.view2);
        view2.bringToFront();
        view3 = (View)findViewById(R.id.view3);
        view3.bringToFront();
        view4 = (View)findViewById(R.id.view4);
        view4.bringToFront();
        cameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        imageView = (ImageView)findViewById(R.id.photo);

        flipCamera = (Button) findViewById(R.id.flipCamera);
        flashCameraButton = (Button) findViewById(R.id.flash);
        captureImage = (Button) findViewById(R.id.captureImage);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        sendImage = (Button) findViewById(R.id.sendImage);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        flipCamera.setOnClickListener(this);
        captureImage.setOnClickListener(this);
        flashCameraButton.setOnClickListener(this);
        sendImage.setOnClickListener(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

//        if (Camera.getNumberOfCameras() > 1) {
//            flipCamera.setVisibility(View.VISIBLE);
//        }
//        if (!getBaseContext().getPackageManager().hasSystemFeature(
//                PackageManager.FEATURE_CAMERA_FLASH)) {
//            flashCameraButton.setVisibility(View.GONE);
//        }
        flipCamera.setVisibility(View.GONE);
        flashCameraButton.setVisibility(View.GONE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!openCamera(Camera.CameraInfo.CAMERA_FACING_FRONT)) {
            alertCameraDialog();
        }

    }

    private boolean openCamera(int id) {
        boolean result = false;
        cameraId = id;
        releaseCamera();
        try {
            camera = Camera.open(cameraId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (camera != null) {
            try {
                setUpCamera(camera);
                camera.setErrorCallback(new Camera.ErrorCallback() {

                    @Override
                    public void onError(int error, Camera camera) {

                    }
                });
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
                result = false;
                releaseCamera();
            }
        }
        return result;
    }

    private void setUpCamera(Camera c) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degree = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degree = 0;
                break;
            case Surface.ROTATION_90:
                degree = 90;
                break;
            case Surface.ROTATION_180:
                degree = 180;
                break;
            case Surface.ROTATION_270:
                degree = 270;
                break;

            default:
                break;
        }

        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            // frontFacing
            rotation = (info.orientation + degree) % 330;
            rotation = (360 - rotation) % 360;
        } else {
            // Back-facing
            rotation = (info.orientation - degree + 360) % 360;
        }
        c.setDisplayOrientation(rotation);
        Camera.Parameters params = c.getParameters();
        if (params.getSupportedFocusModes().contains(
                Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        }

        showFlashButton(params);

//        List<String> focusModes = params.getSupportedFlashModes();
//        if (focusModes != null) {
//            if (focusModes.contains(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
//                params.setFlashMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//            }
//        }
//
        params.setRotation(rotation);

//        Camera.Parameters params = camera.getParameters();
        if (params.getSupportedFocusModes().contains(
                Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        }
        camera.setParameters(params);
    }

    private void showFlashButton(Camera.Parameters params) {
        boolean showFlash = (getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FLASH) && params.getFlashMode() != null)
                && params.getSupportedFlashModes() != null
                && params.getSupportedFocusModes().size() > 1;

//        flashCameraButton.setVisibility(showFlash ? View.VISIBLE
//                : View.INVISIBLE);

    }

    private void releaseCamera() {
        try {
            if (camera != null) {
                camera.setPreviewCallback(null);
                camera.setErrorCallback(null);
                camera.stopPreview();
                camera.release();
                camera = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", e.toString());
            camera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flash:
                flashOnButton();
                break;
            case R.id.flipCamera:
                flipCamera();
                break;
            case R.id.captureImage:
                takeImage();
                break;
            case R.id.sendImage:
//                sendKYC();
                break;

            default:
                break;
        }
    }
    private File imageFile;

    private void takeImage() {
        camera.takePicture(null, null, new Camera.PictureCallback() {



            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                try {
                    // convert byte array into bitmap
                    Bitmap loadedImage = null;
                    Bitmap rotatedBitmap = null;
                    loadedImage = BitmapFactory.decodeByteArray(data, 0,
                            data.length);

                    // rotate Image
                    Matrix rotateMatrix = new Matrix();
                    rotateMatrix.postRotate(270);
                    rotatedBitmap = Bitmap.createBitmap(loadedImage, 0, 0,
                            loadedImage.getWidth(), loadedImage.getHeight(),
                            rotateMatrix, false);
                    String state = Environment.getExternalStorageState();
                    File folder = null;
                    if (state.contains(Environment.MEDIA_MOUNTED)) {
                        folder = new File(Environment
                                .getExternalStorageDirectory() + "/Demo");
                    } else {
                        folder = new File(Environment
                                .getExternalStorageDirectory() + "/Demo");
                    }

                    boolean success = true;
                    if (!folder.exists()) {
                        success = folder.mkdirs();
                    }
                    if (success) {


                        Date date = new Date();
                        imageFile = new File(folder.getAbsolutePath()
                                + File.separator
                                + new Timestamp(date.getTime()).toString()
                                + "Image.jpg");

                        imageFile.createNewFile();
//
                    } else {
                        Toast.makeText(getBaseContext(), "Image Not saved",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ByteArrayOutputStream ostream = new ByteArrayOutputStream();

                    // save image into gallery
                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);

                    FileOutputStream fout = new FileOutputStream(imageFile);
                    fout.write(ostream.toByteArray());
                    fout.close();
                    ContentValues values = new ContentValues();

                    values.put(MediaStore.Images.Media.DATE_TAKEN,
                            System.currentTimeMillis());
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                    values.put(MediaStore.MediaColumns.DATA,
                            imageFile.getAbsolutePath());

                    CustomCamera2.this.getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


                    String filePath = imageFile.getAbsolutePath();
                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    imageView.setVisibility(View.VISIBLE);
                    view1.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);
                    view4.setVisibility(View.GONE);
                    captureImage.setVisibility(View.GONE);
                    surfaceView.setVisibility(View.GONE);
                    imageView.setImageBitmap(bitmap);
                    sendImage.setVisibility(View.VISIBLE);
                    //Sending Image




                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

//    public boolean checkConnection() {
//        if (!ConnectionDetector.isConnected()) {
//            DialogUtil.sErrDialog(this, userDefault, 1, new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialogInterface) {
//                    sendKYC();
//                }
//            });
//        }
//        return ConnectionDetector.isConnected();
//    }


    private ApiInterface apiService;


    public ApiInterface getApiService() {
        if (apiService == null) apiService = app.getApiService();
        return apiService;
    }


    /*REST Call*/
//    public void sendKYC() {
//        if (!checkConnection()) return;
//        if (apiService == null) apiService = app.getApiService();
//
//        MultipartBody.Part body = null;
//        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageFile);
//        body = MultipartBody.Part.createFormData("testFile", "Photo1" + ".jpg", requestFile);
//
//
//
//        Call<ResponseBase> call = apiService.testgambar(body);
//
//        call.enqueue(new Callback<ResponseBase>() {
//            @Override
//            public void onResponse(Call<ResponseBase> call, Response<ResponseBase> response) {
//                Toast.makeText(CustomCamera2.this,"Kirim",Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBase> call, Throwable t) {
//
//            }
//        });
//
//    }


    private void flipCamera() {
        int id = (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK ? Camera.CameraInfo.CAMERA_FACING_FRONT
                : Camera.CameraInfo.CAMERA_FACING_BACK);
        if (!openCamera(id)) {
            alertCameraDialog();
        }
    }

    private void alertCameraDialog() {
        AlertDialog.Builder dialog = createAlert(CustomCamera2.this,
                "Camera info", "error to open camera");
        dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        dialog.show();
    }

    private AlertDialog.Builder createAlert(Context context, String title, String message) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(
                new ContextThemeWrapper(context,
                        android.R.style.Theme_Holo_Light_Dialog));
        dialog.setIcon(R.drawable.digi);
        if (title != null)
            dialog.setTitle(title);
        else
            dialog.setTitle("Information");
        dialog.setMessage(message);
        dialog.setCancelable(false);
        return dialog;

    }

    private void flashOnButton() {
        if (camera != null) {
            try {
                Camera.Parameters param = camera.getParameters();
                param.setFlashMode(!flashmode ? Camera.Parameters.FLASH_MODE_TORCH
                        : Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(param);
                flashmode = !flashmode;
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
    }
}