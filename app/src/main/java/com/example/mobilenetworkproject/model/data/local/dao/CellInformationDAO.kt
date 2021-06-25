package com.example.mobilenetworkproject.model.data.local.dao

import androidx.room.*
import com.example.mobilenetworkproject.model.data.local.entity.CellInformationEntity

@Dao
interface CellInformationDAO {
    @Query("SELECT * FROM cell_information")
    fun selectAllCelInformation(): List<CellInformationEntity>

    @Query("SELECT cell_id FROM cell_information WHERE cell_id=:cell_id" )
    fun getCelInformationByCellId(cell_id : Long): Long

    @Insert
    fun insertCellInformation(cellInformation: CellInformationEntity)

    @Delete
    fun deleteCellInformation(cellInformation: CellInformationEntity)

    @Update
    fun updateCellInformation(cellInformation: CellInformationEntity): Int
}