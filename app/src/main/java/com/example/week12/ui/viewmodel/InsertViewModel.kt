package com.example.week12.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week12.model.Mahasiswa
import com.example.week12.repository.MahasiswaRepository
import kotlinx.coroutines.launch

class InsertViewModel (private val mhs: MahasiswaRepository): ViewModel(){
    var uiState by mutableStateOf(InserUiState())
        private set

    fun updateInsertMhsState(inserUiEvent: InsertUiEvent){
        uiState = InserUiState(inserUiEvent = inserUiEvent)
    }

    suspend fun insertMhs(){
        viewModelScope.launch {
            try {
                mhs.insertMahasiswa(uiState.inserUiEvent.toMhs())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InserUiState(
    val inserUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val nim:String = "",
    val nama:String = "",
    val alamat:String = "",
    val jenisKelamin:String = "",
    val kelas:String = "",
    val angkatan:String = "",
)

fun InsertUiEvent.toMhs(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    alamat = alamat,
    jenisKelamin = jenisKelamin,
    kelas = kelas,
    angkatan = angkatan,
)

fun Mahasiswa.toUiStateMhs(): InserUiState = InserUiState(
    inserUiEvent = toInsertUiEvent()
)

fun Mahasiswa.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    nim = nim,
    nama = nama,
    alamat = alamat,
    jenisKelamin = jenisKelamin,
    kelas = kelas,
    angkatan = angkatan,
)