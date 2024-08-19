package com.example.globaldirectory

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.globaldirectory.databinding.ActivityCountryBinding

class CountryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve data passed from the previous activity through the Intent
        val name = intent.getStringExtra("name")
        val alpha3code = intent.getStringExtra("alpha3code")
        val region = intent.getStringExtra("region")
        val subRegion = if (intent.getStringExtra("subRegion").isNullOrEmpty()) {
            "Sub-region not specified" // Set default if null or empty
        } else {
            intent.getStringExtra("subRegion")
        }
        val currName = intent.getStringExtra("currName")
        val currCode = intent.getStringExtra("currCode")
        val currSymbol = intent.getStringExtra("currSymbol")
        val flag = intent.getStringExtra("flag")
        val numCode = intent.getStringExtra("numCode")
        val language = if (intent.getStringExtra("language").isNullOrEmpty()) {
            "Not specified" // Set default value if language is null or empty
        } else {
            intent.getStringExtra("language")
        }

        // Load the country's flag into the ImageView using Glide
        Glide.with(this).load(flag).into(binding.flag)

        // Set the text views with the retrieved data
        binding.name.text = name
        binding.alpha3code.text = alpha3code
        binding.totalRegion.text = getString(R.string.region, region, subRegion)
        binding.totalCurr.text = getString(R.string.currency, currName, currCode, currSymbol)
        binding.language.text = getString(R.string.language, language)

        // Set content descriptions dynamically
        binding.flag.contentDescription = "Flag of $name"
        binding.name.contentDescription = "Country name: $name"
        binding.alpha3code.contentDescription = "Country code: $alpha3code"
        binding.totalCurr.contentDescription =
            "Currency: ${getString(R.string.currency, currName, currCode, currSymbol)}"
        binding.totalRegion.contentDescription =
            "Region: ${getString(R.string.region, region, subRegion)}"
        binding.language.contentDescription = "Language spoken: $language"
        binding.phoneButton.contentDescription =
            "Button to initiate a phone call to a number in $name"

        // Set an OnClickListener on the phone button to initiate a dial action
        binding.phoneButton.setOnClickListener {
            // Create an Intent to open the phone dialer with the country's phone code
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${numCode}")
            }

            // Start the activity to open the dialer
            startActivity(intent)
        }

    }
}
