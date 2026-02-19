package com.impiricus.docupdate.presentation.message_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.impiricus.docupdate.data.repository.ComplianceRepository
import com.impiricus.docupdate.domain.analytics.AnalyticsTracker
import com.impiricus.docupdate.domain.usecases.GetMessageByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageDetailViewModel @Inject constructor(
    private val getMessageById: GetMessageByIdUseCase,
    private val complianceRepository: ComplianceRepository,
    private val analyticsTracker: AnalyticsTracker
) : ViewModel() {


    private val _state = MutableStateFlow(MessageDetailState())
    val state = _state.asStateFlow()

    fun loadMessage(id: String) {
        viewModelScope.launch {

            _state.update { it.copy(isLoading = true, error = null) }

            try {
                val message = getMessageById(id)

                if (message == null) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = "Message not found"
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            message = message
                        )
                    }
                }

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
    fun runComplianceCheck() {

        val message = _state.value.message ?: return

        viewModelScope.launch {

            analyticsTracker.trackEvent(
                name = "compliance_check_triggered",
                properties = mapOf(
                    "message_id" to message.id,
                    "physician_id" to message.physicianId,
                    "topic" to message.topic
                )
            )

            val result = complianceRepository.runCompliance(
                messageId = message.id,
                text = message.text
            )

            _state.update {
                it.copy(complianceResult = result)
            }
        }
    }

}
