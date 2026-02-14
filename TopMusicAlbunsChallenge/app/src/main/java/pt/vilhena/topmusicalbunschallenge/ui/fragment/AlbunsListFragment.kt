package pt.vilhena.topmusicalbunschallenge.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import pt.vilhena.topmusicalbunschallenge.TopAlbumApplication
import pt.vilhena.topmusicalbunschallenge.data.adapter.AlbunsListAdapter
import pt.vilhena.topmusicalbunschallenge.data.model.dto.NetworkResult
import pt.vilhena.topmusicalbunschallenge.data.viewmodel.TopAlbumViewModel
import pt.vilhena.topmusicalbunschallenge.databinding.FragmentAlbunsListBinding
import javax.inject.Inject

class AlbunsListFragment : Fragment() {

    @Inject
    lateinit var viewModel: TopAlbumViewModel

    private lateinit var binding: FragmentAlbunsListBinding

    companion object {
        const val TAG = "AlbunsListFragment"
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as TopAlbumApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView")
        binding = FragmentAlbunsListBinding.inflate(inflater, container, false)


        viewModel.getTopAlbumsList()

        val listAdapter = AlbunsListAdapter { album ->
            Log.d(TAG, "Album clicked: $album")
            val action = AlbunsListFragmentDirections
                .actionAlbunsListFragmentToAlbumDetailedInformationFragment(album)
            findNavController().navigate(action)
        }

        binding.apply {
            topAlbunsList.adapter = listAdapter
            topAlbunsList.layoutManager = GridLayoutManager(requireContext(), 2)
            retryBtn.setOnClickListener {
                Log.d(TAG, "Retry button clicked")
                viewModel.getTopAlbumsList()
            }

            orderTypeGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
                if (group.checkedButtonId == View.NO_ID) {
                    group.check(checkedId)
                    return@addOnButtonCheckedListener
                }

                if (isChecked) {
                    viewModel.albumsLiveData.value?.let { result ->
                        if (result is NetworkResult.Success) {
                            val listToShow = when (checkedId) {
                                topBtn.id -> result.data
                                alphabeticallyBtn.id -> result.data.sortedBy { it.title }
                                else -> result.data
                            }
                            listAdapter.submitList(listToShow)
                        }
                    }
                }
            }

        }

        viewModel.albumsLiveData.observe(viewLifecycleOwner) { result ->

            when(result) {
                is NetworkResult.Loading -> {
                    Log.d(TAG, "Loading")
                    binding.loadingIcon.visibility = View.VISIBLE
                }
                is NetworkResult.Success -> {
                    Log.d(TAG, "Success")
                    changeErrorVisibility(false)
                    listAdapter.submitList(result.data)
                }
                is NetworkResult.Error -> {
                    Log.d(TAG, "Error: ${result.message}")
                    changeErrorVisibility(true)
                }
            }
        }

        return binding.root
    }


    private fun changeErrorVisibility(visible: Boolean) {
        binding.apply {
            loadingIcon.visibility = View.GONE
            errorContainer.visibility = if (visible) View.VISIBLE else View.GONE
            listContainer.visibility = if (visible) View.GONE else View.VISIBLE
        }
    }
}