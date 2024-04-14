package com.example.myrecyclerviewapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.myrecyclerviewapplication.databinding.ActivityCityDetailsBinding
import com.google.android.material.snackbar.Snackbar

class CityDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil
            .setContentView<ActivityCityDetailsBinding>(this,
                R.layout.activity_city_details)

        if(Singleton.citySelected >= 0){
            Singleton.cities[Singleton.citySelected].apply {
                binding.nameEditText.setText(name)
                binding.populationEditText.setText(population.toString())
                binding.capitalCheckBox.isChecked = isCapital
            }
        }

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val population = binding.populationEditText.text
                .toString().toInt()
            val isCapital = binding.capitalCheckBox.isChecked
            if (Singleton.citySelected < 0) {
                Singleton.cities.add(City(Singleton.citySelected, name, population, isCapital))
            }else{
                Singleton.cities[Singleton.citySelected].apply{
                    this.name = name
                    this.population = population
                    this.isCapital = isCapital
                }
            }
            finish()
        }
    }

    override fun onBackPressed() {
        showDiscardChangesDialog()
    }

    private fun showDiscardChangesDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Descartar Alterações?")
            .setMessage("Tem certeza de que deseja voltar sem salvar as alterações?")
            .setPositiveButton("Sim") { _, _ ->
                super.onBackPressed()
            }
            .setNegativeButton("Não", null)
            .create()
            .show()
    }


}