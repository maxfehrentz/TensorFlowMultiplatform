package domain.useCases

import domain.data.Person

interface ContinousData {
    fun emit(): Person
}