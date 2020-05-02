package com.demo.app.ncov2020.map.commands

import com.demo.app.ncov2020.map.MapView

class MapCommandProcessor(private val mapCommandExecutable: MapCommandExecutable) {
    private val queue = ArrayList<MapCommand>()

    fun addToQueue(mapCommand: MapCommand) : MapCommandProcessor = apply {   queue.add(mapCommand) }

    fun processCommand() : MapCommandProcessor =  apply {
        queue.forEach{ it.execute(mapCommandExecutable)}
        queue.clear()
    }
}