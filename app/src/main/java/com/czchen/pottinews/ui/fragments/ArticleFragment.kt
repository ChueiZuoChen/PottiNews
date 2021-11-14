package com.czchen.pottinews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.czchen.pottinews.databinding.FragmentArticleBinding
import com.czchen.pottinews.db.ArticleDatabase
import com.czchen.pottinews.repository.NewsRepository
import com.czchen.pottinews.ui.MainActivity
import com.czchen.pottinews.ui.NewsViewModel
import com.czchen.pottinews.ui.NewsViewModelFactory
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment() {
    lateinit var binding: FragmentArticleBinding
    lateinit var viewModel: NewsViewModel
    val args:ArticleFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = NewsRepository(ArticleDatabase(requireContext()))
        val viewModelFactory = NewsViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)
        val article = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view,"Article added successfully",Snackbar.LENGTH_LONG).show()
        }


    }
}