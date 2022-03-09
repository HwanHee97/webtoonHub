package com.example.webtoonhub

sealed class UiState{
    object Loading : UiState()
    object Empty : UiState()
    object Success: UiState()
    object Error: UiState()
}