package com.example.covidinfo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.covidinfo.databinding.ActivityDetailBinding
import com.example.covidinfo.model.CountriesItem
import java.text.DecimalFormat
import java.text.NumberFormat

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_COUNTRY = "N"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<CountriesItem>(EXTRA_COUNTRY)
        val formatter: NumberFormat = DecimalFormat("#,###")

        binding.txtNamaCountry.text = data?.country
        binding.txtDate.text = data?.date
        binding.txtCountrycode.text = data?.countryCode
        binding.txtKesembuhanBaru.text = formatter.format(data?.newRecovered)
        binding.txtTotalKesembuhan.text = formatter.format(data?.totalRecovered)
        binding.txtPositifBaru.text = formatter.format(data?.newConfirmed)
        binding.txtTotalPositif.text = formatter.format(data?.totalConfirmed)
        binding.txtKematianBaru.text = formatter.format(data?.newDeaths)
        binding.txtTotalKematian.text = formatter.format(data?.totalDeaths)
    }
}