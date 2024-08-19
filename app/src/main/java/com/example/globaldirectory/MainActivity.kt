package com.example.globaldirectory

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.globaldirectory.databinding.ActivityMainBinding
import com.example.globaldirectory.utils.LoadJSONFile

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Load and parse JSON data.
        val jsonReader = LoadJSONFile()
        val jsonData = jsonReader.loadJSONfromString("data.json", assets)
        val size = jsonData.getInt("TotalCount")
        var countries = ArrayList<String>()

        // Populate list with country names and native names.
        for (i in 0 until size) {
            val countryName =
                jsonData.getJSONArray("Countries").getJSONObject(i).getString("Name")
            val countryNativeName =
                jsonData.getJSONArray("Countries").getJSONObject(i).getString("NativeName")

            countries.add("$countryName, $countryNativeName")
        }

        // Set adapter to display the list of countries.
        var adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, countries)
        binding.countries.adapter = adapter

        // Pass country details to CountryActivity.
        binding.countries.setOnItemClickListener { parent, view, position, id ->

            //Selected Country
            val countryObject = jsonData.getJSONArray("Countries").getJSONObject(position)

            val name = countryObject.getString("Name")
            val alpha3code = countryObject.getString("Alpha3Code")
            val region = countryObject.getString("Region")
            val subRegion = countryObject.getString("SubRegion")
            val currName = countryObject.getString("CurrencyName")
            val currCode = countryObject.getString("CurrencyCode")
            val currSymbol = countryObject.getString("CurrencySymbol")
            val language = countryObject.getString("NativeLanguage")
            val flag = countryObject.getString("FlagPng")
            val numCode = countryObject.getString("NumericCode")


            val intent = Intent(this, CountryActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("alpha3code", alpha3code)
            intent.putExtra("region", region)
            intent.putExtra("subRegion", subRegion)
            intent.putExtra("currName", currName)
            intent.putExtra("currCode", currCode)
            intent.putExtra("currSymbol", currSymbol)
            intent.putExtra("language", language)
            intent.putExtra("flag", flag)
            intent.putExtra("numCode", numCode)

            startActivity(intent)
        }
    }
}