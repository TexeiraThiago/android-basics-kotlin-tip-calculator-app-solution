package br.com.firstaplication.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.firstaplication.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var tip = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            tip = savedInstanceState.getDouble("tipResult")
            displayTip(tip)
        }

        binding.calculateTip.setOnClickListener {
            calculateTip()
        }
    }

    private fun calculateTip() {
        val stringIndexTextField = binding.costOfService.text.toString()
        val cost = stringIndexTextField.toDoubleOrNull()
        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }

        val percentageTip = when(binding.tipsOption.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }
        tip = cost*percentageTip

        if (binding.roundUpSwitch.isChecked) {
           tip = kotlin.math.ceil(tip)
        }
        displayTip(tip)

    }

    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount,formattedTip)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putDouble("tipResult", tip)
        super.onSaveInstanceState(outState)
    }
}

