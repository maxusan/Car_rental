package com.bananadolphin.carrental.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bananadolphin.carrental.R
import com.bananadolphin.carrental.data.Car
import com.bananadolphin.carrental.data.CarListener
import com.bananadolphin.carrental.databinding.CarItemBinding
import java.util.*

class CarsAdapter(val param: CarListener, val mode: Int?) : ListAdapter<Car, CarsAdapter.CarsHolder>(
    callback
) {
    class CarsHolder(
        itemView: View,
        val carItemBinding: CarItemBinding,
        val param: CarListener,
        val mode: Int?
    ): RecyclerView.ViewHolder(itemView) {
        fun bind(car: Car?) {

            itemView.setBackgroundColor( Color.argb(255, Random().nextInt(256), Random().nextInt(256),  Random().nextInt(256)))

            carItemBinding.car = car
            if(mode == 2){
                carItemBinding.deleteCar.visibility = View.GONE
                carItemBinding.updateCar.visibility = View.GONE
            }
            itemView.setOnClickListener {
                if (car != null) {
                    param.carClick(car)
                }
            }
            carItemBinding.deleteCar.setOnClickListener {
                param.deleteCar(car!!)
            }
            carItemBinding.updateCar.setOnClickListener {
                param.updateCar(car!!)
            }
        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsHolder {
        val carItemBinding: CarItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.car_item, parent, false)
        return CarsHolder(carItemBinding.root, carItemBinding, param, mode)
    }

    override fun onBindViewHolder(holder: CarsHolder, position: Int) {
        holder.bind(currentList[position])
    }


}
var callback = object: DiffUtil.ItemCallback<Car>(){
    override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean {
        return oldItem.carId == newItem.carId
    }

    override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean {
        return oldItem == newItem

    }

}