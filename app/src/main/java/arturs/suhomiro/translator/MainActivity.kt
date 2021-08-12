package arturs.suhomiro.translator

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import arturs.suhomiro.translator.screens.favorites_screen.FavoritesFragment
import arturs.suhomiro.translator.screens.history_screen.HistoryFragment
import arturs.suhomiro.translator.screens.main_screen.MainFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.history_btn -> this.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    HistoryFragment()
                ).addToBackStack(null).commit()
            R.id.favorites_btn -> this.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    FavoritesFragment()
                ).addToBackStack(null).commit()

        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("ServiceCast")
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val view: View? = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP ||
                    ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass
                .name.startsWith("android.webkit.")
        ) {
            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x: Float = ev.rawX + view.getLeft() - scrcoords[0]
            val y: Float = ev.rawY + view.getTop() - scrcoords[1]
            if (x < view.getLeft() ||
                x > view.getRight() ||
                y < view.getTop() ||
                y > view.getBottom()) (this.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager).hideSoftInputFromWindow(
                this.window.decorView.applicationWindowToken, 0
            )
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0){
            supportFragmentManager.popBackStackImmediate();
        }
        else{
            super.onBackPressed();
        }
    }

}


