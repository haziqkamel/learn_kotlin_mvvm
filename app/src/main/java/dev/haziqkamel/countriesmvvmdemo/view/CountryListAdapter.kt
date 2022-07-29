package dev.haziqkamel.countriesmvvmdemo.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.haziqkamel.countriesmvvmdemo.R
import dev.haziqkamel.countriesmvvmdemo.databinding.ItemCountryBinding
import dev.haziqkamel.countriesmvvmdemo.model.Country
import dev.haziqkamel.countriesmvvmdemo.util.getProgressDrawable
import dev.haziqkamel.countriesmvvmdemo.util.loadImage

class CountryListAdapter(private var countries: ArrayList<Country>) :
    RecyclerView.Adapter<CountryListAdapter.CountryViewHolder>() {

    fun updateCountries(newCountries: List<Country>) {
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemCountryBinding.bind(view)
        private val countryName = binding.name
        private val imageView = binding.imageView
        private val countryCapital = binding.capital
        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(country: Country) {
            countryName.text = country.countryName
            countryCapital.text = country.capital

            imageView.loadImage(country.flag, progressDrawable)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountryViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_country, parent, false
        )
    )

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount() = countries.size

}