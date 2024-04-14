package com.example.myrecyclerviewapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myrecyclerviewapplication.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main)
        binding.mainRecyclerView.adapter =
            CityAdapter(object : CityAdapter.OnCityClickListener{
                override fun onCityClick(view: View, position: Int) {
                    Singleton.citySelected = position
                    val intent = Intent(this@MainActivity,
                        CityDetailsActivity::class.java)
                    startActivity(intent)
                }

                override fun onCityLongClick(view: View, position: Int) {
                    val removedCity = Singleton.cities.removeAt(position)
                    binding.mainRecyclerView.adapter?.notifyItemRemoved(position)
                    showUndoSnackbar(removedCity)
                }
            })

        for (i in 0..10){
            Singleton.cities.add(
                City(i, "City $i",i)
            )
        }
        binding.mainRecyclerView.layoutManager =
            LinearLayoutManager(this)

        binding.addButton.setOnClickListener {
            Singleton.citySelected = -1
            val intent = Intent(this,
                CityDetailsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showUndoSnackbar(removedCity: City) {
        Snackbar.make(
            binding.root,
            "Cidade ${removedCity.name} removida",
            Snackbar.LENGTH_LONG
        ).setAction("Desfazer") {
            Singleton.cities.add(removedCity)
            binding.mainRecyclerView.adapter?.notifyDataSetChanged()
        }.show()
    }


    override fun onResume() {
        super.onResume()
        binding.mainRecyclerView.adapter?.
                notifyDataSetChanged()
    }
}