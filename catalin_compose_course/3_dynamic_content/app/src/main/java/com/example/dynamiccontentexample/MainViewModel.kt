package com.example.dynamiccontentexample

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val textFieldState = MutableLiveData<String>("")

    fun onTextChanged(value: String) {
        textFieldState.postValue(value)
    }
}