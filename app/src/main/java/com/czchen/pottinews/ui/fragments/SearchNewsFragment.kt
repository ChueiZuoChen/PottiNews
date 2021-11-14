package com.czchen.pottinews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.czchen.pottinews.R
import com.czchen.pottinews.adapters.NewsAdapter
import com.czchen.pottinews.databinding.FragmentSearchNewsBinding
import com.czchen.pottinews.db.ArticleDatabase
import com.czchen.pottinews.repository.NewsRepository
import com.czchen.pottinews.ui.NewsViewModel
import com.czchen.pottinews.ui.NewsViewModelFactory
import com.czchen.pottinews.util.Constants.Companion.SEARCH_TIME_DELAY
import com.czchen.pottinews.util.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*


class SearchNewsFragment : Fragment() {
    lateinit var binding: FragmentSearchNewsBinding
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = NewsRepository(ArticleDatabase(requireContext()))
        val viewModelFactory = NewsViewModelFactory(repository)
        newsAdapter = NewsAdapter()
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)

        setUpAdapter()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }


        /**Search*/
        var job: Job? = null
        binding.etSearch.addTextChangedListener { edString ->
            job?.cancel()
            job = CoroutineScope(Dispatchers.Main).launch {
                delay(SEARCH_TIME_DELAY)
                edString?.let {
                    if (edString.toString().isNotEmpty()) {
                        viewModel.searchNews(edString.toString())
                    }
                }
            }

        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    it.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    it.message?.let { message ->
                        Snackbar.make(
                            requireView(),
                            message,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })


    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setUpAdapter() {
        binding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }
}














