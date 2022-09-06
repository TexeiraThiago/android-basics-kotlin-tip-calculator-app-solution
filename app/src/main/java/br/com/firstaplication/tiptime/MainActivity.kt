package br.com.firstaplication.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
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

        binding.calculateButton.setOnClickListener { calculateTip() }

        binding.costOfServiceEditText.setOnKeyListener { view,keyCode,_ ->
            handleKeyListenerEvent(view,keyCode) }
    }

    private fun calculateTip() {
        val stringIndexTextField = binding.costOfServiceEditText.text.toString()
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
        Log.d("Value", "displayTip: "+formattedTip )
    }

    private fun handleKeyListenerEvent(view: View, Keycode:Int): Boolean{
        if (Keycode == KeyEvent.KEYCODE_ENTER) {
            //Hide Keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
            return true
        }
        return false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putDouble("tipResult", tip)
        super.onSaveInstanceState(outState)
    }


}

