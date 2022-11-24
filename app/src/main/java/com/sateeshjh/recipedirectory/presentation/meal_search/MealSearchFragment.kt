package com.sateeshjh.recipedirectory.presentation.meal_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.sateeshjh.recipedirectory.databinding.FragmentMealSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MealSearchFragment : Fragment() {

    private var _binding: FragmentMealSearchBinding? = null
    val binding: FragmentMealSearchBinding
        get() = _binding!!

    private val mealSearchViewModel: MealSearchViewModel by viewModels()

    private val mealSearchAdapter = MealSearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealSearchBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    mealSearchViewModel.searchMealList(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (binding.search.query.isEmpty())
                    mealSearchViewModel.searchMealList("")
                return false
            }
        })

        binding.searchRecycler.apply {
            adapter = mealSearchAdapter
        }

        lifecycle.coroutineScope.launchWhenCreated {
            mealSearchViewModel.mealSearchList.collectLatest {
                if (it.isLoading) {
                    binding.noRecipeFound.visibility = View.GONE
                    binding.progresMealSearch.visibility = View.VISIBLE
                }

                if (it.error.isNotBlank()) {
                    binding.noRecipeFound.visibility = View.GONE
                    binding.progresMealSearch.visibility = View.GONE
                }

                it.data?.let {
                    if (it.isEmpty()) {
                        binding.noRecipeFound.visibility = View.VISIBLE
                    }

                    binding.progresMealSearch.visibility = View.GONE
                    mealSearchAdapter.setContentList(it.toMutableList())
                }
            }
        }

        mealSearchAdapter.itemClickListener {
            val mealSearchToDetailsAction =
                MealSearchFragmentDirections.actionMealSearchFragmentToMealDetailsFragment()
            mealSearchToDetailsAction.setMealId(it.mealId)
            findNavController().navigate(mealSearchToDetailsAction)
        }

        mealSearchViewModel.searchMealList("")
    }
}