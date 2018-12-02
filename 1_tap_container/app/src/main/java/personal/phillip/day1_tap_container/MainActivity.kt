package personal.phillip.day1_tap_container

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

private const val BUNDLE_KEY_COUNT = "BUNDLE_KEY_COUNT"

class MainActivity : AppCompatActivity() {

    var mCount : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Press tap and increase the count.
        tapBtn.setOnClickListener{
            mCount++
            countText.text = mCount.toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(BUNDLE_KEY_COUNT, mCount)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.reset -> {
                mCount = 0
                countText.text = mCount.toString()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
