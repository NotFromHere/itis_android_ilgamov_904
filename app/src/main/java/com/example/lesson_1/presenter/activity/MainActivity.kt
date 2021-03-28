package com.example.lesson_1.presenter.activity


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.room.Room
import com.example.lesson_1.R
import com.example.lesson_1.data.api.ApiFactory
import com.example.lesson_1.data.impl.LocationRepositoryImpl
import com.example.lesson_1.data.impl.WeatherRepositoryImpl
import com.example.lesson_1.domain.entity.CityDomain
import com.example.lesson_1.domain.usecase.FindCitiesAroundUseCase
import com.example.lesson_1.presenter.AppDatabase
import com.example.lesson_1.presenter.moxy.MainPresenter
import com.example.lesson_1.presenter.moxy.MainView
import com.example.lesson_1.presenter.recycler.RecyclerCityAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter


class MainActivity : MvpAppCompatActivity(), MainView {
    private val rvCityAdapter by lazy { RecyclerCityAdapter() }

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = initPresenter()

    private val findCitiesAroundUseCase: FindCitiesAroundUseCase by lazy {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        )
            .allowMainThreadQueries() //НЕ БЕЙТЕ, НЕ НАДО
            .fallbackToDestructiveMigration()
            .build()
        ApiFactory.weatherAPI.let {
            WeatherRepositoryImpl(it, db.weatherDao()).let {
                FindCitiesAroundUseCase(it)
            }
        }
    }

    private val locationRepositoryImpl by lazy {
        LocationRepositoryImpl(FusedLocationProviderClient(this))
    }

    private fun initPresenter(): MainPresenter =
        MainPresenter(findCitiesAroundUseCase, locationRepositoryImpl)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        mainPresenter.onStart()

    }

    private fun init() {
        rv_main.adapter = rvCityAdapter
        initListeners()
    }

    private fun initListeners() {
        search_main.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mainPresenter.findCity(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun submitList(list: List<CityDomain>) {
        rvCityAdapter.submitList(list)
    }

    override fun startWeather(cityId: Int) {
        Intent(this, WeatherActivity::class.java).run {
            putExtra(WeatherActivity.CITY_ID, cityId)
            startActivity(this)
        }
    }

    override fun provideHttpException(query: String?) {
        Snackbar.make(
            findViewById(android.R.id.content),
            "Город $query не найден",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun progressOn() {
        progress.visibility = View.VISIBLE
    }

    override fun progressOff() {
        progress.visibility = View.INVISIBLE
    }
}




