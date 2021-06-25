package com.example.mobilenetworkproject.model.data.local.dao

import androidx.room.*
import com.example.mobilenetworkproject.model.data.local.entity.CellInformationEntity
import com.example.mobilenetworkproject.model.domain.CellInformation

@Dao
interface CellInformationDAO {
    @Query("SELECT * FROM cell_information")
    fun selectAllCelInformation(): List<CellInformationEntity>

    @Query("SELECT * FROM cell_information WHERE id=:cell_information_id")
    fun getCellInformation(cell_information_id: Int): CellInformationEntity

    @Insert
    fun insertCellInformation(cellInformation: CellInformationEntity)

    @Delete
    fun deleteCellInformation(cellInformation: CellInformationEntity)

    @Update
    fun updateCellInformation(cellInformation: CellInformationEntity): Int
}