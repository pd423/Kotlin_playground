package personal.phillip.a3_image_picker

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE_CAMERA = 1000

    private val REQUEST_CODE_TAKE_IMAGE_FROM_ALBUM = 1000
    private val REQUEST_CODE_TAKE_IMAGE_FROM_CAMERA = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_album.setOnClickListener{
            takeImageFromAlbum()
        }

        btn_camera.setOnClickListener{
            if (isCameraPermissionGranted()) {
                takeImageFromCamera()
            } else {
                requestCameraPermission()
            }
        }
    }

    private fun isCameraPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CODE_CAMERA)
    }

    private fun takeImageFromAlbum() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_TAKE_IMAGE_FROM_ALBUM)
    }

    private fun takeImageFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CODE_TAKE_IMAGE_FROM_CAMERA)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE_CAMERA -> {
                if (TextUtils.equals(permissions[0], Manifest.permission.CAMERA)
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takeImageFromCamera()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE_TAKE_IMAGE_FROM_ALBUM -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data.data)
                    imageView.setImageBitmap(bitmap)
                }
            }
            REQUEST_CODE_TAKE_IMAGE_FROM_CAMERA -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    imageView.setImageBitmap(data.extras.get("data") as Bitmap)
                }
            }
        }
    }
}
