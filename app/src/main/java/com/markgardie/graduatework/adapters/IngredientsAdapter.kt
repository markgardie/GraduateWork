package com.markgardie.graduatework.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.markgardie.graduatework.R
import com.markgardie.graduatework.databinding.IngredientsRowLayoutBinding
import com.markgardie.graduatework.models.ExtendedIngredient
import com.markgardie.graduatework.ui.fragments.ingredients.IngredientsFragment
import com.markgardie.graduatework.ui.fragments.ingredients.IngredientsFragmentDirections
import com.markgardie.graduatework.util.Constants.Companion.BASE_IMAGE_URL
import com.markgardie.graduatework.util.RecipesDiffUtil
import kotlinx.android.synthetic.main.ingredients_row_layout.view.*
import java.lang.Exception



class IngredientsAdapter(
        private val requireActivity: FragmentActivity
): RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(private val binding: IngredientsRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(extendedIngredient: ExtendedIngredient) {
            binding.extendedIngredient = extendedIngredient
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IngredientsRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentIngredient = ingredientsList[position]
        holder.bind(currentIngredient)

        holder.itemView.ingredient_imageView.load(BASE_IMAGE_URL + ingredientsList[position].image) {
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }
        holder.itemView.ingredient_name.text = ingredientsList[position].name.capitalize()
        holder.itemView.ingredient_amount.text = ingredientsList[position].amount.toString()
        holder.itemView.ingredient_unit.text = ingredientsList[position].unit
        holder.itemView.ingredient_consistency.text = ingredientsList[position].consistency
        holder.itemView.ingredient_original.text = ingredientsList[position].original

        holder.itemView.ingredientsRowLayout.setOnClickListener {
            try {
                val action = IngredientsFragmentDirections.actionIngredientsFragmentToPriceActivity(currentIngredient)
                requireActivity.findNavController(R.id.detailsActivityNavHostFragment).navigate(action)
            } catch (e: Exception) {
                Log.d("ingredientsRowLayout.setOnClickListener", e.toString())
            }
        }
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newIngredients: List<ExtendedIngredient>) {
        val ingredientsDiffUtil =
                RecipesDiffUtil(ingredientsList, newIngredients)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientsDiffUtil)
        ingredientsList = newIngredients
        diffUtilResult.dispatchUpdatesTo(this)
    }

}