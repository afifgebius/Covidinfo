package com.example.covidinfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covidinfo.adapter.AdapterCountry
import com.example.covidinfo.databinding.ActivityDetailBinding
import com.example.covidinfo.databinding.ActivityMainBinding
import com.example.covidinfo.model.CountriesItem
import com.example.covidinfo.model.ResponseCovid
import com.example.covidinfo.service.APIservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.text.DecimalFormat
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapters: AdapterCountry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Timber.plant(Timber.DebugTree())

        binding.cardaboutcovid.setOnClickListener {
            startActivity(Intent(this,AboutCovidActivity::class.java))
        }
        binding.cardmenghindarcovid.setOnClickListener {
            startActivity(Intent(this,CaraMencegahCovidActivity::class.java))
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapters.filter.filter(newText)
                return false
            }
        })
        binding.swipeview.setOnRefreshListener {
            getCountry()
            binding.swipeview.isRefreshing = false
        }
        getCountry()
    }

    private fun pindahkedetail(country : CountriesItem){
        val move = Intent(this,DetailActivity::class.java)
        move.putExtra(DetailActivity.EXTRA_COUNTRY,country)
        startActivity(move)
    }

    private fun getCountry() {
        val call = APIservice.getServiceApiService().getAllDataNegara()
        call.enqueue(object : Callback<ResponseCovid> {
            override fun onResponse(call: Call<ResponseCovid>, response: Response<ResponseCovid>) {
                if (response.isSuccessful) {
                    val listcountry = response.body()?.global
                    val formaterr: NumberFormat = DecimalFormat("#,###")

                    binding.txtPositifglobal.text = formaterr.format(listcountry?.totalConfirmed)
                    binding.txtRecoveredglobal.text = formaterr.format(listcountry?.totalRecovered)
                    binding.txtDeathsglobal.text = formaterr.format(listcountry?.totalDeaths)
                    binding.recyclerCountry.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapters =
                            AdapterCountry(response.body()?.countries as ArrayList<CountriesItem>) {
                                country -> pindahkedetail(country)
                            }
                        adapter = adapters
                        binding.progressBar.visibility = View.GONE
                    }
                } else {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<ResponseCovid>, t: Throwable) {
                binding.progressBar.visibility = View.VISIBLE
                Timber.e(t.localizedMessage)
            }
        })
    }
}
