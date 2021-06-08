package com.bangkit.capstone.insightapp.view.activity.welcomescreen

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.bangkit.capstone.insightapp.databinding.ActivityWelcome4Binding
import com.bangkit.capstone.insightapp.ml.ImageRecg
import com.bangkit.capstone.insightapp.ml.ModelShirt
import com.bangkit.capstone.insightapp.ml.ModelShirt1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer


class WelcomeActivity4 : AppCompatActivity() {

    private var _binding: ActivityWelcome4Binding? = null
    private val binding get() = _binding!!

    private lateinit var refUser: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    private lateinit var imageUri: Uri
    lateinit var bitmap : Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityWelcome4Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()
        refUser = FirebaseDatabase.getInstance().reference
        binding.next4.isEnabled = false
        binding.upload.setOnClickListener {

            selectImage()

        }

        binding.next4.setOnClickListener {

            uploadImage()

        }

        val labels = application.assets.open("labels.txt").bufferedReader().use { it.readText() }.split("\n")

        binding.check.setOnClickListener {

            var resized: Bitmap = Bitmap.createScaledBitmap(bitmap, 28, 28,true)
            val model = ModelShirt1.newInstance(this)

            var tBuffer = TensorImage.fromBitmap(resized)
            var byteBuffer = tBuffer.buffer

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 28, 28, 1), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            var max = getMax(outputFeature0.floatArray)

            binding.resulttest.text = outputFeature0.toString()

            model.close()
        }

    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Menupload File...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val fileName = mAuth.currentUser?.uid
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName-image/shirt")

        storageReference.putFile(imageUri).addOnSuccessListener {
            binding.previewPhoto.setImageURI(null)
            if (progressDialog.isShowing) progressDialog.dismiss()
            moveIntent()
        }.addOnFailureListener {
            if (progressDialog.isShowing) progressDialog.dismiss()
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        imageUri = data?.data!!
        var uri: Uri?= data.data
        bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        binding.previewPhoto.visibility = View.VISIBLE
        binding.previewPhoto.setImageURI(imageUri)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            binding.next4.isEnabled = true
        }
    }

    private fun moveIntent() {
        val intent = Intent(this, WelcomeActivity5::class.java)
        startActivity(intent)
    }

    fun getMax(arr: FloatArray) : Int {

        var ind = 0
        var min = 0.0f

        for (i in 0..1000) {

            if (arr[i] > min) {
                min = arr[i]
                ind = i
            }
        }
        return  ind
    }

}