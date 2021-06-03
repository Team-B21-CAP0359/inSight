package com.bangkit.capstone.insightapp.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
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

    private var pengeluaranValid = false
    private var umurValid = false
    private var pendapatanValid = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityFindOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        validateButton()
        binding.umur.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                validateUmur()
            }
        })

        binding.pendapatan.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                validatePendapatan()
            }
        })

        binding.pengeluaran.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                validatePengeluaran()
            }
        })

        binding.findout.setOnClickListener {

            val v1 : Int = binding.umur.text.toString().toInt()
            val v2 : Int = binding.pendapatan.text.toString().toInt()
            val v3 : Int = binding.pengeluaran.text.toString().toInt()

            val byteBuffer : ByteBuffer = ByteBuffer.allocateDirect(3 * 4)
            byteBuffer.putInt(v1)
            byteBuffer.putInt(v2)
            byteBuffer.putInt(v3)

            val model = Mall.newInstance(this)

// Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer.intArray

            binding.hasil.text = """
            Recommended ${outputFeature0[0]}
            Not-Recommended ${outputFeature0[1]}
            Very Recommended ${outputFeature0[2]}
        """.trimIndent()

// Releases model resources if no longer used.
            model.close()

        }

    }

    private fun validatePengeluaran() {
        val input = binding.pengeluaran.text.toString()
        if (input.isEmpty()) {
            pengeluaranValid = false
            showPengeluaranAlert(true)
        } else {
            pengeluaranValid = true
            showPengeluaranAlert(false)
        }
        validateButton()
    }

    private fun validatePendapatan() {
        val input = binding.pendapatan.text.toString()
        if (input.isEmpty()) {
            pendapatanValid = false
            showPendapatanAlert(true)
        } else {
            pendapatanValid = true
            showPendapatanAlert(false)
        }
        validateButton()
    }

    private fun validateUmur() {
        val input = binding.umur.text.toString()
        if (input.isEmpty()) {
            umurValid = false
            showUmurAlert(true)
        } else {
            umurValid = true
            showUmurAlert(false)
        }
        validateButton()
    }

    private fun validateButton() {
        if (pengeluaranValid && pendapatanValid && umurValid) {
            binding.findout.isEnabled = true
            binding.findout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_purple))
        } else {
            binding.findout.isEnabled = false
            binding.findout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        }
    }

    private fun showUmurAlert(isNotValid: Boolean) {
        binding.umur.error = if (isNotValid) getString(R.string.not_valid) else null
    }

    private fun showPendapatanAlert(isNotValid: Boolean) {
        binding.pendapatan.error = if (isNotValid) getString(R.string.not_valid) else null
    }

    private fun showPengeluaranAlert(isNotValid: Boolean) {
        binding.pengeluaran.error = if (isNotValid) getString(R.string.not_valid) else null
    }

}