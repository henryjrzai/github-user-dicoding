package com.hjz.githubapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjz.githubapp.data.model.FollowingViewModel
import com.hjz.githubapp.databinding.FragmentFollowingBinding
import com.hjz.githubapp.ui.adapter.UserAdapter

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : UserAdapter
    private lateinit var followingViewModel : FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showImageAlert(false)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowingViewModel::class.java)

        binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter

        val username = arguments?.getString(DetailUserActivity.EXTRA_USERNAME).toString()

        followingViewModel.getFollowing(username)

        followingViewModel.users.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                adapter.setList(it)
            } else {
                showImageAlert(true)
            }
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner){
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