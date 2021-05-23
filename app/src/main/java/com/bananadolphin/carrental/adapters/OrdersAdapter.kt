package com.bananadolphin.carrental.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bananadolphin.carrental.R
import com.bananadolphin.carrental.data.Order
import com.bananadolphin.carrental.data.RentalDao
import com.bananadolphin.carrental.data.RentalDatabase
import com.bananadolphin.carrental.databinding.OrderItemBinding

class OrdersAdapter(var orders: MutableList<Order>): RecyclerView.Adapter<OrdersAdapter.OrderHolder>() {

    class OrderHolder(itemView: View, val orderItemBinding: OrderItemBinding,val  rentalDao: RentalDao) : RecyclerView.ViewHolder(itemView) {
        fun bindOrder(order: Order) {
            orderItemBinding.orderId.text ="Order id: " + order.orderId.toString()
            orderItemBinding.carName.text ="Car: " + rentalDao.getCarById(order.carId)[0].carManufacturer + "-" + rentalDao.getCarById(order.carId)[0].carModel
            orderItemBinding.estimate.text ="Rental duration: " + order.rentalDuration.toString() + " months"
            orderItemBinding.priceSummary.text ="Rental price: " + order.rentalPrice.toString() + "$"
            orderItemBinding.orderDate.text ="Date of rental: " + order.rentalDate
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val orderItemBinding: OrderItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.order_item, parent, false)
        val rentalDao = RentalDatabase.getDatabase(parent.context).rentalDao()
        return OrderHolder(orderItemBinding.root, orderItemBinding, rentalDao)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
       holder.bindOrder(orders[position])
    }

    override fun getItemCount(): Int =orders.size

}