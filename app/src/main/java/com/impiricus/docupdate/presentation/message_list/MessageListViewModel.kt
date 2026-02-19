package com.impiricus.docupdate.presentation.message_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.impiricus.docupdate.domain.usecases.GetMessagesUseCase
import com.impiricus.docupdate.domain.usecases.GetPhysiciansUseCase
import com.impiricus.docupdate.domain.models.Message
import com.impiricus.docupdate.domain.models.Physician
import com.impiricus.docupdate.util.mapMessagesToUiModel
import com.impiricus.docupdate.util.DateRange
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MessageListViewModel @Inject constructor(
    private val getMessages: GetMessagesUseCase,
    private val getPhysicians: GetPhysiciansUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MessageListState())
    val state = _state.asStateFlow()

    // Cache raw data (important!)
    private var cachedMessages: List<Message> = emptyList()
    private var cachedPhysicians: List<Physician> = emptyList()

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {

            _state.update { it.copy(isLoading = true, error = null) }

            try {
                cachedMessages = getMessages()
                cachedPhysicians = getPhysicians()

                _state.update {
                    it.copy(
                        physicians = cachedPhysicians,
                        isLoading = false
                    )
                }

                applyFiltersAndMap()

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    fun onPhysicianSelected(id: String?) {
        _state.update { it.copy(selectedPhysicianId = id) }
        applyFiltersAndMap()
    }

    fun onDateRangeSelected(range: DateRange) {
        _state.update { it.copy(dateFilter = range) }
        applyFiltersAndMap()
    }

    private fun applyFiltersAndMap() {

        val selectedPhysicianId = _state.value.selectedPhysicianId
        val dateRange = _state.value.dateFilter

        val filtered = cachedMessages.filter { message ->

            val matchesPhysician =
                selectedPhysicianId == null ||
                        message.physicianId == selectedPhysicianId

            val startTimestamp = dateRange.toStartTimestamp()

            val matchesDate =
                startTimestamp == null ||
                        message.timestamp >= startTimestamp

            matchesPhysician && matchesDate
        }

        val uiModels = mapMessagesToUiModel(
            filtered,
            cachedPhysicians
        )

        _state.update {
            it.copy(messages = uiModels)
        }
    }


}
