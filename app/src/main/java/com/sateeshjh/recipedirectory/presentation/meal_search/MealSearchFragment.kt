package com.sateeshjh.recipedirectory.presentation.meal_search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMealSearchBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.search.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    mealSearchViewModel.searchMealList(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        binding.searchRecycler.apply {
            adapter = mealSearchAdapter
        }

        lifecycle.coroutineScope.launchWhenCreated {
            mealSearchViewModel.mealSearchList.collectLatest {
                if (it.isLoading) {
                    binding.progresMealSearch.visibility = View.VISIBLE
                }

                if (it.error.isNotBlank()) {
                    binding.progresMealSearch.visibility = View.GONE
                }

                it.data?.let {
                    binding.progresMealSearch.visibility = View.GONE
                    mealSearchAdapter.setContentList(it.toMutableList())
                }
            }
        }

        mealSearchViewModel.searchMealList("")
    }
}