package com.project.music.app.ui.fragment.home

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.project.music.app.base.BaseFragment
import com.project.music.app.databinding.FragmentHomeBinding
import com.project.music.app.responses.Result
import com.project.music.app.responses.SearchResponse
import com.project.music.app.ui.adapter.PlaylistAdapter
import com.project.music.app.ui.adapter.ShimmerAdapter
import com.project.music.app.ui.fragment.home.viewmodel.HomeViewModel
import com.project.music.app.utils.gone
import com.project.music.app.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.flow.collect
import java.io.IOException


@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var isPlaying: Boolean = false
    lateinit var binding: FragmentHomeBinding
    val viewModel by activityViewModels<HomeViewModel>()

    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(
            inflater, container, false
        )
        initListeners()
        subscribeCallbackFromSuccessStatus()
        return binding.root
    }

    private fun initShimmerList() {
        binding.shimmerLayout.rv_shimmer.adapter = ShimmerAdapter(
            arrayListOf<String>(
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8"
            )
        )
    }

    private fun initListeners() {
        // below listener is to place search icon in keyboard and call api on search
        binding.editSearchField.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.layoutEmpty.gone()
                initShimmerList()
                performSearch()
                return@OnEditorActionListener true
            }
            false
        })

        // app submitted and rejected
        binding.mediaActionsPlay.setOnClickListener {
            if (mediaPlayer.isPlaying()) {
                // pausing the media player if media player
                // is playing we are calling below line to
                // stop our media player.
                mediaPlayer.pause();
                isPlaying = false
                binding.playerControl.media_actions_play.setImageResource(android.R.drawable.ic_media_pause)

                // below line is to display a message
                // when media player is paused.
                Toast.makeText(requireContext(), "Audio has been paused", Toast.LENGTH_SHORT)
                    .show();
            } else {
                isPlaying = true
                mediaPlayer.start()
                binding.playerControl.media_actions_play.setImageResource(android.R.drawable.ic_media_play)
            }
        }
    }

    private fun performSearch() {
        viewModel.search(binding.editSearchField.text.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun subscribeCallbackFromSuccessStatus() {
        getResponse().observe(requireActivity(), Observer {
            renderSuccessResponse(it)
        })

        lifecycleScope.launchWhenCreated {
            viewModel._userData.collect {
                consumeApiResponse(it)
            }
        }
    }

    private fun renderSuccessResponse(response: Any?) {
        when (response) {
            is SearchResponse -> {
                binding.shimmerLayout.gone()

                if (response.results.isEmpty()) {
                    binding.rvPlaylist.gone()
                    binding.layoutNoSongs.visible()
                } else {
                    binding.layoutNoSongs.gone()
                    binding.rvPlaylist.visible()

                    var adapter = PlaylistAdapter(response.results, requireContext()) {

                        lifecycleScope.launchWhenCreated {

                            if (::mediaPlayer.isInitialized)
                                if (mediaPlayer.isPlaying()) {
                                    mediaPlayer.pause()
                                }
                            playAudio(it)

                        }
                    }
                    binding.rvPlaylist.adapter = adapter
                }
            }
        }
    }

    fun playAudio(result: Result) {

        var audioUrl = result.previewUrl

        // initializing media player
        mediaPlayer = MediaPlayer();

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(audioUrl);
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();
            mediaPlayer.start();
            binding.playerControl.media_actions_play.setImageResource(android.R.drawable.ic_media_play)
            binding.playerControl.visible()
            isPlaying = true
        } catch (e: IOException) {
            e.printStackTrace();
            isPlaying = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // when app is in background
        // ... the media player will be killed
        if (::mediaPlayer.isInitialized) {
            if (isPlaying) {
                mediaPlayer.reset()
                mediaPlayer.release()
            }
        }
    }
}