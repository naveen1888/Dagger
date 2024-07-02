package com.rawat.dagger2.mvvm.dagger.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rawat.dagger2.databinding.FragmentHomeBinding
import com.rawat.dagger2.mvvm.dagger.FakerApplication
import com.rawat.dagger2.mvvm.dagger.UiState
import com.rawat.dagger2.mvvm.dagger.viewmodels.MainViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeFragmentAdapter

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    private lateinit var homeViewModel: HomeViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val appComponent = (activity?.application as FakerApplication).applicationComponent
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this, mainViewModelFactory)[HomeViewModel::class.java]
        homeViewModel.fetchProductWithLiveData()
        observeLiveData()
        adapter = HomeFragmentAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        return binding.root
    }

    private fun observeLiveData() {
        homeViewModel.productLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { list ->
                        adapter.saveData(list)
                    }
                }

                is UiState.Error<*> -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

}