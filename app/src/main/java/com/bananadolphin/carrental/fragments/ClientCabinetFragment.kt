package com.bananadolphin.carrental.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bananadolphin.carrental.adapters.OrdersAdapter
import com.bananadolphin.carrental.R
import com.bananadolphin.carrental.data.Order
import com.bananadolphin.carrental.data.RentalDao
import com.bananadolphin.carrental.data.RentalDatabase
import com.bananadolphin.carrental.databinding.FragmentClientCabinetBinding

class ClientCabinetFragment : Fragment() {

    lateinit var fragmentClientCabinetBinding: FragmentClientCabinetBinding
    lateinit var ordersRecycler: RecyclerView
    lateinit var ordersAdapter: OrdersAdapter
    lateinit var ordersList: MutableList<Order>
    lateinit var rentalDao: RentalDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentClientCabinetBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_client_cabinet, container, false)
       rentalDao = RentalDatabase.getDatabase(requireContext()).rentalDao()
        ordersList = rentalDao.getAllOrders() as MutableList<Order>
        ordersRecycler = fragmentClientCabinetBinding.clientCabinetOrdersRecycler
        ordersAdapter = OrdersAdapter(ordersList)
        ordersRecycler.adapter = ordersAdapter
        return fragmentClientCabinetBinding.root
    }


}