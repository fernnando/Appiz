package co.fddittmar.appiz.helper

import android.support.v4.app.Fragment
import co.fddittmar.appiz.R
import co.fddittmar.appiz.view.AboutFragment
import co.fddittmar.appiz.view.NewPlaceFragment
import co.fddittmar.appiz.view.PlacesFragment

enum class BottomNavigationPosition(val position: Int, val id: Int) {
    PLACES(0, R.id.places),
    ADDPLACE(1, R.id.add_place),
    ABOUT(2, R.id.about),
}

fun findNavigationPositionById(id: Int): BottomNavigationPosition = when (id) {
    BottomNavigationPosition.PLACES.id -> BottomNavigationPosition.PLACES
    BottomNavigationPosition.ADDPLACE.id -> BottomNavigationPosition.ADDPLACE
    BottomNavigationPosition.ABOUT.id -> BottomNavigationPosition.ABOUT
    else -> BottomNavigationPosition.PLACES
}

fun BottomNavigationPosition.createFragment(): Fragment = when (this) {
    BottomNavigationPosition.PLACES -> PlacesFragment.newInstance()
    BottomNavigationPosition.ADDPLACE -> NewPlaceFragment.newInstance()
    BottomNavigationPosition.ABOUT -> AboutFragment.newInstance()
}

fun BottomNavigationPosition.getTag(): String = when (this) {
    BottomNavigationPosition.PLACES -> PlacesFragment.TAG
    BottomNavigationPosition.ADDPLACE -> NewPlaceFragment.TAG
    BottomNavigationPosition.ABOUT -> AboutFragment.TAG
}