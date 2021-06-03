package com.bangkit.capstone.insightapp.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bangkit.capstone.insightapp.R
import com.bangkit.capstone.insightapp.databinding.ActivityFindOutBinding
import com.bangkit.capstone.insightapp.databinding.ActivityLoginBinding
import com.bangkit.capstone.insightapp.ml.Mall
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer

class FindOutActivity : AppCompatActivity() {

    private var _binding: ActivityFindOutBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityFindOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.findout.setOnClickListener {

            val v1 : Float = binding.umur.text.toString().toFloat()
            val v2 : Float = binding.pendapatan.text.toString().toFloat()
            val v3 : Float = binding.pengeluaran.text.toString().toFloat()

            val byteBuffer : ByteBuffer = ByteBuffer.allocateDirect(3 * 4)
            byteBuffer.putFloat(v1)
            byteBuffer.putFloat(v2)
            byteBuffer.putFloat(v3)

            val model = Mall.newInstance(this)

// Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

            binding.hasil.text = """
            hasil ${outputFeature0[0]}
            hasil ${outputFeature0[1]}
            hasil ${outputFeature0[2]}
        """.trimIndent()

// Releases model resources if no longer used.
            model.close()

        }

    }
}