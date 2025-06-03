package com.cuan.catatankeuangan.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.cuan.catatankeuangan.domain.model.CategoryReport
import com.cuan.catatankeuangan.domain.model.ProductReportRejection
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportDao {

    @Query("""
        SELECT 
            p.id AS productId,
            p.name AS productName,
            p.image_path AS productImageUri,
            p.sellPrice AS sellPrice,
            p.buyPrice AS buyPrice,
            SUM(tp.quantity) AS totalSold,
            (p.sellPrice * SUM(tp.quantity)) AS totalRevenue,
            (p.buyPrice * SUM(tp.quantity)) AS totalCost,
            ((p.sellPrice - p.buyPrice) * SUM(tp.quantity)) AS totalProfit,
            c.id AS categoryId,
            c.name AS categoryName,
            t.timestamp AS transactionDate
        FROM transaction_table t
        JOIN transaction_product_cross_ref_table tp ON t.id = tp.transactionId
        JOIN product_snapshot_table ps ON tp.snapshotId = ps.id
        JOIN product_table p ON ps.productId = p.id
        LEFT JOIN category_table c ON p.categoryId = c.id
        WHERE t.transactionType = 'INCOME'
        GROUP BY p.id
        ORDER BY totalProfit DESC
    """)
    fun getSalesReport(): Flow<List<ProductReportRejection>>

    @Query("""
    SELECT 
        c.id AS categoryId,
        c.name AS categoryName,
        COUNT(DISTINCT t.id) AS totalTransactions,
        SUM(CASE WHEN t.transactionType = 'INCOME' THEN tp.quantity * ps.sellPrice ELSE 0 END) AS totalIncome,
        SUM(CASE WHEN t.transactionType = 'EXPENSE' THEN tp.quantity * ps.buyPrice ELSE 0 END) AS totalExpense,
        MAX(t.timestamp) AS latestTransactionTime -- tambahkan untuk bantu filter di VM
    FROM transaction_table t
    JOIN transaction_product_cross_ref_table tp ON t.id = tp.transactionId
    JOIN product_snapshot_table ps ON tp.snapshotId = ps.id
    JOIN product_table p ON ps.productId = p.id
    LEFT JOIN category_table c ON p.categoryId = c.id
    GROUP BY c.id
""")
    fun getCategoryReport(): Flow<List<CategoryReport>>
}