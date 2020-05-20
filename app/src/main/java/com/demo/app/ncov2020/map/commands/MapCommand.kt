package com.demo.app.ncov2020.map.commands

interface MapCommand {
        fun execute(mapCommandExecutable: MapCommandExecutable)
}