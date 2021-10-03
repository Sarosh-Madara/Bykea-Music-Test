package com.project.music.app.ui.fragment.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.music.app.arch.api.ApiState
import com.project.music.app.arch.repos.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
public class HomeViewModel @Inject constructor(private val repository: SearchRepository) :
    ViewModel() {

    private val userData: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.EMPTY)
    val _userData: StateFlow<ApiState> = userData

    fun search(
        term: String
    ) {
        viewModelScope.launch {
            userData.value = ApiState.LOADING
            repository.search(term,200)
                .catch { e ->
                    userData.value = ApiState.ERROR(e)
                }.collect { response ->
                    userData.value = ApiState.SUCCESS(response)
                }
        }
    }
}