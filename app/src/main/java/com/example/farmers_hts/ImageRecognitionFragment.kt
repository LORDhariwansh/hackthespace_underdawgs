package com.example.farmers_hts

import Classifier
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ImageRecognitionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageRecognitionFragment : Fragment() {

    private lateinit var mClassifier: Classifier
    private lateinit var mBitmap: Bitmap

    private lateinit var mPhotoImageView: ImageView
    private lateinit var mResultTextView: TextView
    private lateinit var mCameraButton: Button
    private lateinit var mGalleryButton: Button
    private lateinit var mDetectButton: Button

    private val mCameraRequestCode = 0
    private val mGalleryRequestCode = 2

    private val mInputSize = 224
    private val mModelPath = "plant_disease_model.tflite"
    private val mLabelPath = "plant_labels.txt"
    private val mSamplePath = "soybean.JPG"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_image_recognition, container, false)
        mPhotoImageView = view.findViewById(R.id.photoImageView)
        mResultTextView = view.findViewById(R.id.resultTextView)
        mCameraButton = view.findViewById(R.id.cameraButton)
        mGalleryButton = view.findViewById(R.id.galleryButton)
        mDetectButton = view.findViewById(R.id.detectButton)

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mClassifier = Classifier(requireContext().assets, mModelPath, mLabelPath, mInputSize)

        resources.assets.open(mSamplePath).use {
            mBitmap = BitmapFactory.decodeStream(it)
            mBitmap = Bitmap.createScaledBitmap(mBitmap, mInputSize, mInputSize, true)
            mPhotoImageView.setImageBitmap(mBitmap)
        }

        mCameraButton.setOnClickListener {
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(callCameraIntent, mCameraRequestCode)
        }

        mGalleryButton.setOnClickListener {
            val callGalleryIntent = Intent(Intent.ACTION_PICK)
            callGalleryIntent.type = "image/*"
            startActivityForResult(callGalleryIntent, mGalleryRequestCode)
        }

        mDetectButton.setOnClickListener {
            val results = mClassifier.recognizeImage(mBitmap).firstOrNull()
            mResultTextView.text = "${results?.title}\nConfidence: ${results?.confidence}"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                mCameraRequestCode -> {
                    val photo = data?.extras?.get("data") as Bitmap
                    mBitmap = scaleImage(photo)
                    mPhotoImageView.setImageBitmap(mBitmap)
                }
                mGalleryRequestCode -> {
                    val imageUri = data?.data
                    mBitmap = scaleImage(MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri))
                    mPhotoImageView.setImageBitmap(mBitmap)
                }
            }
        }
    }

    private fun scaleImage(bitmap: Bitmap): Bitmap {
        val originalWidth = bitmap.width
        val originalHeight = bitmap.height
        val scaleWidth = mInputSize.toFloat() / originalWidth
        val scaleHeight = mInputSize.toFloat() / originalHeight
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originalHeight, matrix, true)
    }
}