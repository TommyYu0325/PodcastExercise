package com.example.podcastexercise.main.architecture


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.podcastexercise.model.CastCollection
import com.example.podcastexercise.model.PodCast
import com.example.podcastexercise.repository.PodcastRepository
import io.reactivex.disposables.CompositeDisposable

class PodCastViewModel(private val repository: PodcastRepository) : ViewModel() {

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val disposables = CompositeDisposable()


    private val _castsList = MutableLiveData<List<PodCast>>()
    val castsList: LiveData<List<PodCast>>
        get() = _castsList

    private val _castDetail = MutableLiveData<CastCollection>()
    val castDetail: LiveData<CastCollection>
        get() = _castDetail

    fun getCasts() {
        if (_isLoading.value == true) return
        _isLoading.value = true

        val disposable = repository.getCasts()
            .subscribe(
                {
                    _isLoading.value = false
                    if (it.data?.podcast != null) {
                        _castsList.value = it.data.podcast
                    } else {
                        _error.value = Throwable("no data")
                    }
                },
                {
                    _isLoading.value = false
                    _error.value = it
                }
            )
        disposables.add(disposable)
    }

    fun getCastDetail() {
        if (_isLoading.value == true) return
        _isLoading.value = true

        val disposable = repository.getCastDetail()
            .subscribe(
                {
                    _isLoading.value = false
                    if (it.data?.collection != null) {
                        _castDetail.value = it.data.collection
                    } else {
                        _error.value = Throwable("no data")
                    }
                },
                {
                    _isLoading.value = false
                    _error.value = it
                }
            )
        disposables.add(disposable)
    }


    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

}