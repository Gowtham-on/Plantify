package com.project.testone.homeScreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.project.testone.R
import com.project.testone.dataClass.User
import com.project.testone.fragments.bottom_nav_fragments.PlantsFragment
import com.project.testone.fragments.bottom_nav_fragments.SocialFragment
import com.project.testone.fragments.side_navbar_fragments.*
import com.project.testone.infoAndLoginScreens.LoginScreen
import com.project.testone.paymentAndCart.CartActivity
import com.project.testone.paymentAndCart.CheckoutActivity
import kotlinx.android.synthetic.main.action_bar_layout.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nav_header.*

class HomeActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var city: String
    lateinit var state: String
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        firebaseDatabase = FirebaseDatabase.getInstance()

        databaseReference = firebaseDatabase.getReference("Users").child(auth.currentUser!!.uid)


        googleSignInClient = GoogleSignIn.getClient(this, gso)

        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.action_bar_layout)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "Plantify"


        replaceFragment(HomeFragment())

        nav_view.setNavigationItemSelectedListener {
            it.isChecked = true
            when (it.itemId) {
                R.id.home_nav -> replaceFragment(HomeFragment())
                R.id.location_nav -> replaceFragment(LocationFragment())
                R.id.purchase_nav -> replaceFragment(HistoryFragment())
                R.id.about_app_nav -> replaceFragment(AboutAppFragment())
                R.id.notification_nav -> replaceFragment(NotificationFragment())
                R.id.offer_nav -> replaceFragment(OfferFragment())
                R.id.security_nav -> replaceFragment(SecurityFragment())
                R.id.more_nav -> replaceFragment(MoreFragment())

                R.id.logout_nav -> {
                    auth.signOut()
                    googleSignInClient.signOut()
                    Toast.makeText(this, "Logged Out Successfully", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this, LoginScreen::class.java)
                    startActivity(intent)
                }

            }
            true
        }

        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bottomnav_home -> {
                    replaceFragment(HomeFragment())
                }
                R.id.bottomnav_message -> {
                    replaceFragment(PlantsFragment())
                }
                R.id.bottomnav_settings -> {
                    replaceFragment(SocialFragment())
                }
            }
            true
        }

        cartBtn.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentLayout, fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {

            nav_user_name.text = auth.currentUser?.displayName.toString()
            userImageId.setImageURI(auth.currentUser?.photoUrl)
            getData()
            Glide.with(this@HomeActivity).load(auth.currentUser?.photoUrl).into(userImageId)
            Log.d("checking", auth.currentUser?.photoUrl.toString())
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(User::class.java)
                city = value!!.city
                state = value!!.state
                nav_location.text = "$city, $state"
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HomeActivity, "Failed to get data.", Toast.LENGTH_SHORT)
                    .show();

            }
        })
    }

}