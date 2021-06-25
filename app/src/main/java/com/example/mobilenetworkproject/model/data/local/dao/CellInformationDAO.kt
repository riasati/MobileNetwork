package com.example.mobilenetworkproject.model.data.local.dao

import androidx.room.*
import com.example.mobilenetworkproject.model.data.local.entity.CellInformationEntity

@Dao
interface CellInformationDAO {
    @Query("SELECT * FROM cell_information")
    fun selectAllCelInformation(): List<CellInformationEntity>

    @Insert
    fun insertCellInformation(cellInformation: CellInformationEntity): Int

    @Delete
    fun deleteCellInformation(cellInformation: CellInformationEntity)

    @Update
    fun updateCellInformation(cellInformation: CellInformationEntity): Int
}