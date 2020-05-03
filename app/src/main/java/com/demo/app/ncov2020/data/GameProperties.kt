package com.demo.app.ncov2020.data

import com.demo.app.ncov2020.logic.Abilities.HandlerAntibiotics1
import com.demo.app.ncov2020.logic.Disease.*
import com.demo.app.ncov2020.logic.Transsmission.HandlerAIR
import com.demo.app.ncov2020.logic.Transsmission.HandlerGround
import com.demo.app.ncov2020.logic.Transsmission.HandlerWater

object GameProperties {
     val abilityMap = HashMap<String, Ability>()
     val transmissionMap = HashMap<String, Transmission>()
     val symptomMap = HashMap<String, Symptom>()

    init {
        symptomMap["pnevmonia"] = Symptom("Pnevmonia", "Hard to breathe", 2.0, 4, 0.3)
        symptomMap["cough"] = Symptom("Cough", "A-a-a-pchi", 2.0, 4, 0.0)
        abilityMap["antibiotics"] = Ability("Antibiotics1", "Can survive Level1 antibiotics", TypeAbility.ANTIBIOTICS1, HandlerAntibiotics1())
        transmissionMap["plains"] = Transmission("Plains transmission", "You will be able to infect by plains", TypeTrans.AIR, HandlerAIR())
        transmissionMap["tourist"] = Transmission("Tourist transmission", "You will be able to infect by tourists", TypeTrans.GROUND, HandlerGround())
        transmissionMap["ship"] = Transmission("Ship transmission", "You will be able to infect by ships", TypeTrans.WATER, HandlerWater())

    }
}