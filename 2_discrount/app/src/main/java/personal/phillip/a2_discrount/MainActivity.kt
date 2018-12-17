package personal.phillip.a2_discrount

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mDiscount = 0 // 0% to 100% off
    private var mNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateDiscountToTexts()
        setupViews()
    }

    private fun updateDiscountToTexts() {
        discount_title.text = "打折 ($mDiscount%)"
        discount_text.text = String.format("$%.2f", getDiscountPercent() * mNumber)
        after_discount_text.text = String.format("$%.2f",
                mNumber - (mNumber * getDiscountPercent()))
    }

    private fun getDiscountPercent() : Double {
        return mDiscount / 100.0
    }

    private fun setupViews() {
        discount_seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                mDiscount = p1
                updateDiscountToTexts()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        input_number_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                var numberText = input_number_edit_text.text.toString()
                mNumber = if (TextUtils.isEmpty(numberText)) 0 else numberText.toInt()
                updateDiscountToTexts()

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        input_number_edit_text.setOnEditorActionListener{ textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                mNumber = input_number_edit_text.text.toString().toInt()
            }
            updateDiscountToTexts()
            input_number_edit_text.clearFocus()
            false
        }
    }
}
