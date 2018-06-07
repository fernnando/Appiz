package co.fddittmar.appiz

import android.Manifest
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import co.fddittmar.appiz.extension.active
import co.fddittmar.appiz.extension.disableShiftMode
import co.fddittmar.appiz.helper.BottomNavigationPosition
import co.fddittmar.appiz.helper.createFragment
import co.fddittmar.appiz.helper.findNavigationPositionById
import co.fddittmar.appiz.helper.getTag
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import android.content.pm.PackageManager
import android.content.Intent
import android.Manifest.permission
import android.Manifest.permission.CALL_PHONE
import android.app.Activity
import android.support.v4.app.ActivityCompat
import android.os.Build



class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val KEY_POSITION = "keyPosition"
    private val CALL_REQUEST = 100
    var mAuth = FirebaseAuth.getInstance()

    private var navPosition: BottomNavigationPosition = BottomNavigationPosition.PLACES


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreSaveInstanceState(savedInstanceState)
        setContentView(R.layout.activity_main)
        initBottomNavigation()
        initFragment(savedInstanceState)
        phoneNumberPermission()

        Toast.makeText(this, mAuth.currentUser?.email, Toast.LENGTH_LONG).show()

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        // Store the current navigation position.
        outState?.putInt(KEY_POSITION, navPosition.id)
        super.onSaveInstanceState(outState)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navPosition = findNavigationPositionById(item.itemId)
        return switchFragment(navPosition)
    }

    private fun restoreSaveInstanceState(savedInstanceState: Bundle?) {
        // Restore the current navigation position.
        savedInstanceState?.also {
            val id = it.getInt(KEY_POSITION, BottomNavigationPosition.PLACES.id)
            navPosition = findNavigationPositionById(id)
        }
    }

    private fun initBottomNavigation() {
        bottomNavigationView.disableShiftMode() // Extension function
        bottomNavigationView.active(navPosition.position)   // Extension function
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        savedInstanceState ?: switchFragment(BottomNavigationPosition.PLACES)
    }

    /**
     * Immediately execute transactions with FragmentManager#executePendingTransactions.
     */
    private fun switchFragment(navPosition: BottomNavigationPosition): Boolean {
        val fragment = supportFragmentManager.findFragment(navPosition)
        if (fragment.isAdded) return false
        detachFragment()
        attachFragment(fragment, navPosition.getTag())
        supportFragmentManager.executePendingTransactions()
        return true
    }

    private fun FragmentManager.findFragment(position: BottomNavigationPosition): Fragment {
        return findFragmentByTag(position.getTag()) ?: position.createFragment()
    }

    private fun detachFragment() {
        supportFragmentManager.findFragmentById(R.id.container)?.also {
            supportFragmentManager.beginTransaction().detach(it).commit()
        }
    }

    private fun attachFragment(fragment: Fragment, tag: String) {
        if (fragment.isDetached) {
            supportFragmentManager.beginTransaction().attach(fragment).commit()
        } else {
            supportFragmentManager.beginTransaction().add(R.id.container, fragment, tag).commit()
        }
        // Set a transition animation for this transaction.
        supportFragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit()
    }

    /**
     * This method is responsible make a call and also
     * checking run time permissions for CALL_PHONE
     */
    fun phoneNumberPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this as Activity,
                            Manifest.permission.CALL_PHONE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.CALL_PHONE),
                        CALL_REQUEST)
            }
        }

    }

}
