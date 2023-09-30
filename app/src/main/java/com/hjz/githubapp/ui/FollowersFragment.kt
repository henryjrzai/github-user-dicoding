package com.hjz.githubapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjz.githubapp.R
import com.hjz.githubapp.data.model.FollowersViewModel
import com.hjz.githubapp.databinding.FragmentFollowersBinding
import com.hjz.githubapp.ui.adapter.FollowersAdapter

class FollowersFragment : Fragment(R.layout.fragment_followers) {

    private var _binding : FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : FollowersAdapter
    private lateinit var followersViewModel: FollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showImageAlert(false)

        adapter = FollowersAdapter()
        adapter.notifyDataSetChanged()

        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        binding.rvFollowers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowers.setHasFixedSize(true)
        binding.rvFollowers.adapter = adapter

        val username = arguments?.getString(DetailUserActivity.EXTRA_USERNAME).toString()

        followersViewModel.getFollowers(username)

        followersViewModel.followers.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                adapter.setList(it)
            } else {
                showImageAlert(true)
            }
        }

        followersViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showImageAlert(isShow: Boolean) {
        if (isShow) {
            binding.imgAlert.visibility = View.VISIBLE
        } else {
            binding.imgAlert.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}